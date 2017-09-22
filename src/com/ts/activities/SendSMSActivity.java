package com.ts.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;

import static com.ts.constants.ApplicationConstants.*;

public class SendSMSActivity extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        String phoneNumber = extras.getString(TARGET_PHONE_NUMBER); 
        String textMessage = extras.getString("textMessage");
        
        sendSMSToClient(phoneNumber, textMessage);
        
        Intent returnIntent = new Intent();
        setResult(RESULT_OK,returnIntent);
        finish();
	}
	
	private void sendSMSToClient(String phoneNumber, String text){
		SmsManager sm = SmsManager.getDefault();
		sm.sendTextMessage(phoneNumber, null, text, null, null);
	
	}

}
