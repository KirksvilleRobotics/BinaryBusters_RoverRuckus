package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

public class BBAutonomous extends LinearOpMode{
    public DcMotor leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
    public DcMotor rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");

    public ColorSensor colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

    private final double COUNTS_PER_MOTOR_REV = 425;    // eg: TETRIX Motor Encoder
    private final double DRIVE_GEAR_REDUCTION = 0.0;     // This is < 1.0 if geared UP
    private final double WHEEL_DIAMETER_Distance = 0.0;     // For figuring circumference
    private final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_Distance * 3.1415);

    public void encoderDrive(double leftDistance, double rightDistance, double speed) {
        if(speed > 1.0 && speed < 0.0) return;

        int leftTarget;
        int rightTarget;

        leftTarget = leftDrive.getCurrentPosition() + (int)(leftDistance * COUNTS_PER_INCH);
        rightTarget = rightDrive.getCurrentPosition() + (int)(rightDistance * COUNTS_PER_INCH);

        leftDrive.setTargetPosition(leftTarget);
        rightDrive.setTargetPosition(rightTarget);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftDrive.setPower(speed);
        rightDrive.setPower(speed);

        while(leftDrive.isBusy() && rightDrive.isBusy()) {
            telemetry.addData("Target:", leftTarget);
            telemetry.addData("Current:", leftDrive.getCurrentPosition());
            telemetry.addData("Left Motor:", leftDrive.isBusy());
            telemetry.addData("Right Motor:" , rightDrive.isBusy());
            telemetry.update();

            if(!opModeIsActive()) {
                leftDrive.setPower(0);
                rightDrive.setPower(0);
            }
        }

        leftDrive.setPower(0);
        rightDrive.setPower(0);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encoderDrive(double leftDistance, double rightDistance) {
        encoderDrive(leftDistance, rightDistance, 1.0);
    }

    public int checkColor() {
        // Returns:
        // 1 - gold
        // 0 - silver
        // -1 - unsure



        return 1;
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}
