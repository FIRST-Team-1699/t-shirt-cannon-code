package edu.wpi.first.wpilibj.templates;

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
    
    public boolean isFired(){
        return fired;
    }
}
