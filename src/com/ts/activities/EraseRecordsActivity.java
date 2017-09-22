package com.ts.activities;

import com.ts.datahandler.DataHandler;

import android.app.Activity;
import android.content.Context;
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

public class EraseRecordsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.erase_records);

		EraseRecordsEventsListener listener = new EraseRecordsEventsListener();

		//TODO make layout focusable....
		View mainView = findViewById(R.id.erase_records_layout);
		try{
			LinearLayout linear_attentionWord_layout = (LinearLayout)(mainView.findViewById(R.id.eraseRecAttentionWordLayout));
			//          linear_attentionWord_layout.bringToFront();
			linear_attentionWord_layout.setOnClickListener(listener);

			TextView attentionWordTxt = (TextView)findViewById(R.id.eraseRecAttentionWordTxt);
			attentionWordTxt.setOnClickListener(listener);

		}catch(Exception ex){
			ex.printStackTrace();
		}


		Button saveWipeOutInfoBtn = (Button)findViewById(R.id.saveWipeOutInfoBtn);
		saveWipeOutInfoBtn.setOnClickListener(listener);

		Button cancelBtn = (Button)findViewById(R.id.cancelWipeOutInfoBtn);
		cancelBtn.setOnClickListener(listener);

		EditText eraseAttentionWord = (EditText)findViewById(R.id.eraseRecAttentionWordEdittxt);
		CheckBox eraseSmsChkBox = (CheckBox)findViewById(R.id.eraseSmsCheckBox);
		CheckBox eraseContactsChkBox = (CheckBox)findViewById(R.id.eraseContactsCheckBox);
		CheckBox eraseCallsLogChkBox = (CheckBox)findViewById(R.id.eraseCallLogCheckBox);

		eraseAttentionWord.setText(DataHandler.getStringPreferences(ERASE_ATTENTION_WORD));
		eraseSmsChkBox.setChecked(DataHandler.getBooleanPreferences(ERASE_SMS));
		eraseContactsChkBox.setChecked(DataHandler.getBooleanPreferences(ERASE_CONTACTS));
		eraseCallsLogChkBox.setChecked(DataHandler.getBooleanPreferences(ERASE_CALLSLOG));

	}

	public boolean saveWipeOutInfo(){
		boolean saved = true;
		EditText attentionWord = (EditText)findViewById(R.id.eraseRecAttentionWordEdittxt); 
		CheckBox eraseSmsChkBox = (CheckBox)findViewById(R.id.eraseSmsCheckBox);
		CheckBox eraseContactsChkBox = (CheckBox)findViewById(R.id.eraseContactsCheckBox);
		CheckBox eraseCallsLogChkBox = (CheckBox)findViewById(R.id.eraseCallLogCheckBox);

		//update sharedpreferences.
		if(attentionWord.getText() != null && !attentionWord.getText().toString().equals("")){
			DataHandler.updatePreferences(ERASE_ATTENTION_WORD, attentionWord.getText().toString());
			DataHandler.updatePreferences(ERASE_SMS, eraseSmsChkBox.isChecked());
			DataHandler.updatePreferences(ERASE_CALLSLOG, eraseCallsLogChkBox.isChecked());
			DataHandler.updatePreferences(ERASE_CONTACTS, eraseContactsChkBox.isChecked());
		}else{
			saved = false;
			Toast.makeText(getApplicationContext(), "Attention word is requierd !", Toast.LENGTH_LONG).show();
		}
		
		return saved;
		
	}

	
	private class EraseRecordsEventsListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Intent backToControlPanelIntent = new Intent(v.getContext(), ControlSettingsActivity.class);
			switch (v.getId()) {
			case R.id.saveWipeOutInfoBtn:
				if(saveWipeOutInfo()){
					startActivity(backToControlPanelIntent);
				}
				break;

			case R.id.cancelWipeOutInfoBtn:
				startActivity(backToControlPanelIntent);
				break;

			/*case R.id.eraseRecAttentionWordLayout:
				showPopupForAttentionWord(v.getContext());
				break;

			case R.id.eraseRecAttentionWordTxt:
				showPopupForAttentionWord(v.getContext());
				break;*/

			default:
				break;
			}

		}

	}
	
	/*private void showPopupForAttentionWord(Context context){
		InputWordPopupWindow popupWindows = new InputWordPopupWindow();
		popupWindows.setTitle("Attention Word");
		popupWindows.setDescription("Specify a word that initiates your content wipe out when your phone is stolen.");
		popupWindows.setAttentionWordType(ERASE_ATTENTION_WORD);
		popupWindows.initiatePopupWindow(context);
	}*/

}
