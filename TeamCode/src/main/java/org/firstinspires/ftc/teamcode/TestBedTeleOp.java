/*
Made by Shan B., Collin G., Michael K.
*/

package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TestBed TeleOP", group="Linear Opmode")
public abstract class TestBedTeleOp extends LinearOpMode {

    public DcMotor motorFrontLeft = null;
    public DcMotor motorBackLeft = null;
    public DcMotor motorFrontRight = null;
    public DcMotor motorBackRight = null;
    public double gearMultiplier = 1.1;

    @Override
    public void runOpMode() {

        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {

            motorFrontRight.setPower(-gamepad1.right_stick_y * gearMultiplier);
            motorBackRight.setPower(-gamepad1.right_stick_y * gearMultiplier);
            motorFrontLeft.setPower(-gamepad1.left_stick_y * gearMultiplier);
            motorBackLeft.setPower(-gamepad1.left_stick_y * gearMultiplier);
        }
    }


}
