package com.example.login;

public class User {
    public String occupation,password,email;

    public User(){

    }

    public User(String occupation,String password,String email) {
        this.occupation = occupation;
        this.email = email;
        this.password = password;
    }
}
