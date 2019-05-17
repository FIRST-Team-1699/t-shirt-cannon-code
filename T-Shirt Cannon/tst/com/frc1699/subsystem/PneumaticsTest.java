package com.frc1699.subsystem;

public class PneumaticsTest {

    public static void main(String[] args){
        System.out.println(Pneumatics.calcAcceleration(120.0));
        System.out.println(Pneumatics.calcExitVelocity(120.0, 0.1));
    }
}
