package com.frc1699.utils.drive;

import com.frc1699.utils.Utils;

public class DriveHelper {
    
    //Joystick Constants
    private static final double kFWDDeadband = 0.02;
    private static final double kTurnDeadband = 0.02;
    
    //Drive Base Constants
    private static final double kWheelNonLinearity = 0.65;
    private static final double kNegInertiaScalar = 4.0;
    private static final double kSensitivity = 0.65;
    private static final double kQuickStopDeadband = 0.5;
    private static final double kQuickStopWeight = 0.1;
    private static final double kQuickStopScalar = 5.0;
    
    private static double oldTurn = 0.0;
    private static double quickStopAccumulator = 0.0;
    private static double negInertiaAccumulator = 0.0;

    public static DriveSignal calcTankDrive(double fwd, double turn, final boolean quickTurn){
        turn = checkDeadband(turn, kTurnDeadband);
        fwd = checkDeadband(fwd, kFWDDeadband);
        
        double negInertia = turn - oldTurn;
        oldTurn = turn;
        
        final double denom = Math.sin(Math.PI / 2.0 * kWheelNonLinearity);
        //Scale the wheel
        turn = Math.sin(Math.PI / 2.0 * kWheelNonLinearity * turn) / denom;
        turn = Math.sin(Math.PI / 2.0 * kWheelNonLinearity * turn) / denom;
        
        double portOut, starOut, overPower;
        
        double angularPower, linearPower;
        
        double negInertiaPower = negInertia * kNegInertiaScalar;
        negInertiaAccumulator += negInertiaPower;
        
        turn += negInertiaAccumulator;
        if(negInertiaAccumulator > 1){
            negInertiaAccumulator = -1;
        }else if(negInertiaAccumulator < -1){
            negInertiaAccumulator += 1;
        }else{
            negInertiaAccumulator = 0;
        }
        linearPower = fwd;
        
        if(quickTurn){
            if(Math.abs(linearPower) < kQuickStopDeadband){
                quickStopAccumulator = (1 - kQuickStopWeight) * quickStopAccumulator + kQuickStopWeight * Utils.limit(turn, 1.0) * kQuickStopScalar;
            }
            overPower = 1.0;
            angularPower = turn;
        }else{
            overPower = 0.0;
            angularPower = Math.abs(fwd) * turn * kSensitivity - quickStopAccumulator;
            if(quickStopAccumulator > 1){
                quickStopAccumulator = -1;
            }else if(quickStopAccumulator < -1){
                quickStopAccumulator += 1;
            }else{
                quickStopAccumulator = 0;
            }
        }
        
        portOut = starOut = linearPower;
        portOut += angularPower;
        starOut -= angularPower;

        if(portOut > 1.0){
            starOut -= overPower * (portOut - 1.0);
            portOut = 1.0;
        }else if(starOut > 1.0){
            portOut -= overPower * (starOut - 1.0);
            starOut = 1.0;
        } else if (portOut < -1.0) {
            starOut += overPower * (-1.0 - portOut);
            portOut = -1.0;
        } else if (starOut < -1.0) {
            portOut += overPower * (-1.0 - starOut);
            starOut = -1.0;
        }
        
        return DriveSignal.genDriveSignal(portOut, starOut, 0);
    }

    public static  DriveSignal calcHDrive(double fwd, double turn, final double strafe, final boolean quickTurn){
        turn = checkDeadband(turn, kTurnDeadband);
        fwd = checkDeadband(fwd, kFWDDeadband);
        
        double negInertia = turn - oldTurn;
        oldTurn = turn;
        
        final double denom = Math.sin(Math.PI / 2.0 * kWheelNonLinearity);
        //Scale the wheel
        turn = Math.sin(Math.PI / 2.0 * kWheelNonLinearity * turn) / denom;
        turn = Math.sin(Math.PI / 2.0 * kWheelNonLinearity * turn) / denom;
        
        double portOut, starOut, overPower;
        
        double angularPower, linearPower;
        
        double negInertiaPower = negInertia * kNegInertiaScalar;
        negInertiaAccumulator += negInertiaPower;
        
        turn += negInertiaAccumulator;
        if(negInertiaAccumulator > 1){
            negInertiaAccumulator = -1;
        }else if(negInertiaAccumulator < -1){
            negInertiaAccumulator += 1;
        }else{
            negInertiaAccumulator = 0;
        }
        linearPower = fwd;
        
        if(quickTurn){
            if(Math.abs(linearPower) < kQuickStopDeadband){
                quickStopAccumulator = (1 - kQuickStopWeight) * quickStopAccumulator + kQuickStopWeight * Utils.limit(turn, 1.0) * kQuickStopScalar;
            }
            overPower = 1.0;
            angularPower = turn;
        }else{
            overPower = 0.0;
            angularPower = Math.abs(fwd) * turn * kSensitivity - quickStopAccumulator;
            if(quickStopAccumulator > 1){
                quickStopAccumulator = -1;
            }else if(quickStopAccumulator < -1){
                quickStopAccumulator += 1;
            }else{
                quickStopAccumulator = 0;
            }
        }
        
        portOut = starOut = linearPower;
        portOut += angularPower;
        starOut -= angularPower;

        if(portOut > 1.0){
            starOut -= overPower * (portOut - 1.0);
            portOut = 1.0;
        }else if(starOut > 1.0){
            portOut -= overPower * (starOut - 1.0);
            starOut = 1.0;
        } else if (portOut < -1.0) {
            starOut += overPower * (-1.0 - portOut);
            portOut = -1.0;
        } else if (starOut < -1.0) {
            portOut += overPower * (-1.0 - starOut);
            starOut = -1.0;
        }
        
        return DriveSignal.genDriveSignal(portOut, starOut, strafe);
    }
    
    public static double checkDeadband(final double inp, final double deadband){
        return (Math.abs(inp) > Math.abs(deadband)) ? inp : 0.0;
    }
}
