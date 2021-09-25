package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static android.os.SystemClock.sleep;

public class JHopper {
    public static final double SNARFLE_POWER = -0.7;
    public static final double STOP_POWER = 0;
    public static final double VOMIT_POWER = 0.7;
    public double spaces = 0;

    private DcMotor jHopMotor;

    public JHopper(HardwareMap hardwareMap) {
        // Get the motor from the hardware map
        jHopMotor = hardwareMap.get(DcMotorEx.class, "jHopperMotor");

        // initialize the motor
        jHopMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        jHopMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }
    public void snarfle() {
            jHopMotor.setPower(SNARFLE_POWER);
    }


    public void stop() {
        jHopMotor.setPower(STOP_POWER);
    }

    public void vomit() {
        jHopMotor.setPower(VOMIT_POWER);
    }
}
