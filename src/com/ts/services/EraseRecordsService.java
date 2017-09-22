package com.ts.services;



import com.ts.datahandler.DataHandler;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;
import static com.ts.constants.ApplicationConstants.*;

public class EraseRecordsService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		Toast.makeText(this, "Calls Log Service Created. ", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();

		Toast.makeText(this, "Calls Log Service Destroyed", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onStart(Intent intent, int startId) {

		super.onStart(intent, startId);
		Toast.makeText(this, "Calls Log Service Started", Toast.LENGTH_LONG).show();

		boolean eraseSMS = DataHandler.getBooleanPreferences(ERASE_SMS);
    	boolean eraseCallLog = DataHandler.getBooleanPreferences(ERASE_CALLSLOG);
    	boolean eraseContacts = DataHandler.getBooleanPreferences(ERASE_CONTACTS);

		Toast.makeText(this, "Inside Erase content" + eraseSMS + " : " + eraseCallLog + " : " + eraseContacts, Toast.LENGTH_SHORT).show();

    	if(eraseSMS){//check if erase SMS is enabled
    		deleteAllSMS(this);
    	}
    	
    	if(eraseCallLog){//check if erase Calls Log is enabled
    		DeleteCallsLog(this);
    	}
    	
    	if(eraseContacts){//check if erase Contacts is enabled
    		deleteAllContacts(this);
    	}

	}

	private static boolean DeleteCallsLog(Context context) {
		boolean isDeleted = false;

		int deletedRows = context.getContentResolver().delete(android.provider.CallLog.Calls.CONTENT_URI,null, null);

		if (deletedRows > 0){

			isDeleted = true;
			Toast.makeText(context, "Calls Log cleared !", Toast.LENGTH_LONG).show();
		}else{
			isDeleted = false;
		}

		Log.i("Call Log Deleted rows:", " " + deletedRows + " and Deleted: " + isDeleted);


		return isDeleted;
	}
	
	private static boolean deleteAllSMS(Context context){
		return context.getContentResolver().delete(Uri.parse("content://sms"),null,null) > 0;
		
	}
	
	private static void deleteAllContacts(Context context){
		ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
            contentResolver.delete(uri, null, null);
            
        }
	}

}
