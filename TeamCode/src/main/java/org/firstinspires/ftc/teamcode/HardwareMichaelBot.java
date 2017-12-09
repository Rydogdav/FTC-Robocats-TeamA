package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Shan Baig and Collin on 12/5/2017.
 */

public class HardwareMichaelBot {

    public static final double COUNTS_PER_MOTOR_REV1 = 1024;
    public static final double WHEEL_DIAMETER_INCHES = 4.0;
    public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV1 / (WHEEL_DIAMETER_INCHES * Math.PI));
    public static final double DRIVE_SPEED = 0.5;
    public static final double TURN_SPEED = 0.5;
    
    public ElapsedTime runtime = new ElapsedTime();
    public DcMotor motorFrontLeft = null;
    public DcMotor motorBackLeft = null;
    public DcMotor motorFrontRight = null;
    public DcMotor motorBackRight = null;
    public DcMotor liftMotor;
    public DcMotor relicMotor;
    public Servo gripServo1 = null; // left servo
    public Servo gripServo2 = null; // right servo
    public Servo JewelKnock = null;
    public Servo relicServo = null;
    public TouchSensor limitSwitch = null;
    public ColorSensor colorSensor = null;
            
    HardwareMap hwMap  = null;

    public HardwareMichaelBot() {

    }

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;
        
        gripServo1 = hwMap.get(Servo.class, "gripServo1");
        gripServo2 = hwMap.get(Servo.class, "gripServo2");
        JewelKnock = hwMap.get(Servo.class, "jewelKnock");
        relicServo = hwMap.get(Servo.class, "relicServo");

        limitSwitch = hwMap.get(TouchSensor.class, "limitSwitch");

        liftMotor = hwMap.get(DcMotor.class, "liftMotor");
        relicMotor = hwMap.get(DcMotor.class, "relicMotor");
        motorFrontLeft = hwMap.get(DcMotor.class, "leftDrive1");
        motorFrontRight = hwMap.get(DcMotor.class, "rightDrive1");
        motorBackLeft = hwMap.get(DcMotor.class, "leftDrive2");
        motorBackRight = hwMap.get(DcMotor.class, "rightDrive2");

        colorSensor = hwMap.get(ColorSensor.class, "colorSensor");

        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
    }
    
}
