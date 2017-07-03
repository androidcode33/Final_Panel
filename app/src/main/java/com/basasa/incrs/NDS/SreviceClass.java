package com.basasa.incrs.NDS;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.basasa.incrs.Message.DataBaseHelper;
import com.basasa.incrs.posts.Student_Post;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Don Quan on 7/2/2017.
 */

public class SreviceClass extends Service {
    @Nullable

    public static InetAddress SERVERIP;
    public static int SERVERPORT;
    PrintWriter out;
    Socket socket;
    BufferedReader in;
    Boolean mrun=false;
    private String serverMessage;
    DataBaseHelper dataBaseHelper2;
    Context mcontext;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        System.out.println("I am in Ibinder onBind method");
        return myBinder;
    }
    private final IBinder myBinder = new LocalBinder();
    public class LocalBinder extends Binder {
        public SreviceClass getService() {
            System.out.println("I am in Localbinder ");
            return SreviceClass.this;
        }
    }

    public static void setVariable(InetAddress dst,int port1){
        SERVERPORT= port1;
        SERVERIP=dst;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("I am in on create");
    }

    public void IsBoundable(){
        Toast.makeText(this,"I bind like butter", Toast.LENGTH_LONG).show();
    }

    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            System.out.println("in sendMessage lllllllllllllllllllllllllllllllllllllllllllllll"+message );
            out.println(message);
            out.flush();
        }
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        super.onStartCommand(intent, flags, startId);
        System.out.println("I am in on start");
        dataBaseHelper2=new DataBaseHelper(this);
        //  Toast.makeText(this,"Service created ...", Toast.LENGTH_LONG).show();
        Runnable connect = new connectSocket();
        new Thread(connect).start();

        return START_STICKY;
    }


    class connectSocket implements Runnable {

        @Override
        public void run() {
            mrun=true;
            try {
                //here you must put your computer's IP address.
                //serverAddr = InetAddress.getByName(SERVERIP.toString());
                Log.e("TCP Client", "C: Connecting...");
                //create a socket to make the connection with the server

                socket = new Socket(SERVERIP, SERVERPORT);
                try {
                    //send the message to the server
                    out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);

                    Log.e("TCP Client", "C: Sent.");

                    Log.e("TCP Client", "C: Done.");

                    // receive the message which the server sends back
                    in = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));

                    while (mrun) {
                        serverMessage = in.readLine();

                        if (serverMessage != null) {
                            Log.e("RESPONSE FROM SERVER", "S: Received Message: '"
                                    + serverMessage + "'");
                            // call the method messageReceived from MyActivity class
                            StoreDb(serverMessage);
                        }

                        serverMessage = null;
                    }



//                    if (serverMessage!=null){
//                        Student_Post student_post=new Student_Post();
//                        student_post.StoreDb(serverMessage);
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(mcontext)
//                            .setContentText("New post!")
//                            .setContentTitle("Attention!");
//                    NotificationManager manager = (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);
//                    manager.notify(0, builder.build()); //notifikacija
////omoguci stiskanje tipke "Call"
//                }
                }
                catch (Exception e) {

                    Log.e("TCP", "S: Error", e);

                }
            } catch (Exception e) {

                Log.e("TCP", "C: Error", e);

            }

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        socket = null;
    }

    public void StoreDb(String messageToSave){
        try {
            if (messageToSave.contains("ENTER") || messageToSave.contains("/") || messageToSave.startsWith("*")) {
                return;
            } else if (messageToSave.trim().contains("Lecturer")) {
                if (messageToSave.contains(">>")&& messageToSave.contains("-")) {
                    String[] saveMessage = messageToSave.split(">>");
                    dataBaseHelper2.insertIntoDB(Integer.parseInt(saveMessage[0]), saveMessage[2].trim(), "C", "lecturer", saveMessage[3], "lecturer");

                } else {
                    String[] saveMessage = messageToSave.split(">>");
                    dataBaseHelper2.insertIntoDB(Integer.parseInt(saveMessage[0]), saveMessage[2].trim(), "O", "lecturer", "", "lecturer");
                }
            } else if (messageToSave.contains("#")) {
                String[] saveMessage = messageToSave.split("#");
                dataBaseHelper2.insertresponseIntoDB(Integer.parseInt(saveMessage[0]), saveMessage[1].trim(), "");
            } else if (messageToSave.contains(">>")) {
                String[] saveMessage = messageToSave.split(">>");

                dataBaseHelper2.insertIntoDB(Integer.parseInt(saveMessage[0]), saveMessage[2].trim(), "O", saveMessage[1], " ", "student");
            }
        }catch (Exception ex){
            ex.getLocalizedMessage();
        }
    }

}
