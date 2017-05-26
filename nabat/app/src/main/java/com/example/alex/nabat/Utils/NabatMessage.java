package com.example.alex.nabat.Utils;

import android.util.Log;

import com.example.alex.nabat.data.MySettings;
import com.vk.sdk.VKSdk;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Junior on 30.03.2017.
 */

public final class NabatMessage {
    private String name;
    private String firstName;
    private String lastName;
    private String middleName;
    private String password;
    private String email;
    private String phoneNumber;
    private String error;
    private Integer region;
    private String companyName;
    private String companyINN;
    private JSONObject answerFB;
    private JSONObject answerVK;
    private JSONObject message;

    private boolean isEmpty = true;

    private static NabatMessage nabatMessage;

    public String getAnswerFB() {
        if(answerFB != null) {
            return answerFB.toString();
        }
        return null;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }


    public String getAnswerVK() {
        if(answerVK != null) {
            return answerVK.toString();
        }
        return null;
    }

    public String getRegistrationMessage() {
        message = new JSONObject();
        try {
            if(name != null)
                message.put("name", name);
            if(email != null)
                message.put("email", email);
            if(companyName != null)
                message.put("companyName", companyName);
            if(companyINN != null)
                message.put("inn", companyINN);
            if(phoneNumber != null)
                message.put("phone", phoneNumber);
            if(password != null)
                message.put("password", password);
            if(region != null)
                message.put("region", region);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message.toString();
    }

    private void setValuesFromJSONObjectFacebook() {
        if(answerFB != null) {
            try {
                if(answerFB.has("name")) {
                    name = answerFB.getString("name");
                }
                if(answerFB.has("email")) {
                    email = answerFB.getString("email");
                }
                isEmpty = false;
            } catch (JSONException e) {

            }
        }
    }

    private void setValuesFromJSONObjectVk() {
        if(answerVK != null) {
            try {
                if(answerVK.has("response")) {
                    answerVK = answerVK.getJSONArray("response").optJSONObject(0);
                }
                if(answerVK.has("first_name")) {
                    name = "";
                    firstName = answerVK.getString("first_name");
                    name += firstName;
                }
                if(answerVK.has("last_name")) {
                    lastName = answerVK.getString("last_name");
                    name += " " + lastName;
                }
                if(answerVK.has("contacts")) {
                    if(answerVK.getJSONObject("contacts").has("mobile_phone")) {
                        phoneNumber = answerVK.getJSONObject("contacts").getString("mobile_phone");
                    }
                }
                email = VKSdk.getAccessToken().email;
                isEmpty = false;
            } catch (JSONException e) {

            }
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public String getName() {
        return name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAnswerFB(JSONObject obj) {
        this.answerFB = obj;
        setValuesFromJSONObjectFacebook();
    }

    public void setAnswerVK(JSONObject obj) {
        this.answerVK = obj;
        setValuesFromJSONObjectVk();
    }

    public String getEmail() {
        return email;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyINN(String companyINN) {
        this.companyINN = companyINN;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    private NabatMessage() {
    }

    public static synchronized NabatMessage getNabatMessage() {
        if(nabatMessage == null) {
            nabatMessage = new NabatMessage();
        }
        return nabatMessage;
    }
}
