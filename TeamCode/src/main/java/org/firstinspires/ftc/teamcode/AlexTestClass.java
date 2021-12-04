package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Lifter;





//import org.firstinspires.ftc.teamcode.subsystems.Vision;


@TeleOp(name = "Alex")
public class AlexTestClass extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Lifter lifter = new Lifter(hardwareMap);
        waitForStart();

        while (!isStopRequested()) {
            if (gamepad1.x) {
                lifter.slap();
            }

            if (gamepad1.y) {

                lifter.home();
            }
            if (gamepad1.a){
                lifter.armBottomLayer();
            }

            if (gamepad1.b){
                lifter.armDown();
            }
        }
    }
}