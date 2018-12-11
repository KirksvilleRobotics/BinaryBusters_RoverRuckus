package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

@Autonomous(name = "Vuforia Test")
public class VuforiaTest extends LinearOpMode {
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

    private OpenGLMatrix lastKnownLocation;
    private OpenGLMatrix phoneLocation;
//BRYAN IS A CODER!!!!!!!!!!
    private static final String VUFORIA_KEY = "AV/fDE3/////AAABmZ/ALu8qqkAHj/l2PufBjRdsEamDz7lAjr+aOLtYdYX7CKw2/ylO5TdJfQqrKMyFXeOlsrO3SkXUaPNQoQfF3VjmOQTtaX/5DkZAxg/EPJnyoy1x3D9sFiLuNd1lXPIsSr0ho2KlUd98gJ1GAuoKzQQNudG8s1WD6K2S+Zw9yy5JGIKmsbsvHdKJJMgPF88bR2j202eOMNKhiO8fidcCUKU9Rb0+D4fBaj1fMCR3pTJoWZkZeG8NV54J7jr33PrjBzTK1llzETQgG5SPe4nHSUxXCviS28NU9fQqjh7GC1wXliZKgujmkBvXrdVmDfq/woTB4KZdQpkZpMcEE0gyNKh+qPUR5ZxYvF1wH4PumGtg";

    private float robotX = 0;
    private float robotY = 0;
    private float robotAngle = 0;

    public void runOpMode() throws InterruptedException {
        setupVuforia();

        // We don't know where the robot is, so set it to the origin
        // If we don't include this, it would be null, which would cause errors later on
        lastKnownLocation = createMatrix(0, 0, 0, 0, 0, 0);

        waitForStart();

        // Start tracking the targets
        visionTargets.activate();

        while(opModeIsActive()) {
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

            robotX = coordinates[0];
            robotY = coordinates[1];
            robotAngle = Orientation.getOrientation(lastKnownLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;

            // Send information about whether the target is visible, and where the robot is
            //telemetry.addData("Tracking " + target.getName(), listener.isVisible());
            telemetry.addData("Last Known Location", formatMatrix(lastKnownLocation));
            telemetry.addData("X:", robotX);
            telemetry.addData("Y:", robotY);
            telemetry.addData("Angle", robotAngle);

            // Send telemetry and idle to let hardware catch up
            telemetry.update();
            idle();
        }
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
        wheelsTarget.setLocation(createMatrix(0, -1828, 0, 90, 0, 90));

        moonTarget = visionTargets.get(1);
        moonTarget.setName("Moon Target");
        moonTarget.setLocation(createMatrix(0, 1828, 0, 90, 0, -90));

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
}
