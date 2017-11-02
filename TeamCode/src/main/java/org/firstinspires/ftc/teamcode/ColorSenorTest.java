package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by FTC Robotics on 11/2/2017.
 */
@TeleOp (name="Color Sensor Test", group="Linear Opmode")
public abstract class ColorSenorTest extends LinearOpMode {

    public ColorSensor colorSensor = null;

    @Override
    public void runOpMode() {

       colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

       waitForStart();

       while(opModeIsActive()) {

           telemetry.addData("Red Value", colorSensor.red());
           telemetry.addData("Blue Value", colorSensor.blue());
           telemetry.addData("Green Value", colorSensor.green());
           telemetry.update();
       }


    }
}
