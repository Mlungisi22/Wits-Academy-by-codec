package com.example.login;

public class User {

    public String password, email;
    
    public User (){
    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }

}
