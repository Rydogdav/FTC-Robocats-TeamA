/**
 * Made by Shan B., Collin G., Michael K.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name="Auton Red Glyph Side", group="Autonomous")
public class ATeamAutonRedGSide extends LinearOpMode {

    HardwareMichaelBot robot = new HardwareMichaelBot();
    public int JewelDirection;
    public int Forward = 1;
    public int Backward = -1;

    //Variables that change
    public double JewelNudgeDistance = 4;
    public double ArmUpPos = 1;
    public double ArmDownPos = .15;
    public int StartRed = 1;
    public int StartBlue = -1;
    public double DistanceToMark1 = 30;
    public int MoveTimeout = 10;

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

        //encoderDrive(robot.DRIVE_SPEED, 18, 18, 1);       //(Left Wheel Distance (IN.), Right-Wheel Distance, Timeout (Sec))
        robot.colorSensor.enableLed(true);
        KnockJewel();
        robot.colorSensor.enableLed(false);
        DriveToMark1();
        Turn90(1);
        robot.gripServo1.setPosition(.39);
        robot.gripServo2.setPosition(.55);

    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {

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
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(speed);
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(speed);

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
    public void Turn90(int turn) {
        encoderDrive(robot.DRIVE_SPEED, -15 * turn, 15 * turn, MoveTimeout);       //(Left Wheel Distance (IN.), Right-Wheel Distance, Timeout (Sec))
    }
    public void DriveToMark1() {

        MoveFB(StartRed, DistanceToMark1 - (JewelNudgeDistance * StartBlue)); //Since StartSide is either positive 1 or negative 1 it changes the sign of the subtraction
    }

    public void KnockJewel() {
        robot.gripServo1.setPosition(.9);
        robot.gripServo2.setPosition(.05);
        SetArm(ArmDownPos);
        sleep(1000);
        JewelDirection = DecideFB();
        sleep(1500);
        MoveFB(JewelDirection, JewelNudgeDistance); // distance travelled to knock off ball
        sleep(1000);
        SetArm(ArmUpPos);
        sleep(1000);
    }

    public void SetArm(double ArmPos) {

        robot.JewelKnock.setPosition(ArmPos);
    }

    public int DecideFB() {
        if (robot.colorSensor.blue() > robot.colorSensor.red()) {
            telemetry.addLine("Red Backward");
            telemetry.update();
            return Backward;
        }else if (robot.colorSensor.red() > robot.colorSensor.blue()){
            telemetry.addLine("Red Forward");
            telemetry.update();
            return Forward;
        }else
            return 0;
    }

    public void MoveFB(double Direction, double Distance) {

        encoderDrive(robot.DRIVE_SPEED, Distance * Direction, Distance * Direction, MoveTimeout);
    }

}