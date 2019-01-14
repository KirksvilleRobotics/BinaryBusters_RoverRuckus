package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

//@Autonomous(name = "BB Right")
public class BBRight extends BBAutonomous {

    @Override
    public void runOpMode() {
        setup();
        waitForStart();

        encoderDrive(61.0, 61.0, 61.0, 61.0, 0.5);
        sleep(300);
        dropHarold();
        //turn 135 degrees
        encoderDrive(-34.0, -34.0, 34.0, 34.0);
        sleep(300);
        encoderDrive(92.0, 92.0, 92.0, 92.0);

    }
}
