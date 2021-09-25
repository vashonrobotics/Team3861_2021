package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class WobbleArm {

    public enum ArmPosition {UP, DOWN, HOLD, LOAD}

    private static double DOWN_POSITION = 0;
    private static double HOLD_POSITION = 0.16;
    private static double UP_POSITION = 0.27;
    private static double LOAD_POSITION = .05;

    private Servo lifter = null;

    public WobbleArm(HardwareMap hardwareMap) {
        lifter = hardwareMap.get(Servo.class, "lifter");
        lifter.setDirection(Servo.Direction.REVERSE);
    }

    public void setArmPosition(ArmPosition armPosition) {
        if (armPosition == ArmPosition.DOWN) {
            lifter.setPosition(DOWN_POSITION);
        } else if (armPosition == ArmPosition.HOLD) {
            lifter.setPosition(HOLD_POSITION);
        } else if (armPosition == ArmPosition.UP) {
            lifter.setPosition(UP_POSITION);
        } else if (armPosition == ArmPosition.LOAD) {
            lifter.setPosition(LOAD_POSITION);
        }

    }

    public void setArmPosition(double armPosition) {
        lifter.setPosition(armPosition);
    }

    public double getDownPosition() {
        return DOWN_POSITION;
    }
}
