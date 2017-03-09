package com.example.jerye.flashlight.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.jerye.flashlight.CameraComponent;
import com.example.jerye.flashlight.CameraModule;
import com.example.jerye.flashlight.classes.CameraBroadcaster;
import com.example.jerye.flashlight.classes.CameraNotification;
import com.example.jerye.flashlight.classes.CameraSupport;
import com.example.jerye.flashlight.DaggerCameraComponent;
import com.example.jerye.flashlight.R;

import javax.inject.Inject;

/**
 * Created by jerye on 2/15/2017.
 */

public class CameraService extends Service {
    private static final String serviceON = "serviceIsON";
    public static boolean isTorchON = true; //initial state when widget created
    public static final String APPWIDGET_TORCH_IS_ON = "com.example.jerye.flashlight.TORCH_ON";
    public static final String APPWIDGET_TORCH_IS_OFF = "com.example.jerye.flashlight.TORCH_OFF";

    private final IBinder mBinder = new CameraBinder();
    @Inject
    CameraSupport cameraSupport;
    @Inject
    CameraNotification cameraNotification;
    @Inject
    CameraBroadcaster cameraBroadcaster;


    public class CameraBinder extends Binder {

        public CameraService getService() {
            return CameraService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        CameraComponent cameraComponent = DaggerCameraComponent.builder()
                .cameraModule(new CameraModule(this))
                .build();
        cameraComponent.inject(this);


        try {
            if (isTorchON) {
                stopSelf();
            } else {
                cameraBroadcaster.setTimer(intent);
                cameraSupport.turnOnTorch();
                cameraNotification.createNotification();
                cameraBroadcaster.broadcastOnOffIntent(APPWIDGET_TORCH_IS_ON);
                isTorchON = true;
            }
        } catch(Exception e) {

            stopSelf();
        }

        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public void onDestroy() {
        cameraBroadcaster.cancelTimer();
        cameraSupport.turnOffTorch();
        cameraNotification.cancelNotification();
        cameraBroadcaster.broadcastOnOffIntent(APPWIDGET_TORCH_IS_OFF);
        isTorchON = false;
        super.onDestroy();

    }





}
