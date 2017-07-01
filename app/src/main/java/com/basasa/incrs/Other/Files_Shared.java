package com.basasa.incrs.Other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.basasa.incrs.R;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by basasagerald on 3/1/2017.
 */

public class Files_Shared extends AppCompatActivity {
    public final static int SOCKET_PORT = 13267;      // you may change this
    public final static String SERVER = "127.0.0.1";  // localhost
    public final static String
            FILE_TO_RECEIVED = "c:/temp/source-downloaded.pdf";  // you may change this, I give a
    // different name because i don't want to
    // overwrite the one used by server...

    public final static int FILE_SIZE = 6022386; // file size temporary hard coded
    // should bigger than the file to be downloaded
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.files_shared);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("File Shared");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            sock = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Connecting...");

            // receive file
            byte [] mybytearray  = new byte [FILE_SIZE];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(mybytearray, 0 , current);
            bos.flush();
            System.out.println("File " + FILE_TO_RECEIVED
                    + " downloaded (" + current + " bytes read)");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
              if (fos != null) try {
                fos.close();
                if (bos != null) bos.close();
                if (sock != null) sock.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public void onBackPressed() {
        // Write your code here

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

//    public class getFile extends AsyncTask<Void, String, Void>
//    {
//
//        ProgressDialog pB;
//        String hostAddress=null;
//        InetSocketAddress socketAddress;
//        Socket socket=null;
//        ServerSocket serverSocket=null;
//        int port=3001;
//        int SOCKET_TIMEOUT = 3000;
//
//        public getFile(String _hostAddress, Context context)
//        {
//            this.pB = new ProgressDialog(context);
//            this.pB.setTitle("");
//            this.pB.setMessage(null);
//            this.pB.setProgressNumberFormat(null);
//            this.pB.setProgressPercentFormat(null);
//            this.pB.setCancelable(false);
//            //Drawable d = getResources().getDrawable(R.drawable.x_greenprogress);
//            //pB.setProgressDrawable(d);
//            pB.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            hostAddress=_hostAddress;
//        }
//
//        @Override
//        protected void onPreExecute()
//        {
//            this.pB.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params)
//        {
//            try {
//                //sent ip
//                socketAddress  = new InetSocketAddress(this.hostAddress, port);
//                socket = new Socket();
//                socket.bind(null);
//                socket.connect(socketAddress, SOCKET_TIMEOUT);
//                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
//                //out.println(messsage);
//                out.flush();
//                out.close();
//                socket.close();
//                socket = null;
//
//                //receive file
//                socket = new Socket();
//                serverSocket = new ServerSocket(portal);
//                socket = serverSocket.accept();
//
//                final String name;
//                final String ext;
//
//                String[] tokens = filename.split("\\.(?=[^\\.]+$)");
//                name = tokens[0];
//                ext = tokens[1];
//
//                final File f = new File(Environment.getExternalStorageDirectory() + "/"
//                        + getPackageName() + "/" + name + System.currentTimeMillis()
//                        + "." + ext);
//
//
//                File dirs = new File(f.getParent());
//
//                if (!dirs.exists())
//                    dirs.mkdirs();
//
//                f.createNewFile();
//
//
//                InputStream is = socket.getInputStream();
//                FileOutputStream fos = new FileOutputStream(f);
//                BufferedOutputStream bos = new BufferedOutputStream(fos);
//                byte buf[] = new byte[1024];
//                while ((len = is.read(buf)) != -1)
//                {
//                    bos.write(buf, 0, len);
//                    bos.flush();
//                    fileSize+=len;
//                    // to update the progress bar just call:
//                    this.publishProgress("" + (int) ((fileSize * 100) / filelength));
//                }
//                is.close();
//                fos.close();
//                bos.close();
//                socket.close();
//                serverSocket.close();
//            }
//
//            @Override
//            protected void onProgressUpdate(String... progress)
//            {
//                this.pB.setProgress(Integer.parseInt(progress[0]));
//            }
//
//            @Override
//            protected void onPostExecute(Void result)
//            {
//                if (this.pB.isShowing())
//                    this.pB.dismiss();
//            }
//
//        }
}
