/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.BaseBotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.DriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.ComputerVision;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.JHopper;
import org.firstinspires.ftc.teamcode.subsystems.JHopperAuto;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.WobbleArm;
import org.firstinspires.ftc.teamcode.util.AssetsTrajectoryManager;


@Autonomous(name = "Autonomous")
public class AutonomousOpMode extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private WobbleArm wobbleArm = null;
    private Intake intake = null;
    private Shooter shooter = null;
    private JHopper jHopper = null;
    private BaseBotMecanumDrive drive = null;

    private Trajectory redRightA;
    private Trajectory redRightBA;
    private Trajectory redRightBB;
    private Trajectory redRightC;


    @Override
    public void runOpMode() throws InterruptedException {
        drive = DriveFactory.getDrive(hardwareMap);
        wobbleArm = new WobbleArm(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        jHopper = new JHopper(hardwareMap);

        ComputerVision vision = new ComputerVision(this, hardwareMap, telemetry);
        vision.init();

        Trajectory redALaunch = AssetsTrajectoryManager.load("RED_A-Park");
        Trajectory redCLaunch = AssetsTrajectoryManager.load("RED_C-Park");
        Trajectory redBLaunch = AssetsTrajectoryManager.load("RED_B-Park");
        Trajectory redPark = AssetsTrajectoryManager.load("RED_Park");
        Trajectory moveToScan = AssetsTrajectoryManager.load("Move_To_Scan");
        Trajectory redAGrabWB2 = AssetsTrajectoryManager.load("RED-A_WB2-GRAB");
        Trajectory redBGrabWB2 = AssetsTrajectoryManager.load("RED-B_WB2-GRAB");
        Trajectory redCGrabWB2 = AssetsTrajectoryManager.load("RED-C_WB2-GRAB");
        Trajectory redAWB2 = AssetsTrajectoryManager.load("RED-A_WB2");
        Trajectory redBWB2 = AssetsTrajectoryManager.load("RED-B_WB2");
        Trajectory redCWB2 = AssetsTrajectoryManager.load("RED-C_WB2");


        redRightA = AssetsTrajectoryManager.load("REDRIGHT-A");
        redRightBA = AssetsTrajectoryManager.load("REDRIGHT_B");
        redRightBB = AssetsTrajectoryManager.load("REDRIGHT-BB");
        redRightC = AssetsTrajectoryManager.load("REDRIGHT-C");

        //grab wobble goal? - ask team if were starting grabbed or not
        //scan tower - computervision - turn a little to look
        //move to correct square - part of computer vision if then statement - B:(12,60)(36,36)(60,60) R:(12,-60)(36,-36)(60,-60)
        //drop wobble goal - does braden have a name for this tool yet
        //mover to launch zone - is this dependant on CV? B:(0,24)  R:(0,-24)
        //target - middle goal or shot goal? need to get data from launcher testing
        //launch
        //park on line B:(12,24)   R:(12,-24)

        telemetry.addLine("Ready to start.");
        telemetry.update();
        drive.setPoseEstimate(new Pose2d(-63, -48, 0));

        waitForStart();
        drive.followTrajectory(moveToScan);
//        sleep(500);

        ComputerVision.StackType stackType = vision.googles();
        if (stackType == ComputerVision.StackType.ONE) {
            movetoZoneB();
        } else if (stackType == ComputerVision.StackType.QUAD) {
            movetoZoneC();
        } else if (stackType == ComputerVision.StackType.NONE) {
            movetoZoneA();
        }


        drive.turn(Math.PI);
        wormLoad();
        sleep(1700);
        driveForward(7);
        wormUp();
        sleep(700);
        drive.turn(Math.PI);

        if (stackType == ComputerVision.StackType.ONE) {
            shooter.setTargetSpeed(3);
            drive.followTrajectory(redBLaunch);
            yeet();
//            drive.turn(-.15);
            driveForward(6);
        } else if (stackType == ComputerVision.StackType.QUAD) {
            shooter.setTargetSpeed(3);
            drive.followTrajectory(redCLaunch);
            yeet();
//            drive.turn(-.15);
            driveForward(6);
        } else if (stackType == ComputerVision.StackType.NONE) {
            shooter.setTargetSpeed(3);
            drive.followTrajectory(redALaunch);
            yeet();
//            drive.turn(-.15);
            driveForward(11);

//            drive.followTrajectory(redAGrabWB2);
//            wormLoad();
//            sleep(2000);
//            driveBack(7);
//            wormHold();
//            sleep(1000);
//
//            drive.followTrajectory(redAWB2);
//
//            drive.turn(-Math.PI);
//            wormLoad();
//            sleep(1000);
//            driveForward(5);
//            wormHold();
//
//            drive.turn(-Math.PI);
//
//            drive.followTrajectory(redALaunch);
        }

//        drive.turn(Math.PI);
//        wormLoad();
//        sleep(2000);
//        driveBack(7);
//        wormHold();
//        sleep(1000);
//        drive.turn(Math.PI);
//        sleep(1000);

//
//        if (stackType == ComputerVision.StackType.ONE) {
//            drive.followTrajectory(redBWB2);
//        } else if (stackType == ComputerVision.StackType.QUAD) {
//            drive.followTrajectory(redCWB2);
//        } else if (stackType == ComputerVision.StackType.NONE) {
//            drive.followTrajectory(redAWB2);
//        }

//
//        drive.turn(-Math.PI);
//        wormLoad();
//        sleep(1000);
//        driveForward(5);
//        wormHold();
////        sleep(1000);
////        shooter.setTargetSpeed(3);
//        drive.turn(-Math.PI);
//
//        if(isStopRequested()){
//            return;
//        }

//        if (stackType == ComputerVision.StackType.ONE) {
//            drive.followTrajectory(redBLaunch);
//        } else if (stackType == ComputerVision.StackType.QUAD) {
//            drive.followTrajectory(redCLaunch);
//        } else if (stackType == ComputerVision.StackType.NONE) {
//            drive.followTrajectory(redALaunch);
//        }

//        if(isStopRequested()){
//            return;
//        }
////        drive.turn(Math.toRadians(-10));
//
//        if(isStopRequested()){
//            return;
//        }
//
////        yeet();
//
//        if(isStopRequested()){
//            return;
//        }
//
////        shooter.setTargetSpeed(0);
//
//        if(isStopRequested()){
//            return;
//        }

//        drive.followTrajectory(redPark);

        if(isStopRequested()){
            return;
        }

        PositionHandoff.setPose2d(drive.getPoseEstimate());
    }

    private void wormHold() {
        wobbleArm.setArmPosition(WobbleArm.ArmPosition.HOLD);
    }

    private void wormLoad() {
        wobbleArm.setArmPosition(WobbleArm.ArmPosition.LOAD);
    }

    public void movetoZoneA() {
        drive.followTrajectory(redRightA);
    }

    public void movetoZoneB() {
        drive.followTrajectory(redRightBA);
//        drive.followTrajectory(redRightBB);
    }

    public void movetoZoneC() {
        drive.followTrajectory(redRightC);
    }

    public void yeet() {
        shooter.setTargetSpeed(3);
        drive.turn(-0.30);
        sleep(2500);


        intake.snarfle();
        jHopper.snarfle();
        sleep(5000);
        drive.turn(0.30);
        jHopper.stop();
        intake.stop();

//        drive.turn(.12);
//        sleep(1000);
//        intake.snarfle();2
//        jHopper.snarfle();
//        sleep(700);
//        jHopper.stop();
//        intake.stop();
//
//        drive.turn(.12);
//        sleep(1000);
//        intake.snarfle();
//        jHopper.snarfle();
//        sleep(700);

        intake.stop();
        jHopper.stop();
    }

    public void wormUp() {
        wobbleArm.setArmPosition(WobbleArm.ArmPosition.UP);
    }

    public void driveForward(double distance) {
        Trajectory trajectoryForward = drive.trajectoryBuilder(drive.getPoseEstimate())
                .forward(distance)
                .build();
        drive.followTrajectory(trajectoryForward);
    }

    public void driveBack(double distance) {
        Trajectory trajectoryBack = drive.trajectoryBuilder(drive.getPoseEstimate())
                .back(distance)
                .build();
        drive.followTrajectory(trajectoryBack);
    }

}



