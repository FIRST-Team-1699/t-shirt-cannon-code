package com.team1699.utils;

public class Message {

    private final String message;
    private String response;

    public Message(final String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
