package com.example.jerye.flashlight.widget;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.example.jerye.flashlight.R;
import com.example.jerye.flashlight.service.CameraService;

/**
 * Created by jerye on 2/15/2017.
 */

public class CameraWidgetProvider extends AppWidgetProvider {
    Intent cameraIntent;
    protected Context mContext;
    CameraService cameraService;
    private static boolean mBound = false;
    public static final String serviceON = "serviceIsON";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);


        for (int appWidgetId : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.camera_widget);
            mContext = context.getApplicationContext();


            Intent cameraIntent = new Intent(context, CameraService.class);
            cameraIntent.setAction(serviceON);

            context.getApplicationContext().startService(cameraIntent);

            //Equivalent to startservice
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, cameraIntent, 0);

            remoteViews.setOnClickPendingIntent(R.id.widgetFrameView, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.camera_widget);


        if (CameraService.APPWIDGET_TORCH_IS_ON.equals(intent.getAction())) {
            remoteViews.setViewVisibility(R.id.widgetImageOn, ImageView.VISIBLE);
            remoteViews.setViewVisibility(R.id.widgetImageOff, ImageView.INVISIBLE);

        } else if (CameraService.APPWIDGET_TORCH_IS_OFF.equals(intent.getAction())){
            remoteViews.setViewVisibility(R.id.widgetImageOn, ImageView.INVISIBLE);
            remoteViews.setViewVisibility(R.id.widgetImageOff, ImageView.VISIBLE);

        }

        ComponentName componentName = new ComponentName(context, getClass());
        AppWidgetManager.getInstance(context).updateAppWidget(componentName,remoteViews);

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Intent cameraServiceIntentOnDelete = new Intent(context, CameraService.class);
        context.getApplicationContext().stopService(cameraServiceIntentOnDelete);
        CameraService.isTorchON = true;
        super.onDeleted(context, appWidgetIds);
    }


}
