/*
Made by Shan B., Collin G., Michael K.
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name="Vuforia Example Code", group ="Autonomous")
@Disabled
public class VuforiaTest extends LinearOpMode {

    public int JewelDirection;
    public int Forward = 1;
    public int Backward = -1;
    HardwareMichaelBot robot = new HardwareMichaelBot();

    //Variables that change
    public double JewelNudgeDistance = 4;
    public double ArmUpPos = 1;
    public double ArmDownPos = .15;
    public int StartBlue = -1;
    public double distanceToCenterBox = 29;
    public int MoveTimeout = 10;


    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AW7cBtn/////AAAAGYMkfBwkBk+RtHulA/YtafKMtOkKRJGL0" +
                "uSzzGVGQDzIzhkvOQY4lJnPIwwP7MBF5k8AKuhF0QwYx4nlBgTOlMv23OSHNfvy9tE0Egigzp" +
                "bi7p0zUS3bgP9XPd8IsdyQQhwdQQFY64eZiNxqstMPQqOhCyZ+MuQjWWiW1gAeGaPNxD8sUUS" +
                "FbcP0F0LfTWY8JYNFDjr7vUfB8koqwsWCYrB8gQH9ZAwVRDQlHbpx4z1ZEySLnDCRXX0Ns4R1" +
                "PktDJeHJq4Xj0wInNVNPLwCSXaw/yLDkPGa+IWws63uWwr3h4TaxJEs4zgqqbTyCVDPgeAtjm" +
                "wT6KCrrlsS6kW1czPF8bNWq4U5BdinbKBZGrKS7";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        robot.init(hardwareMap);

        robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.motorFrontLeft.getCurrentPosition();
        robot.motorFrontLeft.getCurrentPosition();
        robot.motorFrontRight.getCurrentPosition();
        robot.motorBackRight.getCurrentPosition();

        waitForStart();

        relicTrackables.activate();

        while (opModeIsActive()) {

            /**
             * See if any of the instances of {@link relicTemplate} are currently visible.
             * {@link RelicRecoveryVuMark} is an enum which can have the following values:
             * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
             * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
             */
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark == RelicRecoveryVuMark.CENTER) {
                telemetry.addData("VuMark", "%s visible", vuMark);
                telemetry.update();

                robot.colorSensor.enableLed(true);
                KnockJewel();
                robot.colorSensor.enableLed(false);
                sleep(1000);
                DriveToMark1(distanceToCenterBox);
                Turn30(-3);
                encoderDrive(robot.DRIVE_SPEED_LEFT, robot.DRIVE_SPEED_RIGHT, 6,6, 5);
                robot.gripServo1.setPosition(.39);
                robot.gripServo2.setPosition(.55);
                encoderDrive(robot.DRIVE_SPEED_LEFT, robot.DRIVE_SPEED_RIGHT, -3, -3, 5);

            }
        }
    }
    public void encoderDrive(double Lspeed,double Rspeed,  double leftInches, double rightInches, double timeoutS) {

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

            while (opModeIsActive() && (robot.runtime.seconds() < timeoutS) && (robot.motorFrontLeft.isBusy() && robot.motorFrontRight.isBusy() && (robot.motorFrontLeft.isBusy() && robot.motorBackRight.isBusy()))) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d : %7d :%7d", newLeftTarget1, newRightTarget1, newLeftTarget2, newRightTarget2);
                telemetry.addData("Path2", "Running at %7d :%7d : %7d :%7d", robot.motorFrontLeft.getCurrentPosition(), robot.motorFrontRight.getCurrentPosition(), robot.motorFrontLeft.getCurrentPosition(), robot.motorBackRight.getCurrentPosition());
                telemetry.update();
            }

            robot.motorFrontLeft.setPower(0);
            robot.motorFrontRight.setPower(0);
            robot.motorBackLeft.setPower(0);
            robot.motorBackRight.setPower(0);

            robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }

    public void Turn30(double turn) {
        encoderDrive(robot.DRIVE_SPEED_LEFT, robot.DRIVE_SPEED_RIGHT, -5 * turn, 5 * turn, MoveTimeout);       //(Left Wheel Distance (IN.), Right-Wheel Distance, Timeout (Sec))
    }

    public void DriveToMark1(double distance) {

        MoveFB(StartBlue, distance - (JewelNudgeDistance * StartBlue)); //Since StartSide is either positive 1 or negative 1 it changes the sign of the subtraction
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
        if (robot.colorSensor.blue() < robot.colorSensor.red()) {
            telemetry.addLine("Blue Backward");
            telemetry.update();
            return Backward;
        } else if (robot.colorSensor.blue() > robot.colorSensor.red()) {
            telemetry.addLine("Blue Forward");
            telemetry.update();
            return Forward;
        } else
            return 0;
    }

    public void MoveFB(double Direction, double Distance) {

        encoderDrive(robot.DRIVE_SPEED_LEFT, robot.DRIVE_SPEED_RIGHT, Distance * Direction, Distance * Direction, MoveTimeout);
    }
}