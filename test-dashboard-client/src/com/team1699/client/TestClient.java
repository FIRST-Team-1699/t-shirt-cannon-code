package com.team1699.client;

import com.team1699.utils.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Base64;
import java.util.PriorityQueue;
import java.util.Queue;

public class TestClient implements Runnable{

    private static TestClient instance;

    public static TestClient getInstance(){
        if(instance == null){
            instance = new TestClient();
        }
        return instance;
    }

    private Thread thread;
    private boolean running = false;
    private final int port;
    private final String host;

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    private final Queue<Message> writeQueue;

    private TestClient(){
        this.port = 12345;
        this.host = "localhost";

        this.writeQueue = new PriorityQueue<>();

        System.out.println("------------ Connecting to server ------------");
        try {
            socket = new Socket(host, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("------------ Connected ------------");
    }

    @Override
    public void run() {
        while (running){
            //TODO Add timeout to read
            //TODO Make sure we are reading full array
            if(!writeQueue.isEmpty()){
                Message msg = writeQueue.poll();
                byte[] writeMsg;
                writeMsg = msg.getMessage().getBytes();
                try {
                    out.write(writeMsg);
                    byte[] readMsg = new byte[128];
                    int inLength = in.read(readMsg);
                    msg.setResponse(Base64.getEncoder().encodeToString(readMsg)); //TODO Fix?
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addMsg(final Message msg){
        writeQueue.add(msg);
    }

    public void stopServer(){
        try {
            stop();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void start(){
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop(){
        if(!running){
            return;
        }
        running = false;
        try{
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
