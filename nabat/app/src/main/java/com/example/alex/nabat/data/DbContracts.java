package com.example.alex.nabat.data;

import android.provider.BaseColumns;

/**
 * Created by Junior on 30.03.2017.
 */

public final class DbContracts {
    private DbContracts() {
    }

    public static final class UserEntry implements BaseColumns {
        public final static String TABLE_NAME = "users";

        public final static String NAME = "name";
        public final static String EMAIL = "email";
        public final static String GENDER = "gender";
        public final static String BIRTHDAY = "birthday";
        public final static String TOKEN = "token";
        public final static String LOCATION = "location";
        public final static String PHONE_NUMBER = "phone_number";
    }
}
