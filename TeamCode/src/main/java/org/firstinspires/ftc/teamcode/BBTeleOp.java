package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "BBTeleOp")
class BBTeleOp extends OpMode {
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private DcMotor bottomArm;
    private DcMotor topArm;

    private Servo leftClaw;
    private Servo rightClaw;

    public void init() {
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        bottomArm = hardwareMap.get(DcMotor.class, "bottomArm");
        topArm = hardwareMap.get(DcMotor.class, "topArm");

        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
    }

    public void loop() {
        leftMotor.setPower(gamepad1.left_stick_y);
        rightMotor.setPower(gamepad1.right_stick_y);

        bottomArm.setPower(gamepad2.left_stick_y);
        topArm.setPower(gamepad2.right_stick_y);

        if(gamepad2.a) {
            //close
            leftClaw.setPosition(0);
            rightClaw.setPosition(0);

            telemetry.addData("Claw", "Closed");
        } else if(gamepad2.b) {
            //open
            leftClaw.setPosition(1);
            rightClaw.setPosition(1);

            telemetry.addData("Claw", "Open");
        }
    }
}
