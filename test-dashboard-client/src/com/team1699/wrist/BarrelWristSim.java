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

    //Mass of Barrel Assembly in Kilograms
    static final double kMass = 17.19;
    //Gear Ratio
    static final double kG = 100.0 * 60/12;
    //Distance to Center of Gravity
    static final double kr = 7.165/2;

    //Sample time
    static final double kDt = 0.010;

    // V = I * R + ω / Kv
    // torque = Kt * I

    private BarrelWristSim(){}

    double angle = 5;
    double velocity = 0.0; //Angular Velocity
    double offset = 5;

    double getAcceleration(final double voltage){
        return -Kt * kG * kG / (Kv * kResistance * kr * kr * kMass) * velocity + kG * Kt / (kResistance * kr * kMass) * voltage;
    }

    boolean limitTriggered(){
        return angle > -0.04 && angle < 0.0;
    }

    double encoder(){
        return angle + offset;
    }
}
