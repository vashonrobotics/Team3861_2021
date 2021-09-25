package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    public static final double SNARFLE_POWER = 1.0;
    public static final double STOP_POWER = 0;
    public static final double VOMIT_POWER = -1.0;

    private DcMotor intakeMotor;
    private DcMotor lowerIntakeMotor;

    public Intake(HardwareMap hardwareMap) {
        // Get the motor from the hardware map
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake_motor");
        lowerIntakeMotor = hardwareMap.get(DcMotorEx.class, "lower_intake_motor");

        // initialize the motor
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lowerIntakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        lowerIntakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void snarfle() {
        intakeMotor.setPower(SNARFLE_POWER);
        lowerIntakeMotor.setPower(SNARFLE_POWER);
    }

    public void stop() {
        intakeMotor.setPower(STOP_POWER);
        lowerIntakeMotor.setPower(STOP_POWER);
    }

    public void vomit() {
        intakeMotor.setPower(VOMIT_POWER);
        lowerIntakeMotor.setPower(VOMIT_POWER);
    }
}
