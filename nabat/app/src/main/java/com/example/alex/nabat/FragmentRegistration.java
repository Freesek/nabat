package com.example.alex.nabat;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    Integer region_num;

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
        final Spinner spinner = (Spinner) rootView.findViewById(R.id.region_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(83);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region_num = position + 1;
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        name = (EditText) rootView.findViewById(R.id.userName);
        inn = (EditText) rootView.findViewById(R.id.inn);
        phone = (EditText) rootView.findViewById(R.id.phoneNumber);
        companyName = (EditText) rootView.findViewById(R.id.companyName);
        email = (EditText) rootView.findViewById(R.id.email);
        password = (EditText) rootView.findViewById(R.id.password);
        sendData = (Button) rootView.findViewById(R.id.registrationButton);
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String error = "";
                if(name.getText().length() == 0) {
                    error += "Необходимо ввести ФИО; ";
                } else if(email.getText().length() == 0) {
                    error += "Необходимо ввести email; ";
                } else if(password.getText().length() == 0) {
                    error += "Необходимо ввести ваш пароль;";
                }
                if(error.length() == 0) {
                    nm.setName(name.getText().toString());
                    nm.setCompanyINN(inn.getText().toString());
                    nm.setPhoneNumber(phone.getText().toString());
                    nm.setCompanyName(companyName.getText().toString());
                    nm.setEmail(email.getText().toString());
                    nm.setPassword(password.getText().toString());
                    nm.setRegion(region_num);
                    new NabatConnect(getActivity(), nm.getRegistrationMessage(), Connection.CallBackType.Register) {
                        @Override
                        protected void onPostExecute(Integer integer) {
                            super.onPostExecute(integer);
                            if(settings.getToken().length() > 0) {
                                Toast.makeText(getActivity(), "Спасибо за регистрацию в системе \"Набат\"", Toast.LENGTH_SHORT).show();
                                userLogIn();
                            } else {
                                Toast.makeText(getActivity(), nm.getNabatMessage().getError(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute();
                } else {
                    Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }
    public void userLogIn() {
        settings.setUserActive();
        ((ChangeHeader) getActivity()).changeHeaderNavDrawer(true);
        fTrans = getFragmentManager().beginTransaction();
        FragmentMakeCall fmc = new FragmentMakeCall();
        fTrans.replace(R.id.container, fmc);
        fTrans.commit();
    }
}
