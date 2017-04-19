package com.example.alex.nabat.Utils;

import com.example.alex.nabat.data.MySettings;
import com.vk.sdk.VKSdk;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Junior on 30.03.2017.
 */

public final class NabatMessage {
    private String name;
    private String email;
    private String phoneNumber;
    private String region;
    private String companyName;
    private String companyINN;
    private JSONObject answerFB;
    private JSONObject answerVK;
    private JSONObject message;
    private String android_id;

    private boolean isEmpty = true;

    private static NabatMessage nabatMessage;

    public String getAnswerFB() {
        if(answerFB != null) {
            return answerFB.toString();
        }
        return null;
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
            if(phoneNumber != null)
                message.put("phone", phoneNumber);
            if(android_id != null)
                message.put("android_id",android_id);
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
                setAndroid_id(MySettings.getMySettings().getAndroid_id());
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
                    name = answerVK.getString("first_name");
                }
                if(answerVK.has("last_name")) {
                    name += " " + answerVK.getString("last_name");
                }
                if(answerVK.has("contacts")) {
                    if(answerVK.getJSONObject("contacts").has("mobile_phone")) {
                        phoneNumber = answerVK.getJSONObject("contacts").getString("mobile_phone");
                    }
                }
                email = VKSdk.getAccessToken().email;
                setAndroid_id(MySettings.getMySettings().getAndroid_id());
                isEmpty = false;
            } catch (JSONException e) {

            }
        }
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public void setRegion(String region) {
        this.region = region;
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

    private NabatMessage() {
    }

    public static synchronized NabatMessage getNabatMessage() {
        if(nabatMessage == null) {
            nabatMessage = new NabatMessage();
        }
        return nabatMessage;
    }
}
