package com.ts.services;

import static com.ts.constants.ApplicationConstants.TARGET_EMAIL;
import static com.ts.constants.ApplicationConstants.TARGET_PHONE_NUMBER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import com.ts.datahandler.DataHandler;
import com.ts.email.Mail;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.CallLog;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class ContentHistoryService extends Service{

	private static final int NUMBER_OF_SMS_TO_SEND = 5;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

//		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onStart(Intent intent, int startId) {

		super.onStart(intent, startId);
		String targetPhoneNumber = DataHandler.getStringPreferences(TARGET_PHONE_NUMBER);
        String targetEmail = DataHandler.getStringPreferences(TARGET_EMAIL);
		
        String[] smsArray = sendSmsHistory(targetPhoneNumber);
		
        Mail mail = new Mail("wheremyandroid@gmail.com", "droid321");
		
        String[] toArr = {targetEmail.trim()}; 
        mail.set_to(toArr); 
        mail.set_from("wheremyandroid@gmail.com"); 
        mail.set_subject("Where is my droid logs"); 
        
        String [] numbers = getLastNumbers(5);
        String body = "Here is your call logs: \n";
        for(int i=0;i<numbers.length;i++){
        	body = body + numbers[i] + "\n";
        	
        }
        
        body += "\n\nThe last 5 sent SMS are:\n";
        for(int i=0;i<smsArray.length;i++){
        	body = body + smsArray[i] + "\n";
        }
        
        mail.setBody(body); 
    	
    	try{
    		mail.addAttachment(getLastPicture());
    		if(mail.send()) { 
    			Toast.makeText(this, "Email was sent.", Toast.LENGTH_LONG).show();
	          } else { 
	            Toast.makeText(this, "Email was not sent.", Toast.LENGTH_LONG).show(); 
	          } 
    	} catch(Exception ex){
    	    Toast.makeText(this, "Error: " + ex.getMessage() , Toast.LENGTH_LONG).show();
    	}
    	            	
    	  

	}
	
	private String[] sendSmsHistory(String targetPhoneNumber){
		
		Uri uri = Uri.parse("content://sms/sent");
		Cursor c= getContentResolver().query(uri, null, null ,null,null);

		int noOfMsgToSend = c.getCount() < NUMBER_OF_SMS_TO_SEND ? c.getCount() : NUMBER_OF_SMS_TO_SEND;
		String smsArray[] = new String[noOfMsgToSend];

		String[] body = new String[noOfMsgToSend];
		String[] number = new String[noOfMsgToSend];
		
		
		if(c.moveToFirst()){
			for(int i = 0; i < noOfMsgToSend; i++){
				body[i] = c.getString(c.getColumnIndexOrThrow("body")).toString();
				number[i] = c.getString(c.getColumnIndexOrThrow("address")).toString();
				
				smsArray[i] = number[i] + ": \n" + body[i];
				c.moveToNext();
			}
		}
		c.close();
		
		return smsArray;
		
	}
	
	private String[] getLastNumbers(int quantity){
		String [] numbers = new String[quantity];
		try{
			Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, android.provider.CallLog.Calls.DATE + " DESC");
			int numIndex = cursor.getColumnIndexOrThrow("number");
			int dateIndex = cursor.getColumnIndexOrThrow("date");
			for(int i = 0; i < quantity; i++){
				cursor.moveToNext();
				Date date = new Date(Long.parseLong(cursor.getString(dateIndex))); 
				numbers[i]  =  cursor.getString(numIndex) + " | " + date.toLocaleString();
			}
			
			
		} catch(Exception ex){
			Toast.makeText(this, "Error:"+ex.getMessage(), Toast.LENGTH_LONG).show();
		}
		return numbers;
	}
	
	private String getLastPicture(){
		String[] proj = { MediaStore.Images.Media.DATA };
	    Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, null );
		cursor.moveToLast();
		String path = cursor.getString(0);
		return path;
	}
}
