package com.example.login;

public class model_posting_question {
    private String email,heading,question;

    model_posting_question(){}

    public model_posting_question(String email, String heading, String question){
        this.email=email;
        this.heading=heading;
        this.question=question;
    }

    public String getEmail() {
        return email;
    }


    public String getQuestion() {
        return question;
    }

    public String getHeading() {
        return heading;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
