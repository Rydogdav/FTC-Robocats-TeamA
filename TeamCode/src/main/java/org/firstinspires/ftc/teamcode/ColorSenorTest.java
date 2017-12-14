package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by FTC Robotics on 11/2/2017.
 */
@TeleOp (name="Color Senor Test", group="Linear Opmode")
public class ColorSenorTest extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();
    public ColorSensor colorSensor = null;

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing");
        telemetry.update();

        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

        telemetry.addLine("Initialization Complete");
        telemetry.update();

       waitForStart();
        telemetry.clearAll();
        runtime.reset();


        telemetry.addLine("Started");
        telemetry.update();

       while(opModeIsActive()) {

           colorSensor.enableLed(true);
           telemetry.addData("Red Value", colorSensor.red());
           telemetry.addData("Blue Value", colorSensor.blue());
           telemetry.addData("Green Value", colorSensor.green());
           telemetry.update();

           idle();
       }
    }
}
