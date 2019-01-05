package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "BB Left")
public class BBLeft extends BBAutonomous {

    @Override
    public void runOpMode() {
        setup();
        waitForStart();

        encoderDrive(36.0, 36.0, 36.0, 36.0, 0.5);
        //turn 90 degrees
        encoderDrive(-15.0, -15.0, 15.0, 15.0, 0.5);
        encoderDrive(71.0, 71.0, 71.0, 71.0, 0.5);
        //TODO FIGURE OUT HOW TO DROP MARKER
        encoderDrive(-92.0, -92.0, -92.0, -92.0);
    }
}
