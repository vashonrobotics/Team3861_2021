package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.Vision;

@Autonomous(name = "Visionop") // The string here is what the OpMode Shows up as on the phone
// and the @Autonomous declares this OpMode as an Autonomous one

public class Visionop extends LinearOpMode {

    public DcMotor motor;

    @Override
    public void runOpMode() throws InterruptedException {

        motor = hardwareMap.get(DcMotor.class, "testMotor");

        Vision vision = new Vision(this, hardwareMap, telemetry);
        vision.init();

        waitForStart();

        while (!isStopRequested()){
            if (vision.ducktective()) {
                motor.setPower(1);
            } else {
                motor.setPower(0);
            }
        }

    }
}