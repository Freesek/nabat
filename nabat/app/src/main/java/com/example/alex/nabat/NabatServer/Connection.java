package com.example.alex.nabat.NabatServer;

import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.alex.nabat.Utils.ErrorParser;
import com.example.alex.nabat.Utils.NabatMessage;
import com.example.alex.nabat.data.MySettings;
import com.facebook.Profile;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by al on 04.09.16.
 */


public class Connection {

    MySettings ms = MySettings.getMySettings();

    public enum CallBackType {
        Login,
        Register,
        RegisterSocial
    }

    private static class Holder {
        private final static Connection inst = new Connection();
    }

    public synchronized static Connection getInst() {
        return Holder.inst;
    }

    private Connection() {

    }


    private synchronized void doGetRequest(final String a_url, final CallBackType tp, final String data) {
        JSONObject obj = null;
        try {
            String url = "http://104.236.23.64/receiver" + a_url;
            URL object = new URL(url);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setRequestProperty("Content-Type", "application/json;charset=UTF8");
            if (tp.equals(CallBackType.Login)) {
                con.setRequestProperty("Authorization", "Basic " + new String(Base64.encode(data.getBytes(), Base64.DEFAULT)));
                con.setRequestMethod("POST");
            } else {
                con.setRequestProperty("Authorization", "Basic "
                        + new String(Base64.encode(("application:7598").getBytes(), Base64.DEFAULT)));
                con.setRequestMethod("POST");
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(data);
                wr.flush();
            }
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK || HttpResult == HttpURLConnection.HTTP_CREATED) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                String sb = "";
                while ((line = br.readLine()) != null) {
                    sb = line + "\n";
                }
                br.close();
                obj = new JSONObject(sb);
                if (obj.has("token")) {
                    ms.putToken(obj.getString("token"));
                    Log.d("token", ms.getToken());
                } else {
                    ms.putToken("");
                }
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String line = null;
                String sb = "";
                while ((line = br.readLine()) != null) {
                    sb = line + "\n";
                }
                obj = new JSONObject(sb);
                String errorMessage = (new ErrorParser(obj.getString("message"), HttpResult)).getTextErrorForUser();
                Log.d("error111", errorMessage);
                setError(errorMessage);
            }
        } catch (Exception e) {

        }
    }

    private void setError(String error) {
        NabatMessage.getNabatMessage().setError(error);
    }


    public synchronized void login(String data) {
        doGetRequest("/login", CallBackType.Login, data);
    }

    public synchronized void register(String data) {
        doGetRequest("/register", CallBackType.Register, data);
    }

    public synchronized void registerWithSocial(String data) {
        doGetRequest("/register/social", CallBackType.RegisterSocial, data);
    }
}
