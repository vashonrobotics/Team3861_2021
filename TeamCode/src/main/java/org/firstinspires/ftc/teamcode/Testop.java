package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

// This is an opmode that tests to see if we have successfully gotten code to the robot
//  It also goes through and explains everything that goes on


@Autonomous(name = "Testop") // The string here is what the OpMode Shows up as on the phone
                                 // and the @Autonomous declares this OpMode as an Autonomous one

public class Testop extends LinearOpMode { // LinearOpMode is the base of all the OpModes we will be using

    private DcMotor testMotor; // Creates a DcMotor Object

    @Override // This is overriding a previously defined method. I.E. runOpMode() already exists,
              // but we override it, and thats where everything physical happens

    public void runOpMode() throws InterruptedException { // "throws InterruptedException" is saying that when something goes
                                                          // terribly wrong, it will stop the code.
                                                          // DO NOT REMOVE: it could lead to serious damage to our robot

        testMotor = hardwareMap.get(DcMotor.class, "testMotor"); // The deviceName MUST match the name of
                                                                           // The motor on the phone

        testMotor.setDirection(DcMotorSimple.Direction.FORWARD); // Might not be necessary,
                                                                 // but all of last years code did something similar

        waitForStart(); // Make sure to waitForStart() before turning on any motors/servos...
                        // If you don't it will get angry, and throw an Interrupted Exception

        testMotor.setPower(0.5); // Turn on at half power
        sleep(1000);   // Wait 1 second
        testMotor.setPower(0);   // Turn off
    }
}