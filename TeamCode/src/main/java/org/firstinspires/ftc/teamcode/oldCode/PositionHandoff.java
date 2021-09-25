package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class PositionHandoff {
    private static Pose2d pose2d;

    public static Pose2d getPose2d() {
        return pose2d;
    }

    public static void setPose2d(Pose2d pose2d) {
        PositionHandoff.pose2d = pose2d;
    }
}
