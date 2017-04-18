package com.example.alex.nabat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.alex.nabat.data.MySettings;


public class FullscreenActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    public Button makeCall;
    public MySettings settings;

    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        settings = MySettings.getMySettings();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public void inClick(View view) {
        makeCall = (Button) findViewById(R.id.callButton);
        if(makeCall.getId() == view.getId()) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(this.getString(R.string.NABAT_PHONE_URL)));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(ContextCompat.checkSelfPermission(FullscreenActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FullscreenActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            } else {
                startActivity(intent);
            }
        }
        if(view.getId() == R.id.loginActivity) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
