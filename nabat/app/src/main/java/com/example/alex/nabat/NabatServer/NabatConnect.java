package com.example.alex.nabat.NabatServer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by Junior on 30.03.2017.
 */

public class NabatConnect extends AsyncTask<Long, Integer, Integer> {
    final Activity activity;
    final ProgressDialog pd;
    final String data;
    final Connection.CallBackType type;

    enum NabatResponse {
        OK,
        ERROR,
        NETWORKERROR
    }

    public NabatConnect(Activity activity, String data, Connection.CallBackType type) {
        super();
        this.data = data;
        this.activity = activity;
        this.type = type;
        pd = new ProgressDialog(activity);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Загрузка...");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd.show();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(pd.isShowing()) {
            pd.dismiss();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Integer integer) {
        super.onCancelled(integer);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Integer doInBackground(Long... longs) {
        Connection con = Connection.getInst();
        if(type.equals(Connection.CallBackType.Login)) {
            con.login(data);
        } else if(type.equals(Connection.CallBackType.Register)) {
            con.register(data);
        } else if(type.equals(Connection.CallBackType.RegisterSocial)) {
            con.registerWithSocial(data);
        }
        return 1;
    }
}
