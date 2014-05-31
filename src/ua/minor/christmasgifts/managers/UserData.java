package ua.minor.christmasgifts.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {
	private static UserData INSTANCE;
	
	private static final String USER_NAME = "user_name";
	
	private SharedPreferences mSettings;
	private SharedPreferences.Editor mEditor;
	
	private String userName;
	
	UserData() {
	}

	public synchronized static UserData getInstance() {
		if(INSTANCE == null){
			INSTANCE = new UserData();
		}
		return INSTANCE;
	}

	public synchronized void init(Context pContext) {
		if (mSettings == null) {
			mSettings = pContext.getSharedPreferences("game_prefs", Context.MODE_PRIVATE);
			mEditor = mSettings.edit();
			userName = mSettings.getString(USER_NAME, "Anonym");
		}
	}

	public synchronized String getUserName() {
		if (userName.length() > 9)
			return userName.substring(0, 8);
		return userName;
	}
	
	public synchronized void setUserName(String newName) {
		userName = newName;
		
		mEditor.putString(USER_NAME, userName);
		mEditor.commit();
	}
}
