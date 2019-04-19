package com.frc1699.utils;

import java.util.Vector;

public class Queue {
    
    private final Vector queue;
    
    public Queue(){
        queue = new Vector();
    }
    
    public synchronized void add(Object e){
        queue.addElement(e);
    }
    
    //Removes object
    public synchronized Object poll(){
        Object returnElement = queue.elementAt(0);
        queue.removeElementAt(0);
        return returnElement;
    }
    
    //Does not remove object
    public synchronized Object peek(){
        return queue.elementAt(0);
    }
}
