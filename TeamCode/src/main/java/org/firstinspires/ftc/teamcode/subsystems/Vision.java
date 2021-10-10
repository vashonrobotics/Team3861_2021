package org.firstinspires.ftc.teamcode.subsystems;

import android.annotation.SuppressLint;
import android.util.Log;

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
            "ATX87s7/////AAABmdP75JTOE0ShgAJ1CnEf4ahMNo1qgzeM+u53RnfxoGWfzuBp09/Lua+76wJ4nngpzRbp2O08gMzvknONJiIRBZP7JXlZx87u0NFL1KsSVPAYyHYnMknl4BzgCvRJ5gLzwd8NKcd5PLdUd1eEKRfikRr087Vds6hAlu7YRF9OmmhO2Hi0e1UmOhnWysg0CktFRTITaCW6brYVC1IfHl8R9GB0xgdlcuNztOm0LtOCf48DCsaetn2hlhC7mjWt15CjNFyK7w47M0OovFoghxhf+vtKMxqcyzqQPu3/OqqgPpGboeiNnLvbaChcQ7gCg7W2skUp5/BTZJMqO0oyQceolzUuCrdhf7n+D1dD4h6WPxyS";
    private VuforiaLocalizer vuforia; // Vuforia tells the robot where it is via vision
    private TFObjectDetector tfod; // Tensor Flow Object Detector: It detects objects using the model asset file we send it

    public Vision(LinearOpMode opMode, HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.opMode = opMode;
        initVuforia();
        initTfod(hardwareMap);
    }

    public void init() {
        if (tfod != null) {
            tfod.activate();

            // Uncomment the following line if you want to adjust the magnification and/or the aspect ratio of the input images.
            //tfod.setZoom(2.5, 1.78);
        }
    }

    @SuppressLint("DefaultLocale")
    public boolean ducktective() {
        FtcDashboard.start();
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

                for (Recognition recognition : updatedRecognitions) {
                    if (recognition.getLabel().equals("Duck")) {
                        duckDetected = true;
                    } else if (recognition.getLabel().equals("Marker")) {
                        duckDetected = false;
                    }
                }

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

        String packageName = hardwareMap.appContext.getPackageName();
        Log.d("packageName: ", packageName);

        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", packageName); // ngl, I still don't understand this

        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f; // It was already set to 0.4f when we instantiated it. Why are we increasing it?
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;

        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
}













