package com.example.login;

public class model_reply_question {
    private String email, answer;

    model_reply_question(){}

    public model_reply_question(String email, String answer){
        this.email=email;
        this.answer =answer;
    }

    public String getEmail() {
        return email;
    }


    public String getAnswer() {
        return answer;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
