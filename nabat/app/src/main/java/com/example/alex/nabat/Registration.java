package com.example.alex.nabat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.example.al.nabat.R;
import com.example.alex.nabat.NabatServer.CallBack;
import com.example.alex.nabat.NabatServer.Connection;
import com.example.alex.nabat.Utils.NabatMessage;


public class Registration extends AppCompatActivity implements CallBack {

    private int region;

    private ProgressDialog prog1;

    private  Registration _this;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
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

        _this =this;
        prog1 = new ProgressDialog(this);
        prog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prog1.setMessage("Загрузка...");
        prog1.setIndeterminate(true); // выдать значек ожидания
        prog1.setCancelable(false);


        findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.lay).setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.textView8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.lay).setVisibility(View.INVISIBLE);
            }
        });


        findViewById(R.id.button3).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection.getInst().Register(NabatMessage.getNabatMessage().getRegistrationMessage(),_this);
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
