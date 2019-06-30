package com.frc1699.client;

import com.frc1699.utils.Queue;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class Client  implements Runnable{
    
    private static Client instance;
    
    public static Client getInstance(){
        if(instance == null){
            instance = new Client();
        }
        return instance;
    }
    
    private StreamConnection socket;
    private InputStream in;
    private OutputStream out;
    
    private final Queue writeQueue;
    
    private final String host = "10.16.00.11";
    private final int port = 12345;
    
    private Thread thread;
    private boolean running = false;
    
    private Client(){
        writeQueue = new Queue();
        
        try{
            socket = (StreamConnection) Connector.open("socket://" + host + ":" + port, Connector.READ_WRITE);
            
            String request = "GET / HTTP/1.0\n\n";

            out = socket.openOutputStream();
            out.write(request.getBytes());

            // Read the server's reply, up to a maximum
            // of 128 bytes.
            in = socket.openInputStream();
            final int MAX_LENGTH = 128;
            byte[] buf = new byte[MAX_LENGTH];
            int total = 0;
            while (total < MAX_LENGTH) {
                int count = in.read(buf, total, MAX_LENGTH - total);
                if (count < 0) {
                    break;
                }
                total += count;
            }
            String reply = new String(buf, 0, total);;
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            // Close open streams and the socket
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException ex1) {
            }
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException ex1) {
            }
            try {
                if (socket != null) {
                    socket.close();
                    socket = null;
                }
            } catch (IOException ex1) {
            }
        }
    }
    
    public void run(){
        while(running){
            synchronized(writeQueue){
                if(writeQueue.peek() != null && writeQueue.peek() instanceof String){
                    String writeString = (String) writeQueue.peek();
                    try{
                        out.write(writeString.getBytes());
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }else if(!(writeQueue.poll() instanceof String)){ //TODO Check if breaks things
                    System.err.println("Invalid type in writeQueue to server");
                }
            }
        }
    }   

    public void addMessage(final String message){
        //Add Message to queue so it can be sent to the server
        writeQueue.add(message);
    }

    public synchronized void start(){
        if(running)return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running)return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
