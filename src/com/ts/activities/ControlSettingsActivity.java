package com.ts.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


public class ControlSettingsActivity extends Activity {

	private ControlPanelEventListener buttonslistener;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        setContentView(R.layout.control_settings);
        buttonslistener = new ControlPanelEventListener();
        
        try{
        	View mainView = findViewById(R.id.control_settings_parent_layout);
      	  	
        	LinearLayout eraseRec_layout = (LinearLayout)(mainView.findViewById(R.id.eraseRecSettingsLayout));
            eraseRec_layout.setOnClickListener(buttonslistener);
    
            TextView eraseRecWordTxt = (TextView)findViewById(R.id.eraseRecSettingsTxt);
            eraseRecWordTxt.setOnClickListener(buttonslistener);
    
            LinearLayout gpsSetting_layout = (LinearLayout)(mainView.findViewById(R.id.openGpsSettingsLayout));
            gpsSetting_layout.setOnClickListener(buttonslistener);
            
            TextView gpsSettingTxt = (TextView)findViewById(R.id.openGpsSettingsTxt);
            gpsSettingTxt.setOnClickListener(buttonslistener);
    
            LinearLayout getHistory_layout = (LinearLayout)(mainView.findViewById(R.id.getHistoryLayout));
            getHistory_layout.setOnClickListener(buttonslistener);
            
            TextView getHistoryTxt = (TextView)findViewById(R.id.getHistoryTxt);
            getHistoryTxt.setOnClickListener(buttonslistener);

            
        }catch(Exception ex){
      	  ex.printStackTrace();
        }
        
    }
    
    
    
    private class ControlPanelEventListener implements View.OnClickListener{

		public void onClick(View v) {
			Intent eraseRecIntent = new Intent(v.getContext(), EraseRecordsActivity.class);
			Intent gpsSettingIntent = new Intent(v.getContext(), GPSSettingActivity.class);
			Intent historySettingIntent = new Intent(v.getContext(), HistorySettingsActivity.class);
			
			switch (v.getId()) {
			case R.id.eraseRecSettingsLayout://either text of layout clicked, respond to event
			case R.id.eraseRecSettingsTxt:
				startActivity(eraseRecIntent);
				break;
			
			case R.id.openGpsSettingsLayout:
			case R.id.openGpsSettingsTxt:
				startActivity(gpsSettingIntent);
				break;
				
			case R.id.getHistoryLayout:
			case R.id.getHistoryTxt:
					startActivity(historySettingIntent);
			default:
				break;
			}
			
		}
    	
    }
}
