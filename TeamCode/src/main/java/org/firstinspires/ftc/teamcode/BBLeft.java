package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "BB Left")
public class BBLeft extends BBAutonomous {

    @Override
    public void runOpMode() {
        setup();
        waitForStart();

        encoderDrive(36.0, 36.0, 36.0, 36.0, 0.75);
        sleep(300);
        //turn 90 degrees
        encoderDrive(-18.0, -18.0, 18.0, 18.0, 0.75);
        sleep(300);
        encoderDrive(71.0, 71.0, 71.0, 71.0, 0.75);
        sleep(300);
        dropHarold();
        encoderDrive(-120.0, -120.0, -120.0, -120.0);
    }
}
