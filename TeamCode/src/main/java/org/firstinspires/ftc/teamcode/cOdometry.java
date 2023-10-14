package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.lang.Math;

public class cOdometry {

    private int Odometry_Left;
    private int Odometry_Right;
    private int Odometry_Back;

    private double PosX; // in cm
    private double PosY; // in cm
    private double PosTheta; // in radians

    final private double WheelRadius=1.375/2*2.54; //in Inches converted to CM
    final private double LeftRightDist=7.625*2.54; //Distance between the left and right wheels Inches converted to CM
    final private double BackOrigDist=8.25*2.54; //Distance from back odomoetry wheel to robot origin Inches converted to CM

    final private double CMperTick =  2*Math.PI*WheelRadius/8192;

    public void Init (int Left, int Right, int Back) {
        Odometry_Left=Left;
        Odometry_Right=Right;
        Odometry_Back=Back;
        PosX=0;
        PosY=0;
        PosTheta=0;
    }
    public void Run(int Left, int Right, int Back) {
        double DeltaLeft;
        double DeltaRight;
        double DeltaBack;
        double DeltaPosX;
        double DeltaPosY;
        double DeltaPosTheta;

        DeltaLeft=Odometry_Left - Left;
        DeltaRight=Odometry_Right - Right;
        DeltaBack=Odometry_Back - Back;

        DeltaPosX=CMperTick*((DeltaLeft+DeltaRight)/2);
        DeltaPosY=CMperTick*(DeltaBack-BackOrigDist*((DeltaRight-DeltaLeft)/LeftRightDist));
        DeltaPosTheta=CMperTick*((DeltaRight-DeltaLeft)/LeftRightDist);

        PosTheta=PosTheta+DeltaPosTheta;
        PosX=PosX+DeltaPosX*Math.cos(PosTheta)-DeltaPosY*Math.sin(PosTheta);
        PosY=PosY+DeltaPosX*Math.sin(PosTheta)+DeltaPosY*Math.cos(PosTheta);

        Odometry_Left=Left;
        Odometry_Right=Right;
        Odometry_Back=Back;
    }

    public double X() {
        return PosX*2.54;
    } //output in inches
    public double Y() {
        return PosY*2.54;
    } //output in inches
    public double Theta() {return PosTheta*(180/Math.PI); } //output in degrees
}
