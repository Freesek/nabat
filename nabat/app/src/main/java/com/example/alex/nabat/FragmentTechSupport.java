package com.example.alex.nabat;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTechSupport extends Fragment {

    Button sendMessageTechSup;
    String destEmail = "info@nabat24.ru";


    public FragmentTechSupport() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_tech_support, container, false);
        sendMessageTechSup =(Button) rootView.findViewById(R.id.send_message_tech_sup);
        sendMessageTechSup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(getActivity());
                String information = ((EditText) rootView.findViewById(R.id.textTechSupp)).getText().toString();
                if(information.isEmpty()) {
                    Toast.makeText(getContext(), "Пожалуйста, введите данные для отправки", Toast.LENGTH_LONG).show();
                } else {
                    Intent send = new Intent(Intent.ACTION_SENDTO);
                    String uriText = "mailto:" + destEmail +
                            "?subject=" + Uri.encode("Обращение в техническую поддержку с приложения") +
                            "&body=" + Uri.encode(information);
                    Uri uri = Uri.parse(uriText);
                    send.setData(uri);
                    startActivity(send);
                }
            }
        });
        return rootView;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

}
