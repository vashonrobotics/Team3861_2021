package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveFactory {
    public static BaseBotMecanumDrive getDrive(HardwareMap hardwareMap) {
        return new BotMecanumDrive(hardwareMap);

                //ProgrammerBotMecanumDrive(hardwareMap)
                //
    }

}
