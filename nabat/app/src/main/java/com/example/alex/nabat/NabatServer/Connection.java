package com.example.alex.nabat.NabatServer;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by al on 04.09.16.
 */


enum CallBackType
{
    Login,
    Register
}



public class Connection {


    private static class Holder {
        private final static Connection inst = new Connection();
    }



    public synchronized static Connection getInst()
    {
        return Holder.inst;
    }
    private Connection(){

    }


    private CallBack cb;

    private void RequestDone(boolean Success, CallBackType tp, String data){
        cb.Call(Success,data);
    }

    private synchronized void DoGetRequest(final String a_url, final CallBackType tp,final String data){

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    String url="http://82.146.33.19:8154"+a_url;
                    URL object=new URL(url);
                    HttpURLConnection con = (HttpURLConnection) object.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "application/json");
                    con.setRequestMethod("POST");
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    int HttpResult = con.getResponseCode();
                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader(con.getInputStream(), "utf-8"));
                        String line = null;
                        String sb="";
                        while ((line = br.readLine()) != null) {
                            sb=line + "\n";
                        }
                        br.close();
                        JSONObject obj=new JSONObject(sb);
                        RequestDone(obj.getBoolean("success"),tp,sb);
                    } else {
                        RequestDone(false,tp,"");
                        return;
                    }

                } catch (Exception e) {
                    RequestDone(false,tp,"");
                }
            }
        };
        t.start();
    }


    public synchronized void Register(String data,CallBack c) {
        cb = c;
        DoGetRequest("/add.php",CallBackType.Register,data);
    }
    public synchronized void Login(String data,CallBack c) {
        cb =c;
        DoGetRequest("/check.php",CallBackType.Login,data);
    }
}
