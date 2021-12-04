package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.BaseBotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.DriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.Carosel;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

@Autonomous(name = "RedGetOutDaWay")
public class RedGetOutDaWay extends LinearOpMode {

    Vision vision = null;
    Carosel carousel = null;
    BaseBotMecanumDrive drive = null;

    private Trajectory testTrajectory;

    @Override
    public void runOpMode() throws InterruptedException {

        vision = new Vision(this, hardwareMap, telemetry);

        drive = DriveFactory.getDrive(hardwareMap);
        // drive.setVisionLocalizer(vision.getVuforia());
        // VisionLocalizer visionLocalizer = new VisionLocalizer(FtcDashboard.getInstance().getTelemetry(), vision.getVuforia());

        waitForStart();

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineTo(new Vector2d(28, 0))
                .build());

        // Blue
        /*
        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineTo(new Vector2d(28, -32))
                .build());
        */
        // Red

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineTo(new Vector2d(28, 32))
                .build());

    }
}