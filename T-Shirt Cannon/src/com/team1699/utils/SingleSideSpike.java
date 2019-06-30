package com.team1699.utils;

import com.team1699.constants.Constants;
import edu.wpi.first.wpilibj.Relay;

public class SingleSideSpike { 
    
    //Used to activate one side of the spike since we use one spike to fire two solenoids
    
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
