package com.example.jerye.flashlight.classes;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jerye on 2/21/2017.
 */

public class CameraPreferences {
    private SharedPreferences sharedPreferences;

    public CameraPreferences(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean getTimerState(){
        return sharedPreferences.getBoolean("timer", false);
    }

    public int getTimerValue(){
        return sharedPreferences.getInt("timer_value", 10)*60000;
    }


    public boolean getVibrateState(){

        return sharedPreferences.getBoolean("vibrate", false);
    }

    public int getVibrateValue(){
        return sharedPreferences.getInt("vibrate_value", 1)*60000-1000;
    }

    public boolean getNotificationState(){
        return sharedPreferences.getBoolean("notification", true);
    }

}
