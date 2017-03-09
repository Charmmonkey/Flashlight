package com.example.jerye.flashlight.classes;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Build;

/**
 * Created by jerye on 2/14/2017.
 */

@TargetApi(Build.VERSION_CODES.M)
public class CameraNew implements CameraSupport {

    CameraManager cameraManager;
    boolean isTorchEnabled = false;

    public CameraNew(Context context) {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

    }


    @Override
    public CameraSupport turnOnTorch() {
        turnTorchOnOff(true);
        return this;
    }

    @Override
    public CameraSupport turnOffTorch() {
        turnTorchOnOff(false);
        return this;
    }


    private void turnTorchOnOff(boolean isOn) {
        try {
            String[] cameraId = cameraManager.getCameraIdList();
            cameraManager.setTorchMode(cameraId[0], isOn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
