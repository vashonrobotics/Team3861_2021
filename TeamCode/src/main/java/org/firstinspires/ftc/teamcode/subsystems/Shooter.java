package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Shooter {
    private static final double AT_SPEED_THRESHOLD = 10;
    private final DcMotorEx highSpeedBarfMotor;
    private double targetSpeed = 0;

    private Telemetry telemetry = null;

    public Shooter(HardwareMap hardwareMap) {
        highSpeedBarfMotor = hardwareMap.get(DcMotorEx.class, "highSpeedBarfMotor");
        highSpeedBarfMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        highSpeedBarfMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        this(hardwareMap);
        this.telemetry = telemetry;
    }

    public void setTargetSpeed(double targetSpeed) {
        double radiansPerSecond = rpmToRadiansPerSecond(targetSpeed);
        RobotLog.d("Setting: " + Double.toString(radiansPerSecond));
        highSpeedBarfMotor.setVelocity(radiansPerSecond, AngleUnit.RADIANS);
        this.targetSpeed = targetSpeed;
    }

    private double rpmToRadiansPerSecond(double targetSpeed) {
        return targetSpeed * 2 * Math.PI;
    }

    public boolean isAtSpeed() {
        double actualSpeed = highSpeedBarfMotor.getVelocity(AngleUnit.RADIANS);

        // update telemetry
        double targetSpeedRadiansPerSecond = rpmToRadiansPerSecond(targetSpeed);
        if(telemetry != null) {
            telemetry.addData("targetVelocity", targetSpeedRadiansPerSecond);
            telemetry.addData("actualVelocity", actualSpeed);
            telemetry.addData("error", targetSpeedRadiansPerSecond - actualSpeed);
        }

        RobotLog.d(Double.toString(actualSpeed));
        return Math.abs(actualSpeed - targetSpeedRadiansPerSecond) < AT_SPEED_THRESHOLD;
    }
}
