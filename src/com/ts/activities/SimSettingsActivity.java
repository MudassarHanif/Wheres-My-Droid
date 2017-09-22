package com.ts.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static com.ts.constants.ApplicationConstants.*;

public class SimSettingsActivity extends Activity{
	
	private int DELETE_CONTACTS_CODE = 1;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simsettings);
        
        EditText simSerialNumber = (EditText)findViewById(R.id.simSerialNumberTxt);
        EditText simPhoneNumber = (EditText)findViewById(R.id.phoneNum);
        
        simSerialNumber.setText(getSimSerialNumber());
        simPhoneNumber.setText(getPhoneNumber());
        
        simSerialNumber.setEnabled(false);
        simPhoneNumber.setEnabled(false);
        
        updateSharedPreferences(PHONE_NUM, simPhoneNumber.getText().toString());
		updateSharedPreferences(SIM_SERIAL_NUM, simSerialNumber.getText().toString());
		
        
        Button backToControlPanelBtn = (Button)findViewById(R.id.backToControlPanelbutton);
        backToControlPanelBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent backIntent = new Intent(v.getContext(),ControlSettingsActivity.class); 
				startActivity(backIntent);
				
			}
		});
        
        
        /*Delete All Contacts */
        /*Button deleteContactsBtn = (Button)findViewById(R.id.deleteContactsBtn);
        deleteContactsBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent deleteContactsIntent = new Intent(view.getContext(), ContactManagerActivity.class);
				startActivityForResult(deleteContactsIntent, DELETE_CONTACTS_CODE);


			}
		});*/
      
	}
	
	private String getPhoneNumber() {
		return ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getLine1Number();
	}

	private String getSimSerialNumber() {
		return ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getSimSerialNumber();
	}
	
	private void updateSharedPreferences(String key, String value) {
		SharedPreferences settings = getSharedPreferences(PREFS_FILE_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();

	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		   if (requestCode == DELETE_CONTACTS_CODE) {
		      if (resultCode == RESULT_OK) {
		        Toast.makeText(getApplicationContext(), "All  Phone Contacts Deleted successfully !", Toast.LENGTH_LONG).show();
		      }else{
		    	  Toast.makeText(getApplicationContext(), "Phone contacts could not be deleted !", Toast.LENGTH_LONG).show();
		      }
		   }
		}



}
