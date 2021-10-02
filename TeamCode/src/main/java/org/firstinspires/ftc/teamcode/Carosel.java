package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Carosel {

    public static double UPPOWER = 0.25;
    public static double STOPPOWER = 0;

    private DcMotor caroselMotor;

    public Carosel(HardwareMap HardwareMap) {
        caroselMotor = HardwareMap.get(DcMotorEx.class, "caroselMotor");

        caroselMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        caroselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void UPPOWER() {
        caroselMotor.setPower(UPPOWER);
    }
    public void IncreasePower() {
        UPPOWER += 0.10;
        UPPOWER += 0.10;
        caroselMotor.setPower(UPPOWER);
    }
    public void DecreasePower() {
        UPPOWER -= 0.10;
        UPPOWER -= 0.10;
        UPPOWER -= 0.10;
        caroselMotor.setPower(UPPOWER);
    }
    public void STOPPOWER() {
        caroselMotor.setPower(STOPPOWER);
    }


}



