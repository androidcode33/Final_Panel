package com.basasa.incrs.NDS;

import com.basasa.incrs.posts.Student_Post;

import java.io.BufferedReader;

/**
 * Created by Don Quan on 7/2/2017.
 */

public class savethread extends Thread {

    BufferedReader in=null;
    String response;
   public savethread(BufferedReader in){
        this.in=in;
    }
    public void run(){

        try {

            while ((response=in.readLine())!=null){
                Student_Post student_post=new Student_Post();
                student_post.StoreDb(response);
            }
        }catch (Exception e){
            e.getLocalizedMessage();
        }
    }

}
