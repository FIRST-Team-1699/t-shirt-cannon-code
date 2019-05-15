package com.frc1699.subsystem;

import edu.wpi.first.wpilibj.AnalogChannel;

public class Pneumatics implements PneumaticsLoop {

    private final AnalogChannel preasureSensor;

    private double preasure = 0.0;

    public Pneumatics(){
        preasureSensor = new AnalogChannel(kPreasureSensorPort);
    }

    public void update() {
        preasure = 250.0 * preasureSensor.getVoltage() / 5.0 - 25.0;
    }

    public double getSystemPreasure(){
        return preasure;
    }
}
