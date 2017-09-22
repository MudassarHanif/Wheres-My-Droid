package com.ts.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class PreventUnInstallService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Toast.makeText(this, "PreventUnInstallService Service Created", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

//		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onStart(Intent intent, int startId) {

		super.onStart(intent, startId);
		Toast.makeText(this, "______--------_______PreventUnInstallService Service Started", Toast.LENGTH_LONG).show();

		Process proc = null;
			 
			try {
			    proc = Runtime.getRuntime().exec("/system/bin/logcat -b main");
			} catch(IOException e) {
			    // work exception
			}
			 
			BufferedReader reader = null;
			try {
			    reader = new BufferedReader(new InputStreamReader(
			            proc.getInputStream()
			    ));
			 
			    String line;
			 
			    while ( true ) {
			        line = reader.readLine();
//			        Log.i("_-_-_-_-_-_-_-PreventUnInstallService: " , line);
			        if (line != null && line.matches(".*UninstallerActivity.*")) {
			        	Toast.makeText(this, "_-_-_-_-_-_-_-PreventUnInstallService: " + line, Toast.LENGTH_LONG).show();

					}
			    }
			}catch(Exception ex){
				ex.printStackTrace();
			}
		
	}
	
/*	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("~~~~~~~~PreventUnInstallService", "Received start id " + startId + ": " + intent);

		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}
*/
	
}
