package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
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


@Autonomous(name = "SimpleAutonomous")
public class SimpleAutonomous extends LinearOpMode {

    Vision vision = null;
    Carosel carousel = null;
    BaseBotMecanumDrive drive = null;

    private Trajectory testTrajectory;
    private Trajectory goRight;
    private Trajectory moveForward;
    private Trajectory moveFromWall;

    private Trajectory moveToCarousel;

    private Pose2d blueWarehouse;
    private Pose2d blueStorage;
    private Pose2d redWarehouse;
    private Pose2d redStorage;

    private Pose2d blueCarousel;
    private Pose2d redCarousel;

    @Override
    public void runOpMode() throws InterruptedException {

        vision = new Vision(this, hardwareMap, telemetry);
        vision.init();

        drive = DriveFactory.getDrive(hardwareMap);
        // drive.setVisionLocalizer(vision.getVuforia());

        carousel = new Carosel(hardwareMap);

        /*
        blueWarehouse = new Pose2d(12, -60, 3 * Math.PI / 2);
        blueStorage = new Pose2d(-60, -36, 0);
        redWarehouse = new Pose2d(60, 12, Math.PI);
        redStorage = new Pose2d(60, -36, Math.PI);

        blueCarousel = new Pose2d(-65, -60, 0);

        drive.getLocalizer().setPoseEstimate( blueWarehouse);

        moveToCarousel = drive.trajectoryBuilder(drive.getPoseEstimate()).lineToLinearHeading(blueCarousel).build();
        moveFromWall = drive.trajectoryBuilder(drive.getPoseEstimate()).forward(5).build();
        moveForward = drive.trajectoryBuilder(drive.getPoseEstimate()).forward(20).build(); */
        // testTrajectory = AssetsTrajectoryManager.load("simpleForward");
        // testTrajectory = drive.trajectoryBuilder(drive.getPoseEstimate()).lineTo(new Vector2d(0, 0)).build();
        // goRight =        drive.trajectoryBuilder(drive.getPoseEstimate()).strafeRight(20).build();


        waitForStart();

        drive.setWeightedDrivePower(new Pose2d(1, 0, 0));
        sleep(1000);
        drive.setWeightedDrivePower(new Pose2d(0,0,0));

        // drive.followTrajectory(moveForward);
        // drive.followTrajectory(moveFromWall);
        // drive.followTrajectory(moveToCarousel);
        //drive.followTrajectory(goRight);
        /* carousel.PowerOn();
        sleep(3000);
        carousel.PowerOff(); */

        //if(vision.ducktective()) {
        //}
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
