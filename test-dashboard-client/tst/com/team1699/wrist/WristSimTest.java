package com.team1699.wrist;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WristSimTest {

    private BarrelWristSim simWrist = BarrelWristSim.getInstance();
    final static double goal = 20.0;

    @Test
    void testWristModel(){
        BarrelWrist wrist = new BarrelWrist();
        wrist.setGoal(goal);

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("dump.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.write("# time, angle, voltage, aVel, aAcceleration, goal, limitSensor, lastError\n");

        double currentTime = 0.0;
        while(currentTime < 30.0) {
            final double voltage = wrist.update(simWrist.encoder(), simWrist.limitTriggered(), true);
            pw.write(String.format("%f, %f, %f, %f, %f, %f, %f, %f\n", currentTime, simWrist.angle, voltage, simWrist.aVel, simWrist.getAcceleration(voltage), wrist.getFilteredGoal(), simWrist.limitTriggered() ? 1.0 : 0.0, wrist.lastError));
            simulateTime(voltage, BarrelWristSim.kDt);
            currentTime += BarrelWristSim.kDt;
            pw.flush();
        }

        pw.close();

        assertEquals(goal, simWrist.angle, 0.01);
    }

    void simulateTime(final double voltage, final double time){
        assertTrue(voltage <= 12.0 && voltage >= -12.0, String.format("System asked for: %f volts which is greater than 12.0", voltage));
        final double kSimTime = 0.0001;

        double currentTime = 0.0;
        while(currentTime < time){
            final double acceleration = simWrist.getAcceleration(voltage);
            simWrist.angle += simWrist.aVel * kSimTime;
            simWrist.aVel += acceleration * kSimTime;
            currentTime += kSimTime;
//            if(simWrist.limitTriggered()){
//                assertTrue(simWrist.aVel > -10.0, String.format("System running at %f rpm which is less than -10.0", simWrist.aVel));
//            }
//            assertTrue(simWrist.angle >= WristLoop.kMinAngle - 0.1, String.format("System is at %f meters which is less than minimum angle of %f", simWrist.angle, WristLoop.kMinAngle));
//            assertTrue(simWrist.angle <= WristLoop.kMaxAngle + 0.1, String.format("System is at %f meters which is greater than the maximum angle of %f", simWrist.angle, WristLoop.kMaxAngle));
        }
    }
}