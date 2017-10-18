package edu.wpi.first.wpilibj.templates;

public class Barrel {
    
    private SingleSideSpike spike;
    private int id;
    
    private class BarrelState{
        public int state = 0;
    }
    
    public Barrel(int id, SingleSideSpike spike){
        this.id = id;
        this.spike = spike;
    }
}
