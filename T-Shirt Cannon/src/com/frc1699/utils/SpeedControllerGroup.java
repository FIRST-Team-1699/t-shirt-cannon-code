package com.frc1699.utils;

import edu.wpi.first.wpilibj.SpeedController;

public class SpeedControllerGroup {

    private final SpeedController[] controllers;
    private final int masterID;

    //masterID is the index in the controller array
    public SpeedControllerGroup(final int masterID, final SpeedController[] controllers){
        this.controllers = controllers;
        this.masterID = masterID;
    }

    public void set(final double voltage){
        for(int i = 0; i < controllers.length; i++){
            controllers[i].set(voltage);
        }
    }

    public double get(){
        return controllers[masterID].get();
    }
}
