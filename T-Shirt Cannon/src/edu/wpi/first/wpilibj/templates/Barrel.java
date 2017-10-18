package edu.wpi.first.wpilibj.templates;

public class Barrel {
    
    private SingleSideSpike spike;
    private int id;
    private boolean fired;
    
    public Barrel(int id, SingleSideSpike spike){
        this.id = id;
        this.spike = spike;
        fired = false;
    }
    
    public void fire(){
        spike.trigger();
        fired = true;
    }
    
    public void setFired(boolean fired){
        this.fired = fired;
    }
    
    public boolean isFired(){
        return fired;
    }
}
