/* Made by Collin G., Shan B., Michael K.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp GripperTest", group="Linear Opmode")
public class TeamA_GripperTest extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();
    //public DcMotor liftMotor = null;            //lift motor
    public Servo gripServo1 = null;             //left servo
    public Servo gripServo2 = null;             //right servo
    //public int servoDegreesR;
    //public int servoDegreesL;
    //public double servoEquationL = (1/255 * servoDegreesL);
    //public double servoEquationR = (1/255 * servoDegreesR);

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        gripServo1 = hardwareMap.get(Servo.class, "gripServo1");
        gripServo2 = hardwareMap.get(Servo.class, "gripServo2");
       //liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");

        //liftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            /*while (gamepad1.x) {
                liftMotor.setPower(0.5);
            }
            while (gamepad1.y) {
                liftMotor.setPower(-0.5);
            }*/
            if (gamepad1.b) { //closes gripper
                //servoDegreesL = 160;
                //servoDegreesR = 95;

                gripServo1.setPosition(.62);
                gripServo2.setPosition(.37);
            }
            if (gamepad1.a) { //opens gripper
                //servoDegreesR = 180;
                //servoDegreesL = 0;

                gripServo1.setPosition(.15);
                gripServo2.setPosition(.825);
            }
        }
    }
}
