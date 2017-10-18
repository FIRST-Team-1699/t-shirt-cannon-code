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
    
    //Single Relay Side
    SingleSideSpike s1;
    SingleSideSpike s2;
    SingleSideSpike s3;
    SingleSideSpike s4;
    SingleSideSpike s5;
    SingleSideSpike s6;
    SingleSideSpike s7;
    
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
        //Change to SingleSidedSpikes
        r5 = new Relay(5);
        r6 = new Relay(6);
        r7 = new Relay(7);
        r8 = new Relay(8);
        
        //Even numbered barrels use kForward, Odd numbers use kReverse
        //SingleSideSpikes
        s1 = new SingleSideSpike(1, r5);
        s2 = new SingleSideSpike(2, r5);
        s3 = new SingleSideSpike(3, r6);
        s4 = new SingleSideSpike(4, r6);
        s5 = new SingleSideSpike(5, r7);
        s6 = new SingleSideSpike(6, r7);
        s7 = new SingleSideSpike(7, r8);
    }

    public void teleopPeriodic() {
        d.tankDrive(ls, rs);
    }    
}
