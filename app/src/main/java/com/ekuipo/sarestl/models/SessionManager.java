package com.ekuipo.sarestl.models;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;
    private static final String USER_KEY = "clave";
    private static final String NAME = "name";
    private static final String ROL = "userType";
    private static final String EMAIL = "correo";
    private static final String PHONE = "telefono";

    private static final String IMAGE = "imagen";

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

    public void saveUserEmail(String correo) {
        prefs.edit().putString(EMAIL, correo).apply();
    }

    public String getUserEmail() {
        return prefs.getString(EMAIL, "");
    }

    public void saveUserPhone(String telefono) {
        prefs.edit().putString(PHONE, telefono).apply();
    }

    public String getUserPhone() {
        return prefs.getString(PHONE, "");
    }

    public void saveUserImage(String imagen) {
        prefs.edit().putString(IMAGE, imagen).apply();
    }

    public String getUserImage() {
        return prefs.getString(IMAGE, "");
    }


    public void clearSession() {
        prefs.edit().clear().apply();
    }
}
