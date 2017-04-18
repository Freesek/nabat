package com.example.alex.nabat.Utils;

import com.example.alex.nabat.data.MySettings;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Junior on 30.03.2017.
 */

public final class NabatMessage {
    private String name;
    private String gender;
    private String birthday;
    private String email;
    private String phoneNumber;
    private String token;
    private String id;
    private String location;
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
            if(gender != null)
                message.put("gender", gender);
            if(birthday != null)
                message.put("birthday", birthday);
            if(email != null)
                message.put("email", email);
            if(phoneNumber != null)
                message.put("phone", phoneNumber);
            if(android_id != null)
                message.put("android_id",android_id);
            if(location != null)
                message.put("location", location);
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
                if(answerFB.has("gender")) {
                    gender = answerFB.getString("gender");
                }
                if(answerFB.has("birthday")) {
                    birthday = (String) answerFB.get("birthday");
                }
                if(answerFB.has("location")) {
                    if(answerFB.getJSONObject("location").has("name")) {
                        location = answerFB.getJSONObject("location").getString("name");
                    }
                }
                if(answerFB.has("id")) {
                    id = answerFB.getString("id");
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
                if(answerVK.has("first_name")) {
                    name = answerVK.getString("first_name");
                }
                if(answerVK.has("id")) {
                    id = answerVK.getString("id");
                }
                if(answerVK.has("last_name")) {
                    name += " " + answerVK.getString("last_name");
                }
                if(answerVK.has("bdate")) {
                    birthday = (String) answerVK.get("bdate");
                }
                if(answerVK.has("city")) {
                    if(answerVK.getJSONObject("city").has("title")) {
                        location = answerVK.getJSONObject("city").getString("title");
                    }
                }
                if(answerVK.has("contacts")) {
                    if(answerVK.getJSONObject("contacts").has("mobile_phone")) {
                        phoneNumber = answerVK.getJSONObject("contacts").getString("mobile_phone");
                    }
                }
                if(answerVK.has("sex")) {
                    switch (answerVK.getInt("sex")) {
                        case 1 : {
                            gender = "female";
                            break;
                        }
                        case 2 : {
                            gender = "male";
                            break;
                        }
                    }
                }
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

    public String getLocation() {
        return location;
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

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setAnswerFB(JSONObject obj) {
        this.answerFB = obj;
        setValuesFromJSONObjectFacebook();
    }

    public void setAnswerVK(JSONObject obj) {
        this.answerVK = obj;
        setValuesFromJSONObjectVk();
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setToken(String token) {
        this.token = token;
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
