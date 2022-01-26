package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.BaseBotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.DriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.Carosel;
import org.firstinspires.ftc.teamcode.subsystems.Lifter;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

@Autonomous(name = "BradenTestAutoOp")
public class BradenTestAutoOp extends LinearOpMode {

    Vision vision = null;
    Carosel carousel = null;
    BaseBotMecanumDrive drive = null;
    Lifter lifter = null;

    private boolean duckFirst;
    private boolean duckSecond;

    private Trajectory testTrajectory;

    @Override
    public void runOpMode() throws InterruptedException {

        carousel = new Carosel(hardwareMap);
        lifter = new Lifter(hardwareMap);
        vision = new Vision(this, hardwareMap, telemetry);
        vision.init();

        drive = DriveFactory.getDrive(hardwareMap);

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

        // x is forward
        // y is left

        */

        // Ducktective
        // lifter.slap();

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(6, 0, 0))
                .build());
        sleep(1000);

        if (vision.ducktective()) {
            duckFirst = true;
        } else {
            duckFirst = false;
            drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                    .lineToLinearHeading(new Pose2d(6, -6, 0))
                    .build());
            sleep(2000);
            if (vision.ducktective()) {
                duckSecond = true;
            } else {
                duckSecond = false;
            }
        }

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(21.5, 23, 0))
                .build());

        if (duckFirst) {
            lifter.armBottomLayer();
        } else if (duckSecond) {
            lifter.armMiddleLayer();
        } else {
            lifter.armTopLayer();
        }

        sleep(2000);

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(23.5, 23, 0))
                .build());
        sleep(1000);

        lifter.barf(1);
        sleep(500);

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(21, 23, 0))
                .build());

        lifter.armDown();
        lifter.intakeStop();
        lifter.intakeStop();

        vision.shutdown();
        sleep(3000);

        /*
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