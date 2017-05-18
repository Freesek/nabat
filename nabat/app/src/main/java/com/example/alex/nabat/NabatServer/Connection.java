package com.example.alex.nabat.NabatServer;

import android.util.Log;

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
        try {
            String url = "http://104.236.23.64/receiver" + a_url;
            URL object = new URL(url);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setRequestProperty("Content-Type", "application/json;charset=UTF8");
            //con.setRequestProperty("Accept", "application/json");
            //con.setRequestProperty("data", data);
            con.setRequestMethod("POST");
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(data);
            wr.flush();
            int HttpResult = con.getResponseCode();
            Log.d("message", data);
            Log.d("responseCode1", " " + HttpResult);
            Log.d("responseCode2", con.toString());
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                String sb = "";
                while ((line = br.readLine()) != null) {
                    sb = line + "\n";
                }
                Log.d("responseCode", "1");
                br.close();
                Log.d("responseCode", sb.length() + "");
                JSONObject obj = new JSONObject(sb);
                Log.d("responseCode", "3");
                Log.d("object*****", obj.toString());
            } else {
                return;
            }
        } catch (Exception e) {
        }
    }


    public synchronized void register(String data) {
        doGetRequest("/login", CallBackType.Register, data);
    }

    public synchronized void login(String data) {
        doGetRequest("/register", CallBackType.Login, data);
    }

    public synchronized void registerWithSocial(String data) {
        doGetRequest("/register/social", CallBackType.Login, data);
    }
}
