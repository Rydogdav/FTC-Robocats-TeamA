/**
 * Made by Shan B., Collin G., Michael K.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;


@Autonomous (name="Turn", group="Autonomous")
public class Turn extends LinearOpMode {

    public int JewelDirection;
    public int Forward = 1;
    public int Backward = -1;
    HardwareMichaelBot robot  = new HardwareMichaelBot();

    //Variables that change
    public double ArmUpPos = 1;
    public double ArmDownPos = .15;
    public int StartBlue = -1;
    public int StartRed = 1;
    public double DistanceToMark1 = 25; //15
    public int MoveTimeout = 10; // walrus


    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.motorFrontLeft.getCurrentPosition();
        robot.motorBackLeft.getCurrentPosition();
        robot.motorFrontRight.getCurrentPosition();
        robot.motorBackRight.getCurrentPosition();

        waitForStart();

        turn(30);
        sleep(1000);
        turn(-30);
        //sleep(1000);
        //Turn(5);
        //sleep(1000);
        //Turn(-5);

    }

    public void encoderDrive(double Lspeed,double Rspeed, double leftInches, double rightInches, double timeoutS) {

        int newLeftTarget1;
        int newRightTarget1;
        int newLeftTarget2;
        int newRightTarget2;

        if (opModeIsActive()) {

            newLeftTarget1 = robot.motorFrontLeft.getCurrentPosition() + (int) (leftInches * robot.COUNTS_PER_INCH);
            newRightTarget1 = robot.motorFrontRight.getCurrentPosition() + (int) (rightInches * robot.COUNTS_PER_INCH);
            newLeftTarget2 = robot.motorBackLeft.getCurrentPosition() + (int) (leftInches * robot.COUNTS_PER_INCH);
            newRightTarget2 = robot.motorBackRight.getCurrentPosition() + (int) (rightInches * robot.COUNTS_PER_INCH);
            robot.motorFrontLeft.setTargetPosition(newLeftTarget1);
            robot.motorFrontRight.setTargetPosition(newRightTarget1);
            robot.motorBackLeft.setTargetPosition(newLeftTarget2);
            robot.motorBackRight.setTargetPosition(newRightTarget2);

            robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            robot.runtime.reset();
            robot.motorFrontLeft.setPower(Lspeed);
            robot.motorFrontRight.setPower(Rspeed);
            robot.motorBackLeft.setPower(Lspeed);
            robot.motorBackRight.setPower(Rspeed);

            while (opModeIsActive() && (robot.runtime.seconds() < timeoutS) && (robot.motorFrontLeft.isBusy() && robot.motorFrontRight.isBusy() && (robot.motorBackLeft.isBusy() && robot.motorBackRight.isBusy()))) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d : %7d :%7d", newLeftTarget1,  newRightTarget1, newLeftTarget2, newRightTarget2);
                telemetry.addData("Path2",  "Running at %7d :%7d : %7d :%7d", robot.motorFrontLeft.getCurrentPosition(), robot.motorFrontRight.getCurrentPosition(), robot.motorBackLeft.getCurrentPosition(), robot.motorBackRight.getCurrentPosition());
                telemetry.update();
            }

            robot.motorFrontLeft.setPower(0);
            robot.motorBackLeft.setPower(0);
            robot.motorFrontRight.setPower(0);
            robot.motorBackRight.setPower(0);

            robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }
    public void turn(double turn) {
        encoderDrive(robot.DRIVE_SPEED_LEFT, robot.DRIVE_SPEED_RIGHT, (1/6) * turn, (-1/6) * turn, MoveTimeout);       //(Left Wheel Distance (IN.), Right-Wheel Distance, Timeout (Sec))
    }


    public void DriveToMark1() {

        //MoveFB(StartBlue, DistanceToMark1); //Since StartSide is either positive 1 or negative 1 it changes the sign of the subtraction
    }

    public void KnockJewel() {
        robot.gripServo1.setPosition(.9);
        robot.gripServo2.setPosition(.05);
        SetArm(ArmDownPos);
        sleep(1000);
        JewelDirection = DecideFB();
        sleep(1500);
        //turn to knock ball code here
        sleep(1000);
        SetArm(ArmUpPos);
        sleep(1000);
    }

    public void SetArm(double ArmPos) {

        robot.JewelKnock.setPosition(ArmPos);
    }

    public int DecideFB() {
        if (robot.colorSensor.blue() < robot.colorSensor.red()) {
            telemetry.addLine("Blue Backward");
            telemetry.update();
            return Backward;
        }else if (robot.colorSensor.blue() > robot.colorSensor.red()) {
            telemetry.addLine("Blue Forward");
            telemetry.update();
            return Forward;
        }else
            return 0;
    }
}
