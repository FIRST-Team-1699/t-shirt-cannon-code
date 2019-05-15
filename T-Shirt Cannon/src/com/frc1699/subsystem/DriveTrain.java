package com.frc1699.subsystem;

import com.frc1699.IO.ControlBoard;
import com.frc1699.constants.Constants;
import com.frc1699.utils.drive.BetterTalon;
import com.frc1699.utils.drive.DriveHelper;
import com.frc1699.utils.drive.DriveSignal;

public class DriveTrain implements DriveLoop {

    private final int driveState;

    private final BetterTalon portMaster;
    private final BetterTalon starMaster;
    private final BetterTalon strafeMaster;

    private final OutputSignal outputSignal;

    //TODO Convert from SpeedControllerGroup to BetterTalon
    public DriveTrain(final int driveState){
        this.driveState = driveState;
        this.outputSignal = new OutputSignal();

        switch (driveState){
            case State.TANK_DRIVE:
                portMaster = new BetterTalon(Constants.talon1Port);
                portMaster.addSlave(Constants.talon2Port);
                portMaster.addSlave(Constants.talon5Port);
                starMaster = new BetterTalon(Constants.talon3Port);
                starMaster.addSlave(Constants.talon4Port);
                starMaster.addSlave(Constants.talon6Port);
                strafeMaster = null;
                break;
            case State.H_DRIVE:
                portMaster = new BetterTalon(Constants.talon1Port);
                portMaster.addSlave(Constants.talon2Port);
                starMaster = new BetterTalon(Constants.talon3Port);
                starMaster.addSlave(Constants.talon4Port);
                strafeMaster = new BetterTalon(Constants.talon5Port);
                starMaster.addSlave(Constants.talon6Port);
                break;
            default:
                //TODO Throw exception
                portMaster = null;
                starMaster = null;
                strafeMaster = null;
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

        portMaster.set(outputSignal.portVoltage);
        starMaster.set(outputSignal.starVoltage);
        if(strafeMaster != null){
            strafeMaster.set(outputSignal.strafeVoltage);
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
