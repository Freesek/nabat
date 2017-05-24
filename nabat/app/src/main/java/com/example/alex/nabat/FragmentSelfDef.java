package com.example.alex.nabat;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSelfDef extends Fragment {


    public FragmentSelfDef() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_self_def, container, false);
        final Spinner spinner = (Spinner) rootView.findViewById(R.id.self_def_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.steps, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        final FragmentStepOne fsOne = new FragmentStepOne();
        final FragmentStepTwo fsTwo = new FragmentStepTwo();
        final FragmentStepThree fsThree = new FragmentStepThree();
        final FragmentStepFour fsFour = new FragmentStepFour();
        //FragmentTransaction fTrans = getChildFragmentManager().beginTransaction();
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fTrans = getChildFragmentManager().beginTransaction();
                switch (position) {
                    case 0 : {
                        fTrans.replace(R.id.container_self_def, fsOne);
                        break;
                    }
                    case 1 : {
                        fTrans.replace(R.id.container_self_def, fsTwo);
                        break;
                    }
                    case 2 : {
                        fTrans.replace(R.id.container_self_def, fsThree);
                        break;
                    }
                    case 3 : {
                        fTrans.replace(R.id.container_self_def, fsFour);
                        break;
                    }
                }
                fTrans.commit();
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        return rootView;
    }

}
