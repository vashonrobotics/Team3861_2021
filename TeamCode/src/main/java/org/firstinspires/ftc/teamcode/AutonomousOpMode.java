package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.drive.Drive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.BaseBotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.BotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.DriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.Carosel;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

@Autonomous(name = "Autonomous")
public class AutonomousOpMode extends LinearOpMode {

    Vision vision = null;
    Carosel carousel = null;
    BaseBotMecanumDrive drive = null;

    @Override
    public void runOpMode() throws InterruptedException {

        vision = new Vision(this, hardwareMap, telemetry);
        vision.init();

        drive = DriveFactory.getDrive(hardwareMap);

        carousel = new Carosel(hardwareMap);

        waitForStart();
        // Move to scan
        if (vision.ducktective()) {
            // Move arm to corresponding level
        } else {
            // Move to scan second spot
            if (vision.ducktective()) {
                // move arm to corresponding level
            } else {
                // move arm to remaining level
            }
        }

        // Move to alliance Shipping hub
        // Place block at correct level

        // Move to carosel
        carousel.PowerOn();
        for (int i = 0; i < 3; i++) {
            carousel.IncreasePower();
            sleep(500);
        }
        carousel.PowerOff();

        // Move to warehouse
    }
}
