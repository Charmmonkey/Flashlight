package com.example.jerye.flashlight;

import android.content.Intent;

import com.example.jerye.flashlight.classes.CameraNotification;
import com.example.jerye.flashlight.service.CameraService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jerye on 2/14/2017.
 */

@Singleton
@Component(modules = {CameraModule.class})
public interface CameraComponent {
    void inject(CameraService cameraService);

}
