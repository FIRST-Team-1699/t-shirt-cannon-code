package edu.wpi.first.wpilibj.templates;

public class Barrel {
    
    private SingleSideSpike spike;
    private int id;
    
    public Barrel(int id, SingleSideSpike spike){
        this.id = id;
        this.spike = spike;
    }
    
    public void fire(){
        spike.trigger();
    }
}
