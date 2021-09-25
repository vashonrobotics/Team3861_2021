package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

public class HeadingCalculator {
    static Vector2d GOAL_POSITION = new Vector2d(72, -36);

    public static double getAngleToTurnFacing(Vector2d targetPosition, Pose2d pose) {
        double angle = HeadingCalculator.getAngleToTurnFacingGoal(pose.vec(),
                pose.headingVec());

        return angle;
    }

    public static double getAngleToTurnFacingGoal(Pose2d pose) {
        double angle = HeadingCalculator.getAngleToTurnFacingGoal(pose.vec(),
                pose.headingVec());

        return angle;
    }

    public static double getDistanceFromGoal(Pose2d pose) {
        return Math.sqrt(Math.pow(pose.getX() - GOAL_POSITION.getX(), 2) + Math.pow(pose.getY() - GOAL_POSITION.getY(), 2));
    }

    public static double getAngleToTurnFacing(Vector2d targetPosition, Vector2d currentPosition,
                                              Vector2d headingVec) {
        Vector2d targetVector = targetPosition.minus(currentPosition);

        double headingCos = targetVector.dot(headingVec); // don't need to normalize for atan2
        double pseudoCross = pseudoCross(targetVector, headingVec);
        return Math.atan2(pseudoCross, headingCos);
    }

    public static double getAngleToTurnFacingGoal(Vector2d currentPosition, Vector2d headingVec) {
        return getAngleToTurnFacing(GOAL_POSITION, currentPosition, headingVec);
    }

    private static double pseudoCross(Vector2d targetVector, Vector2d headingVec) {
        return headingVec.getX() * targetVector.getY() -
                targetVector.getX() * headingVec.getY();
    }
}
