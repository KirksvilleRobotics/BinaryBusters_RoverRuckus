package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "BB TeleOp")
public class BBTeleOp extends OpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private DcMotor arm;
    private DcMotor lift;

    private final double COUNTS_PER_DEGREE = 2;


    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        arm = hardwareMap.get(DcMotor.class, "arm");
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lift = hardwareMap.get(DcMotor.class, "lift");
    }

    @Override
    public void loop() {
        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double angle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI /4;
        double rightX = gamepad1.right_stick_x;

        final double v1 = r * Math.sin(angle) - rightX;
        final double v2 = r * Math.cos(angle) + rightX;
        final double v3 = r * Math.cos(angle) - rightX;
        final double v4 = r * Math.sin(angle) + rightX;

        frontLeft.setPower(v1);
        frontRight.setPower(v2);
        backLeft.setPower(v3);
        backRight.setPower(v4);

        //Arm
        if(gamepad2.a) {
            grabBall();
        } else {
            arm.setPower(0.4 * gamepad2.right_stick_y);
        }

        lift.setPower(gamepad2.left_stick_y);
    }

    public void grabBall() {
        try {
            moveArm(0);
            Thread.sleep(50);
            moveArm(0);
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
    }
}
