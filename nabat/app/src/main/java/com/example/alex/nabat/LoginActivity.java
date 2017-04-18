package com.example.alex.nabat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import com.example.al.nabat.R;
import com.example.alex.nabat.Utils.NabatMessage;
import com.example.alex.nabat.data.DbContracts;
import com.example.alex.nabat.data.MySettings;
import com.example.alex.nabat.data.SaveToDB;
import com.example.alex.nabat.data.UserDbHelper;
import com.facebook.AccessToken;
import com.facebook.Profile;

/**
 * Created by Junior on 29.03.2017.
 */

public class LoginActivity extends FragmentActivity implements SaveToDB {
    private UserDbHelper mUserDb = null;
    private NabatMessage nm = NabatMessage.getNabatMessage();
    private MySettings ms = MySettings.getMySettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserDb = new UserDbHelper(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.callToast: {
                NabatMessage nm = NabatMessage.getNabatMessage();
                Profile profile = Profile.getCurrentProfile();
                AccessToken at = AccessToken.getCurrentAccessToken();
                Toast.makeText(this, (nm.getAnswerFB() + " " + nm.getLocation()) + " " + nm.getBirthday() + " " + at.getLastRefresh() + " " + ms.getFBLogin() + " " + nm.isEmpty() + nm.getPhoneNumber(), Toast.LENGTH_LONG).show();
                saveNewUser(nm);
            }
        }
    }

    public void saveData() {
        saveNewUser(nm);
    }

    private boolean saveNewUser(NabatMessage message) {
        if(ms.getAlredySaveToDB()) {
            return false;
        } else if(message.isEmpty()) {
            return false;
        }
        boolean success = true;
        SQLiteDatabase db = mUserDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(nm.getBirthday() != null) {
            values.put(DbContracts.UserEntry.EMAIL, nm.getEmail());
        }
        if(nm.getGender() != null) {
            values.put(DbContracts.UserEntry.GENDER, nm.getGender());
        }
        if(nm.getName() != null) {
            values.put(DbContracts.UserEntry.NAME, nm.getName());
        }
        if(nm.getPhoneNumber() != null) {
            values.put(DbContracts.UserEntry.PHONE_NUMBER, nm.getPhoneNumber());
        } else {
            TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String line;
            if((line = (tMgr.getLine1Number())) != null) {
                values.put(DbContracts.UserEntry.PHONE_NUMBER, line);
            }
        }
        if(db.insert(DbContracts.UserEntry.TABLE_NAME, null, values) != -1) {

        } else {
            success = false;
        }
        return success;
    }
}
