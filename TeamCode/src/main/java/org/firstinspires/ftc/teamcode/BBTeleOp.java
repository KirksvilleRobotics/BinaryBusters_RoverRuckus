package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
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

        /*telemetry.addData("Front Left:", v1);
        telemetry.addData("Front Right:", v2);
        telemetry.addData("Back Left:", v3);
        telemetry.addData("Back Right:", v4);*/

        frontLeft.setPower(v1);
        frontRight.setPower(v2);
        backLeft.setPower(v3);
        backRight.setPower(v4);

        /*if(gamepad2.b) {
            if(suspendArm) {
                suspendArm = false;
            } else {
                suspendArm = true;
            }
        }*/

        if(gamepad2.right_bumper) {
            armMultiplier = -0.45;
            armMessage = "high";
        } else if(gamepad2.left_bumper) {
            armMultiplier = -0.35;
            armMessage = "low";
        }

        telemetry.addData("Power Multiplier:", armMessage);

        //Arm
        if(gamepad2.a) {
            grabBall();
        } else if(Math.abs(gamepad2.right_stick_y) > 0.05) {
            arm.setPower(armMultiplier * gamepad2.right_stick_y);
        }
        /*} else if(suspendArm) {
            arm.setPower(0.1);
        } else {
            arm.setPower(0);
        }*/

        telemetry.addData("Arm Power:", arm.getPower());

        lift.setPower(gamepad2.left_stick_y);

        if(gamepad2.dpad_up) {
            //extend
            armExtender.setPower(1);
        } else if(gamepad2.dpad_down) {
            //retract
            armExtender.setPower(-1);
        }
    }


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
    }
}
