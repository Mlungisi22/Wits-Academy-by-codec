package com.example.login;

public class post {
    String postMessage = "";
    post (String postMessage){
        this.postMessage = postMessage;
    }

    public post() {
    }

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }
}
