package edu.wpi.first.wpilibj.templates;

//Queue to use for t-shirt cannon, written in java 4

import java.util.Vector;

public class CircularQueue {

    Vector queue = new Vector();

    public void addData(Object data){
        queue.addElement(data);
    }

    public Object get(){
        Object result;
        result = queue.elementAt(0);
        queue.removeElementAt(0);
        queue.addElement(result);
        return result;
    }

    public int getSize(){
        return queue.size();
    }
}
