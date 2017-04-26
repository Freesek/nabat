package com.example.alex.nabat;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.alex.nabat.Utils.NabatMessage;
import com.example.alex.nabat.data.MySettings;
import com.example.alex.nabat.data.SaveToDB;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

/**
 * Created by Junior on 29.03.2017.
 */

public class FragmentVk extends Fragment {
    private static final String[] sMyScope = new String[]{
            VKScope.EMAIL
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vk, container, false);
        Button vkAuth = (Button) rootView.findViewById(R.id.vkButton);
        vkAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.login(getActivity(), sMyScope);
                VKRequest request = new VKRequest("users.get", VKParameters.from(VKApiConst.FIELDS, "first_name,last_name,contacts"));
                request.executeSyncWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        NabatMessage.getNabatMessage().setAnswerVK(response.json);
                        MySettings ms = MySettings.getMySettings();
                        ms.logInWithVk();
                        SaveToDB stdb = (SaveToDB) getActivity();
                        stdb.saveData();
                    }

                    @Override
                    public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                        super.attemptFailed(request, attemptNumber, totalAttempts);
                    }

                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                    }

                    @Override
                    public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                        super.onProgress(progressType, bytesLoaded, bytesTotal);
                    }
                });
            }
        });
        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
            }
            @Override
            public void onError(VKError error) {
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
