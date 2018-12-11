package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class BBAutonomous extends LinearOpMode {

    private DcMotor leftDrive;
    private DcMotor rightDrive;

    private Servo leftClaw;
    private Servo rightClaw;

    //private ColorSensor colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

    private final double COUNTS_PER_MOTOR_REV = 366;    // eg: TETRIX Motor Encoder
    private final double DRIVE_GEAR_REDUCTION = 2;     // This is < 1.0 if geared UP
    private final double WHEEL_DIAMETER_DISTANCE = 4;     // For figuring circumference
    private final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_DISTANCE * 3.1415);

    public void setup() {
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");

        closeClaw();

    }

    public void encoderDrive(double leftDistance, double rightDistance, double speed) {
        telemetry.addData("Position", "Line 23");
        telemetry.update();
        sleep(1000);
        if(speed > 1.0 && speed < 0.0) return;

        int leftTarget;
        int rightTarget;

        leftTarget = leftDrive.getCurrentPosition() + (int)(leftDistance * COUNTS_PER_INCH);
        rightTarget = rightDrive.getCurrentPosition() + (int)(rightDistance * COUNTS_PER_INCH);

        telemetry.addData("Target", leftTarget);
        telemetry.addData("Position", leftDrive.getCurrentPosition());
        telemetry.update();


        leftDrive.setTargetPosition(leftTarget);
        rightDrive.setTargetPosition(rightTarget);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftDrive.setPower(speed);
        rightDrive.setPower(speed);

        while(leftDrive.isBusy() && rightDrive.isBusy()) {
            telemetry.addData("Left Target:", leftTarget);
            telemetry.addData("Right Target:", rightTarget);
            telemetry.addData("Left Current:", leftDrive.getCurrentPosition());
            telemetry.addData("Right Current:", rightDrive.getCurrentPosition());
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
        telemetry.addData("ENCODER DRIVE", "FUNCTION");
        encoderDrive(leftDistance, rightDistance, 1.0);
    }

    public void openClaw() {
        leftClaw.setPosition(1);
        rightClaw.setPosition(0.3);
    }

    public void closeClaw() {
        leftClaw.setPosition(0.45);
        rightClaw.setPosition(0.65);
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
