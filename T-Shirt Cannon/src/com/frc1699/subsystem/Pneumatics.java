package com.frc1699.subsystem;

import com.frc1699.utils.Utils;
import edu.wpi.first.wpilibj.AnalogChannel;

public class Pneumatics implements PneumaticsLoop {

    //Rev Robotics Pressure Sensor
    private final AnalogChannel pressureSensor;

    //Current system pressure
    private double pressure = 0.0;

    //Goal pressure
    private double goal = 120.0;

    private boolean wantPressureChange = false;

    public Pneumatics(){
        pressureSensor = new AnalogChannel(kPressureSensorPort);
    }

    public void update() {
        //Records current system pressure
        pressure = 250.0 * pressureSensor.getVoltage() / 5.0 - 25.0;

        //Update regulator to change system pressure
        if(wantPressureChange) {
            //Adjust regulator

            if(Utils.epsilonEquals(pressure, goal, 5.0)){
                wantPressureChange = false;
            }
        }
    }

    public void setGoal(final double goal){
        this.goal = goal;
        wantPressureChange = true;
    }

    public double getSystemPressure(){
        return pressure;
    }

    //Helper functions for T-Shirt Physics that rely on pressure
    //Returns exit velocity in m/s
    public static double calcExitVelocity(final double pressure, final double openTime){
        return 0.5 * calcAcceleration(pressure) * openTime * openTime;
    }

    //TODO Need to change 0.0 to be an actual velocity
    //Returns acceleration in m/s^2
    public static double calcAcceleration(final double pressure){
        return ((pressure * kPsiPaConv * kCrossArea) / kShirtMass) - ((kDragCoeff * kAirDensity * 0.0 * kCrossArea * 0.5) / kShirtMass);
    }

    //Returns the pressure in Psi required to achieve an acceleration
    public static double calcPressure(final double acceleration){
        return ((acceleration * kShirtMass) / (kPsiPaConv * kCrossArea)) - ((kDragCoeff * kAirDensity * 0.0 * kCrossArea * 0.5) / (kPsiPaConv * kCrossArea));
    }
}
