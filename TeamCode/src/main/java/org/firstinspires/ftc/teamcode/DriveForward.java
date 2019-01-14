package org.firstinspires.ftc.teamcode;

public class DriveForward extends BBAutonomous {
    @Override
    public void runOpMode() {
        setup();

        encoderDrive(10, 10, 10, 10, 0.3);
    }
}
