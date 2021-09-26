package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Autonomous(name = "Autonomous")
public class Testop extends LinearOpMode {

    private DcMotor testMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        testMotor = hardwareMap.get(DcMotor.class, "testMotor");

        testMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();
        
        testMotor.setPower(0.5);
        sleep(1000);
        testMotor.setPower(0);

    }
}
