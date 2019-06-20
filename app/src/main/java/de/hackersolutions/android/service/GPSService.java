package de.hackersolutions.android.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;

import java.util.Date;
import de.hackersolutions.android.data.AppDatabase;
import de.hackersolutions.android.data.domain.Location;
import de.hackersolutions.android.data.domain.dao.LocationDao;
import de.hackersolutions.android.listener.MyLocationListener;

public class GPSService extends Service {

    private String tag = GPSService.class.getName();
    private static boolean stopService = false;
    private Handler mHandler = new Handler();
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    public double lati,  longi;

    private AppDatabase database = Room.databaseBuilder(GPSService.this, AppDatabase.class, "db-location")
            .allowMainThreadQueries()
            .build();
    private LocationDao dao = database.getLocationDao();

    LocationListener[] mLocationListeners = new LocationListener[]{
            new MyLocationListener(LocationManager.GPS_PROVIDER, this),
            new MyLocationListener(LocationManager.NETWORK_PROVIDER, this)
    };


    final Runnable ToastRunnable = new Runnable(){
        @SuppressLint("MissingPermission")
        public void run(){
            try {
                mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                        mLocationListeners[0]);
            } catch (Exception e) {
                Log.d(tag, e.getMessage());
            }

                Location loc = new Location(longi, lati, new Date());
                dao.insert(loc);

            Toast.makeText(getApplicationContext(), "Long: " + String.valueOf(longi) + ", Lati: " + String.valueOf(lati),
                    Toast.LENGTH_LONG).show();
            if(stopService) {
                mHandler.removeCallbacks(ToastRunnable);
            } else {
                mHandler.postDelayed( ToastRunnable, 5000);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(tag, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(tag, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);



        Toast.makeText(this, "onhandleintent", Toast.LENGTH_SHORT).show();
        mHandler.postDelayed( ToastRunnable, 5000);
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService = false;
        Log.i(tag, "Service stopped");
    }

    public static void stopTracking() {
        stopService = true;
    }
}
