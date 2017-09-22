package com.ts.activities;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class CallLogsManagerActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(DeleteCallsLog(getApplicationContext())){
	     
        	setResult(RESULT_OK, null);
	     
        }else{
        	
        	setResult(RESULT_FIRST_USER, null);
        }
        
        finish();
	}
	
	public static boolean DeleteCallsLog(Context context) {
		boolean isDeleted = false;
		
		int deletedRows = context.getContentResolver().delete(android.provider.CallLog.Calls.CONTENT_URI,null, null);
			
			if (deletedRows > 0){
			
				isDeleted = true;
			
			}else{
				isDeleted = false;
			}

			Log.i("Call Log Deleted rows:", " " + deletedRows + " and Deleted: " + isDeleted);
	
			
		
			return isDeleted;
		}
	
}
