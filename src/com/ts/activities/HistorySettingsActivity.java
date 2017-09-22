package com.ts.activities;

import com.ts.datahandler.DataHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.ts.constants.ApplicationConstants.*;

public class HistorySettingsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_settings);

		SaveHistoryEventsListener listener = new SaveHistoryEventsListener();

		Button saveWipeOutInfoBtn = (Button)findViewById(R.id.saveBtn);
		saveWipeOutInfoBtn.setOnClickListener(listener);

		Button cancelBtn = (Button)findViewById(R.id.cancelBtn);
		cancelBtn.setOnClickListener(listener);

		EditText attentionWord = (EditText)findViewById(R.id.getHistoryAttentionWordEdittxt);
		CheckBox retrieveCallsChkBox = (CheckBox)findViewById(R.id.retrieveCallsCheckBox);
		CheckBox retrievePicCheckBox = (CheckBox)findViewById(R.id.retrievePicCheckBox);
		CheckBox retrieveSmsCheckBox = (CheckBox)findViewById(R.id.retrieveSmsCheckBox);

		attentionWord.setText(DataHandler.getStringPreferences(GET_HISTORY_ATTENTION_WORD));
		retrieveCallsChkBox.setChecked(DataHandler.getBooleanPreferences(RETRIEVE_LAST_CALLS));
		retrievePicCheckBox.setChecked(DataHandler.getBooleanPreferences(RETRIEVE_LAST_PIC));
		retrieveSmsCheckBox.setChecked(DataHandler.getBooleanPreferences(RETRIEVE_LAST_SMS));

	}

	public boolean saveHistorySettings(){
		boolean saved = true;
		EditText attentionWord = (EditText)findViewById(R.id.getHistoryAttentionWordEdittxt);
		CheckBox retrieveCallsChkBox = (CheckBox)findViewById(R.id.retrieveCallsCheckBox);
		CheckBox retrievePicCheckBox = (CheckBox)findViewById(R.id.retrievePicCheckBox);
		CheckBox retrieveSmsCheckBox = (CheckBox)findViewById(R.id.retrieveSmsCheckBox);

		//update sharedpreferences.
		if(attentionWord.getText() != null && !attentionWord.getText().toString().equals("")){
			DataHandler.updatePreferences(GET_HISTORY_ATTENTION_WORD, attentionWord.getText().toString());
			DataHandler.updatePreferences(RETRIEVE_LAST_CALLS, retrieveCallsChkBox.isChecked());
			DataHandler.updatePreferences(RETRIEVE_LAST_PIC, retrievePicCheckBox.isChecked());
			DataHandler.updatePreferences(RETRIEVE_LAST_SMS, retrieveSmsCheckBox.isChecked());
		}else{
			saved = false;
			Toast.makeText(getApplicationContext(), "Attention word is requierd !", Toast.LENGTH_LONG).show();
		}

		return saved;

	}
	
	public void finishActivity(){
		finish();
	}


	private class SaveHistoryEventsListener implements View.OnClickListener{

		public void onClick(View v) {
			Intent backToControlPanelIntent = new Intent(v.getContext(), ControlSettingsActivity.class);
			switch (v.getId()) {
			case R.id.saveBtn:
				if(saveHistorySettings()){
					startActivity(backToControlPanelIntent);
				}
				break;

			case R.id.cancelBtn:
				startActivity(backToControlPanelIntent);
				break;

			default:
				break;
			}

		}

	}

}
