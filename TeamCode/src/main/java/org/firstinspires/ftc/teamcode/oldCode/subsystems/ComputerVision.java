/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

public class ComputerVision {

    private final Telemetry telemetry;
    private final LinearOpMode opMode;

    public enum StackType {NONE, ONE, QUAD}

    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    private static final String VUFORIA_KEY =
            " AfdtDjj/////AAABmSp18qqTqUEYrfASUDj9ojCEPgyv/+3QyV1T/YOsOoFzl7KZtvlusn/W+i4UPgZUCsQdd2xR9Vd1kXR4hoStrTiZrT7KxsuLH+R4lMgjJ3NP0Xx5k9a7cAE4gY2DCbf8SEN/+ewYXiOrk5MrM62OovFMgKU5BDZBw9osYg/QZxGMdBPEJdR7gsDhnGogUrRPcwbsSJkIHpmxycJ9Hf/AdamUprPkV+ta7VDgG8+ZnBXKWMitFenMgv9eXxvY9GFPJm59bP9kXAQAOnZwUPqwtXD/4zr6alQf0N43rAWHaQQ59E3C4JL7XqIxDf+EO3sK9aKmzAQOc9ozJlktK37fld3Ow7OdYMhWiIs37lJ7mAoW";

    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    public ComputerVision(LinearOpMode opMode, HardwareMap hardwareMap, Telemetry telemetry) {
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


    public StackType googles() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(25);

        boolean stackDetected = false;
        StackType result = StackType.NONE;
        long DETECTION_TIME = 2000;

        long startTimeMillis = System.currentTimeMillis();
        long deadline = startTimeMillis + DETECTION_TIME;
        while (opMode.opModeIsActive() && !stackDetected
                && deadline > System.currentTimeMillis()) {

            TelemetryPacket packet = new TelemetryPacket();

            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());

                for (Recognition recognition : updatedRecognitions) {
                    if (recognition.getLabel().equals("Single")) {
                        result = StackType.ONE;
                        stackDetected = true;
                    } else if (recognition.getLabel().equals("Quad")) {
                        result = StackType.QUAD;
                        stackDetected = true;
                    }

                    packet.put("label", recognition.getLabel());

                    packet.put("top,left", String.format("%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop()));
                    packet.put("right, bottom", String.format("%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom()));


//                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
//                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.0" +
//                                    "3f",
//                            recognition.getLeft(), recognition.getTop());
//                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
//                            recognition.getRight(), recognition.getBottom());
                }
//                telemetry.update();
                dashboard.sendTelemetryPacket(packet);
            }
        }


        if (tfod != null) {
            tfod.shutdown();
        }

        return result;

    }

    private void initVuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod(HardwareMap hardwareMap) {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}

