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
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by al on 04.09.16.
 */


public class Connection {

    MySettings ms = MySettings.getMySettings();
    NabatMessage nm = NabatMessage.getNabatMessage();

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
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                        }
                        @Override
                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                        }
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier nabatHost = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    if(hostname.equals("receive.nabat24.ru") || hostname.equals("api.vk.com") || hostname.equals("graph.facebook.com"))
                        return true;
                    return false;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(nabatHost);
            String url = "https://receive.nabat24.ru" + a_url;
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
                    if(tp.equals(CallBackType.Login)) {
                        nm.setName(obj.getString("name"));
                        nm.setEmail(obj.getString("email"));
                    }
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
                Log.d("error111", obj.getString("message"));
                setError(errorMessage);
                Log.d("hz", errorMessage);
            }
        } catch (Exception e) {
            Log.d("exception", e.toString());
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
