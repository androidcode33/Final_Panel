package com.basasa.incrs.Recyclerview;

/**
 * Created by basasagerald on 3/1/2017.
 */

public class Student_Model {

    private String question;
    private int id;
    private String response;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;
    public Student_Model() {

    }

    public Student_Model(int id, String question, String response,int count) {
        this.question = question;
        this.id=id;
        this.response=response;
        this.count=count;
    }

    public String getQuestion() {
        return question;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    public void setQuestion(String question) {
        this.question = question;
    }




}
