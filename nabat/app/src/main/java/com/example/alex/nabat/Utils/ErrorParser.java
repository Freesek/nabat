package com.example.alex.nabat.Utils;

/**
 * Created by alexey on 25.05.17.
 */

public class ErrorParser {
    private final String mErrorMessage;
    private final int mHttpCode;

    public ErrorParser(String ErrorMessage, int httpCode) {
        this.mErrorMessage = ErrorMessage;
        this.mHttpCode = httpCode;
    }

    public String getTextForUser() {
        String userError = null;
        switch (mHttpCode) {
            case 400 : {

            }
        }
        return userError;
    }
}
