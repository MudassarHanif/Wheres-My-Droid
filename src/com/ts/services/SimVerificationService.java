package com.ts.services;

import static com.ts.constants.ApplicationConstants.*;

import com.ts.activities.SendSMSActivity;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class SimVerificationService extends Service{

	private String simSerialNumber;
	private String targetPhoneNumber;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		simSerialNumber = getSharedPreferences(SIM_SERIAL_NUM);
		targetPhoneNumber = getSharedPreferences(TARGET_PHONE_NUMBER);
		
//		Toast.makeText(this, "Service Created. Sim Serial Number: " + simSerialNumber, Toast.LENGTH_LONG).show();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

//		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onStart(Intent intent, int startId) {

		super.onStart(intent, startId);
//		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

		if(! isSameUser()){
			/*Call an activity to send an SMS to specified number about stolen phone*/
			Intent sendStolenSmsIntent = new Intent(this, SendSMSActivity.class);
			sendStolenSmsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			String smsMessage = "The sim card is changed in the phone. \nNew sim number is: " + getSimSerialNumber() + "\nNew Phone Number: " + getPhoneNumber();
			sendStolenSmsIntent.putExtra("textMessage", smsMessage);
			sendStolenSmsIntent.putExtra(TARGET_PHONE_NUMBER, targetPhoneNumber);
			startActivity(sendStolenSmsIntent);
			
		}

	}
	
	/**check if previous sim serial number stored in preferences is different form 
	/*the sim serial number retrieved from telephonyManager.
	 * 
	 * @return isSameUser
	 */
	private boolean isSameUser(){
		boolean isSameUser = false;
		String currentUserSimNum = getSimSerialNumber();
		if(currentUserSimNum.equals(simSerialNumber)){
			isSameUser = true;
//			Toast.makeText(this, "same user", Toast.LENGTH_LONG).show();
//		}else{
//			Toast.makeText(this, "Phone is stolen", Toast.LENGTH_LONG).show();
		}
		
		return isSameUser;
	}
	
	private String getSharedPreferences(String key) {
		SharedPreferences settings = getSharedPreferences(PREFS_FILE_NAME, 0);
		return settings.getString(key, null);

	}
	
	private String getSimSerialNumber() {
		return ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getSimSerialNumber();
	}

	private String getPhoneNumber() {
		return ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getLine1Number();
	}
}
