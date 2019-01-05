package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "BBStraight")
public class BBStraight extends BBAutonomous {
    @Override
    public void runOpMode() {
        setup();
        waitForStart();

        encoderDrive(40.0, 40.0, 40.0, 40.0, 0.5);
    }
}
