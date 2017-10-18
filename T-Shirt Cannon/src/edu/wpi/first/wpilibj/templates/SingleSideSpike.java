package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Relay;

public class SingleSideSpike { 
    
    //Instance Vars
    private int id;
    private Relay r;
    private Relay.Value v;
    
    public SingleSideSpike(int id, Relay r){
        this.id = id;
        this.r = r;
        if(id % 2 == 0){
            v = Relay.Value.kForward;
        }else{
            v = Relay.Value.kReverse;
        }
    }
    
    //Triggers solenoid
    public void trigger(){
        r.set(v);
        try {
            Thread.sleep(Constants.sleepTime);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        r.set(Relay.Value.kOff);
    }
    
    //Returns ID
    public int getID(){
        return this.id;
    }
}
