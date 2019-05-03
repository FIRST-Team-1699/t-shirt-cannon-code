package com.team1699.wrist;

public class BarrelWrist implements WristLoop {

    double goal_ = 0.0;
    private int state = State.UNINITIALIZED;
    double offset = 0.0;
    double lastError = 0.0;
    double filteredGoal = 0.0;

    @Override
    public double update(double encoder, boolean limitTriggered, boolean enabled) {

        filteredGoal = goal_;

        double position = encoder + offset;
        switch (state){
            case 0: //Uninitialized
                if (enabled){
                    state = State.ZEROING;
                    filteredGoal = position;
                }
                break;
            case 1: //Zeroing
                filteredGoal -= kDt * kZeroingVelocity;
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

        final double maxVoltage = state == State.RUNNING ? kMaxVoltage : kMaxZeroingVoltage;

        if(voltage >= maxVoltage){
            return maxVoltage;
        }else if(voltage <= -maxVoltage){
            return -maxVoltage;
        }else{
            return voltage;
        }
    }

    @Override
    public void setGoal(double goal) {
        if(goal > kMaxHeight) {
            goal_ = kMaxHeight;
        }else if(goal < kMinHeight){
            goal_ = kMinHeight;
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
