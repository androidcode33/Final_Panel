package com.basasa.incrs.posts;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.basasa.incrs.Message.DataBaseHelper;
import com.basasa.incrs.Message.MyCustomAdapter;
import com.basasa.incrs.NDS.ChatThread;
import com.basasa.incrs.R;

import java.util.ArrayList;

/**
 * Created by basasagerald on 3/1/2017.
 */

public class Post_Question extends AppCompatActivity {

    private ListView mList;
    private ArrayList<String> arrayList;
    private MyCustomAdapter mAdapter;

    EditText editText;
    DataBaseHelper dataBaseHelper;
    private ChatThread chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_question);

        arrayList = new ArrayList<String>();

        editText = (EditText) findViewById(R.id.editText);

        //relate the listView from java to the one created in xml
        mList = (ListView)findViewById(R.id.list);
        mAdapter = new MyCustomAdapter(this, arrayList);
        mList.setAdapter(mAdapter);

        dataBaseHelper=new DataBaseHelper(this);
        // connect to the server
        new connectTask().execute("");

    }
    public void StoreDb(String messageToSave){
//        if (messageToSave.contains("ENTER")|| messageToSave.contains("/")|| messageToSave.startsWith("*")){
//            return;
//        }
//        else if(messageToSave.trim().startsWith("Lecturer")) {
//            if (messageToSave.contains("-")){
//                String [] saveMessage=messageToSave.split(":");
//                dataBaseHelper.insertIntoDB(saveMessage[saveMessage.length-1].trim(),"C","lecturer");
//
//            }else{
//                String [] saveMessage=messageToSave.split(":");
//                dataBaseHelper.insertIntoDB(saveMessage[saveMessage.length-1].trim(),"O","lecturer");
//            }
//        }
//        else {
//            if (messageToSave.contains("#")){
//                String [] saveMessage=messageToSave.split(":");
//                String QandA =saveMessage[saveMessage.length-1].trim();
//                String [] Response= QandA.split("#");
//                dataBaseHelper.insertresponseIntoDB(1, Response[Response.length-1].trim());
//
//            }else {
//                String [] saveMessage=messageToSave.split(":");
//                dataBaseHelper.insertIntoDB(saveMessage[saveMessage.length-1].trim(),"O","student");
//            }
//
//        }
    }
    public class connectTask extends AsyncTask<String,String,ChatThread> {

        @Override
        protected ChatThread doInBackground(String... message) {

            //we create a chatclient object and
            chat = new ChatThread(new ChatThread.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            //chat.run();

            return null;
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            StoreDb(values[0]);
            //in the arrayList we add the messaged received from server
            arrayList.add(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            mAdapter.notifyDataSetChanged();
        }
    }
    public void sendMessage(){
        String message = editText.getText().toString();

        //add the text in the arrayList
        //arrayList.add("c: " + message);

        //sends the message to the server
        if (chat != null) {
            chat.sendMsg(message);
        }

        //refresh the list
        mAdapter.notifyDataSetChanged();
        editText.setText("");
    }
    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_send, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_send:
                sendMessage();
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}