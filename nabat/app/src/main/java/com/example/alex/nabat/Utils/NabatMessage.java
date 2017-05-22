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

    public String getAnswerVK() {
        if(answerVK != null) {
            return answerVK.toString();
        }
        return null;
    }

    public String getRegistrationMessage() {
        message = new JSONObject();
        try {
            if(firstName != null)
                message.put("firstName", firstName);
            if(lastName != null)
                message.put("lastName", lastName);
            if(middleName != null)
                message.put("middleName", middleName);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message.toString();
    }

    private void setValuesFromJSONObjectFacebook() {
        if(answerFB != null) {
            try {
                if(answerFB.has("name")) {
                    String[] array = answerFB.getString("name").split(" ");
                    if(array.length > 1) {
                        firstName = array[0];
                        lastName = array[1];
                    }
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
                    firstName = answerVK.getString("first_name");
                    Log.d("first_name", firstName);
                }
                if(answerVK.has("last_name")) {
                    lastName = answerVK.getString("last_name");
                    Log.d("last_name", lastName);
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
