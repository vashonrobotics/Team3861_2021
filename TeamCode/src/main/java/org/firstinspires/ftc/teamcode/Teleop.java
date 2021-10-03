package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.teamcode.drive.BaseBotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.DriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.Carosel;

@TeleOp(name = "Teleop")
public class Teleop extends LinearOpMode {

    boolean was_dpad_left = false;
    boolean was_dpad_right = false;

    Carosel Carousel = null;

    @Override
    public void runOpMode() throws InterruptedException {

        //BaseBotMecanumDrive drive = DriveFactory.getDrive(hardwareMap);
        Carousel = new Carosel(hardwareMap);

        waitForStart();

        while (!isStopRequested()) {

            if (gamepad1.dpad_up) {
                Carousel.PowerOn();
            }
            if (gamepad1.dpad_down) {
                Carousel.PowerOff();
            }

            if (gamepad1.dpad_left && !was_dpad_left) {
                Carousel.DecreasePower();
            }
            was_dpad_left = gamepad1.dpad_left;

            if (gamepad1.dpad_right && !was_dpad_right) {
                Carousel.IncreasePower();
            }
            was_dpad_right = gamepad1.dpad_right;
        }
    }
}
