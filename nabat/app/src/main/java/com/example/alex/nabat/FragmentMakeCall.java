package com.example.alex.nabat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.alex.nabat.Utils.RegistrationDialog;
import com.example.alex.nabat.data.MySettings;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMakeCall extends Fragment {

    Button makeCall;
    public MySettings settings;
    private static final int REQUEST_CALL = 1;


    public FragmentMakeCall() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_make_call, container, false);
        makeCall = (Button) rootView.findViewById(R.id.callButton);
        settings = MySettings.getMySettings();
        makeCall.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int eventAction = motionEvent.getAction();
                if (eventAction == MotionEvent.ACTION_UP) {
                    if(settings.isUserActive()) {
                        makeCall.clearAnimation();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(rootView.getContext().getString(R.string.NABAT_PHONE_URL)));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (ContextCompat.checkSelfPermission(rootView.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        } else {
                            startActivity(intent);
                        }
                    } else {
                        makeCall.clearAnimation();
                        Toast.makeText(rootView.getContext(), "Данные не те, ты не авторизован братан", Toast.LENGTH_LONG);
                        //new RegistrationDialog().show(getSupportFragmentManager(), "registrationDialog");
                    }
                }
                if (eventAction == MotionEvent.ACTION_DOWN) {
                    final Animation anim = AnimationUtils.loadAnimation(rootView.getContext(), R.anim.animation_call_button);
                    makeCall.startAnimation(anim);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    makeCall.clearAnimation();
                }
                return true;
            }
        });
        return inflater.inflate(R.layout.fragment_make_call, container, false);
    }

    public void setUserInactive(View view) {
        MySettings.getMySettings().setUserInactive();
    }

}
