package com.team1699.utils.drive;

public class DriveSignal {

    public final double portVoltage, starVoltage, strafeVoltage;

    private DriveSignal(final double portVoltage, final double starVoltage, final double strageVoltage){
        this.portVoltage = portVoltage;
        this.starVoltage = starVoltage;
        this.strafeVoltage = strageVoltage;
    }

    public static DriveSignal genDriveSignal(final double portVoltage, final double starVoltage, final double strafeVoltage){
        return new DriveSignal(portVoltage, starVoltage, strafeVoltage);
    }
}
