package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.vuforia.HINT;
import com.vuforia.Vuforia;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

public class BBAutonomous extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    //private ColorSensor colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

    private VuforiaLocalizer vuforiaLocalizer;
    private VuforiaLocalizer.Parameters parameters;
    private VuforiaTrackables visionTargets;

    private VuforiaTrackable wheelsTarget;
    private VuforiaTrackable moonTarget;
    private VuforiaTrackable cratersTarget;
    private VuforiaTrackable nebulaTarget;

    private VuforiaTrackableDefaultListener wheelsListener;
    private VuforiaTrackableDefaultListener moonListener;
    private VuforiaTrackableDefaultListener cratersListener;
    private VuforiaTrackableDefaultListener nebulaListener;

    private OpenGLMatrix phoneLocation;

    private static final String VUFORIA_KEY = "AV/fDE3/////AAABmZ/ALu8qqkAHj/l2PufBjRdsEamDz7lAjr+aOLtYdYX7CKw2/ylO5TdJfQqrKMyFXeOlsrO3SkXUaPNQoQfF3VjmOQTtaX/5DkZAxg/EPJnyoy1x3D9sFiLuNd1lXPIsSr0ho2KlUd98gJ1GAuoKzQQNudG8s1WD6K2S+Zw9yy5JGIKmsbsvHdKJJMgPF88bR2j202eOMNKhiO8fidcCUKU9Rb0+D4fBaj1fMCR3pTJoWZkZeG8NV54J7jr33PrjBzTK1llzETQgG5SPe4nHSUxXCviS28NU9fQqjh7GC1wXliZKgujmkBvXrdVmDfq/woTB4KZdQpkZpMcEE0gyNKh+qPUR5ZxYvF1wH4PumGtg";


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

    // VUFORIA BELOW
    // POSSIBLE USE

    private double[] getPosition() {
        OpenGLMatrix lastKnownLocation = createMatrix(0, 0, 0, 0, 0, 0);
        // Ask the listener for the latest information on where the robot is
        OpenGLMatrix latestLocation = wheelsListener.getUpdatedRobotLocation();

        // The listener will sometimes return null, so we check for that to prevent errors
        if(latestLocation == null)
            latestLocation = moonListener.getUpdatedRobotLocation();
        if(latestLocation == null)
            latestLocation = cratersListener.getUpdatedRobotLocation();
        if(latestLocation == null)
            latestLocation = nebulaListener.getUpdatedRobotLocation();

        if(latestLocation != null)
            lastKnownLocation = latestLocation;

        float[] coordinates = lastKnownLocation.getTranslation().getData();
        double robotAngle = Orientation.getOrientation(lastKnownLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
        double[] position = {(double)coordinates[0], (double)coordinates[1], robotAngle};

        return position;
    }

    public boolean checkPosition(double targetX, double targetY, double robotX, double robotY) {
        return true;
    }

    private void setupVuforia() {
        // Setup parameters to create localizer
        parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId); // To remove the camera view from the screen, remove the R.id.cameraMonitorViewId
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.useExtendedTracking = false;
        vuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters);

        // These are the vision targets that we want to use
        // The string needs to be the name of the appropriate .xml file in the assets folder
        visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("RoverRuckus");
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        // Setup the target to be tracked
        wheelsTarget = visionTargets.get(0); // 0 corresponds to the wheels target
        wheelsTarget.setName("Wheels Target");
        wheelsTarget.setLocation(createMatrix(-1828, 0, 0, 90, 0, 90));

        moonTarget = visionTargets.get(1);
        moonTarget.setName("Moon Target");
        moonTarget.setLocation(createMatrix(1828, 0, 0, 90, 0, -90));

        cratersTarget = visionTargets.get(2);
        cratersTarget.setName("Craters Target");
        cratersTarget.setLocation(createMatrix(0, -1828, 0, 90, 0, 0));

        nebulaTarget = visionTargets.get(3);
        nebulaTarget.setName("Nebula Target");
        nebulaTarget.setLocation(createMatrix(0, 1828, 0, 90, 0, 180));

        // Set phone location on robot
        phoneLocation = createMatrix(0, 0, 0, 90, 0, 0);

        // Setup listener and inform it of phone information
        wheelsListener = (VuforiaTrackableDefaultListener) wheelsTarget.getListener();
        wheelsListener.setPhoneInformation(phoneLocation, parameters.cameraDirection);

        moonListener = (VuforiaTrackableDefaultListener) moonTarget.getListener();
        moonListener.setPhoneInformation(phoneLocation, parameters.cameraDirection);

        cratersListener = (VuforiaTrackableDefaultListener) cratersTarget.getListener();
        cratersListener.setPhoneInformation(phoneLocation, parameters.cameraDirection);

        nebulaListener = (VuforiaTrackableDefaultListener) nebulaTarget.getListener();
        nebulaListener.setPhoneInformation(phoneLocation, parameters.cameraDirection);
    }

    // Creates a matrix for determining the locations and orientations of objects
    // Units are millimeters for x, y, and z, and degrees for u, v, and w
    private OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w) {
        return OpenGLMatrix.translation(x, y, z).
                multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, u, v, w));
    }

    // Formats a matrix into a readable string
    private String formatMatrix(OpenGLMatrix matrix) {
        return matrix.formatAsTransform();
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}
