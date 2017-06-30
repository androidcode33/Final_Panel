package com.basasa.incrs.Recyclerview_Lect;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.basasa.incrs.R;

import java.util.List;

/**
 * Created by basasagerald on 3/2/2017.
 */

public class Lecturer_PostAdapter extends RecyclerView.Adapter<Lecturer_PostAdapter.MyViewHolder> {

    private List<Lecturer_Model> postsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  question, answer;
        public ImageView postIcon;

        public MyViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.question);
            answer = (TextView) view.findViewById(R.id.answer);
            postIcon = (ImageView) view.findViewById(R.id.post_icon);
        }
    }

    public Lecturer_PostAdapter(List<Lecturer_Model> postsList) {
        this.postsList = postsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_lecturer, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Lecturer_Model post = postsList.get(position);
        holder.question.setText(post.getQuestion());
        holder.answer.setText("Answer");

        if(post.getQuestion().startsWith("a") || post.getQuestion().startsWith("A"))
            holder.postIcon.setImageResource(R.mipmap.a);
        else if (post.getQuestion().startsWith("b") || post.getQuestion().startsWith("B"))
            holder.postIcon.setImageResource(R.mipmap.b);
        else if (post.getQuestion().startsWith("c") || post.getQuestion().startsWith("C"))
            holder.postIcon.setImageResource(R.mipmap.c);
        else if (post.getQuestion().startsWith("d") || post.getQuestion().startsWith("D"))
            holder.postIcon.setImageResource(R.mipmap.d);

        else if (post.getQuestion().startsWith("e") || post.getQuestion().startsWith("E"))
            holder.postIcon.setImageResource(R.mipmap.e);
        else if (post.getQuestion().startsWith("f") || post.getQuestion().startsWith("F"))
            holder.postIcon.setImageResource(R.mipmap.f);
        else if (post.getQuestion().startsWith("g") || post.getQuestion().startsWith("G"))
            holder.postIcon.setImageResource(R.mipmap.g);
        else if (post.getQuestion().startsWith("h") || post.getQuestion().startsWith("H"))
            holder.postIcon.setImageResource(R.mipmap.h);
        else if (post.getQuestion().startsWith("i") || post.getQuestion().startsWith("I"))
            holder.postIcon.setImageResource(R.mipmap.i);
        else if (post.getQuestion().startsWith("j") || post.getQuestion().startsWith("J"))
            holder.postIcon.setImageResource(R.mipmap.j);
        else if (post.getQuestion().startsWith("k") || post.getQuestion().startsWith("K"))
            holder.postIcon.setImageResource(R.mipmap.k);
        else if (post.getQuestion().startsWith("l") || post.getQuestion().startsWith("L"))
            holder.postIcon.setImageResource(R.mipmap.l);
        else if (post.getQuestion().startsWith("m") || post.getQuestion().startsWith("M"))
            holder.postIcon.setImageResource(R.mipmap.m);
        else if (post.getQuestion().startsWith("n") || post.getQuestion().startsWith("N"))
            holder.postIcon.setImageResource(R.mipmap.n);
        else if (post.getQuestion().startsWith("o") || post.getQuestion().startsWith("O"))
            holder.postIcon.setImageResource(R.mipmap.o);
        else if (post.getQuestion().startsWith("p") || post.getQuestion().startsWith("P"))
            holder.postIcon.setImageResource(R.mipmap.p);
        else if (post.getQuestion().startsWith("q") || post.getQuestion().startsWith("Q"))
            holder.postIcon.setImageResource(R.mipmap.q);
        else if (post.getQuestion().startsWith("r") || post.getQuestion().startsWith("R"))
            holder.postIcon.setImageResource(R.mipmap.r);
        else if (post.getQuestion().startsWith("s") || post.getQuestion().startsWith("S"))
            holder.postIcon.setImageResource(R.mipmap.s);
        else if (post.getQuestion().startsWith("t") || post.getQuestion().startsWith("T"))
            holder.postIcon.setImageResource(R.mipmap.t);
        else if (post.getQuestion().startsWith("u") || post.getQuestion().startsWith("U"))
            holder.postIcon.setImageResource(R.mipmap.u);
        else if (post.getQuestion().startsWith("v") || post.getQuestion().startsWith("V"))
            holder.postIcon.setImageResource(R.mipmap.v);
        else if (post.getQuestion().startsWith("w") || post.getQuestion().startsWith("W"))
            holder.postIcon.setImageResource(R.mipmap.w);
        else if (post.getQuestion().startsWith("x") || post.getQuestion().startsWith("X"))
            holder.postIcon.setImageResource(R.mipmap.x);
        else if (post.getQuestion().startsWith("y") || post.getQuestion().startsWith("Y"))
            holder.postIcon.setImageResource(R.mipmap.y);
        else if (post.getQuestion().startsWith("z") || post.getQuestion().startsWith("Z"))
            holder.postIcon.setImageResource(R.mipmap.z);
        else
            holder.postIcon.setImageResource(R.mipmap.a);
    }
    @Override
    public int getItemCount() {
        return postsList.size();
    }

}
