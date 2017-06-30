package com.basasa.incrs.AnswerView;

/**
 * Created by Don Quan on 6/30/2017.
 */

public class Answers_Model {
    public Answers_Model(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Answers_Model() {
    }

    private String answer;
}
