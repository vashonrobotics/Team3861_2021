package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@SuppressWarnings("unused")
public class Lifter {

    //public double Power = 1.0;

    private final DcMotor lifterMotor;
    private final Servo slapMotor;

    public Lifter(HardwareMap HardwareMap) {
        lifterMotor = HardwareMap.get(DcMotorEx.class, "lifterMotor");

        lifterMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        lifterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slapMotor = HardwareMap.get(Servo.class, "slapMotor");

        slapMotor.setPosition(1.0);
    }

    private static double DOWN_POSITION = 1.0;
    private static double HOLD_POSITION = 0.45;
    private static double UP_POSITION = 0.0;
    private static double LOAD_POSITION = 1.0;


    long setTime = System.currentTimeMillis();
    boolean hasRun = false;

    public void slap() {
        if (System.currentTimeMillis() - setTime > 10000 && !hasRun) {
            //Will only run after 10 seconds, and will only run onc
            slapMotor.setPosition(HOLD_POSITION);
            slapMotor.setPosition(LOAD_POSITION);
            hasRun = true;
        }
    }



}

