package com.basasa.incrs.NDS;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by basasagerald on 4/14/2017.
 */

public class ChatThread  {
    private static  int port;
    private static InetAddress host;
    PrintWriter out=null;
    BufferedReader in=null;
    private boolean mRun=false;

    private String serverMessage;
    private OnMessageReceived mMessageListener = null;

    public static void setVariable(InetAddress dst,int port1){
        port= port1;
        host=dst;
    }
    public ChatThread(OnMessageReceived listener) {
        mMessageListener = listener;

    }
    public void run() {
        mRun = true;

        //Getting Local address

//        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        try {

            Socket socket = new Socket(host, port);
            try {

                // send the message to the server
                out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);

                Log.e("TCP client", "C: Sent.");

                Log.e("TCP client", "C: Done.");

                // receive the message which the server sends back
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));

                // in this while the client listens for the messages sent by the
                // server
                while (mRun) {
                        serverMessage = in.readLine();

                        if (serverMessage != null && mMessageListener != null) {
                            Log.e("RESPONSE FROM SERVER", "S: Received Message: '"
                                    + serverMessage + "'");
                            // call the method messageReceived from MyActivity class
                            mMessageListener.messageReceived(serverMessage);
                        }

                        serverMessage = null;
                }

            } catch (Exception e) {

                Log.e("TCP", "S: Error", e);

            } finally {
                // the socket must be closed. It is not possible to reconnect to
                // this socket
                // after it is closed, which means a new socket instance has to
                // be created.
                //socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }}


    public void sendMsg(String msg){
        if (out != null && !out.checkError()) {
            out.println(msg);
            out.flush();
        }
    }

    public void disconnect(){
        mRun=false;
    }

    // Declare the interface. The method messageReceived(String message) will
    // must be implemented in the MyActivity
    // class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}