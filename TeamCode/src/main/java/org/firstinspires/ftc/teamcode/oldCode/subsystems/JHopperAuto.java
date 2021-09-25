package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static android.os.SystemClock.sleep;

public class JHopperAuto {
    public static final double SNARFLE_POWER = -1.0;
    public static final double STOP_POWER = 0;
    public static final double VOMIT_POWER = 1.0;
    public double spaces = 0;

    private DcMotor jHopMotor;

    public JHopperAuto(HardwareMap hardwareMap) {
        // Get the motor from the hardware map
        jHopMotor = hardwareMap.get(DcMotorEx.class, "jHopperMotor");

        // initialize the motor
        jHopMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        int currentJHop = jHopMotor.getCurrentPosition();
        jHopMotor.setTargetPosition(currentJHop);
        jHopMotor.setPower(1);
        jHopMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }
    public void snarfle() {
        int currentPos = jHopMotor.getCurrentPosition();
        jHopMotor.setTargetPosition(currentPos + 1120/3);
    }

    public void vomit() {
        int currentPos = jHopMotor.getCurrentPosition();
        jHopMotor.setTargetPosition(currentPos - 1120/3);
    }
}
