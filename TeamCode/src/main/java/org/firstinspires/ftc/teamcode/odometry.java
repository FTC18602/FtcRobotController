package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.Math;

@TeleOp(name = "odometry")
public class odometry extends LinearOpMode {

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private DcMotor front_left_port_0;


    double Odometry_left_current;
    double Odometry_right_current;
    double Odometry_back_current;
    double Odometry_left_current_old;
    double Odometry_right_current_old;
    double Odometry_back_current_old;
    double RadiusWheel;           // wheel radius
    double TicksPerCM;
    double DistLefttoRight;           // cm distance between left and right encoders
    double DistBacktoOrigin;          // cm distance between robot origin and back encoder


    private void All_variables_0(){
        Odometry_back_current = 0;
        Odometry_right_current = 0;
        Odometry_back_current_old = 0;
        Odometry_right_current_old = 0;
        Odometry_left_current = 0;
        Odometry_left_current_old = 0;
        RadiusWheel = 0;
        TicksPerCM = 0;
        DistLefttoRight = 0;
        DistBacktoOrigin = 0;
    }

    private void dynamics(){
        Odometry_left_current = back_left_port_3.getCurrentPosition();
        Odometry_right_current = back_right_port_1.getCurrentPosition();
        Odometry_back_current = front_right_port_2.getCurrentPosition();
    }

    private void constants(){
        RadiusWheel = 3;
        TicksPerCM = 2*Math.PI*RadiusWheel/8192;
        DistLefttoRight = 5;
        DistBacktoOrigin = 5;
    }

    public void runOpMode() {


    }
}