package de.hackersolutions.android.activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import de.hackersolutions.android.R;
import de.hackersolutions.android.service.GPSService;

public class SettingsActivity extends AppCompatActivity {

    private Switch settingsTracking;
    private EditText settingsTrackingInterval;

    private SharedPreferences sharedpreferences;

    private String tag = SettingsActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsTracking = (Switch) findViewById(R.id.switch_location_tracking);
        settingsTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor sharedpreferencesEditor = sharedpreferences.edit();
                sharedpreferencesEditor.putBoolean("StatusTracking", settingsTracking.isChecked());
                sharedpreferencesEditor.commit();

                Intent intent = new Intent(SettingsActivity.this, GPSService.class);
                if(settingsTracking.isChecked()) {
                    startService(intent);
                    Log.i(tag, "Start");
                } else {
                    GPSService.stopTracking();
                    Log.i(tag, "Stop");
                }
            }
        });

        /*settingsTrackingInterval = (EditText) findViewById(R.id.input_trackinginterval);
        settingsTrackingInterval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    SharedPreferences.Editor sharedpreferencesEditor = sharedpreferences.edit();
                    sharedpreferencesEditor.putInt("TrackingInterval", Integer.valueOf(settingsTrackingInterval.getText().toString()));
                    sharedpreferencesEditor.commit();
                }
            }
        });*/

        sharedpreferences = getSharedPreferences("SettingsPref", Context.MODE_PRIVATE);
        boolean trackingStatus = sharedpreferences.getBoolean("StatusTracking", false);
        try {
            settingsTracking.setChecked(trackingStatus);
        } catch (NullPointerException e) {
            Log.d("Error", e.getMessage());
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

        int trackingInterval = sharedpreferences.getInt("TrackingInterval", 30);
        try {
            settingsTrackingInterval.setText(trackingInterval, TextView.BufferType.EDITABLE);
        } catch (NullPointerException e) {
            Log.d("Error", e.getMessage());
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

}
