package com.ekuipo.sarestl.models;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;
    private static final String USER_KEY = "clave";
    private static final String NAME = "nombre";
    private static final String ROL = "rol";

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE);
    }

    public void saveUserKey(String clave) {
        prefs.edit().putString(USER_KEY, clave).apply();
    }

    public String getUserKey() {
        return prefs.getString(USER_KEY, "");
    }

    public void saveUserName(String nombre) {
        prefs.edit().putString(NAME, nombre).apply();
    }

    public String getUserName() {
        return prefs.getString(NAME, "");
    }

    public void saveUserRol(String rol) {
        prefs.edit().putString(ROL, rol).apply();
    }

    public String getUserRol() {
        return prefs.getString(ROL, "");
    }

    public void clearSession() {
        prefs.edit().clear().apply();
    }
}
