package com.frc1699.subsystem;

import edu.wpi.first.wpilibj.AnalogChannel;

public class Pneumatics implements PneumaticsLoop {

    //Rev Robotics Pressure Sensor
    private final AnalogChannel pressureSensor;

    private double pressure = 0.0;

    public Pneumatics(){
        pressureSensor = new AnalogChannel(kPreasureSensorPort);
    }

    public void update() {
        pressure = 250.0 * pressureSensor.getVoltage() / 5.0 - 25.0;
    }

    public double getSystemPressure(){
        return pressure;
    }

    //Helper functions for T-Shirt Physics that rely on pressure
    //Returns exit velocity in m/s
    public static double calcExitVelocity(final double pressure, final double openTime){
        return 0.5 * calcAcceleration(pressure) * openTime * openTime;
    }

    //Returns acceleration in m/s^2
    public static double calcAcceleration(final double pressure){
        return ((pressure * kPsiPaConv * kCrossArea) / kShirtMass) - ((kDragCoeff * kAirDensity * kCrossArea * 0.5) / kShirtMass);
    }
}
