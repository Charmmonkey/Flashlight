package com.example.jerye.flashlight;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.example.jerye.flashlight.classes.CameraBroadcaster;
import com.example.jerye.flashlight.classes.CameraNew;
import com.example.jerye.flashlight.classes.CameraNotification;
import com.example.jerye.flashlight.classes.CameraOld;
import com.example.jerye.flashlight.classes.CameraPreferences;
import com.example.jerye.flashlight.classes.CameraSupport;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jerye on 2/14/2017.
 */

@Module
public class CameraModule {

    Context context;

    public CameraModule(Context context){
        this.context = context;
    }

    @Provides @Singleton
    CameraSupport provideCameraSupport(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            return new CameraNew(this.context);
        } else {
            return new CameraOld();
        }
    }

    @Provides @Singleton
    CameraNotification provideCameraNotification(CameraPreferences cameraPreferences){
        return new CameraNotification(this.context, cameraPreferences);
    }

    @Provides @Singleton
    CameraBroadcaster provideCameraBroadcaster(CameraPreferences cameraPreferences){
        return new CameraBroadcaster(this.context, cameraPreferences);
    }

    @Provides @Singleton
    CameraPreferences provideCameraPreferences(){
        return new CameraPreferences(this.context);
    }
}
