package com.example.alex.nabat;

import android.app.Application;
import android.content.Context;
import android.provider.Settings.Secure;

import com.example.alex.nabat.Utils.NabatMessage;
import com.example.alex.nabat.data.MySettings;
import com.vk.sdk.VKSdk;

/**
 * Created by alexey on 06.04.17.
 */

public class ApplicationStart extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
        MySettings ms = MySettings.getMySettings();
        ms.setContext(getBaseContext());
        ms.setAndroid_id(Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID));
    }

    @Override
    public Context getBaseContext() {
        return super.getBaseContext();
    }
}
