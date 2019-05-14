package com.frc1699;

import com.frc1699.IO.ControlBoard;
import com.frc1699.subsystem.BarrelHolder;
import com.frc1699.constants.Constants;
import com.frc1699.subsystem.DriveLoop;
import com.frc1699.subsystem.DriveTrain;
import com.frc1699.utils.Utils;
import com.frc1699.subsystem.BarrelWrist;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends IterativeRobot {    
    //Drive motors
    private Talon portMaster;
    private Talon portSlave;
    private Talon starMaster;
    private Talon starSlave;
    
    //Drive train
    private DriveTrain driveTrain;
        
    //Says if trigger is released
    private boolean released = true;
        
    //Wrist Vars
    //Encoder
    private Encoder wristEncoder;
    //Magnetic Limit Switch
    private DigitalInput wristLowerLimit;
    //Subsystem
    private BarrelWrist wrist;

    //Barrel Holder
    private BarrelHolder barrelHolder;
    
    public void robotInit() {
        //Robot Drive
        driveTrain = new DriveTrain(DriveLoop.State.TANK_DRIVE); //TODO Implement H-Drive
        
        //Wrist
        wrist = new BarrelWrist();
        //TODO Add init for wrist encoder and limit switch
        wristEncoder = new Encoder(0, 0, 0, 0);
        wristLowerLimit = new DigitalInput(0);

        //Barrel Holder
        barrelHolder = new BarrelHolder();
    }

    public void teleopPeriodic() {
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
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.righStickBarrel1Fire)){
            barrelHolder.fireManual(1);
        }
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.righStickBarrel2Fire)){
            barrelHolder.fireManual(2);
        }
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.righStickBarrel3Fire)){
            barrelHolder.fireManual(3);
        }
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.righStickBarrel4Fire)){
            barrelHolder.fireManual(4);
        }
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.righStickBarrel5Fire)){
            barrelHolder.fireManual(5);
        }
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.righStickBarrel6Fire)){
            barrelHolder.fireManual(6);
        }
        if(ControlBoard.getInstance().getDriveStick().getRawButton(Constants.righStickBarrel7Fire)){
            barrelHolder.fireManual(7);
        }
    }
}
