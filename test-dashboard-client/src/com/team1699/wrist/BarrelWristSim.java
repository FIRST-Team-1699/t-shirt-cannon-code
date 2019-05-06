package com.team1699.wrist;

import static com.team1699.utils.MotorConstants.Motor775Pro.*;

//Code inspiration from: https://www.youtube.com/watch?v=uGtT8ojgSzg
public class BarrelWristSim {

    private static BarrelWristSim instance;

    public static BarrelWristSim getInstance() {
        if(instance == null){
            instance = new BarrelWristSim();
        }
        return instance;
    }

    //Distance from center of rotation in meters
    static final double cg = 0.0;
    //Mass of Barrel Assembly in Kilograms
    //static final double kMass = 13.6078; //TODO Change to actual mass
    static final double kMass = 20.0;
    //Gear Ratio
    static final double kG = 100.0 * 60/12; //TODO Check
    //Radius of pulley
    static final double kr = 0.25 * 0.0254 * 22.0 / Math.PI / 2.0; //TODO Check if needed

    //Sample time
    public static final double kDt = 0.010;

    // V = I * R + ω / Kv
    // torque = Kt * I

    BarrelWristSim(){}

    double position = 0.1;
    double velocity = 0.0;
    double offset = 0.1;

    double getAcceleration(final double voltage){
        return -Kt * kG * kG / (Kv * kResistance * kr * kr * kMass) * velocity + kG * Kt / (kResistance * kr * kMass) * voltage;
    }

    boolean limitTriggered(){
        return position > -0.04 && position < 0.0;
    }

    double encoder(){
        return position + offset;
    }
}