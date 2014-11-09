package com.themigration.gps_poc;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPS_Module {
	protected LocationManager locationManager;
	protected LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			latitude = location.getLatitude();
			longitude = location.getLongitude();
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}
		@Override
		public void onProviderEnabled(String provider) {}
		@Override
		public void onProviderDisabled(String provider) {}
	};
	
	protected Context context;
	protected Criteria criteria;
	
	private double latitude;
	private double longitude;
	
    public GPS_Module(final Context context) {
        this.context = context;
    	
    	locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    	
    	criteria = new Criteria();
    	criteria.setAccuracy(Criteria.ACCURACY_LOW);
    }
    
    public void updateLocation() {
    	locationManager.requestSingleUpdate(criteria, locationListener, null);
    }
    
    public double getLatitude() {
    	return latitude;
    }
    
    public double getLongitude() {
    	return longitude;
    }
}