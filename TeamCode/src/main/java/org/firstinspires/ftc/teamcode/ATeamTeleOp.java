/*
Made by Shan B., Collin G., Michael K.
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp Alpha 1.0", group="Linear Opmode")
public class ATeamTeleOp extends LinearOpMode {
    
    public ElapsedTime runtime = new ElapsedTime();
    public DcMotor motorFrontLeft = null;
    public DcMotor motorBackLeft = null;
    public DcMotor motorFrontRight = null;
    public DcMotor motorBackRight = null;
    public DcMotor liftMotor;
    public Servo gripServo1 = null; // left servo
    public Servo gripServo2 = null; // right servo
    public TouchSensor limitSwitch = null;
    public double gearMultiplier = 1.1;

    @Override
    public void runOpMode() {
        telemetry.addLine("Initializing");
        telemetry.update();

        gripServo1 = hardwareMap.get(Servo.class, "gripServo1");
        gripServo2 = hardwareMap.get(Servo.class, "gripServo2");

        limitSwitch = hardwareMap.get(TouchSensor.class, "limitSwitch");

        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        motorFrontLeft = hardwareMap.get(DcMotor.class, "leftDrive1");
        motorFrontRight = hardwareMap.get(DcMotor.class, "rightDrive1");
        motorBackLeft = hardwareMap.get(DcMotor.class, "leftDrive2");
        motorBackRight = hardwareMap.get(DcMotor.class, "rightDrive2");

        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);

        telemetry.addLine("Initialization Complete");
        telemetry.update();

        waitForStart();
        telemetry.clearAll();
        runtime.reset();

        telemetry.addLine("Started");
        telemetry.update();

        while (opModeIsActive()) {
            if (gamepad1.x) {
                liftMotor.setPower(.65);
            } else if (gamepad1.y && (!limitSwitch.isPressed())) {
                liftMotor.setPower(-.35);
            } else {
                liftMotor.setPower(0);
            }

            if (gamepad1.b) { //opens gripper

                gripServo1.setPosition(.47);
                gripServo2.setPosition(.47);
            } else if (gamepad1.a) { //closes gripper

                gripServo1.setPosition(.9);
                gripServo2.setPosition(.05);
            }

            if (gamepad1.left_bumper){

                gearMultiplier = .5;
            }

            if (gamepad1.right_bumper) {

                gearMultiplier = 1.1;
            }
            
            motorFrontRight.setPower(-gamepad1.right_stick_y * gearMultiplier);
            motorBackRight.setPower(-gamepad1.right_stick_y * gearMultiplier);
            motorFrontLeft.setPower(-gamepad1.left_stick_y * gearMultiplier);
            motorBackLeft.setPower(-gamepad1.left_stick_y * gearMultiplier);
            

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", gamepad1.left_stick_y, gamepad1.right_stick_y);
            telemetry.update();

            idle();
        }
    }
}