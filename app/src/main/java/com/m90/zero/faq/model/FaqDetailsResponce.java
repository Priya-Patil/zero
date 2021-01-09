package com.m90.zero.faq.model;

import com.google.gson.annotations.SerializedName;

public class FaqDetailsResponce {

    @SerializedName("id")
    public int id;

    @SerializedName("question")
    public String question;

 @SerializedName("answer")
    public String answer;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    @Override
    public String toString() {
        return "FaqResponce{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
