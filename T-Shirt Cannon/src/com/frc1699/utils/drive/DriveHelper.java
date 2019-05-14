package com.frc1699.utils.drive;

public class DriveHelper {

    public static DriveSignal calcTankDrive(final double fwdInput, final double turnInput, final boolean quickTurn){
        return DriveSignal.genDriveSignal(0, 0, 0);
    }

    public static  DriveSignal calcHDrive(final double fwdInput, final double turnInput, final double strageInput){
        return DriveSignal.genDriveSignal(0, 0, 0);
    }
}
