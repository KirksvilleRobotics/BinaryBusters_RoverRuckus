package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

//@Autonomous(name = "BB Right")
public class BBRight extends BBAutonomous {

    @Override
    public void runOpMode() {
        setup();
        waitForStart();

        encoderDrive(61.0, 61.0, 61.0, 61.0, 0.5);
        //TODO DROP MARKER
        //turn 135 degrees
        encoderDrive(-34.0, -34.0, 34.0, 34.0);
        encoderDrive(92.0, 92.0, 92.0, 92.0);

    }
}
