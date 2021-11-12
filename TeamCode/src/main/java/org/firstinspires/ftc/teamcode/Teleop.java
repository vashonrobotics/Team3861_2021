package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import org.firstinspires.ftc.teamcode.drive.BaseBotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.DriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.Carosel;
import org.firstinspires.ftc.teamcode.subsystems.Lifter;

// import com.qualcomm.robotcore.hardware.DcMotor;
//import org.firstinspires.ftc.teamcode.subsystems.Vision;


@TeleOp(name = "Teleop")
public class Teleop extends LinearOpMode {


    BaseBotMecanumDrive drive = null;

    boolean was_dpad_left = false;
    boolean was_dpad_right = false;

    Carosel Carousel = null;
    private boolean was_x;
    private boolean was_y;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

       // Vision vision = new Vision(this, hardwareMap, telemetry);
       // vision.init();

        drive = DriveFactory.getDrive(hardwareMap);
        Carosel carousel = new Carosel(hardwareMap);
        Lifter lifter = new Lifter(hardwareMap);


        waitForStart();

        while (!isStopRequested()) {

            if (gamepad2.dpad_up) {
                carousel.PowerOn();
            }
            if (gamepad2.dpad_down) {
                carousel.PowerOff();
            }

            if (gamepad2.dpad_left && !was_dpad_left) {
                carousel.setDirection(DcMotorSimple.Direction.REVERSE);
            }
            was_dpad_left = gamepad1.dpad_left;

            if (gamepad2.dpad_right && !was_dpad_right) {
                carousel.setDirection(DcMotorSimple.Direction.FORWARD);
            }
            was_dpad_right = gamepad1.dpad_right;

            if (gamepad2.x && !was_x) {
                lifter.slap();
            }
            was_x = gamepad2.x;

            if (gamepad2.y && !was_y) {
                lifter.home();
            }
            was_y = gamepad2.y;

            if (gamepad2.a){
                lifter.setPowerHex();
            }

            if (gamepad2.b){
                lifter.nothingHex();
            }

            drive.setWeightedDrivePower(//turning and moving and strafing
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            // telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("FrontMotors", "left (%.2f), right (%.2f)", leftFrontPower, rightFrontPower);
            //telemetry.addData("RearMotors", "left (%.2f), right (%.2f)", leftRearPower, rightRearPower);
            //telemetry.update();
        }
    }
}