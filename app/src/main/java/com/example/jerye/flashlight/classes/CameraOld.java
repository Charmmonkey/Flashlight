package com.example.jerye.flashlight.classes;

import android.hardware.Camera;
import android.util.Log;

/**
 * Created by jerye on 2/13/2017.
 */

@SuppressWarnings("deprecation")
public class CameraOld implements CameraSupport {

    private static Camera camera;
    private static Camera.Parameters parameters;


    @Override
    public CameraSupport turnOnTorch() {
        try {
            camera = Camera.open(0);
            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public CameraSupport turnOffTorch() {

        if (parameters != null) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.stopPreview();
            camera.release();
        }
        return this;

    }
}
