package com.ekuipo.sarestl.models;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;
    private static final String USER_KEY = "clave";
    private static final String NAME = "name";
    private static final String ROL = "userType";

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE);
    }

    public void saveUserKey(String clave) {
        prefs.edit().putString(USER_KEY, clave).apply();
    }

    public String getUserKey() {
        return prefs.getString(USER_KEY, "");
    }

    public void saveUserName(String name) {
        prefs.edit().putString(NAME, name).apply();
    }

    public String getUserName() {
        return prefs.getString(NAME, "");
    }

    public void saveUserRol(String userType) {
        prefs.edit().putString(ROL, userType).apply();
    }

    public String getUserRol() {
        return prefs.getString(ROL, "");
    }

    public void clearSession() {
        prefs.edit().clear().apply();
    }
}
