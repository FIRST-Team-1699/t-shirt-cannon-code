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
    
    //Barrels
    Barrel b1;
    Barrel b2;
    Barrel b3;
    Barrel b4;
    Barrel b5;
    Barrel b6;
    Barrel b7;
    
    public void robotInit() {
        //Drive motors
        portMaster = new Talon(Constants.t1Port);
        portSlave = new Talon(Constants.t2Port);
        starMaster = new Talon(Constants.t3Port);
        starSlave = new Talon(Constants.t4Port);
        
        //Robot Drive
        d = new RobotDrive(portMaster, portSlave, starMaster, starSlave);
        
        //Joysticks
        rs = new Joystick(Constants.rsPort);
        ls = new Joystick(Constants.lsPort);
        
        //Spikes
        /*
        *kOff - Turns both relay outputs off
        *kForward - Sets the relay to forward (M+ @ 12V, M- @ GND)
        *kReverse - Sets the relay to reverse (M+ @ GND, M- @ 12V)
        *KOn - Sets both relay outputs on (M+ @ 12V, M- @ 12V).
        */
        //Change to SingleSidedSpikes
        r5 = new Relay(Constants.r5Port);
        r6 = new Relay(Constants.r6Port);
        r7 = new Relay(Constants.r7Port);
        r8 = new Relay(Constants.r8Port);
        
        //Even numbered barrels use kForward, Odd numbers use kReverse
        //SingleSideSpikes
        s1 = new SingleSideSpike(1, r5);
        s2 = new SingleSideSpike(2, r5);
        s3 = new SingleSideSpike(3, r6);
        s4 = new SingleSideSpike(4, r6);
        s5 = new SingleSideSpike(5, r7);
        s6 = new SingleSideSpike(6, r7);
        s7 = new SingleSideSpike(7, r8);
        
        //Barrels
        b1 = new Barrel(1, s1);
        b2 = new Barrel(2, s2);
        b3 = new Barrel(3, s3);
        b4 = new Barrel(4, s4);
        b5 = new Barrel(5, s5);
        b6 = new Barrel(6, s6);
        b7 = new Barrel(7, s7);
    }

    public void teleopPeriodic() {
        d.tankDrive(ls, rs);
        
        debugControl();
        
        stateBasedControl();
        
        if(ls.getRawButton(7)){
            resetBarrels();
        }
    }

    private void stateBasedControl(){
        
    }
    
    private void debugControl(){
        if(ls.getRawButton(2)){
            b1.fire();
        }else if(ls.getRawButton(3)){
            b2.fire();
        }else if(ls.getRawButton(4)){
            b3.fire();
        }else if(ls.getRawButton(5)){
            b4.fire();
        }else if(rs.getRawButton(3)){
            b5.fire();
        }else if(rs.getRawButton(4)){
            b6.fire();
        }else if(rs.getRawButton(5)){
            b7.fire();
        }
    }
    
    private void resetBarrels(){
        b1.setFired(false);
        b2.setFired(false);
        b3.setFired(false);
        b4.setFired(false);
        b5.setFired(false);
        b6.setFired(false);
        b7.setFired(false);
    }
}
