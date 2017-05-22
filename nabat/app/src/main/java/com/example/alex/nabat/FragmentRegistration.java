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
public class FragmentRegistration extends Fragment {

    Button sendData;
    EditText name;
    EditText inn;
    EditText phone;
    EditText companyName;
    EditText email;
    EditText password;

    MySettings settings = MySettings.getMySettings();

    NabatMessage nm = NabatMessage.getNabatMessage();
    FragmentTransaction fTrans;



    public FragmentRegistration() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        name = (EditText) rootView.findViewById(R.id.userName);
        inn = (EditText) rootView.findViewById(R.id.inn);
        phone = (EditText) rootView.findViewById(R.id.phoneNumber);
        companyName = (EditText) rootView.findViewById(R.id.companyName);
        email = (EditText) rootView.findViewById(R.id.email);
        password = (EditText) rootView.findViewById(R.id.userName);
        sendData = (Button) rootView.findViewById(R.id.registrationButton);
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String error = "";
                if(name.getText().length() == 0) {
                    error += "Необходимо ввести ФИО; ";
                } else if(inn.getText().length() == 0){
                    error += "Необходимо ввести ИНН; ";
                } else if(phone.getText().length() == 0) {
                    error += "Необходимо ввести номер телефона; ";
                } else if(companyName.getText().length() == 0) {
                    error += "Необходимо ввести название компании; ";
                } else if(email.getText().length() == 0) {
                    error += "Необходимо ввести email; ";
                } else if(password.getText().length() == 0) {
                    error += "Необходимо ввести ваш новый пароль;";
                }
                if(error.length() == 0) {
                    String[] array = name.getText().toString().split(" ");
                    if(array.length == 3) {
                        nm.setLastName(array[0]);
                        nm.setFirstName(array[1]);
                        nm.setMiddleName(array[2]);
                    } else {
                        Toast.makeText(getActivity(), "Введите ФИО полностью", Toast.LENGTH_SHORT);
                        return;
                    }
                    nm.setCompanyINN(inn.getText().toString());
                    nm.setPhoneNumber(phone.getText().toString());
                    nm.setCompanyName(companyName.getText().toString());
                    nm.setEmail(email.getText().toString());
                    nm.setPassword(password.getText().toString());
                    new NabatConnect(getActivity(), nm.getRegistrationMessage(), Connection.CallBackType.Register) {
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
        return rootView;
    }
    public void userLogIn() {
        settings.setUserActive();
        ((ChangeHeader) getActivity()).changeHeaderNavDrawer();
        fTrans = getFragmentManager().beginTransaction();
        FragmentMakeCall fmc = new FragmentMakeCall();
        fTrans.replace(R.id.container, fmc);
        fTrans.commit();
    }
}
