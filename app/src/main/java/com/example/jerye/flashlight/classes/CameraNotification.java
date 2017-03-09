package com.example.jerye.flashlight.classes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.jerye.flashlight.R;
import com.example.jerye.flashlight.service.CameraService;

/**
 * Created by jerye on 2/19/2017.
 */

public class CameraNotification extends NotificationCompat {
    private Context context;
    private final int NOTIFICATION_ID = 1;
    private static NotificationManager mNotificationManager;
    private boolean mNotificationState;

    public CameraNotification(Context context, CameraPreferences cameraPreferences) {
        this.context = context;
        mNotificationState = cameraPreferences.getNotificationState();
    }


    public CameraNotification createNotification() {

        if(!mNotificationState){
            return this;
        }

        Intent resultIntent = new Intent(this.context, CameraService.class);
        PendingIntent resultPendingIntent = PendingIntent.getService(this.context, 0, resultIntent, 0);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.notification);
        remoteViews.setOnClickPendingIntent(R.id.layout,resultPendingIntent);



        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new android.support.v4.app.NotificationCompat.Builder(this.context)
                        .setSmallIcon(R.drawable.ic_stat_flamenotification)
                        .setContent(remoteViews)
                        .setAutoCancel(true);


//        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        return this;
    }

    public CameraNotification cancelNotification() {
        if (!mNotificationState){
            return this;
        }

        if (mNotificationManager != null) {
            mNotificationManager.cancel(NOTIFICATION_ID);
        }
        return this;
    }
}
