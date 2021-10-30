package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

public abstract class BaseBotMecanumDrive extends com.acmerobotics.roadrunner.drive.MecanumDrive {
    public BaseBotMecanumDrive(double kV, double kA, double kStatic, double trackWidth, double wheelBase, double lateralMultiplier) {
        super(kV, kA, kStatic, trackWidth, wheelBase, lateralMultiplier);
    }

    public abstract void setVisionLocalizer(VuforiaLocalizer vuforia);

    public abstract TrajectoryBuilder trajectoryBuilder(Pose2d startPose);

    public abstract TrajectoryBuilder trajectoryBuilder(Pose2d startPose, boolean reversed);

    public abstract TrajectoryBuilder trajectoryBuilder(Pose2d startPose, double startHeading);

    public abstract void turnAsync(double angle);

    public abstract void turn(double angle);

    public abstract void followTrajectory(Trajectory trajectory);

    public abstract void update();

    public abstract boolean isBusy();

    public abstract void setMode(DcMotor.RunMode runMode);

    public abstract void setPIDCoefficients(DcMotor.RunMode runMode, PIDCoefficients coefficients);

    public abstract void setWeightedDrivePower(Pose2d drivePower);
}
