package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.BaseBotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.BotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.DriveFactory;
import org.firstinspires.ftc.teamcode.drive.VisionLocalizer;
import org.firstinspires.ftc.teamcode.subsystems.Carosel;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.util.AssetsTrajectoryManager;


@Autonomous(name = "Autonomous")
public class AutonomousOpMode extends LinearOpMode {

    Vision vision = null;
    Carosel carousel = null;
    BaseBotMecanumDrive drive = null;

    private Trajectory testTrajectory;

    @Override
    public void runOpMode() throws InterruptedException {

        vision = new Vision(this, hardwareMap, telemetry);
        vision.init();

        drive = DriveFactory.getDrive(hardwareMap);
        drive.setVisionLocalizer(vision.getVuforia());

       Carosel carosel = new Carosel(hardwareMap);


        // testTrajectory = AssetsTrajectoryManager.load("simpleForward");
        testTrajectory = drive.trajectoryBuilder(drive.getPoseEstimate()).forward(20).build();

        waitForStart();

        drive.followTrajectory(testTrajectory);

        /*
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
