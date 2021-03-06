package com.team1699.wrist;

public interface WristLoop {

    //Stores the potential states of the wrist since we can't use enums
    class State {
        static int UNINITIALIZED = 0;
        static int ZEROING = 1;
        static int RUNNING = 2;
        static int ESTOPPED = 3;
    }

    double kDt = 0.05;

    //Zeroing velocity in rpm
    double kZeroingAVelocity = 1.0;

    //Max Angle
    double kMaxAngle = 60.0;

    //Min Angle
    double kMinAngle = 0.0;

    //Max voltage to be applied
    double kMaxVoltage = 12.0;

    //Max voltage when zeroing
    double kMaxZeroingVoltage = 4.0;

    //Control loop constants
    double Kp = 3.0;
    double Kv = 10.0;

    //Returns voltage that needs to be applied to the wrist motor
    double update(double encoder, boolean limitTriggered, boolean enabled);

    //Sets the goal
    void setGoal(double goal);
}