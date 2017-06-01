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
import com.example.alex.nabat.Utils.NabatMessage;
import com.example.alex.nabat.Utils.NabatConnection;
import com.example.alex.nabat.data.MySettings;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


/**
 * Created by Junior on 30.03.2017.
 */

public class FragmentFb extends Fragment {
    private CallbackManager callbackManager = null;
    private final List<String> permissionNeeds = Arrays.asList("email");
    private NabatMessage nm = NabatMessage.getNabatMessage();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_fb, container, false);
        Button fbAuth = (Button) rootView.findViewById(R.id.fbButton);
        final LoginButton fbButton = (LoginButton) rootView.findViewById(R.id.login_button);
        fbButton.setReadPermissions(permissionNeeds);
        fbButton.setFragment(this);
        fbAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((NabatConnection) getActivity()).isOnline()) {
                    fbButton.performClick();
                } else {
                    Toast.makeText(getContext(), "Отсутствует интернет соединение", Toast.LENGTH_LONG).show();
                }
            }
        });
        fbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken token = AccessToken.getCurrentAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                nm.setAnswerFB(object);
                                MySettings ms = MySettings.getMySettings();
                                LoginSocial ls = (LoginSocial) getParentFragment();
                                ls.loginSocial();
                            }


                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
                Log.d("Fberror", "hello1");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Fberror", error.toString());
            }

        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode,responseCode,data);
        callbackManager.onActivityResult(requestCode,responseCode,data);
    }
}
