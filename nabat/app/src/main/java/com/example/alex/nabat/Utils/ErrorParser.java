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

    public String getTextErrorForUser() {
        String userError = "";
        switch (mHttpCode) {
            case 400 : {
                if(mErrorMessage.contains("Field region has wrong value")) {
                    userError += "Неверное значение в поле Регион; ";
                }
                if(mErrorMessage.contains("Field inn has wrong value")) {
                    userError += "ИНН должен состоять из 10 цифр; ";
                }
                if (mErrorMessage.contains("Field email has wrong value")) {
                    userError += "В поле EMAIL должна быть указана существующая почта; ";
                }
                break;
            }
            case 409 : {
                if(mErrorMessage.contains("User with email")) {
                    userError += "Пользователь с такой почтой уже зарегистрирован";
                }
                break;
            }
            case 401 : {
                userError += "Неверные почта/пароль";
                break;
            }
            default:userError+="Неизвестная ошибка сервера, обратитесь в службу поддержки!";
        }
        return userError;
    }
}
