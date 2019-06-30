package com.team1699.utils;

//Queue to use for t-shirt cannon, written in java 4

import java.util.Vector;

public class CircularQueue {

    Vector queue = new Vector(); //Like an ArrayList

    public void addData(Object data){ //Adds data to array
        queue.addElement(data);
    }

    public Object get(){ //Returns data on bottom and puts that piece of data on the top
        Object result;
        result = queue.elementAt(0);
        queue.removeElementAt(0);
        queue.addElement(result);
        return result;
    }

    public int getSize(){ //Returns size of array
        return queue.size();
    }
}
