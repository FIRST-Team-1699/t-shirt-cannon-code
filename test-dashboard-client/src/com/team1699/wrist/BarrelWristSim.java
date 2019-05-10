package com.team1699.wrist;

import com.team1699.utils.MotorConstants;

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
    //Rotational Inertia of Barrel Assembly in Kilograms * meters * meters
    static final double kInertia = 0.04441392;
    //Gear Ratio
    static final double kG = 100.0 * 60/12;
    //Radius of pulley
    static final double kr = 3.5825;

    //Sample time
    public static final double kDt = 0.010;

    // V = I * R + ω / Kv
    // torque = Kt * I

    BarrelWristSim(){}

    double position = 0.1;
    double velocity = 0.0;
    double offset = 0.1;

    double getAcceleration(final double voltage){
        return (voltage - (velocity / Kv)) * ((kG * Kt) / (kInertia * kResistance));
    }

    boolean limitTriggered(){
        return position > -0.04 && position < 0.0;
    }

    double encoder(){
        return position + offset;
    }
}