package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

public class DriveFactory {
    public static BaseBotMecanumDrive getDrive(HardwareMap hardwareMap, VuforiaLocalizer vuforia) {
        return new BotMecanumDrive(hardwareMap, vuforia);

                //ProgrammerBotMecanumDrive(hardwareMap)
                //
    }

}
