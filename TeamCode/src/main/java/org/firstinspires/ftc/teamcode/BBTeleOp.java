package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "BB TeleOp")
public class BBTeleOp extends OpMode {
    private DcMotor leftDrive;
    private DcMotor rightDrive;

    private DcMotor bottomArm;
    private DcMotor topArm;

    private Servo leftClaw;
    private Servo rightClaw;

    public void init() {
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");

        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        bottomArm = hardwareMap.get(DcMotor.class, "bottomArm");
        topArm = hardwareMap.get(DcMotor.class, "topArm");

        bottomArm.setDirection(DcMotorSimple.Direction.REVERSE);

        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
    }

    public void loop() {

        leftDrive.setPower(gamepad1.left_stick_y);
        rightDrive.setPower(gamepad1.right_stick_y);

        //bottomArm.setPower(gamepad2.left_stick_y);
        topArm.setPower(gamepad2.right_stick_y);

        if(gamepad2.a) {
            //close
            leftClaw.setPosition(0.8);
            rightClaw.setPosition(0.5);

            telemetry.addData("Claw", "Closed");
        } else if(gamepad2.b) {
            //open
            leftClaw.setPosition(0.5);
            rightClaw.setPosition(0.3);

            telemetry.addData("Claw", "Open");
        }
    }
}
