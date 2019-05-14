package com.frc1699.subsystem;

public interface DriveLoop {

    class State {
        public static final int TANK_DRIVE = 0;
        public static final int H_DRIVE = 1;
    }

    double TANK_DRIVE_WHEEL_SIZE = 8.0; //Inches
    double H_DRIVE_OUTER_SIZE = 8.0; //Inches
    double H_DRIVE_STRAFE_SIZE = 3.25; // Inches

    void update();
}
