package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@SuppressWarnings("unused") // This looks nice and reduces the amount of warnings the code gives
public class Carosel {

    public double Power = 0.20;

    private final DcMotor caroselMotor;

    public Carosel(HardwareMap HardwareMap) {
        caroselMotor = HardwareMap.get(DcMotorEx.class, "caroselMotor");

        caroselMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        caroselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void PowerOn() {
        caroselMotor.setPower(Power);
    }

    public void IncreasePower() {
        if (Power <= 0.90) {
            Power += 0.10;
            caroselMotor.setPower(Power);
        }
    }

    public void DecreasePower() {
        if (Power >= 0.10) {
            Power -= 0.10;
            caroselMotor.setPower(Power);
        }
    }

    public void PowerOff() {
        caroselMotor.setPower(0);
    }
}



