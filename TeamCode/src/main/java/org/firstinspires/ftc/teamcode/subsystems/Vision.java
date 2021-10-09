package org.firstinspires.ftc.teamcode.subsystems;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@SuppressWarnings("unused")
public class Vision {

    private final Telemetry telemetry; // Telemetry shows us what it's seeing on the phone
    private final LinearOpMode opMode;

    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite"; // contains models of the objects that we scan for
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };

    private static final String VUFORIA_KEY = // License key, this tells Vuforia that we are licensed to use their code, or something
            " AfdtDjj/////AAABmSp18qqTqUEYrfASUDj9ojCEPgyv/+3QyV1T/YOsOoFzl7KZtvlusn/W+i4UPgZUCsQdd2xR9Vd1kXR4hoStrTiZrT7KxsuLH+R4lMgjJ3NP0Xx5k9a7cAE4gY2DCbf8SEN/+ewYXiOrk5MrM62OovFMgKU5BDZBw9osYg/QZxGMdBPEJdR7gsDhnGogUrRPcwbsSJkIHpmxycJ9Hf/AdamUprPkV+ta7VDgG8+ZnBXKWMitFenMgv9eXxvY9GFPJm59bP9kXAQAOnZwUPqwtXD/4zr6alQf0N43rAWHaQQ59E3C4JL7XqIxDf+EO3sK9aKmzAQOc9ozJlktK37fld3Ow7OdYMhWiIs37lJ7mAoW";

    private VuforiaLocalizer vuforia; // Vuforia tells the robot where it is via vision
    private TFObjectDetector tfod; // Tensor Flow Object Detector: It detects objects using the model asset file we send it

    public Vision(LinearOpMode opMode, HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.opMode = opMode;
        initVuforia();
        initTfod(hardwareMap);
    }

    @SuppressLint("DefaultLocale")
    public boolean finder() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(25);

        boolean duckDetected = false;

        long DETECTION_TIME = 3000; // How long we give it to determine whether or not there is a duck
        long startTimeMillis = System.currentTimeMillis();
        long deadline = startTimeMillis + DETECTION_TIME;

        while (opMode.opModeIsActive() && !duckDetected
                && deadline > System.currentTimeMillis()) {
            TelemetryPacket packet = new TelemetryPacket();

            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());

                duckDetected = true;

                for (Recognition recognition : updatedRecognitions) {
                    packet.put("label", recognition.getLabel());

                    packet.put("top,left", String.format("%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop()));
                    packet.put("right, bottom", String.format("%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom()));
                }
                dashboard.sendTelemetryPacket(packet);
            }
        }
        if (tfod != null) {
            tfod.shutdown();
        }

        return duckDetected;
    }

    private void initVuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        // Here it automatically finds the camera that we're using, last year we had to do this manually

        vuforia = ClassFactory.getInstance().createVuforia(parameters); // Initialize Vuforia given the parameters
    }

    private void initTfod(HardwareMap hardwareMap) {

        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName()); // ngl, I still don't understand this

        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f; // It was already set to 0.4f when we instantiated it. Why are we increasing it?

        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
}













