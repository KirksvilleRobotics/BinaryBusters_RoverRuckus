package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/* TODO
Talk to drivers about mapping and control
 */

@TeleOp(name = "BB TeleOp")
public class BBTeleOp extends OpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private DcMotor arm;
    private DcMotor lift;

    private CRServo armExtender;

    private final double COUNTS_PER_DEGREE = 2;
    private boolean suspendArm = false;
    private double armMultiplier = -0.35;
    private String armMessage = "low";


    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        arm = hardwareMap.get(DcMotor.class, "arm");
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift = hardwareMap.get(DcMotor.class, "lift");
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        //Drive code
        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double absR = Math.abs(Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y));
        int rSign = 0;

        if(Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y) < 0) {
            rSign = -1;
        } else if(Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y) > 0) {
            rSign = 1;
        }

        telemetry.addData("R:", r);

        double angle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI /4;

        double rightX = 0.0;
        double absRightX = Math.abs(gamepad1.right_stick_x);
        int rightXSign = 0;

        if(gamepad1.right_stick_x < 0) rightXSign = -1;
        else if(gamepad1.right_stick_x > 0) rightXSign = 1;
        telemetry.addData("abs right x:", absRightX);

        if(absRightX >= 0 && absRightX <= 0.1) {
            rightX = 0;
        } else if(absRightX <= 0.7) {
            rightX = linearMap(absRightX, 0.1, 0.7, 0.1, 0.4);
        } else {
            telemetry.addData("Test", linearMap(absRightX, 0.7, 1.0, 0.7, 1.0));
            rightX = linearMap(absRightX, 0.7, 1.0, 0.7, 1.0);
        }

        rightX *= rightXSign;
        telemetry.addData("Right X:", rightX);

        final double v1 = r * Math.sin(angle) - rightX;
        final double v2 = r * Math.cos(angle) + rightX;
        final double v3 = r * Math.cos(angle) - rightX;
        final double v4 = r * Math.sin(angle) + rightX;

        /*telemetry.addData("Front Left:", v1);
        telemetry.addData("Front Right:", v2);
        telemetry.addData("Back Left:", v3);
        telemetry.addData("Back Right:", v4);*/

        frontLeft.setPower(v1);
        frontRight.setPower(v2);
        backLeft.setPower(v3);
        backRight.setPower(v4);


        //Arm Code
        double armPower = 0.0;
        double absArmPower = Math.abs(gamepad2.right_stick_y);
        int armSign = 0;

        if(gamepad2.right_stick_y < 0) armSign = -1;
        else if(gamepad2.right_stick_y > 0) armSign = 1;

        if(absArmPower >= 0.0 && absArmPower <= 0.1) {
            armPower = 0.0;
        } else if(absArmPower <= 0.7) {
            armPower = linearMap(absArmPower, 0.1, 0.7, 0.0, 0.35);
        } else {
            armPower = linearMap(absArmPower, 0.7, 1.0, 0.35, 0.45);
        }

        armPower *= armSign;

        arm.setPower(armPower);
        telemetry.addData("Arm power:", arm.getPower());

        //Lift
        double liftPower = 0.0;
        double absLiftPower = Math.abs(gamepad2.left_stick_y);
        int liftSign = 0;

        if(gamepad2.left_stick_y < 0) liftSign = -1;
        else if(gamepad2.left_stick_y > 0) liftSign = 1;

        if(absLiftPower >= 0.0 && absLiftPower <= 0.1) {
            liftPower = 0.0;
        } else if(absLiftPower < 0.7) {
            liftPower = linearMap(absLiftPower, 0.1, 0.7, 0.1, 0.4);
        } else {
            liftPower = linearMap(absLiftPower, 0.7, 1.0, 0.4, 1.0);
        }

        liftPower *= liftSign;

        lift.setPower(liftPower);

        telemetry.addData("Lift Power:", lift.getPower());
    }

    public double linearMap(double val, double inMin, double inMax, double outMin, double outMax) {
        return (val - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;

    }

/*
    public void grabBall() {
        try {
            moveArm(-5);
            Thread.sleep(50);
            moveArm(5);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void moveArm(double degrees) {
        int armTarget = arm.getCurrentPosition() + (int)(COUNTS_PER_DEGREE * degrees);
        arm.setTargetPosition(armTarget);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm.setPower(1);

        while(arm.isBusy()) {
            telemetry.addData("Current Position:", arm.getCurrentPosition());
            telemetry.addData("Target Position:", armTarget);
        }

        arm.setPower(0);

        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }*/
}
