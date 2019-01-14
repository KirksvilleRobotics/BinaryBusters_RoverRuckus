package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class BBAutonomous extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private DcMotor arm;

    //private ColorSensor colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

    private final double COUNTS_PER_MOTOR_REV = 240;    // eg: TETRIX Motor Encoder
    private final double DRIVE_GEAR_REDUCTION = 1;     // This is < 1.0 if geared UP
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

        arm = hardwareMap.get(DcMotor.class, "arm");
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

    public void dropHarold() {
        arm.setPower(-0.4);
        sleep(600);
        arm.setPower(0.4);
        sleep(600);
        arm.setPower(0);
    }

    public int checkColor() {
        // Returns:
        // 1 - gold
        // 0 - silver
        // -1 - unsure



        return 1;
    }
/*
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    /**
     * Get current cumulative angle rotation from last reset.
     * @return Angle in degrees. + = left, - = right.
     */
/*
    private double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    /**
     * See if we are moving in a straight line and if not return a power correction value.
     * @return Power adjustment, + is adjust left - is adjust right.
     */
   /* private double checkDirection()
    {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     * @param degrees Degrees to turn, + is left - is right
     */
    /*private void rotate(int degrees, double power)
    {
        double  leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0)
        {   // turn right.
            leftPower = -power;
            rightPower = power;
        }
        else if (degrees > 0)
        {   // turn left.
            leftPower = power;
            rightPower = -power;
        }
        else return;

        // set power to rotate.
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);

        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (opModeIsActive() && getAngle() == 0) {}

            while (opModeIsActive() && getAngle() > degrees) {}
        }
        else    // left turn.
            while (opModeIsActive() && getAngle() < degrees) {}

        // turn the motors off.
        rightMotor.setPower(0);
        leftMotor.setPower(0);

        // wait for rotation to stop.
        sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }
*/
    @Override
    public void runOpMode() throws InterruptedException {

    }
}
