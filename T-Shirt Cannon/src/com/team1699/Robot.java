package com.team1699;

import com.team1699.utils.SingleSideSpike;
import com.team1699.constants.Constants;
import com.team1699.utils.CircularQueue;
import com.team1699.barrel.Barrel;
import com.team1699.utils.Utils;
import com.team1699.wrist.BarrelWrist;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends IterativeRobot {    
    //Drive motors
    Talon portMaster;
    Talon portSlave;
    Talon starMaster;
    Talon starSlave;
    
    //Drive train
    RobotDrive driveTrain;
    
    //Joysticks
    Joystick rightStick;
    
    //Spikes
    Relay relay5;
    Relay relay6;
    Relay relay7;
    Relay relay8;
    
    //Single Relay Side
    SingleSideSpike spike1;
    SingleSideSpike spike2;
    SingleSideSpike spike3;
    SingleSideSpike spike4;
    SingleSideSpike spike5;
    SingleSideSpike spike6;
    SingleSideSpike spike7;
    
    //Barrels
    Barrel barrel1;
    Barrel barrel2;
    Barrel barrel3;
    Barrel barrel4;
    Barrel barrel5;
    Barrel barrel6;
    Barrel barrel7;
    
    //Barrel Array
    CircularQueue barrelList;
    
    //Says if trigger is released
    private boolean released = true;
    
    //Wrist Vars
    //Encoder
    Encoder wristEncoder;
    //Magnetic Limit Switch
    DigitalInput wristLowerLimit;
    //Subsystem
    BarrelWrist wrist;
    
    public void robotInit() {
        //Drive motors
        portMaster = new Talon(Constants.talon1Port);
        portSlave = new Talon(Constants.talon2Port);
        starMaster = new Talon(Constants.talon3Port);
        starSlave = new Talon(Constants.talon4Port);
        
        //Robot Drive
        driveTrain = new RobotDrive(portMaster, portSlave, starMaster, starSlave);
        driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        
        //Joysticks
        rightStick = new Joystick(Constants.rightStickPort);
        
        //Spikes
        /*
        *kOff - Turns both relay outputs off
        *kForward - Sets the relay to forward (M+ @ 12V, M- @ GND)
        *kReverse - Sets the relay to reverse (M+ @ GND, M- @ 12V)
        *KOn - Sets both relay outputs on (M+ @ 12V, M- @ 12V).
        */
        //Change to SingleSidedSpikes
        relay5 = new Relay(Constants.relay5Port);
        relay6 = new Relay(Constants.relay6Port);
        relay7 = new Relay(Constants.relay7Port);
        relay8 = new Relay(Constants.relay8Port);
        
        //Even numbered barrels use kForward, Odd numbers use kReverse
        //SingleSideSpikes
        spike1 = new SingleSideSpike(1, relay5);
        spike2 = new SingleSideSpike(2, relay5);
        spike3 = new SingleSideSpike(3, relay6);
        spike4 = new SingleSideSpike(4, relay6);
        spike5 = new SingleSideSpike(5, relay7);
        spike6 = new SingleSideSpike(6, relay7);
        spike7 = new SingleSideSpike(7, relay8);
        
        //Barrels
        barrel1 = new Barrel(1, spike1);
        barrel2 = new Barrel(2, spike2);
        barrel3 = new Barrel(3, spike3);
        barrel4 = new Barrel(4, spike4);
        barrel5 = new Barrel(5, spike5);
        barrel6 = new Barrel(6, spike6);
        barrel7 = new Barrel(7, spike7);
        
        //Barrel Queue
        barrelList = new CircularQueue();
        barrelList.addData(barrel1);
        barrelList.addData(barrel2);
        barrelList.addData(barrel3);
        barrelList.addData(barrel4);
        barrelList.addData(barrel5);
        barrelList.addData(barrel6);
        barrelList.addData(barrel7);
        
        //Wrist
        wrist = new BarrelWrist();
        
        //TODO Add init for wrist encoder and limit switch
    }

    public void teleopPeriodic() {
        //System.out.println("SETH IS COOL");
        //Runs drive train
        driveTrain.arcadeDrive(rightStick);
        
        //Used for debuging/fires single barrel
        debugControl();
        
        //Fires barrels in sequence
        stateBasedControl();
        
        //Resets barrel state
        if(rightStick.getRawButton(3)){
            resetBarrels();
        }
        
        //Update the wrist
        //wrist.update(wristEncoder.get(), wristLowerLimit.get(), true); //TODO Invert limit if needed
        
        //Code to run barrel rotation
//        double desiredAngle = rightStick.getThrottle(); //TODO Scale
//        if(Utils.epsilonEquals(wrist.getGoal(), desiredAngle, 1.0)){
//            wrist.setGoal(desiredAngle);
//        }
    }

    private void stateBasedControl(){
        //Fires next barrel when trigger is pressed
        if(rightStick.getRawButton(1) && released){
            Barrel currentBarrel = (Barrel) barrelList.get();
            if(!currentBarrel.isFired()){
                currentBarrel.fire();
            }
            released = false;
        }
        if(!rightStick.getRawButton(1)){
            released = true;
        }
    }
    
    //Change to use single sided solenoid
    private void debugControl(){
        //Fires barrel based on what button is pressed       
        if(rightStick.getRawButton(Constants.righStickBarrel1Fire)){
            relay5.set(Relay.Value.kReverse);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            relay5.set(Relay.Value.kOff);
        }
        if(rightStick.getRawButton(Constants.righStickBarrel2Fire)){
            relay5.set(Relay.Value.kForward);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            relay5.set(Relay.Value.kOff);
        }
        if(rightStick.getRawButton(Constants.righStickBarrel3Fire)){
            relay6.set(Relay.Value.kReverse);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            relay6.set(Relay.Value.kOff);
        }
        if(rightStick.getRawButton(Constants.righStickBarrel4Fire)){
            relay6.set(Relay.Value.kForward);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            relay6.set(Relay.Value.kOff);
        }
        if(rightStick.getRawButton(Constants.righStickBarrel5Fire)){
            relay7.set(Relay.Value.kReverse);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            relay7.set(Relay.Value.kOff);
        }
        if(rightStick.getRawButton(Constants.righStickBarrel6Fire)){
            relay7.set(Relay.Value.kForward);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            relay7.set(Relay.Value.kOff);
        }
        if(rightStick.getRawButton(Constants.righStickBarrel7Fire)){
            relay8.set(Relay.Value.kReverse);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            relay8.set(Relay.Value.kOff);
        }
    }
    
    //Resets the state of all barrels
    private void resetBarrels(){
        barrel1.setFired(false);
        barrel2.setFired(false);
        barrel3.setFired(false);
        barrel4.setFired(false);
        barrel5.setFired(false);
        barrel6.setFired(false);
    }
}
