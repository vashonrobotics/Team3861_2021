package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.BaseBotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.BotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.DriveFactory;
import org.firstinspires.ftc.teamcode.drive.VisionLocalizer;
import org.firstinspires.ftc.teamcode.subsystems.Carosel;
import org.firstinspires.ftc.teamcode.subsystems.Lifter;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

@Autonomous(name = "BradenTestAutoOp")
public class BradenTestAutoOp extends LinearOpMode {

    Vision vision = null;
    Carosel carousel = null;
    BaseBotMecanumDrive drive = null;
    Lifter lifter = null;

    private Trajectory testTrajectory;

    @Override
    public void runOpMode() throws InterruptedException {

        carousel = new Carosel(hardwareMap);
        lifter = new Lifter(hardwareMap);
        vision = new Vision(this, hardwareMap, telemetry);
        vision.init();

        drive = DriveFactory.getDrive(hardwareMap);
        // drive.setVisionLocalizer(vision.getVuforia());
        // VisionLocalizer visionLocalizer = new VisionLocalizer(FtcDashboard.getInstance().getTelemetry(), vision.getVuforia());

        waitForStart();

        // Direcetional Test
        /*
        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate()).lineTo(new Vector2d(5, 0)).build());
        sleep(1000);
        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate()).lineTo(new Vector2d(-5, 0)).build());
        sleep(1000);
        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate()).lineTo(new Vector2d(0, 5)).build());
        sleep(1000);
        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate()).lineTo(new Vector2d(0, -5)).build());
        */

        // Ducktective

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-4, 6, Math.toRadians(90)))
                .build());
        sleep(1000);
        if (vision.ducktective()) {
            lifter.setPowerHex();
            sleep(1000);
            lifter.slap();
            lifter.nothingHex();
            sleep(100);
            lifter.home();
        }


        // Carousel

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-23, 0, 0))
                .build());

        carousel.PowerOn();
        sleep(4000);
        carousel.PowerOff();


        // Red Storage Unit

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineTo(new Vector2d(-30, 36))
                .build());

        // Blue Storage Unit
        /*
        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineTo(new Vector2d(-28, -36))
                .build());
         */
    }
}