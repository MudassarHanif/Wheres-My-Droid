package com.ts.datahandler;

import static com.ts.constants.ApplicationConstants.PREFS_FILE_NAME;
import android.content.Context;
import android.content.SharedPreferences;

public class DataHandler {

	private static Context context;
	
	 public DataHandler(Context context) {
	      this.context = context;
	 }
	
	public static void updatePreferences(String key, String value) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();

	}
	
	public static void updatePreferences(String key, Boolean value) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		editor.commit();

	}
	
	public static void updatePreferences(String key, int value) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		editor.commit();

	}
	
	public static String getStringPreferences(String key) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, 0);
		return settings.getString(key, null);

	}
	
	public static Boolean getBooleanPreferences(String key) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, 0);
		return settings.getBoolean(key, false);

	}
	
	public static int getIntPreferences(String key) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, 0);
		return settings.getInt(key, -1);

	}
	
}
