package com.basasa.incrs.posts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basasa.incrs.AnswerView.View_Post_Answers;
import com.basasa.incrs.NDS.SreviceClass;
import com.basasa.incrs.R;
import com.basasa.incrs.Recyclerview.DividerItemDecoration;
import com.basasa.incrs.Recyclerview.RecyclerTouchListener;
import com.basasa.incrs.Recyclerview_Lect.Lecturer_Model;
import com.basasa.incrs.Recyclerview_Lect.Lecturer_PostAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by basasagerald on 2/28/2017.
 */

public class Lecturer_Post extends Fragment implements View.OnClickListener{

    private List<Lecturer_Model> postList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Lecturer_PostAdapter mAdapter;
    private SQLiteDatabase storeData;
    private Handler handler=new Handler();
    private Runnable runnable;
    String s;
    int x;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lecturer_posts,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView)view.findViewById(R.id.lecturer_posts);

        mAdapter = new Lecturer_PostAdapter(postList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Lecturer_Model post = postList.get(position);
                DialogBoxAnswer(post.getId(),post.getQuestion());
            }

            @Override
            public void onLongClick(View view, int position) {
                Lecturer_Model post = postList.get(position);
                Intent intent=new Intent(getActivity(), View_Post_Answers.class);
                intent.putExtra("message", post.getQuestion());
                intent.putExtra("id",post.getId());
                startActivity(intent);
            }
        }));

        getDataFromDB1();

        runnable=new Runnable() {
            @Override
            public void run() {
                getDataFromDB1();
                handler.postDelayed(this,3000);
            }
        };
        runnable.run();

    }
    @Override
    public void onResume() {
        super.onResume();
        getDataFromDB1();

    }
    public void getDataFromDB1(){
        postList.clear();
        String sender ="lecturer";
        String query = "select MessageID, messages,Type from message where Sender='"+sender+"';";
        String count=("SELECT COUNT(ID) from responses");
        storeData = getContext().openOrCreateDatabase("MessageDB",MODE_PRIVATE,null);
        try {
           Cursor cursor = storeData.rawQuery(query, null);
            Cursor counter = storeData.rawQuery(count, null);
            cursor.moveToFirst();
            do {
                Lecturer_Model model = new Lecturer_Model();
                model.setId(cursor.getInt(0));
                model.setQuestion(cursor.getString(1));
                model.setType(cursor.getString(2));
                postList.add(model);
            } while (cursor.moveToNext());

        }catch (Exception e){
            System.out.println(e);

        }
        mAdapter.notifyDataSetChanged();
        Log.d("message data", postList.toString());

    }
    void DialogBoxAnswer( final int id, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.answer_posts_dialog, null);
        final TextView textView=(TextView)dialogView.findViewById(R.id.question);
        final EditText question = (EditText) dialogView.findViewById(R.id.questions);
        final LinearLayout open = (LinearLayout) dialogView.findViewById(R.id.openended);
        final LinearLayout footer = (LinearLayout) dialogView.findViewById(R.id.footer);
        footer.setVisibility(View.INVISIBLE);
        open.setVisibility(View.VISIBLE);
        builder.setView(dialogView);
        textView.setText(message);
        String query = "select  Options,Type from message where MessageID='"+id+"';";

        SQLiteDatabase db = getContext().openOrCreateDatabase("MessageDB",MODE_PRIVATE,null);
        try {
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            do {
               // int idz=cursor.getInt(0);
                String q =cursor.getString(0);
                String questiontype=cursor.getString(1);

                if (questiontype=="O"){
                    x=1;
                    footer.setVisibility(View.INVISIBLE);
                }else {
                    open.setVisibility(View.INVISIBLE);
                    x=2;
                }
                if (q.contains("-")){

                    RadioGroup rgp = (RadioGroup)dialogView. findViewById(R.id.radio_group);
                    rgp.setOrientation(LinearLayout.VERTICAL);
                    //String message="question#option1@option2@option3@option4@option5@option6";
                    String [] optionz=q.split("-");

                    for (int i = 0; i <= optionz.length; i++) {
                        RadioButton rbn = new RadioButton(getContext());
                        rbn.setId(i + 1000);
                        rbn.setText(optionz [optionz.length-i]);
                        rbn.setOnClickListener(this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT, 1f);
                        rbn.setLayoutParams(params);
                        rgp.addView(rbn);
                    }

                }else {
                    System.out.println("open ended question");
                }

            }while (cursor.moveToNext());
        }catch (Exception e){
            System.out.println(e);
        }
        builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Student_Post student_post=new Student_Post();
            if (x==1){
                student_post.sendMsg(id+"#"+question.getText().toString().trim());
                //student_post.sendMsg(id+"#"+question.getText().toString().trim());
            }
            else if (x==2){
                //student_post.sendMsg(id+"#"+s);
                student_post.sendMsg(id+"#"+s);
            }
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

    @Override
    public void onClick(View v) {
        try {
            s = ((RadioButton) v).getText().toString();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
