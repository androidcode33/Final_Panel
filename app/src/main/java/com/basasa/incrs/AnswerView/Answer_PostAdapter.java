package com.basasa.incrs.AnswerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.basasa.incrs.R;
import com.basasa.incrs.Recyclerview_Lect.Lecturer_Model;

import java.util.List;

/**
 * Created by basasagerald on 3/2/2017.
 */

public class Answer_PostAdapter extends RecyclerView.Adapter<Answer_PostAdapter.MyViewHolder> {

    private List<Answers_Model> postsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView answer;


        public MyViewHolder(View view) {
            super(view);
            ;
            answer = (TextView) view.findViewById(R.id.answerz);

        }
    }

    public Answer_PostAdapter(List<Answers_Model> postsList) {
        this.postsList = postsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_answers, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Answers_Model post = postsList.get(position);
        holder.answer.setText(post.getAnswer());

    }
    @Override
    public int getItemCount() {
        return postsList.size();
    }

}
