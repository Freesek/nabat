package com.example.alex.nabat;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alex.nabat.NabatServer.Connection;
import com.example.alex.nabat.NabatServer.LoginSocial;
import com.example.alex.nabat.NabatServer.NabatConnect;
import com.example.alex.nabat.Utils.NabatMessage;
import com.example.alex.nabat.data.ChangeHeader;
import com.example.alex.nabat.data.MySettings;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends Fragment implements LoginSocial{
    EditText login;
    EditText password;
    Button enter;
    FragmentTransaction fTrans;
    MySettings settings = MySettings.getMySettings();
    NabatMessage nm = NabatMessage.getNabatMessage();

    public FragmentLogin() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        login = (EditText) rootView.findViewById(R.id.editLogin);
        password = (EditText) rootView.findViewById(R.id.editPassword);
        enter = (Button) rootView.findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login.getText().length() == 0) {
                    Toast.makeText(rootView.getContext(), "Введите ваш email", Toast.LENGTH_SHORT).show();
                } else if(password.getText().length() == 0) {
                    Toast.makeText(rootView.getContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
                } else {
                    String line = login.getText() + ":" + password.getText();
                    new NabatConnect(getActivity(), line, Connection.CallBackType.Login) {
                        @Override
                        protected void onPostExecute(Integer integer) {
                            super.onPostExecute(integer);
                            if(settings.getToken().length() > 0) {
                                userLogIn();
                            } else {
                                Toast.makeText(getActivity(), nm.getNabatMessage().getError(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute();
                }
            }
        });
        Button nabatRegistration = (Button) rootView.findViewById(R.id.nabatRegistrationButton);
        fTrans = getFragmentManager().beginTransaction();
        nabatRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fTrans = getFragmentManager().beginTransaction();
                fTrans.replace(R.id.container, new FragmentRegistration());
                fTrans.commit();
            }
        });
        return rootView;
    }

    public void userLogIn() {
        settings.setUserActive();
        Toast.makeText(getActivity(), nm.getAnswerVK(), Toast.LENGTH_LONG).show();
        settings.setDataForHeader(nm.getLastName() + " " + nm.getFirstName(), nm.getEmail());
        ((ChangeHeader) getActivity()).changeHeaderNavDrawer();
        fTrans = getFragmentManager().beginTransaction();
        FragmentMakeCall fmc = new FragmentMakeCall();
        fTrans.replace(R.id.container, fmc);
        fTrans.commit();
    }

    @Override
    public void loginSocial() {
        new NabatConnect(getActivity(), nm.getRegistrationMessage(), Connection.CallBackType.RegisterSocial) {
            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                if(settings.getToken() != null) {
                    userLogIn();
                } else {
                    Toast.makeText(getActivity(), nm.getRegistrationMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
