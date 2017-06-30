package com.basasa.incrs.posts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.basasa.incrs.R;
import com.basasa.incrs.Recyclerview.DividerItemDecoration;
import com.basasa.incrs.Recyclerview.RecyclerTouchListener;
import com.basasa.incrs.Recyclerview_Lect.Lecturer_Model;
import com.basasa.incrs.Recyclerview_Lect.Lecturer_PostAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by basasagerald on 2/28/2017.
 */

public class Lecturer_Post extends Fragment {

    private List<Lecturer_Model> postList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Lecturer_PostAdapter mAdapter;

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
                if (post.equals(0)){

                }
            }

            @Override
            public void onLongClick(View view, int position) {
                Lecturer_Model post = postList.get(position);
                Toast.makeText(getActivity(), post.getQuestion() + " is selected!", Toast.LENGTH_SHORT).show();
            }
        }));

        //preparePostData();
    }

//    private void preparePostData() {
//        Lecturer_Model post = new Lecturer_Model("Health", "best description");
//        postList.add(post);
//
//        post = new Lecturer_Model("understood", "i have not understood that point 1");
//        postList.add(post);
//
//        post = new Lecturer_Model("last slide", "what does deny mean?");
//        postList.add(post);
//
//        post = new Lecturer_Model("tired", "its some students are tired");
//        postList.add(post);
//
//        post = new Lecturer_Model("feeed back", "feed back on our project");
//        postList.add(post);
//
//        post = new Lecturer_Model("deadline", "When is the deadline for course work");
//        postList.add(post);
//
//
//
//        post = new Lecturer_Model("security", "i have not understood that");
//        postList.add(post);
//
//        post = new Lecturer_Model("last slide", "how to hack");
//        postList.add(post);
//
//        post = new Lecturer_Model("papers", "Our test question papers");
//        postList.add(post);
//
//        mAdapter.notifyDataSetChanged();
//    }
}
