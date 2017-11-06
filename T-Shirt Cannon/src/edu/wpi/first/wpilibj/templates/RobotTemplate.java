package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class RobotTemplate extends IterativeRobot {
    
    //Name vars better
    
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
    
    //Barrel Array
    Barrel[] barrelList;
    
    //State/Index Machine
    StateMachine stateMachine;
    
    //Says if trigger is released
    private boolean released = true;
    
    public void robotInit() {
        //Drive motors
        portMaster = new Talon(Constants.t1Port);
        portSlave = new Talon(Constants.t2Port);
        starMaster = new Talon(Constants.t3Port);
        starSlave = new Talon(Constants.t4Port);
        
        //Robot Drive
        d = new RobotDrive(portMaster, portSlave, starMaster, starSlave);
        d.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        d.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        d.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        d.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        
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
        
        //Barrel Array
        barrelList = new Barrel[7];
        barrelList[0] = b1;
        barrelList[1] = b2;
        barrelList[2] = b3;
        barrelList[3] = b4;
        barrelList[4] = b5;
        barrelList[5] = b6;
        barrelList[6] = b7;
        
        //StateMachine
        stateMachine = new StateMachine(6);
    }

    public void teleopPeriodic() {
        //Runs drive train
        d.arcadeDrive(rs);
        
        //Used for debuging/fires single barrel out of order
        debugControl();
        
        //Fires barrels in sequence
        stateBasedControl();
        
        //Resets barrel state
        if(rs.getRawButton(3)){
            resetBarrels();
        }
    }

    //Change to use queue instead of whatever this is
    private void stateBasedControl(){
        //Fires next barrel when trigger is pressed
        if(rs.getRawButton(1) && released){
            int currentState = stateMachine.getCurrentState();
            Barrel currentBarrel = barrelList[currentState];
            if(!currentBarrel.isFired()){
                stateMachine.incrementState();
            }
            stateMachine.incrementState();
            released = false;
        }
        if(!rs.getRawButton(1)){
            released = true;
        }
    }
    
    //Change to use single sided solenoid
    private void debugControl(){
        //Fires barrel based on what button is pressed       
        if(rs.getRawButton(Constants.rsB1Fire)){
            r5.set(Relay.Value.kReverse);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            r5.set(Relay.Value.kOff);
        }
        if(rs.getRawButton(Constants.rsB2Fire)){
            r5.set(Relay.Value.kForward);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            r5.set(Relay.Value.kOff);
        }
        if(rs.getRawButton(Constants.rsB3Fire)){
            r6.set(Relay.Value.kReverse);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            r6.set(Relay.Value.kOff);
        }
        if(rs.getRawButton(Constants.rsB4Fire)){
            r6.set(Relay.Value.kForward);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            r6.set(Relay.Value.kOff);
        }
        if(rs.getRawButton(Constants.rsB5Fire)){
            r7.set(Relay.Value.kReverse);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            r7.set(Relay.Value.kOff);
        }
        if(rs.getRawButton(Constants.rsB6Fire)){
            r7.set(Relay.Value.kForward);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            r7.set(Relay.Value.kOff);
        }
        if(rs.getRawButton(Constants.rsB7Fire)){
            r8.set(Relay.Value.kReverse);
            try {
                Thread.sleep(Constants.sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            r8.set(Relay.Value.kOff);
        }
    }
    
    //Resets the state of all barrels
    private void resetBarrels(){
        b1.setFired(false);
        b2.setFired(false);
        b3.setFired(false);
        b4.setFired(false);
        b5.setFired(false);
        b6.setFired(false);
        b7.setFired(false);
        stateMachine.setCurrentState(0);
    }
}
