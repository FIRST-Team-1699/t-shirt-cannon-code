package com.frc1699.subsystem;

import com.frc1699.constants.Constants;
import com.frc1699.utils.CircularLinkedList;
import com.frc1699.utils.SingleSideSpike;
import edu.wpi.first.wpilibj.Relay;

public class BarrelHolder {
    
    //Spikes
    Relay relay5;
    Relay relay6;
    Relay relay7;
    Relay relay8;
    
    //Single Relay Side
    SingleSideSpike spike1;
    SingleSideSpike spike2;
    SingleSideSpike spike3;
    SingleSideSpike spike4;
    SingleSideSpike spike5;
    SingleSideSpike spike6;
    SingleSideSpike spike7;
    
    //Barrels
    Barrel barrel1;
    Barrel barrel2;
    Barrel barrel3;
    Barrel barrel4;
    Barrel barrel5;
    Barrel barrel6;
    Barrel barrel7;
    
    //Barrel List
    private CircularLinkedList barrelList;
    
    public BarrelHolder(){
        //Spikes
        /*
        *kOff - Turns both relay outputs off
        *kForward - Sets the relay to forward (M+ @ 12V, M- @ GND)
        *kReverse - Sets the relay to reverse (M+ @ GND, M- @ 12V)
        *KOn - Sets both relay outputs on (M+ @ 12V, M- @ 12V).
        */
        //Change to SingleSidedSpikes
        relay5 = new Relay(Constants.relay5Port);
        relay6 = new Relay(Constants.relay6Port);
        relay7 = new Relay(Constants.relay7Port);
        relay8 = new Relay(Constants.relay8Port);
        
        //Even numbered barrels use kForward, Odd numbers use kReverse
        //SingleSideSpikes
        spike1 = new SingleSideSpike(1, relay5);
        spike2 = new SingleSideSpike(2, relay5);
        spike3 = new SingleSideSpike(3, relay6);
        spike4 = new SingleSideSpike(4, relay6);
        spike5 = new SingleSideSpike(5, relay7);
        spike6 = new SingleSideSpike(6, relay7);
        spike7 = new SingleSideSpike(7, relay8);
        
        //Barrels
        barrel1 = new Barrel(1, spike1);
        barrel2 = new Barrel(2, spike2);
        barrel3 = new Barrel(3, spike3);
        barrel4 = new Barrel(4, spike4);
        barrel5 = new Barrel(5, spike5);
        barrel6 = new Barrel(6, spike6);
        barrel7 = new Barrel(7, spike7);
        
        barrelList.createNodeAtEnd(barrel1);
        barrelList.createNodeAtEnd(barrel2);
        barrelList.createNodeAtEnd(barrel3);
        barrelList.createNodeAtEnd(barrel4);
        barrelList.createNodeAtEnd(barrel5);
        barrelList.createNodeAtEnd(barrel6);
        barrelList.createNodeAtEnd(barrel7);
    }
    
    //Fires the next loaded barrel. Will return true if attempt was successful
    public boolean fireNext(){
        return false;
    }
    
    //Will fire given barrrel number. Will return true if barrel was successfully fired
    public boolean fireManual(int barrelNumber){
        switch(barrelNumber){
            case 1:
                barrel1.fire();
                return true;
            case 2:
                barrel2.fire();
                return true;
            case 3:
                barrel3.fire();
                return true;
            case 4:
                barrel4.fire();
                return true;
            case 5:
                barrel5.fire();
                return true;
            case 6:
                barrel6.fire();
                return true;
            case 7:
                barrel7.fire();
                return true;
            default:
                return false;
        }
    }
    
    //Resets the state of all barrels
    public void resetBarrels(){
        barrel1.setFired(false);
        barrel2.setFired(false);
        barrel3.setFired(false);
        barrel4.setFired(false);
        barrel5.setFired(false);
        barrel6.setFired(false);
    }
}
