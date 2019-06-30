package com.team1699;

import com.team1699.IO.ControlBoard;
import com.team1699.client.Client;
import com.team1699.constants.Constants;
import com.team1699.subsystem.BarrelHolder;
import com.team1699.subsystem.BarrelWrist;
import com.team1699.subsystem.DriveLoop;
import com.team1699.subsystem.DriveTrain;
import com.team1699.subsystem.Pneumatics;
import com.team1699.utils.Utils;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

    //Drive train
    private DriveTrain driveTrain;
        
    //Says if trigger is released
    private boolean released = true;

    //Pneumatics
    private Pneumatics pneumatics;

    //Wrist Vars
    //Encoder
    private Encoder wristEncoder;
    //Magnetic Limit Switch
    private DigitalInput wristLowerLimit;
    //Subsystem
    private BarrelWrist wrist;

    //Barrel Holder
    private BarrelHolder barrelHolder;
    
    //TODO Remove stuff that is not needed for current robot
    //TODO Add client
    
    public void robotInit() {
        //Robot Drive
        driveTrain = new DriveTrain(DriveLoop.State.TANK_DRIVE); //TODO Implement H-Drive (hardware limit?)
        
        //Wrist
        wrist = new BarrelWrist();
        //TODO Add init for wrist encoder and limit switch
        //wristEncoder = new Encoder(0, 0, 0, 0);
        //wristLowerLimit = new DigitalInput(0);

        //Pneumatics
        pneumatics = new Pneumatics();

        //Barrel Holder
        barrelHolder = new BarrelHolder(pneumatics);

        System.out.println("Registering barrels");
        Client.getInstance().addMessage("1 1");
        Client.getInstance().addMessage("2 1");
        Client.getInstance().addMessage("3 1");
        Client.getInstance().addMessage("4 1");
        Client.getInstance().addMessage("5 1");
        Client.getInstance().addMessage("6 1");
        Client.getInstance().addMessage("7 1");
        System.out.println("Finished registering barrels");
    }

    public void teleopPeriodic() {
        System.out.println("Main Update");
        //Update drive train
        driveTrain.update();
        
        //Used for debugging/fires single barrel
        debugControl();
        
        //Fires barrels in sequence
        stateBasedControl();

        //Update the barrel states
        barrelHolder.update();
        
        //Resets barrel state
        if(ControlBoard.getInstance().getDriveStick().getRawButton(3)){
            barrelHolder.resetBarrels();
        }
        
        //Update the wrist
        wrist.update(wristEncoder.get(), wristLowerLimit.get(), true); //TODO Invert limit if needed
        
        //Code to run barrel rotation
        double desiredAngle = (ControlBoard.getInstance().getDriveStick().getThrottle() + 1.0) * 30.0;
        if(Utils.epsilonEquals(wrist.getGoal(), desiredAngle, 1.0)){
            wrist.setGoal(desiredAngle);
        }

        //Update pressure
        pneumatics.update();
    }

    private void stateBasedControl(){
        //Fires next barrel when trigger is pressed
        if(ControlBoard.getInstance().getDriveStick().getRawButton(1) && released){
            barrelHolder.fireNext();
            released = false;
        }
        if(!ControlBoard.getInstance().getDriveStick().getRawButton(1)){
            released = true;
        }
    }
    
    //Change to use single sided solenoid
    private void debugControl(){
        //Fires barrel based on what button is pressed
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.rightStickBarrel1Fire)){
            barrelHolder.fireManual(1);
        }
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.rightStickBarrel2Fire)){
            barrelHolder.fireManual(2);
        }
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.rightStickBarrel3Fire)){
            barrelHolder.fireManual(3);
        }
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.rightStickBarrel4Fire)){
            barrelHolder.fireManual(4);
        }
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.rightStickBarrel5Fire)){
            barrelHolder.fireManual(5);
        }
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.rightStickBarrel6Fire)){
            barrelHolder.fireManual(6);
        }
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.rightStickBarrel7Fire)){
            barrelHolder.fireManual(7);
        }
    }
}
