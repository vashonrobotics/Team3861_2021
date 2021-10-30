package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@SuppressWarnings("unused")
public class Lifter {

    public double Power = 1.0;

    private final DcMotor lifterMotor;
    private final Servo slapMotor;

    public Lifter(HardwareMap HardwareMap) {
        lifterMotor = HardwareMap.get(DcMotorEx.class, "lifterMotor");

        lifterMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        lifterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slapMotor = HardwareMap.get(Servo.class, "slapMotor");

        slapMotor.setPosition(1.0);
    }

    public void slap(){

        slapMotor.setPosition(0.45);

        slapMotor.setPosition(1.0);

    }

    public void slapNot() {
        lifterMotor.setPower(0);
    }


}

