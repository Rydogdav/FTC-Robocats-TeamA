/**
 * Made by Shan B., Collin G., Michael K.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="TeleOp Alpha 1.0", group="Linear Opmode")
public class ATeamTeleOp extends LinearOpMode {

    public double gearMultiplier = 1.1;
    public double ArmUpPos = 1;
    public double ArmDownPos = 0;
    HardwareMichaelBot robot  = new HardwareMichaelBot();



    @Override
    public void runOpMode() {

        robot.init(hardwareMap);
        telemetry.addLine("Initializing");
        telemetry.update();

        telemetry.addLine("Initialization Complete");
        telemetry.update();

        waitForStart();
        robot.JewelKnock.setPosition(ArmUpPos);
        telemetry.clearAll();
        robot.runtime.reset();
        robot.gripServo1.setPosition(.47);
        robot.gripServo2.setPosition(.47);

        telemetry.addLine("Started");
        telemetry.update();

        while (opModeIsActive()) {
            if (gamepad1.x) {
                robot.liftMotor.setPower(1);
            } else if (gamepad1.y && (!robot.limitSwitch.isPressed())) {
                robot.liftMotor.setPower(-.5);
            } else {
                robot.liftMotor.setPower(0);
            }

            if (gamepad1.right_bumper) {

                robot.relicServo.setPosition(0);
            }

            if (gamepad1.left_bumper) {

                robot.relicServo.setPosition(1);
            }

            if (gamepad1.b) { //opens gripper

                robot.gripServo1.setPosition(.47);
                robot.gripServo2.setPosition(.47);
            } else if (gamepad1.a) { //closes grippers

                robot.gripServo1.setPosition(.9);
                robot.gripServo2.setPosition(.05);
            }

            if (gamepad1.left_trigger > 0){

                gearMultiplier = .2;
            }

            if (gamepad1.right_trigger > 0) {

                gearMultiplier = 1.1;
            }
            if (gamepad1.dpad_up) {
                robot.relicMotor.setPower(.25);
            } else if (gamepad1.dpad_down) {
                robot.relicMotor.setPower(-.25);
            } else {
                robot.relicMotor.setPower(0);
            }

            robot.motorFrontLeft.setPower(-gamepad1.left_stick_y * gearMultiplier);
            robot.motorBackLeft.setPower(-gamepad1.left_stick_y * gearMultiplier);
            robot.motorFrontRight.setPower(-gamepad1.right_stick_y * gearMultiplier * 0.7);  // speed factor added to drive straighter
            robot.motorBackRight.setPower(-gamepad1.right_stick_y * gearMultiplier * 0.7);   // speed factor added to drive straighter

            telemetry.addData("Status", "Run Time: " + robot.runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", gamepad1.left_stick_y, gamepad1.right_stick_y);
            telemetry.update();

            idle();
        }
    }
}