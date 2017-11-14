/*
Made by Shan B., Collin G., Michael K.
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name="Auton Red Glyph Side", group="Autonomous")
public class ATeamAutonRedGSide extends LinearOpMode {

    public static final double COUNTS_PER_MOTOR_REV1 = 1024;
    //public static final double COUNTS_PER_MOTOR_REV2 = 560;
    //public static final double DRIVE_GEAR_REDUCTION = 40;//1.0;
    public static final double WHEEL_DIAMETER_INCHES = 4.0;
    public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV1 / (WHEEL_DIAMETER_INCHES * Math.PI));
    //public static final double COUNTS_PER_INCH2 = (COUNTS_PER_MOTOR_REV1 * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
    public static final double DRIVE_SPEED = 0.5;
    public static final double TURN_SPEED = 0.5;
    public ElapsedTime runtime = new ElapsedTime();
    public DcMotor leftDrive1 = null;
    public DcMotor leftDrive2 = null;
    public DcMotor rightDrive1 = null;
    public DcMotor rightDrive2 = null;
    public Servo JewelKnock = null;
    public ColorSensor colorSensor = null;
    public int JewelDirection;
    public int Forward = 1;
    public int Backward = -1;
    //public int error = 0;
    //public int kp = 1;

    //Variables that change
    public double JewelNudgeDistance = 4;
    public double ArmUpPos = 1;
    public double ArmDownPos = 0;
    public int StartRed = 1;
    public int StartBlue = -1;
    public double DistanceToBox = 40;
    public int MoveTimeout = 10;


    @Override
    public void runOpMode() {

        leftDrive1  = hardwareMap.get(DcMotor.class, "leftDrive1");
        rightDrive1 = hardwareMap.get(DcMotor.class, "rightDrive1");
        leftDrive2  = hardwareMap.get(DcMotor.class, "leftDrive2");
        rightDrive2 = hardwareMap.get(DcMotor.class, "rightDrive2");

        JewelKnock = hardwareMap.get (Servo.class, "jewelKnock");

        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

        leftDrive1.setDirection(DcMotor.Direction.REVERSE);
        rightDrive1.setDirection(DcMotor.Direction.FORWARD);
        leftDrive2.setDirection(DcMotor.Direction.REVERSE);
        rightDrive2.setDirection(DcMotor.Direction.FORWARD);

        leftDrive1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        leftDrive1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDrive2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftDrive1.getCurrentPosition();
        leftDrive2.getCurrentPosition();
        rightDrive1.getCurrentPosition();
        rightDrive2.getCurrentPosition();

        waitForStart();

        //encoderDrive(DRIVE_SPEED, 18, 18, 1);       //(Left Wheel Distance (IN.), Right-Wheel Distance, Timeout (Sec))
        colorSensor.enableLed(true);
        KnockJewel();
        colorSensor.enableLed(false);
        DriveToBox();
    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {

        int newLeftTarget1;
        int newRightTarget1;
        int newLeftTarget2;
        int newRightTarget2;

        if (opModeIsActive()) {

            newLeftTarget1 = leftDrive1.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget1 = rightDrive2.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            newLeftTarget2 = leftDrive1.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget2 = rightDrive2.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            leftDrive1.setTargetPosition(newLeftTarget1);
            rightDrive1.setTargetPosition(newRightTarget1);
            leftDrive2.setTargetPosition(newLeftTarget2);
            rightDrive2.setTargetPosition(newRightTarget2);

            leftDrive1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftDrive2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            leftDrive1.setPower(speed);
            rightDrive1.setPower(speed);
            leftDrive2.setPower(speed);
            rightDrive2.setPower(speed);

            while (opModeIsActive() && (runtime.seconds() < timeoutS) && (leftDrive1.isBusy() && rightDrive1.isBusy() && (leftDrive2.isBusy() && rightDrive2.isBusy()))) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d : %7d :%7d", newLeftTarget1,  newRightTarget1, newLeftTarget2, newRightTarget2);
                telemetry.addData("Path2",  "Running at %7d :%7d : %7d :%7d", leftDrive1.getCurrentPosition(), rightDrive1.getCurrentPosition(), leftDrive2.getCurrentPosition(), rightDrive2.getCurrentPosition());
                telemetry.update();
            }

            leftDrive1.setPower(0);
            leftDrive2.setPower(0);
            rightDrive1.setPower(0);
            rightDrive2.setPower(0);

            leftDrive1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftDrive2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightDrive1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightDrive2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }
    public void DriveToBox() {

        MoveFB(StartBlue, DistanceToBox - (JewelNudgeDistance * StartBlue)); //Since StartSide is either positive 1 or negative 1 it changes the sign of the subtraction
    }

    public void KnockJewel() {
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

        JewelKnock.setPosition(ArmPos);
    }

    public int DecideFB() {
        if (colorSensor.blue() > colorSensor.red()) {
            telemetry.addLine("Red Backward");
            telemetry.update();
            return Backward;
        }else if (colorSensor.red() > colorSensor.blue()){
            telemetry.addLine("Red Forward");
            telemetry.update();
            return Forward;
        }else
            return 0;
    }

    public void MoveFB(double Direction, double Distance) {

        encoderDrive(DRIVE_SPEED, Distance * Direction, Distance * Direction, MoveTimeout);
    }

}