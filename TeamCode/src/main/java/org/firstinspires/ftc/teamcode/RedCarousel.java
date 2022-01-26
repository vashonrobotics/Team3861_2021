package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.BaseBotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.DriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.Carosel;
import org.firstinspires.ftc.teamcode.subsystems.Lifter;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

@Autonomous(name = "RedCarousel")
public class RedCarousel extends LinearOpMode {

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

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(8, 0, 0))
                .build());
        sleep(1000);

        if (vision.ducktective()) {
            duckFirst = true;
        } else {
            duckFirst = false;
            drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                    .lineToLinearHeading(new Pose2d(8, -6, 0))
                    .build());
            sleep(1000);
            duckSecond = vision.ducktective();
        }

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(23, -30, 0))
                .build());

        if (duckFirst) {
            lifter.armBottomLayer();
        } else if (duckSecond) {
            lifter.armMiddleLayer();
        } else {
            lifter.armTopLayer();
        }

        sleep(800);

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(24.5, -30, 0))
                .build());
        sleep(1000);

        lifter.barf(0.8);
        sleep(500);

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(22, -30, 0))
                .build());

        lifter.armDown();
        lifter.intakeStop();

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(0, 17, -Math.toRadians(90)))
                .build());
        carousel.setDirection(DcMotorSimple.Direction.REVERSE);
        carousel.PowerOn();
        sleep(4200);
        carousel.PowerOff();

        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(30, 24, -Math.toRadians(90)))
                .build());

        vision.shutdown();
    }
}
