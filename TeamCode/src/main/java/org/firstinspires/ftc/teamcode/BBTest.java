package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "BBTest")
public class BBTest extends BBAutonomous {
    @Override
    public void runOpMode() {
        setup();
        waitForStart();
        telemetry.addData("Ready", "Run");
        telemetry.update();
        //encoderDrive(10, 10);
        encoderDrive(20, 20, -20, -20,  0.5);
    }
}
