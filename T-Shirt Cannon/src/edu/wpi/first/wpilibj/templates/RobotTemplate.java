package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class RobotTemplate extends IterativeRobot {
    
    //Drive motors
    Talon portMaster;
    Talon portSlave;
    Talon starMaster;
    Talon starSlave;
    
    //Drive train
    RobotDrive d;
    
    //Joysticks
    Joystick rs;
    Joystick ls;
    
    //Spikes
    Relay r5;
    Relay r6;
    Relay r7;
    Relay r8;
    
    public void robotInit() {
        //Drive motors
        portMaster = new Talon(1);
        portSlave = new Talon(2);
        starMaster = new Talon(3);
        starSlave = new Talon(4);
        
        //Robot Drive
        d = new RobotDrive(portMaster, portSlave, starMaster, starSlave);
        
        //Joysticks
        rs = new Joystick(2);
        ls = new Joystick(1);
        
        //Spikes
        /*
        *kOff - Turns both relay outputs off
        *kForward - Sets the relay to forward (M+ @ 12V, M- @ GND)
        *kReverse - Sets the relay to reverse (M+ @ GND, M- @ 12V)
        *KOn - Sets both relay outputs on (M+ @ 12V, M- @ 12V).
        */
        r5 = new Relay(5);
        r6 = new Relay(6);
        r7 = new Relay(7);
        r8 = new Relay(8);
    }

    public void teleopPeriodic() {
        d.tankDrive(ls, rs);
    }    
}
