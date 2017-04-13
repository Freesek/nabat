package com.example.alex.nabat;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.al.nabat.R;

/**
 * Created by alexey on 06.04.17.
 */

public class FragmentNabat extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_nabat, container, false);
        Button nabatRegistration = (Button) rootView.findViewById(R.id.nabatRegistrationButton);
        nabatRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inflater.getContext(), Registration.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
