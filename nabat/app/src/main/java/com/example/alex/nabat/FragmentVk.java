package com.example.alex.nabat;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.alex.nabat.NabatServer.LoginSocial;
import com.example.alex.nabat.Utils.NabatConnection;
import com.example.alex.nabat.Utils.NabatMessage;
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
    FragmentVk fv = this;
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
                if(((NabatConnection) getActivity()).isOnline()) {
                    VKSdk.login(fv, sMyScope);
                } else {
                    Toast.makeText(getContext(), "Отсутствует интернет соединение", Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                vkReqGetData();
                LoginSocial ls = (LoginSocial) getParentFragment();
                ls.loginSocial();
            }
            @Override
            public void onError(VKError error) {
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void vkReqGetData() {
        VKRequest request = new VKRequest("users.get", VKParameters.from(VKApiConst.FIELDS, "first_name,last_name,contacts"));
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.d("hello sooka", "hello1");
                NabatMessage.getNabatMessage().setAnswerVK(response.json);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                Log.d("hello sooka", "hello2");
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                Log.d("hello sooka", error.toString());
                super.onError(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                Log.d("hello sooka", "hello4");
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });
    }
}
