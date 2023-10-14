package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

// <<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/TestOdometry221022.java
//@TeleOp(name = "android studio working")
//public class TestOdometry221022 extends LinearOpMode {
//=======
@TeleOp(name = "android_studio_working")
public class android_studio_working extends LinearOpMode {
//>>>>>>> 65dff116a207aa9262adb1c444045092fe0642ea:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/android_studio_working.java

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private Servo claw;
    private DcMotor slide1;
    private DcMotor front_left_port_0;

    double drive_mode;
    int Odometry_left;
    int Odometry_right;
    int Odometry_back;
    boolean telemtoggle;
    cOdometry oRobotPosition;

    /**
     * Describe this function...
     */
    private void Dynamic_variables() {
        Odometry_left = back_left_port_3.getCurrentPosition();
        Odometry_right = back_right_port_1.getCurrentPosition();
        Odometry_back = front_right_port_2.getCurrentPosition();
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
        slide1 = hardwareMap.get(DcMotor.class, "slide1");
        front_left_port_0 = hardwareMap.get(DcMotor.class, "front_left_port_0");

        oRobotPosition = new cOdometry();
        oRobotPosition.Init(back_left_port_3.getCurrentPosition(), back_right_port_1.getCurrentPosition(), front_right_port_2.getCurrentPosition());

        initialization();
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                oRobotPosition.Run(back_left_port_3.getCurrentPosition(), back_right_port_1.getCurrentPosition(), front_right_port_2.getCurrentPosition());

                Dynamic_variables();
                holonomic();
                servo();
                slide();
                if (telemtoggle) {
                    telem();
                }
            }
        }
    }

    /**
     * Describe this function...
     */
    private void telem() {
        telemetry.addData("Lbumper", gamepad1.dpad_left);
        telemetry.addData("Rbumper", gamepad1.dpad_right);
        telemetry.addData("clawpos", claw.getPosition());
        telemetry.addData("X", gamepad1.x);
        telemetry.addData("Y", gamepad1.y);
        telemetry.addData("slidepower", slide1.getPower());
        telemetry.addData("Slide1POS", slide1.getCurrentPosition());
        // Configuration file needs to be updated to match where the OW are plugged into the robot.  Now plugged into motor encoder positions.
        telemetry.addData("OW left", Odometry_left);
        telemetry.addData("OW right", Odometry_right);
        telemetry.addData("OW back", Odometry_back);
        telemetry.addData("X Pos", oRobotPosition.X());
        telemetry.addData("Y Pos", oRobotPosition.Y());
        telemetry.addData("Theta", oRobotPosition.Theta());
        telemetry.update();
    }

    /**
     * Describe this function...
     */
    private void initialization() {
        front_right_port_2.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right_port_1.setDirection(DcMotorSimple.Direction.REVERSE);
        claw.scaleRange(0, 1);
        // getting error when run to position mode is set during initialization, error says position must be set first.
        slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // The next block resets the slide encoder to zero.  Make sure the slide is fully down before initializing the program.
        slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_left_port_3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_right_port_1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_left_port_0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_right_port_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_left_port_3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        back_right_port_1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front_left_port_0.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front_right_port_2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive_mode = 1;
        telemtoggle = true;
        claw.setPosition(0);
    }

    /**
     * Describe this function...
     */
    private void slide() {
        if (gamepad1.x) {
            // -3500 appears to be full height extension
            slide1.setTargetPosition(-3140);
            slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ((DcMotorEx) slide1).setVelocity(-1500);
        }
        if (gamepad1.y) {
            slide1.setTargetPosition(0);
            slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ((DcMotorEx) slide1).setVelocity(600);
        }
    }

    /**
     * Describe this function...
     */
    private void holonomic() {
        double Vertical;
        double Horizontal;
        double Pivot;

        if (gamepad1.a) {
            drive_mode = 1;
        }
        if (gamepad1.b) {
            drive_mode = 0.25;
        }
        Vertical = drive_mode * -gamepad1.right_stick_y;
        Horizontal = drive_mode * gamepad1.right_stick_x;
        Pivot = drive_mode * gamepad1.left_stick_x;
        front_right_port_2.setPower(-Pivot + (Vertical - Horizontal));
        back_right_port_1.setPower(-Pivot + Vertical + Horizontal);
        front_left_port_0.setPower(Pivot + Vertical + Horizontal);
        back_left_port_3.setPower(Pivot + (Vertical - Horizontal));
        // Put loop blocks here.
    }

    /**
     * Describe this function...
     */
    private void servo() {
        if (gamepad1.left_bumper) {
            claw.setPosition(0);
        }
        if (gamepad1.right_bumper) {
            claw.setPosition(0.6);
        }
    }
}