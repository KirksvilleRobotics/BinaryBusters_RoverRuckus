package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Test Turns")
public class Turn90CCW extends BBAutonomous {
    @Override
    public void runOpMode() {
        setup();

        rotate(-15, 0.3);
        sleep(5000);

        rotate(-180, 0.3);
        sleep(5000);
        rotate(-90, 0.3);
        sleep(5000);
        rotate(-45, 0.3);
    }
}
