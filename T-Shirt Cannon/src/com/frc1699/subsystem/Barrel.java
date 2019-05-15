package com.frc1699.subsystem;

import com.frc1699.utils.SingleSideSpike;

public class Barrel {
    
    //Instance Vars
    private SingleSideSpike spike;
    private int id;
    private boolean fired;

    private double systemPressure = 0.0;

    public Barrel(int id, SingleSideSpike spike){
        this.id = id;
        this.spike = spike;
        fired = false;
    }

    //Updates barrel state
    public void update(final double pressure){
        systemPressure = pressure;
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