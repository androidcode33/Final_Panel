package com.basasa.incrs.Other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.basasa.incrs.R;


/**
 * Created by basasagerald on 3/1/2017.
 */

public class Files_Shared extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.files_shared);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("File Shared");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
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
