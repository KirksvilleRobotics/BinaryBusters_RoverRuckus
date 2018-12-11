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

    //private PIDMotor bottomArm;
    private DcMotor bottomArm;
    private DcMotor topArm;

    private Servo leftClaw;
    private Servo rightClaw;

    private final double THRESHOLD = 0.05;
    private boolean positionSet = false;

    public void init() {
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");

        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        //bottomArm = new PIDMotor(hardwareMap.get(DcMotor.class, "bottomArm"), 500, 500, 500, 1.0);
        bottomArm = hardwareMap.get(DcMotor.class, "bottomArm");
        topArm = hardwareMap.get(DcMotor.class, "topArm");

        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
    }

    public void loop() {

        leftDrive.setPower(gamepad1.left_stick_y);
        rightDrive.setPower( gamepad1.right_stick_y);

        /*if(Math.abs(gamepad2.left_stick_y) >= THRESHOLD) {
            bottomArm.setPower(gamepad2.left_stick_y);
            positionSet = false;
        } else {
            if(!positionSet) {
                bottomArm.setPosition();
                positionSet = true;
            }
            bottomArm.update();
        }*/

        bottomArm.setPower(-gamepad2.left_stick_y);
        topArm.setPower(gamepad2.right_stick_y);

        if(gamepad2.right_bumper) {
            //close
            leftClaw.setPosition(0.45);
            rightClaw.setPosition(0.65);

            telemetry.addData("Claw", "Closed");
        } else if(gamepad2.left_bumper) {
            //open
            leftClaw.setPosition(1);
            rightClaw.setPosition(0.3);

            telemetry.addData("Claw", "Open");
        }
    }
}
