package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

//@Autonomous(name = "BB Right")
public class BBRight extends BBAutonomous {

    @Override
    public void runOpMode() {
        setup();
        waitForStart();

        encoderDrive(61, 61);
        openClaw();
        //turn 135 degrees
        encoderDrive(-34, 34);
        encoderDrive(92, 92);

    }
}
