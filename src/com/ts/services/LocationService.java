package com.ts.services;


import java.util.List;

import com.ts.email.GMailSender;

import static com.ts.constants.ApplicationConstants.*;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

public class LocationService extends Service {
	LocationManager locationManager;
	LocationListener locationListener;
	String targerPhoneNumber;
	String targetEmail;
	Context cont = this;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		try{
	        Bundle extras = intent.getExtras();
	        targerPhoneNumber = extras.getString(TARGET_PHONE_NUMBER);
	        targetEmail = extras.getString(TARGET_EMAIL);
	        int frequency = Integer.parseInt(extras.getString(FREQUENCY)); 
	                
			//Toast.makeText(this, "Service Started ", Toast.LENGTH_LONG).show();
			locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    	locationListener = new MyLocationListener();
	    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, frequency, 10, locationListener );
	    	
		}catch(Exception ex){
			Toast.makeText(this, "Error"+ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	public class MyLocationListener implements LocationListener {
			SmsManager sm = SmsManager.getDefault();
			GMailSender sender = new GMailSender("wheremyandroid@gmail.com", "droid321");
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				String map = "maps.google.com/?q="+location.getLatitude()+","+location.getLongitude();
				sm.sendTextMessage(targerPhoneNumber, null,"Location is:\n"+ map , null, null);
				try{
					sender.sendMail("Location Update", "maps.google.com/?q="+location.getLatitude()+","+location.getLongitude(), "wheremyandroid@gmail.com", targetEmail);
				} catch(Exception ex){
					Toast.makeText(cont, "Error:"+ex.getMessage(), Toast.LENGTH_LONG).show();
				}
				
				//locationManager.removeUpdates(locationListener);
			}

			//if GPS is turned off
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				float[] coordinates = getlocation();
				
				String map = "maps.google.com/?q=" + coordinates[0] + "," + coordinates[1];
				
				sm.sendTextMessage(targerPhoneNumber, null,"GPS is turned off \n Last known location is: \n" + map , null, null);
							
				try{
					sender.sendMail("Location Update", "The GPS is turned off last known location is \n maps.google.com/?q="+coordinates[0]+","+coordinates[1], "wheremyandroid@gmail.com", targetEmail);
				} catch(Exception ex){
					Toast.makeText(cont, "Error:"+ex.getMessage(), Toast.LENGTH_LONG).show();

				}
				
				locationManager.removeUpdates(locationListener);
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub

			}
			public float[] getlocation() {
		        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		        List<String> providers = locationManager.getProviders(true);
		        Location l = null;
		        
		        for (int i = 0; i < providers.size(); i++) {
		            l = locationManager.getLastKnownLocation(providers.get(i));
		            if (l != null)
		               break;
		        }
		        float[] gps = new float[2];

		        if (l != null) {
		            gps[0] = (float) l.getLatitude();
		            gps[1] = (float) l.getLongitude();
		        }
		        return gps;
		    }
			        
		}
	
}
