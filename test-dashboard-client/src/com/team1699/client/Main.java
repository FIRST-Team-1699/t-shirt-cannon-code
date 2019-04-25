package com.team1699.client;

import com.team1699.utils.Message;

public class Main {

    public static void main(String[] args){
        TestClient.getInstance();
        Message msg = new Message("3 1");
        TestClient.getInstance().addMsg(msg);
        System.out.println(msg.getResponse());
    }
}
