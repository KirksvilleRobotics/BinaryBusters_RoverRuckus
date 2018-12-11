package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "BB Left")
public class BBLeft extends BBAutonomous {

    @Override
    public void runOpMode() {
        setup();
        waitForStart();

        encoderDrive(36, 36, 0.5);
        //turn 90 degrees
        encoderDrive(-15, 15, 0.5);
        encoderDrive(71, 71, 0.5);
        openClaw();
        encoderDrive(-92, -92);
    }
}
