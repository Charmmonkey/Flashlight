package com.example.jerye.flashlight;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.jerye.flashlight.service.CameraService;
import com.example.jerye.flashlight.widget.CameraWidgetProvider;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by jerye on 2/19/2017.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";
    private int preferenceValue;
    private boolean preferenceState;
    private static Resources resource;
    public String timerKey;
    public String timerValueKey;
    public int timerValueDefault;
    public int timerValueSummaryId;
    public String vibrateKey;
    public String vibrateValueKey;
    public int vibrateValueDefault;
    public int vibrateValueSummaryId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        timerKey = getString(R.string.preference_timer_key);
        timerValueKey = getString(R.string.preference_timer_value_key);
        timerValueDefault = 10;
//                getString(R.string.preference_timer_value_default);
        timerValueSummaryId = R.string.preference_timer_summary;
        vibrateKey = getString(R.string.preference_vibrate_key);
        vibrateValueKey = getString(R.string.preference_vibrate_value_key);
        vibrateValueDefault = 1;
//                getString(R.string.preference_vibrate_value_default);
        vibrateValueSummaryId = R.string.preference_vibrate_summary;

        addPreferencesFromResource(R.xml.camera_preference);
        View view = inflater.inflate(R.layout.activity_main, container);


        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(getActivity(), TOAST_TEXT, Toast.LENGTH_LONG).show();

        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();

        setPreferenceSummary(sharedPreferences,
                timerValueKey,
                timerValueDefault,
                timerValueSummaryId);
        setPreferenceSummary(sharedPreferences,
                vibrateValueKey,
                vibrateValueDefault,
                vibrateValueSummaryId);

        return super.onCreateView(inflater, container, savedInstanceState);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resource = getResources();


    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        switch (key) {
            case "timer_value":
                preferenceState = sharedPreferences.getBoolean(timerKey, false);
                if (preferenceState) {
                    setPreferenceSummary(sharedPreferences,
                            key,
                            timerValueDefault,
                            timerValueSummaryId);
                }

                break;
            case "vibrate_value":
                preferenceState = sharedPreferences.getBoolean(vibrateKey, false);
                if (preferenceState) {
                    setPreferenceSummary(sharedPreferences,
                            key,
                            vibrateValueDefault,
                            vibrateValueSummaryId);
                }

                break;
            default:
                break;
        }

    }

    @Override
    public void onPause() {
        if (isMyServiceRunning(CameraService.class, getActivity())) {

            Intent cameraServiceIntent = new Intent(getActivity(), CameraService.class);
            cameraServiceIntent.setAction(CameraWidgetProvider.serviceON);

            getActivity().stopService(cameraServiceIntent);

            getActivity().startService(cameraServiceIntent);
        }

        super.onPause();
    }

    @Override
    public void onStop() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }


    private void setPreferenceSummary(SharedPreferences sharedPreferences, String key, int defaultValue, int stringId) {

        preferenceValue = sharedPreferences.getInt(key, defaultValue);
        String preferenceVibrateString = String.format(resource.getString(stringId), preferenceValue);
        this.findPreference(key).setSummary(preferenceVibrateString);


    }

    private boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

