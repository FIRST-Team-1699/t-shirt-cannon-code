package com.frc1699.subsystem;

public interface PneumaticsLoop {

    int kPreasureSensorPort = 0; //TODO Change

    //Length of the Cannon Barrels in meters
    double kBarrelLength = 30.0 * 0.0254;
    //Conversion ratio for PSI to Pascals
    double kPsiPaConv = 6894.757;
    //Air Density in kg/m^3
    double kAirDensity = 1.225;
    //Drag Coeff
    double kDragCoeff = 0.82;
    //Diameter of t-shirt in meters
    double kShirtDiameter = 0.0; //TODO Change
    //Cross-sectional area of shirt in meters^2
    double kCrossArea = Math.PI * (kShirtDiameter / 2) * (kShirtDiameter / 2); //TODO Change
    //Mass of T-Shirt in kg
    double kShirtMass = 0.15; //TODO Change
    //Max velocity in m/s
    double kMaxVelocity = 2 * Pneumatics.calcAcceleration(125.0) * kBarrelLength;

    void update();
}
