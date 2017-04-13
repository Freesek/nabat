package com.example.alex.nabat.data;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by alexey on 06.04.17.
 */

public class MySettings {
    private SharedPreferences settings;
    private static MySettings mySettings;
    private static final String APP_PREFERENCES = "userStatus";
    private SharedPreferences.Editor editor;
    private String APP_PREFERENCES_ID = "id";
    private String APP_PREFERENCES_ACTIVE = "active";
    private String APP_PREFERENCES_VK = "vk";
    private String APP_PREFERENCES_FB = "fb";
    private String APP_PREFERENCES_NABAT = "nabat";
    private String APP_PREFERENCES_ALREDY_SAVE = "saved_in_db";
    private Context context;
    private String android_id;
    private boolean alredySaveToDB = false;
    private MySettings() {}

    public static MySettings getMySettings() {
        if(mySettings == null) {
            mySettings = new MySettings();
        }
        return mySettings;
    }

    public void putId(int id) {
        editor.putInt(APP_PREFERENCES_ID, id);
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

    public void logInWithVk() {
        editor.putBoolean(APP_PREFERENCES_ACTIVE, true);
        editor.putBoolean(APP_PREFERENCES_VK,true);
        editor.apply();
    }

    public void logOutWithVk() {
        editor.putBoolean(APP_PREFERENCES_ACTIVE, false);
        editor.putBoolean(APP_PREFERENCES_VK, false);
        editor.apply();
    }

    public void logInWithFb() {
        editor.putBoolean(APP_PREFERENCES_ACTIVE, true);
        editor.putBoolean(APP_PREFERENCES_FB,true);
        editor.apply();
    }

    public void logOutWithFb() {
        editor.putBoolean(APP_PREFERENCES_ACTIVE, false);
        editor.putBoolean(APP_PREFERENCES_FB, false);
        editor.apply();
    }

    public void logInWithNabat() {
        editor.putBoolean(APP_PREFERENCES_ACTIVE, true);
        editor.putBoolean(APP_PREFERENCES_NABAT,true);
        editor.apply();
    }

    public void logOutWithNabat() {
        editor.putBoolean(APP_PREFERENCES_ACTIVE, false);
        editor.putBoolean(APP_PREFERENCES_NABAT, false);
        editor.apply();
    }

    public boolean isUserActive() {
        return settings.getBoolean(APP_PREFERENCES_ACTIVE, false);
    }

    public int getCurrentUserId() {
        return settings.getInt(APP_PREFERENCES_ID, 0);
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
    }

    public String getAndroid_id() {
        return android_id;
    }

    public boolean getAlredySaveToDB() {
        return settings.getBoolean(APP_PREFERENCES_ALREDY_SAVE, false);
    }

    public void saveToDB() {
        editor.putBoolean(APP_PREFERENCES_ALREDY_SAVE, true);
        editor.apply();
    }
}
