package com.ts.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartupPhoneIntentReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("com.ts.services.SimVerificationService");
		context.startService(serviceIntent);
		
	}

}
