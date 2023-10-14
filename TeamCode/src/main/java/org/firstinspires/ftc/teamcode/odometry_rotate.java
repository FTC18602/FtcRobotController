package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import java.lang.Math;

@TeleOp(name = "odometry_rotate")
public class odometry_rotate extends LinearOpMode {

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private Servo claw;
    private DcMotor front_left_port_0;

    double drive_mode;
    double Odometry_left;
    double Odometry_right;
    double Odometry_back;
    boolean telemtoggle;

    /**
     * Describe this function...
     */
    private void Dynamic_variables() {
        Odometry_left = back_left_port_3.getCurrentPosition()/(8192/(2*Math.PI));
        Odometry_right = back_right_port_1.getCurrentPosition()/(8192/(2*Math.PI));
        Odometry_back = front_right_port_2.getCurrentPosition()/(8192/(2*Math.PI));
    }

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        back_left_port_3 = hardwareMap.get(DcMotor.class, "back_left_port_3");
        back_right_port_1 = hardwareMap.get(DcMotor.class, "back_right_port_1");
        front_right_port_2 = hardwareMap.get(DcMotor.class, "front_right_port_2");
        claw = hardwareMap.get(Servo.class, "claw");
        front_left_port_0 = hardwareMap.get(DcMotor.class, "front_left_port_0");


        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                Dynamic_variables();
                front_right_port_2.setPower(0.25);
                front_left_port_0.setPower(-0.25);
                back_left_port_3.setPower(-0.25);
                back_right_port_1.setPower(0.25);
                telemetry.addData("OW left", Odometry_left);
                telemetry.addData("OW right", Odometry_right);
                telemetry.addData("OW back", Odometry_back);
                telemetry.addData("owleftrightdiff", Odometry_left-Odometry_right);
                telemetry.update();
                }
            }
        }
    }

