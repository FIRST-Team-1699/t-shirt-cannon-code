package com.frc1699.Barrel;

import com.frc1699.utils.SingleSideSpike;

public class Barrel {
    
    //Instance Vars
    private SingleSideSpike spike;
    private int id;
    private boolean fired;
    
    public Barrel(int id, SingleSideSpike spike){
        this.id = id;
        this.spike = spike;
        fired = false;
    }
    
    //Fires barrel
    public void fire(){
        spike.trigger();
        fired = true;
    }
    
    //Getters/Setters
    public void setFired(boolean fired){
        this.fired = fired;
    }
    
    //Returns if the barrel has been fired
    public boolean isFired(){
        return fired;
    }
    
    public int getId(){
        return id;
    }
}
