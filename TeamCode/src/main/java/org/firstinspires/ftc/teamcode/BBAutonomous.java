package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class BBAutonomous extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    //private ColorSensor colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

    private final double COUNTS_PER_MOTOR_REV = 366;    // eg: TETRIX Motor Encoder
    private final double DRIVE_GEAR_REDUCTION = 2;     // This is < 1.0 if geared UP
    private final double WHEEL_DIAMETER_DISTANCE = 4;     // For figuring circumference
    private final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_DISTANCE * 3.1415);

    public void setup() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encoderDrive(double frontLeftDistance, double backLeftDistance, double frontRightDistance, double backRightDistance, double speed) {
        telemetry.addData("Position", "Line 23");
        telemetry.update();
        sleep(1000);
        if(speed > 1.0 && speed < 0.0) return;

        int frontLeftTarget;
        int backLeftTarget;
        int frontRightTarget;
        int backRightTarget;

        frontLeftTarget = frontLeft.getCurrentPosition() + (int)(frontLeftDistance * COUNTS_PER_INCH);
        backLeftTarget = backLeft.getCurrentPosition() + (int)(backLeftDistance * COUNTS_PER_INCH);
        frontRightTarget = frontRight.getCurrentPosition() + (int)(frontRightDistance * COUNTS_PER_INCH);
        backRightTarget = backRight.getCurrentPosition() + (int)(backRightDistance * COUNTS_PER_INCH);

        frontLeft.setTargetPosition(frontLeftTarget);
        backLeft.setTargetPosition(backLeftTarget);
        frontRight.setTargetPosition(frontRightTarget);
        backRight.setTargetPosition(backRightTarget);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        frontRight.setPower(speed);
        backRight.setPower(speed);

        while(frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            /*telemetry.addData("Left Target:", leftTarget);
            telemetry.addData("Right Target:", rightTarget);
            telemetry.addData("Left Current:", leftDrive.getCurrentPosition());
            telemetry.addData("Right Current:", rightDrive.getCurrentPosition());
            telemetry.addData("Left Motor:", leftDrive.isBusy());
            telemetry.addData("Right Motor:" , rightDrive.isBusy());
            telemetry.update();*/

            if(!opModeIsActive()) {
                frontLeft.setPower(0);
                backLeft.setPower(0);
                frontRight.setPower(0);
                backRight.setPower(0);
            }
        }

        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encoderDrive(double frontLeftDistance, double backLeftDistance, double frontRightDistance, double backRightDistance) {
        telemetry.addData("ENCODER DRIVE", "FUNCTION");
        encoderDrive(frontLeftDistance, backLeftDistance, frontRightDistance, backRightDistance, 1.0);
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
