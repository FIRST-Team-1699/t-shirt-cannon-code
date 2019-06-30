package com.team1699.utils.drive;

import edu.wpi.first.wpilibj.Talon;
import java.util.Vector;

//A wrapper to provide functions to make the Talon act more like the modern TalonSRX
public class BetterTalon {
    
    private final Talon talon;
    private final Vector slaves;
    
    public BetterTalon(final int port){
        talon = new Talon(port);
        slaves = new Vector();
    }
    
    public void addSlave(final int port){
        slaves.addElement(new Talon(port));
    }
    
    public void addSlave(final Talon t){
        slaves.addElement(t);
    }
    
    public void set(final double pwm){
        talon.set(pwm);
        for(int i = 0; i < slaves.size(); i++){
            Talon t = (Talon) (slaves.elementAt(i));
            t.set(pwm);
        }
    }
    
    public double get(){
        return talon.get();
    }
}
