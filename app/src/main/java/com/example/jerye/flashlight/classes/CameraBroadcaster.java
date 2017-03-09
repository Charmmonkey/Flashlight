package com.example.jerye.flashlight.classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Camera;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;

import com.example.jerye.flashlight.service.CameraService;

/**
 * Created by jerye on 2/19/2017.
 */

public class CameraBroadcaster {
    private static CountDownTimer countDownTimer;
    private Context context;
    private CameraPreferences mCameraPreferences;
    private boolean mTimerState;
    private boolean mVibrateState;
    private int mTimerValue;
    private int mVibrateValue;
    private int countDownFrom;
    private static Vibrator mVibrator;

    public CameraBroadcaster(Context context, CameraPreferences cameraPreferences) {
        this.context = context;
        mCameraPreferences = cameraPreferences;
        mTimerState = mCameraPreferences.getTimerState();
        mTimerValue = mCameraPreferences.getTimerValue();
        mVibrateState = mCameraPreferences.getVibrateState();
        mVibrateValue = mCameraPreferences.getVibrateValue();
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

    }

    public CameraBroadcaster broadcastOnOffIntent(String currentTorchState) {
        Intent cameraOnIntent = new Intent(currentTorchState);
        context.sendBroadcast(cameraOnIntent);
        return this;
    }


    public CameraBroadcaster setTimer(final Intent intent) {
        if (mTimerState) {
            countDownFrom = mTimerValue;
        } else {
            countDownFrom = 600000000;

        }


        countDownTimer = new CountDownTimer(countDownFrom, mVibrateValue) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (mVibrateState) {
                    mVibrator.vibrate(500);

                } else {
                }
            }
            @Override
            public void onFinish() {
                context.stopService(intent);
            }
        };
        countDownTimer.start();

        return this;
    }

    public CameraBroadcaster cancelTimer() {
        if(countDownTimer != null){
            countDownTimer.cancel();
        }else{

        }
        return this;
    }
}
