package org.firstinspires.ftc.teamcode;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.drive.BaseBotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.DriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.JHopper;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.WobbleArm;
import org.firstinspires.ftc.teamcode.util.HeadingCalculator;

import java.time.Duration;

import static org.firstinspires.ftc.teamcode.subsystems.WobbleArm.ArmPosition.DOWN;
import static org.firstinspires.ftc.teamcode.subsystems.WobbleArm.ArmPosition.HOLD;
import static org.firstinspires.ftc.teamcode.subsystems.WobbleArm.ArmPosition.LOAD;
import static org.firstinspires.ftc.teamcode.subsystems.WobbleArm.ArmPosition.UP;

@TeleOp(name = "Teleop")
public class Teleop extends LinearOpMode {

    public static final double ARM_STEP = 0.01;
    public WobbleArm wobbleArm = null;
    private Intake intake = null;
    private Shooter shooter = null;
    private JHopper jHopper = null;

    long lastButtonPress = 0;
    long lastJHopAdvance = 0;
    private long timeOfLower;

    enum States {
        NORMAL,
        LOWERING,
        LOWERED,
        LIFTING
    }

    private States states = States.NORMAL;

    public void runOpMode() throws InterruptedException {

        wobbleArm = new WobbleArm(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        jHopper = new JHopper(hardwareMap);

        BaseBotMecanumDrive drive = DriveFactory.getDrive(hardwareMap);

        // grab the pose from the autonomous mode.
        Pose2d handoffPose = PositionHandoff.getPose2d();
        if(handoffPose != null) {
            drive.setPoseEstimate(handoffPose);
        } else {
            // make something up
            drive.setPoseEstimate(new Pose2d(-48, 24, 0));
        }

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        wobbleArm.setArmPosition(UP);

        while (!isStopRequested()) {
            double scaling = 1.0;
            if(gamepad1.b) {
                scaling = 0.5;
            }
            //holding b and trying to move in a not turning way crashes it

            drive.setWeightedDrivePower(//turning and moving and strafing
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x * scaling
                    )
            );


            switch(states) {
                case NORMAL:
                    if (gamepad1.a) {
                        wobbleArm.setArmPosition(LOAD);
                        timeOfLower = System.currentTimeMillis();
                        states = States.LOWERING;
                    }
                break;
                case LOWERING:
                    if (System.currentTimeMillis() - timeOfLower > 1000) {
                        states = States.LOWERED;
                    }
                break;
                case LOWERED:
                    Trajectory trajectoryBack = drive.trajectoryBuilder(drive.getPoseEstimate())
                            .back(8)
                            .build();
                    drive.followTrajectory(trajectoryBack);
                    states = States.LIFTING;
                break;
                case LIFTING:
                    wobbleArm.setArmPosition(HOLD);
                    states = States.NORMAL;
                break;
            }

            if (gamepad2.dpad_down) {
                wobbleArm.setArmPosition(LOAD);
                //lower wobble goal arm
            }
            if (gamepad2.dpad_left) {
                //lift wobble goal arm
                wobbleArm.setArmPosition(HOLD);
            }
            if (gamepad2.dpad_up) {
                wobbleArm.setArmPosition(UP);
            }
            if (gamepad2.dpad_right ){
                wobbleArm.setArmPosition(HOLD);
            }

//            if (gamepad2.dpad_down && deadTimeAfterPressPassed()) {
//                currentArmPosition = Math.max(0, currentArmPosition - ARM_STEP);
//                wobbleArm.setArmPosition(currentArmPosition);
//                lastButtonPress = System.currentTimeMillis();
//            }
//
//            if (gamepad2.dpad_up && deadTimeAfterPressPassed()) {
//                currentArmPosition = Math.min(1, currentArmPosition + ARM_STEP);
//                wobbleArm.setArmPosition(currentArmPosition);
//                lastButtonPress = System.currentTimeMillis();
//            }

            if (gamepad2.left_trigger > .3) {
                intake.snarfle();
            } else if (gamepad2.right_trigger > .3) {
                intake.vomit();
            } else {
                intake.stop();
            }

            if (gamepad2.left_bumper) {
                jHopper.snarfle();
                sleep(100);
                jHopper.stop();
            } else if (gamepad2.right_bumper) {
                jHopper.vomit();
                sleep(100);
                jHopper.stop();
            } else {
                jHopper.stop();
            }

            if (gamepad2.a) {
                shooter.setTargetSpeed(7.5);
                if (shooter.isAtSpeed()) {
                    intake.snarfle();
                    jHopper.snarfle();
                }
            }
            else shooter.setTargetSpeed(Math.max(0, -gamepad2.left_stick_y * 7.5));



            if (gamepad1.x) {
                // none of these did anything
                //aim for top goal
                Pose2d currentPose = drive.getPoseEstimate();
                double angleToTurnFacingGoal = HeadingCalculator.getAngleToTurnFacingGoal(currentPose);
                drive.turn(angleToTurnFacingGoal);
                currentPose = drive.getPoseEstimate();
                double distanceToMove = 84 - HeadingCalculator.getDistanceFromGoal(currentPose);
                drive.followTrajectory(drive.trajectoryBuilder(currentPose).back(distanceToMove).build());

//                shooter.setTargetSpeed(1);
//                sleep(1000);
//                jHopper.snarfle();
//                sleep(2000);

            }


            if (gamepad1.y) {
                //aim for low goal
                Pose2d currentPose = drive.getPoseEstimate();
                double angleToTurnFacingGoal = HeadingCalculator.getAngleToTurnFacingGoal(currentPose);
                drive.turn(angleToTurnFacingGoal);
                currentPose = drive.getPoseEstimate();
                double distanceToMove = 84 - HeadingCalculator.getDistanceFromGoal(currentPose);
                drive.followTrajectory(drive.trajectoryBuilder(currentPose).back(distanceToMove).build());

                shooter.setTargetSpeed(3);
                sleep(1000);
                jHopper.snarfle();
                sleep(2000);
            }

            drive.update();

//                Pose2d poseEstimate = drive.getPoseEstimate();
//            String line = String.format("Wobble Pos: %f", currentArmPosition);
//            RobotLog.d(line);
//
//            telemetry.addLine(line);
//                telemetry.addData("x", poseEstimate.getX());
//                telemetry.addData("y", poseEstimate.getY());
//                telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();

        }
    }

    private boolean deadTimeAfterPressPassed() {
        return (System.nanoTime() - lastButtonPress) > 100;
    }

    private boolean deadTimeAfterJHopAdvance() {
        return (System.currentTimeMillis() - lastJHopAdvance) > 500;
    }
}
