package com.alex.cycling.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by comexs on 16/3/28.
 */
public class LocationSersor implements LocationListener {


    private LocationManager mLocationManager;
    private LocationListener listener;
    private CacheWorkThread trackWorkThread;

    public void start(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
//        trackWorkThread = new CacheWorkThread(context, this);
//        trackWorkThread.start();
    }

    public void end() {
        if (null == mLocationManager) {
            return;
        }
        if (null != trackWorkThread) trackWorkThread.quit();
        mLocationManager.removeUpdates(this);
    }

    public void setLocationListener(LocationListener listener) {
        this.listener = listener;
    }


    @Override
    public void onLocationChanged(Location location) {
        if (null != listener) listener.onLocationChange(location, 1);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        int signal = 0;
        if (listener != null) {
            listener.onLocationChange(null, signal);
        }
    }


    public interface LocationListener {

        void onLocationChange(Location location, int signal);

    }
}
