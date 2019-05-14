package com.frc1699.subsystem;

import com.frc1699.IO.ControlBoard;
import com.frc1699.constants.Constants;
import com.frc1699.utils.drive.SpeedControllerGroup;
import com.frc1699.utils.drive.DriveHelper;
import com.frc1699.utils.drive.DriveSignal;
import edu.wpi.first.wpilibj.Talon;

public class DriveTrain implements DriveLoop {

    private final int driveState;

    private final SpeedControllerGroup portGroup;
    private final SpeedControllerGroup starGroup;
    private final SpeedControllerGroup strafeGroup;

    private final OutputSignal outputSignal;

    //TODO Convert from SpeedControllerGroup to BetterTalon
    public DriveTrain(final int driveState){
        this.driveState = driveState;
        this.outputSignal = new OutputSignal();

        switch (driveState){
            case State.TANK_DRIVE:
                portGroup = new SpeedControllerGroup(1, new Talon[]{new Talon(Constants.talon1Port), new Talon(Constants.talon2Port), new Talon(Constants.talon5Port)});
                starGroup = new SpeedControllerGroup(3, new Talon[]{new Talon(Constants.talon3Port), new Talon(Constants.talon4Port), new Talon(Constants.talon6Port)});
                strafeGroup = null;
                break;
            case State.H_DRIVE:
                portGroup = new SpeedControllerGroup(1, new Talon[]{new Talon(Constants.talon1Port), new Talon(Constants.talon2Port)});
                starGroup = new SpeedControllerGroup(3, new Talon[]{new Talon(Constants.talon3Port), new Talon(Constants.talon4Port)});
                strafeGroup = new SpeedControllerGroup(5, new Talon[]{new Talon(Constants.talon5Port), new Talon(Constants.talon6Port)});;
                break;
            default:
                //TODO Throw exception
                portGroup = null;
                starGroup = null;
                strafeGroup = null;
                System.err.println("Invalid Drive State Requested");
        }
    }

    public void update() {
        switch(driveState){
            case State.TANK_DRIVE:
                runTankDrive();
                break;
            case State.H_DRIVE:
                runHDrive();
                break;
            default:
                //TODO Throw exception
                System.err.println("Invalid Drive State Requested");
        }

        portGroup.set(outputSignal.portVoltage);
        starGroup.set(outputSignal.starVoltage);
        if(strafeGroup != null){
            strafeGroup.set(outputSignal.strafeVoltage);
        }
    }

    private void runTankDrive(){
        //TODO Check correct axis
        DriveSignal signal = DriveHelper.calcTankDrive(ControlBoard.getInstance().getDriveStick().getX(), ControlBoard.getInstance().getDriveStick().getY(), ControlBoard.getInstance().getDriveStick().getTrigger());
        outputSignal.portVoltage = signal.portVoltage;
        outputSignal.starVoltage = signal.starVoltage;
    }

    private void runHDrive(){
        //TODO Check correct axis
        DriveSignal signal = DriveHelper.calcHDrive(ControlBoard.getInstance().getDriveStick().getX(), ControlBoard.getInstance().getDriveStick().getY(), ControlBoard.getInstance().getDriveStick().getZ(), ControlBoard.getInstance().getDriveStick().getTrigger());
        outputSignal.portVoltage = signal.portVoltage;
        outputSignal.starVoltage = signal.starVoltage;
        outputSignal.strafeVoltage = signal.strafeVoltage;
    }

    class OutputSignal{
        double portVoltage;
        double starVoltage;
        double strafeVoltage;
    }
}
