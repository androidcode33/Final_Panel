package com.basasa.incrs.posts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.basasa.incrs.Message.DataBaseHelper;
import com.basasa.incrs.Message.MyCustomAdapter;
import com.basasa.incrs.NDS.ChatThread;
import com.basasa.incrs.R;
import com.basasa.incrs.Recyclerview.DividerItemDecoration;
import com.basasa.incrs.Recyclerview.RecyclerTouchListener;
import com.basasa.incrs.Recyclerview.Student_Model;
import com.basasa.incrs.Recyclerview.Student_PostAdapter;



import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by basasagerald on 2/28/2017.
 */

public class Student_Post extends Fragment  {
    private List<Student_Model> postList;
    private RecyclerView recyclerView;
    private Student_PostAdapter mAdapter;
    private SQLiteDatabase storeData;
    FloatingActionButton fab;
   int port;
    int check=0;
    InetAddress host;
    //dialog
    private ArrayList<String> arrayList;
    private MyCustomAdapter adapter;
    DataBaseHelper dataBaseHelper2;
    private ChatThread chat;
    //ProgressDialog dialog=null;
    private static final int REQUEST_CODE = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.student_posts, container, false);
        fab=(FloatingActionButton)view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             DialogBox();
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView)view.findViewById(R.id.student_posts);
        postList = new ArrayList<Student_Model>();
        mAdapter = new Student_PostAdapter(postList);
        //dialog = new ProgressDialog(getContext());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Student_Model post = postList.get(position);

//                Intent intent=new Intent(getActivity(), );
//                intent.putExtra("message", post.getQuestion());
//                intent.putExtra("id",post.getId());
//                startActivity(intent);

                DialogBoxAnswer(post.getId(),post.getQuestion());

            }
            @Override
            public void onLongClick(View view, int position) {
                Student_Model post = postList.get(position);
            }
        }));
        //dialog
        dataBaseHelper2=new DataBaseHelper(getContext());

        //fetch();
        getDataFromDB1();

        DataBaseHelper dataBaseHelper=new DataBaseHelper(getContext());
        dataBaseHelper.fetch();
        threadconnection();

    }

    void DialogBox(){
        new connectTask().execute("");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_question_dialog, null);
        final EditText question = (EditText) dialogView.findViewById(R.id.questions);
        final ListView mList =(ListView)dialogView.findViewById(R.id.list_questions);

        arrayList = new ArrayList<String>();
        adapter = new MyCustomAdapter(getContext(), arrayList);

        mList.setAdapter(adapter);
        builder.setView(dialogView);
        builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             sendMessage(question.getText().toString().trim());
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    void DialogBoxAnswer(int id, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.answer_posts_dialog, null);
        final EditText question = (EditText) dialogView.findViewById(R.id.questions);
        final LinearLayout open = (LinearLayout) dialogView.findViewById(R.id.openended);
        final LinearLayout footer = (LinearLayout) dialogView.findViewById(R.id.footer);

        builder.setView(dialogView);
        builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendMessage(question.getText().toString().trim());
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }
    public void StoreDb(String messageToSave){
        if (messageToSave.contains("ENTER")|| messageToSave.contains("/")|| messageToSave.startsWith("*")){
            return;
        }
        else if(messageToSave.trim().startsWith("Lecturer")) {
            if (messageToSave.contains("-")){
                String [] saveMessage=messageToSave.split(":");
                dataBaseHelper2.insertIntoDB(saveMessage[saveMessage.length-1].trim(),"C","lecturer");

            }else{
                String [] saveMessage=messageToSave.split(":");
                dataBaseHelper2.insertIntoDB(saveMessage[saveMessage.length-1].trim(),"O","lecturer");
            }
        }
        else {
            if (messageToSave.contains("#")){
                String [] saveMessage=messageToSave.split(":");
                String QandA =saveMessage[saveMessage.length-1].trim();
                String [] Response= QandA.split("#");
                dataBaseHelper2.insertresponseIntoDB(1, Response[Response.length-1].trim());

            }else {
                String [] saveMessage=messageToSave.split(":");
                dataBaseHelper2.insertIntoDB(saveMessage[saveMessage.length-1].trim(),"O","student");
            }

        }
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
            chat.run();

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
    public void sendMessage(String message){


        //add the text in the arrayList
        //arrayList.add("c: " + message);

        //sends the message to the server
        if (chat != null) {
            chat.sendMsg(message);
        }

        //refresh the list
        adapter.notifyDataSetChanged();

    }
    public void threadconnection(){
        check=1;
        ConnectivityManager connManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
       // mWifi.isConnected()
        if (check==1) {
          t.run();
//            asyc= new MyAsyncTask();
//            asyc.execute();

        }
        else{
            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            startActivity(intent);
        }

    }
    final Handler h = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 0){
                System.out.print("yeeeeeeeeeeeeeeeeeeeeeeeees");
            }else{
                System.out.print("errooooo");
            }
        }
    };

    Thread t = new Thread() {
        @Override
        public void run(){

            boolean succeed=false;
            if(succeed){
                //we can't update the UI from here so we'll signal our handler and it will do it for us.
                h.sendEmptyMessage(0);
            }else{
                h.sendEmptyMessage(1);
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        getDataFromDB1();
        //threadconnection();
    }
    @Override
    public void onPause() {
        super.onPause();
//        getDataFromDB1();
//        threadconnection();
    }
    public void getDataFromDB1(){
        postList.clear();
        String sender ="student";
        String query = "select ID, messages from message where Sender='"+sender+"';";
        String count=("SELECT COUNT(ID) from responses");
        storeData = getContext().openOrCreateDatabase("MessageDB",MODE_PRIVATE,null);
        try {
            storeData.execSQL("CREATE TABLE IF NOT EXISTS message(ID INTEGER PRIMARY KEY AUTOINCREMENT, messages TEXT,  Type TEXT CHECK(Type IN('O','C')) NOT NULL DEFAULT 'O', Sender TEXT CHECK(Sender IN('student','lecturer')) NOT NULL DEFAULT 'student');");
            storeData.execSQL("CREATE TABLE IF NOT EXISTS responses(  ID INTEGER PRIMARY KEY AUTOINCREMENT,MessageID INTEGER, Responses TEXT);");
            Cursor cursor = storeData.rawQuery(query, null);
            Cursor counter = storeData.rawQuery(count, null);
            cursor.moveToFirst();
            do {
                Student_Model model = new Student_Model();
                model.setId(cursor.getInt(0));
                model.setQuestion(cursor.getString(1));

                postList.add(model);
            } while (cursor.moveToNext());

        }catch (Exception e){
            System.out.println(e);

        }
        mAdapter.notifyDataSetChanged();
//         if (counter.moveToFirst()){
//             do {
//             System.out.println("The number is zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz :"+counter.getInt(0));
//             }while (counter.moveToNext());
//         }
        Log.d("message data", postList.toString());

    }
    public void fetch(){
        String query = "select * from message";
        SQLiteDatabase db = getContext().openOrCreateDatabase("MessageDB",MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        do {
            System.out.println("The message is :"+cursor.getString(3));
        }while (cursor.moveToNext());


        db.close();
    }

}
