package com.basasa.incrs.AnswerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.basasa.incrs.R;
import com.basasa.incrs.Recyclerview.DividerItemDecoration;
import com.basasa.incrs.Recyclerview.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class View_Post_Answers extends AppCompatActivity {

    private List<Answers_Model> postList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Answer_PostAdapter mAdapter;
    private SQLiteDatabase storeData;
    String message;
    int id;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__post__answers);

        recyclerView = (RecyclerView)findViewById(R.id.answers);

        mAdapter = new Answer_PostAdapter(postList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        txt=(TextView)findViewById(R.id.questionz);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));
           id=getIntent().getIntExtra("id",0);
          txt.setText(getIntent().getStringExtra("message"));

        getDataFromDB1();
    }
    @Override
    public void onResume() {
        super.onResume();
        getDataFromDB1();

    }

    public void getDataFromDB1(){
        postList.clear();
        String query = "select  Responses from responses where MessageID='"+id+"';";
        String count=("SELECT COUNT(ID) from responses");
        storeData = openOrCreateDatabase("MessageDB",MODE_PRIVATE,null);
        try {
           Cursor cursor = storeData.rawQuery(query, null);
            Cursor counter = storeData.rawQuery(count, null);
            cursor.moveToFirst();
            do {
                Answers_Model model = new Answers_Model();
                model.setAnswer(cursor.getString(0));

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
}
