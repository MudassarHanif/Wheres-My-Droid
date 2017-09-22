package com.ts.activities;

import com.ts.datahandler.DataHandler;

import android.app.Activity;
import static com.ts.constants.ApplicationConstants.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GPSSettingActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_settings);
            	
             
		EditText attentationWord = (EditText) findViewById(R.id.attentionWordText);
		EditText frequency = (EditText) findViewById(R.id.frequencyText);
		EditText email = (EditText) findViewById(R.id.emailText);
		EditText phoneNumber = (EditText) findViewById(R.id.targetPhoneNum);
        
        attentationWord.setText(DataHandler.getStringPreferences(GPS_ATTENTION_WORD));
        frequency.setText(DataHandler.getStringPreferences(FREQUENCY));
        email.setText(DataHandler.getStringPreferences(TARGET_EMAIL));
        phoneNumber.setText(DataHandler.getStringPreferences(TARGET_PHONE_NUMBER));
        
        GPSSettingEventListener gpsListener = new GPSSettingEventListener();
        Button okBtn = (Button)findViewById(R.id.okButton);
        Button goBackBtn = (Button)findViewById(R.id.goBackButton);
               
        okBtn.setOnClickListener(gpsListener);
        goBackBtn.setOnClickListener(gpsListener);
        
	}
	
	private class GPSSettingEventListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.okButton:
			{
				EditText attentationWord = (EditText) findViewById(R.id.attentionWordText);
				EditText frequency = (EditText) findViewById(R.id.frequencyText);
				EditText email = (EditText) findViewById(R.id.emailText);
				EditText phoneNumber = (EditText) findViewById(R.id.targetPhoneNum);
				
				String attentationWordText = attentationWord.getText().toString();
				String frequencyText = frequency.getText().toString();
				String emailText = email.getText().toString();
				String phoneNumberText = phoneNumber.getText().toString();
			
				if(attentationWordText.trim().equals("")){
					Toast.makeText(getApplicationContext(), "Attention Word cannot be empty!" , Toast.LENGTH_LONG).show();
				} else if(emailText.trim().equals("")){
					Toast.makeText(getApplicationContext(), "Email Account cannot be empty!" , Toast.LENGTH_LONG).show();
				} else if(phoneNumberText.trim().equals("")){
					Toast.makeText(getApplicationContext(), "Phone Number cannot be empty!" , Toast.LENGTH_LONG).show();
				} else {
					if(frequencyText.trim().equals("")){
						frequencyText = "0";
					}
					DataHandler.updatePreferences(GPS_ATTENTION_WORD, attentationWordText);
					DataHandler.updatePreferences(FREQUENCY, frequencyText);
					DataHandler.updatePreferences(TARGET_EMAIL, emailText);
					DataHandler.updatePreferences(TARGET_PHONE_NUMBER, phoneNumberText);
					Toast.makeText(getApplicationContext(), "Setting Saved" , Toast.LENGTH_LONG).show();
				}
						
				break;		
			}
			case R.id.goBackButton:
			{
				Intent controlPanelIntent = new Intent(v.getContext(), ControlSettingsActivity.class);
				startActivity(controlPanelIntent);
				break;
			}
			default:
				break;
			}
			
		}
		
	}
}
