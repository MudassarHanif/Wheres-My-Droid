package com.ts.app;

import com.ts.datahandler.DataHandler;

import android.app.Application;
import android.content.Intent;

public class ApplicationController extends Application {
	
	private DataHandler dataHandler;
	
	public DataHandler getDataHandler() {
		return dataHandler;
	}

	//Application wide instance variables
    //Preferable to expose them via getter/setter methods
	@Override
	public void onCreate() {
		super.onCreate();

		dataHandler = new DataHandler(this);

	}
        //Appplication wide methods
}
