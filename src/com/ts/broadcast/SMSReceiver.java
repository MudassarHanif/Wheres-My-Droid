package com.ts.broadcast;

import static com.ts.constants.ApplicationConstants.*;

import com.ts.datahandler.DataHandler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;


public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "boradcast receiver called", Toast.LENGTH_SHORT).show();
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		String receivedCompleteSMS="";
        Object messages[] = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];
        for (int n = 0; n < messages.length; n++) {
            smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
            receivedCompleteSMS = receivedCompleteSMS + smsMessage[n].getMessageBody(); 
        }
        receivedCompleteSMS = receivedCompleteSMS.trim().toLowerCase();
        
//        String receivedPhoneNumber = smsMessage[0].getOriginatingAddress();
//        Toast.makeText(context, "sms is: " + receivedCompleteSMS, Toast.LENGTH_SHORT).show();
        String gpsAttentionWord = DataHandler.getStringPreferences(GPS_ATTENTION_WORD);
        String eraseRecAttentionWord = DataHandler.getStringPreferences(ERASE_ATTENTION_WORD);
        String retrieveHistoryAttentionWord = DataHandler.getStringPreferences(GET_HISTORY_ATTENTION_WORD);
        String targetPhoneNumber = DataHandler.getStringPreferences(TARGET_PHONE_NUMBER);
        String targetEmail = DataHandler.getStringPreferences(TARGET_EMAIL);
        String frequency = DataHandler.getStringPreferences(FREQUENCY);
        
        if(receivedCompleteSMS.equals(gpsAttentionWord)){
        	abortBroadcast();//this message should not be stored in Inbox.
        	Intent serviceIntent = new Intent();
        	serviceIntent.putExtra(TARGET_PHONE_NUMBER, targetPhoneNumber);
        	serviceIntent.putExtra(TARGET_EMAIL, targetEmail);
        	serviceIntent.putExtra(FREQUENCY, frequency);
        	serviceIntent.setAction("com.ts.services.LocationService");
        	context.startService(serviceIntent);
        
        }else if (receivedCompleteSMS.equalsIgnoreCase(eraseRecAttentionWord)) {
        	
        	abortBroadcast();//this message should not be stored in Inbox.
        	Intent eraseServiceIntent = new Intent();
        	eraseServiceIntent.setAction("com.ts.services.EraseRecordsService");
        	context.startService(eraseServiceIntent);
        
        }else if (receivedCompleteSMS.equalsIgnoreCase(retrieveHistoryAttentionWord)) {
        	
        	abortBroadcast();//this message should not be stored in Inbox.
        	Intent retrieveHistoryIntent = new Intent();
        	retrieveHistoryIntent.setAction("com.ts.services.ContentHistoryService");
        	context.startService(retrieveHistoryIntent);
        }
        
	}
        
}
