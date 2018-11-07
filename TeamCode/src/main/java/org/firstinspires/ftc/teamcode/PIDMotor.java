package org.firstinspires.ftc.teamcode;

/*
TODO LIST:
[] Work on control structure
[] Design method for constant updates
[] Implement into teleop
[] Test constants
*/

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class PIDMotor {
    DcMotor motor;

    private int position;

    private final int proportion;
    private final int integral;
    private final int derivative;
    private final double maxPower;

    private int targetPosition;
    private int oldErrorP;
    private long currentTime;
    private long oldTime;
    
    private double errorI;

    public PIDMotor(DcMotor motor, int proportion, int integral, int derivative, double maxPower) {
        this.motor = motor;
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        this.proportion = proportion;
        this.integral = integral;
        this.derivative = derivative;
        this.maxPower = maxPower;

        targetPosition = motor.getCurrentPosition();
        oldErrorP = 0;
        currentTime = 1;
        oldTime = 0;

        errorI = 0;
    }

    public void setPosition() {
        targetPosition = motor.getCurrentPosition(); //get motor position
    }

    public void update() {
        currentTime = System.currentTimeMillis();

        long dt = currentTime - oldTime;

        int errorP =  targetPosition - motor.getCurrentPosition(); //current motor position
        double errorD = (errorP - oldErrorP) / dt;

        double power = proportion * errorP + integral * errorI + derivative * errorD;

        if((power >= 0 && power <= maxPower) && ((errorP >= 0 && errorI >= 0)) || (errorP < 0 && errorI < 0)) {

        } else {
            errorI = errorI + dt * 1 * errorP;
        }

        oldErrorP = errorP;

        motor.setPower(power);
    }

    public void setPower(double power) {
        motor.setPower(power);
    }

    public void reverse() {
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
