package de.hackersolutions.android.listener;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import de.hackersolutions.android.service.GPSService;

public class MyLocationListener implements android.location.LocationListener {
    private String tag = MyLocationListener.class.getName();
    private GPSService gPSService;

    Location mLastLocation;

    public MyLocationListener(String provider, GPSService gPSService) {
        Log.e(tag, "MyLocationListener " + provider);
        mLastLocation = new Location(provider);
        this.gPSService = gPSService;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e(tag, "onLocationChanged: " + location);
        gPSService.longi = location.getLongitude();
        gPSService.lati = location.getLatitude();
        mLastLocation.set(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.e(tag, "onProviderDisabled: " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.e(tag, "onProviderEnabled: " + provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e(tag, "onStatusChanged: " + provider);
    }
}