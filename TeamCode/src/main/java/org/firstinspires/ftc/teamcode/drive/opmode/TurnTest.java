package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.BaseBotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.BotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.DriveFactory;

/*
 * This is a simple routine to test turning capabilities.
 */
@Config
@Autonomous(group = "drive")
public class TurnTest extends LinearOpMode {
    public static double ANGLE = 90; // deg

    @Override
    public void runOpMode() throws InterruptedException {
        BaseBotMecanumDrive drive = DriveFactory.getDrive(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        drive.turn(Math.toRadians(ANGLE));
    }
}
