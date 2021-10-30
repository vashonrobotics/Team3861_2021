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

       Carosel carosel = new Carosel(hardwareMap);

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

        if (carosel.digitalTouch.getState() == true) {
            telemetry.addData("Digital Touch", "Is Not Pressed");
            sleep(1000);
            carosel.PowerOn();
        } else {
            telemetry.addData("Digital Touch", "Is Pressed");
            carousel.PowerOff();
        }

        /*telemetry.update();
        carousel.PowerOn();
        for (int i = 0; i < 3; i++) {
            carousel.IncreasePower();
            sleep(500);
        }*/


        // Move to warehouse
    }
}
