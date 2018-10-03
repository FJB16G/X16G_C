package jp.ac.chiba_fjb.x16g_c.test09202;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import com.google.android.gms.maps.LocationSource;

public class MyLocationSource implements LocationSource, android.location.LocationListener {
        private final static int GPS_TIME = 5 * 10000;//5秒
        private final static int NET_TIME = 10 * 10000;//10秒
        private Context mContext;
        private LocationManager mLocationManager;
        private LocationSource.OnLocationChangedListener mListener;
        private Location mLastLocation;

        MyLocationSource(Context context) {
      mContext = context;
       mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        }

        @Override
public void activate(OnLocationChangedListener listener) {

        mListener = listener;
        LocationProvider gpsProvider = mLocationManager.getProvider(LocationManager.GPS_PROVIDER);
       if (gpsProvider != null) {
      //警告は無視
       mLocationManager.requestLocationUpdates(gpsProvider.getName(), GPS_TIME, 10, this);
        }
        LocationProvider networkProvider = mLocationManager.getProvider(LocationManager.NETWORK_PROVIDER);
        ;
      if(networkProvider!=null){
       //警告は無視
       mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, NET_TIME, 0, this);
       }

        }

      @Override
public void deactivate() {
        //警告は無視
     mLocationManager.removeUpdates(this);
       }
      @Override
public void onLocationChanged(Location location) {
  if(mListener != null)
      {
   mListener.onLocationChanged(location);
    mLastLocation = location;
        }
       }
        @Override
public void onProviderDisabled(String arg0) {
    // TODO 自動生成されたメソッド・スタブ

        }
        @Override
public void onProviderEnabled(String arg0) {
        // TODO 自動生成されたメソッド・スタブ

        }
        @Override
public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO 自動生成されたメソッド・スタブ

        }
        }