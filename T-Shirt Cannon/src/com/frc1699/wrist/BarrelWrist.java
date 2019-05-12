package com.frc1699.wrist;

public class BarrelWrist implements WristLoop {

    double goal_ = 0.0;
    private int state = State.UNINITIALIZED;
    double offset = 0.0;
    double lastError = 0.0;
    double filteredGoal = 0.0;

    public double update(double encoder, boolean limitTriggered, boolean enabled) {

        double position = encoder + offset;
        switch (state){
            case 0: //Uninitialized
                if (enabled){
                    state = State.ZEROING;
                    filteredGoal = position;
                }
                break;
            case 1: //Zeroing
                filteredGoal -= kDt * kZeroingAVelocity;
                if (limitTriggered) {
                    state = State.RUNNING;
                    offset = -encoder;
                    position = 0.0;
                }
                if (!enabled) {
                    state = State.UNINITIALIZED;
                }
                break;
            case 2: //Running
                filteredGoal = goal_;
                break;
            case 3: //EStopped

                break;
        }

        final double error = filteredGoal - position;
        final double vel = (error - lastError) / kDt;
        lastError = error;
        double voltage = Kp * error + Kv * vel;

        //System.out.println(String.format("G: %f E: %f P: %f V: %f", filteredGoal, error, position, voltage));

        final double maxVoltage = state == State.RUNNING ? kMaxVoltage : kMaxZeroingVoltage;

        if(voltage >= maxVoltage){
            return maxVoltage;
        }else if(voltage <= -maxVoltage){
            return -maxVoltage;
        }else{
            return voltage;
        }
    }

    public void setGoal(double goal) {
        if(goal > kMaxAngle) {
            goal_ = kMaxAngle;
        }else if(goal < kMinAngle){
            goal_ = kMinAngle;
        }else{
            goal_ = goal;
        }
    }

    public double getGoal() {
        return goal_;
    }

    public double getFilteredGoal() {
        return filteredGoal;
    }
}
