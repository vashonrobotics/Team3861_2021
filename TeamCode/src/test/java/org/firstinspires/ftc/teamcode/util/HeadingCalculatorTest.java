package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class HeadingCalculatorTest {

    @org.junit.jupiter.api.Test
    void getAngleToTurnFacing() {
        Vector2d target0 = new Vector2d(1, 0);
        Vector2d target1 = new Vector2d(1, 1);
        Vector2d target2 = new Vector2d(1, -1);
        Vector2d target3 = new Vector2d(0, 1);
        Vector2d target4 = new Vector2d(0, -1);
        Vector2d target5 = new Vector2d(-1, 1);
        Vector2d target6 = new Vector2d(-1, -1);
        Vector2d target7 = new Vector2d(-1, 0);
        Vector2d currentPosition = new Vector2d(0, 0);
        Vector2d headingVector = new Vector2d(1, 0);

        double angleToTurnFacing0 = HeadingCalculator.getAngleToTurnFacing(target0, currentPosition, headingVector);
        Assertions.assertEquals(0.0, angleToTurnFacing0,
                "Already pointing at your destination should result in no rotation");

        double angleToTurnFacing1 = HeadingCalculator.getAngleToTurnFacing(target1, currentPosition, headingVector);
        Assertions.assertEquals( Math.PI / 4, angleToTurnFacing1);

        double angleToTurnFacing2 = HeadingCalculator.getAngleToTurnFacing(target2, currentPosition, headingVector);
        Assertions.assertEquals(-Math.PI/4, angleToTurnFacing2);

        double angleToTurnFacing3 = HeadingCalculator.getAngleToTurnFacing(target3, currentPosition, headingVector);
        Assertions.assertEquals(Math.PI/2, angleToTurnFacing3);

        double angleToTurnFacing4 = HeadingCalculator.getAngleToTurnFacing(target4, currentPosition, headingVector);
        Assertions.assertEquals(-Math.PI/2, angleToTurnFacing4);

        double angleToTurnFacing5 = HeadingCalculator.getAngleToTurnFacing(target5, currentPosition, headingVector);
        Assertions.assertEquals(3 * Math.PI/4, angleToTurnFacing5);

        double angleToTurnFacing6 = HeadingCalculator.getAngleToTurnFacing(target6, currentPosition, headingVector);
        Assertions.assertEquals(-3 * Math.PI/4, angleToTurnFacing6);

        double angleToTurnFacing7 = HeadingCalculator.getAngleToTurnFacing(target7, currentPosition, headingVector);
        Assertions.assertEquals(Math.PI, angleToTurnFacing7);
    }
}