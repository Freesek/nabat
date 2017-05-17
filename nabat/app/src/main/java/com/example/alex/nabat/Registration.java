package com.example.alex.nabat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.example.alex.nabat.NabatServer.CallBack;
import com.example.alex.nabat.NabatServer.Connection;
import com.example.alex.nabat.Utils.NabatMessage;


public class Registration extends AppCompatActivity implements CallBack {

    private int region;

    private ProgressDialog prog1;

    private  Registration _this;
    NabatMessage nm = NabatMessage.getNabatMessage();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final Spinner spinner = (Spinner) findViewById(R.id.region_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(58);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region=position;
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        prog1 = new ProgressDialog(this);
        prog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prog1.setMessage("Загрузка...");
        prog1.setIndeterminate(true); // выдать значек ожидания
        prog1.setCancelable(false);
        _this = this;

        findViewById(R.id.thankYouBackground).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.lay).setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.thankYouText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.lay).setVisibility(View.INVISIBLE);
            }
        });


        findViewById(R.id.registrationButton).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                nm.setName(((EditText) findViewById(R.id.userName)).getText().toString());
                nm.setRegion(spinner.getSelectedItem().toString());
                nm.setEmail(((EditText) findViewById(R.id.email)).getText().toString());
                nm.setPhoneNumber(((EditText) findViewById(R.id.phoneNumber)).getText().toString());
                nm.setCompanyName(((EditText) findViewById(R.id.companyName)).getText().toString());
                Log.d("Message", nm.getRegistrationMessage());
                Connection.getInst().register(nm.getRegistrationMessage());
                prog1.show();
            }
        });
    }

    @Override
    public void Call(final boolean  Success, String data) {
        new Thread() {
            public void run() {
                _this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        prog1.dismiss();
                        if (Success) {
                            findViewById(R.id.lay).setVisibility(View.VISIBLE);
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "Проверьте вводимые данные", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }.start();
    }
}
