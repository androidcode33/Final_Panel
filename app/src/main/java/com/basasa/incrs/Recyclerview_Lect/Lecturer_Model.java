package com.basasa.incrs.Recyclerview_Lect;

/**
 * Created by basasagerald on 3/2/2017.
 */

public class Lecturer_Model {

    private String question;
    private int id;
    private String response;
    private int count;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    private String Type;
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lecturer_Model() {

    }

    public Lecturer_Model( String question) {

        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
