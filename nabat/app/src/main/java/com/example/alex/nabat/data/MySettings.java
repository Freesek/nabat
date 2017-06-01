package com.example.alex.nabat.data;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;


/**
 * Created by alexey on 06.04.17.
 */

public class MySettings {
    private SharedPreferences settings;
    private static MySettings mySettings;
    private static final String APP_PREFERENCES = "userStatus";
    private SharedPreferences.Editor editor;
    private final String APP_PREFERENCES_ACTIVE = "active";
    private final String APP_PREFERENCES_TOKEN = "token";
    private final String APP_PREFERENCES_EMAIL = "email";
    private final String APP_PREFERENCES_NAME = "name";
    private Context context;
    private MySettings() {}

    public static synchronized MySettings getMySettings() {
        if(mySettings == null) {
            mySettings = new MySettings();
        }
        return mySettings;
    }

    public void setDataForHeader(String name, String email) {
        editor.putString(APP_PREFERENCES_NAME, name);
        editor.putString(APP_PREFERENCES_EMAIL, email);
        editor.apply();
    }

    public String getName() {
        return settings.getString("name", "Набат");
    }
    public String getEmail() {
        return settings.getString("email", "info@nabat24.ru");
    }

    public void putToken(String token) {
        editor.putString(APP_PREFERENCES_TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        return settings.getString(APP_PREFERENCES_TOKEN, null);
    }


    public void setUserInactive() {
        editor.putBoolean(APP_PREFERENCES_ACTIVE, false);
        editor.putString(APP_PREFERENCES_TOKEN, "");
        editor.putString(APP_PREFERENCES_NAME, "");
        editor.putString(APP_PREFERENCES_EMAIL, "");
        editor.apply();
    }

    public void setUserActive() {
        editor.putBoolean(APP_PREFERENCES_ACTIVE, true);
        editor.apply();
    }

    public void setContext(Context context) {
        this.context = context;
        setSettings(this.context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));
        setEditor(settings.edit());
    }

    public void setSettings(SharedPreferences preferences) {
        this.settings = preferences;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }

    public boolean isUserActive() {
        return settings.getBoolean(APP_PREFERENCES_ACTIVE, false);
    }

}
