package com.team1699.IO;

import com.team1699.constants.Constants;
import edu.wpi.first.wpilibj.Joystick;

public class ControlBoard {

    private static ControlBoard instance;

    public static ControlBoard getInstance(){
        if(instance == null){
            instance = new ControlBoard();
        }
        return instance;
    }

    private final Joystick driveStick;

    private ControlBoard(){
        driveStick = new Joystick(Constants.driveStickPort);
    }

    public Joystick getDriveStick(){
        return driveStick;
    }
}
