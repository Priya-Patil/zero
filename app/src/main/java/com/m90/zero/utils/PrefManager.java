package com.m90.zero.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefManager {
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    public static int PRIVATE_MODE = 0;
    // Shared preferences file name
    public static final String PREF_NAME = "badsha";
    // All Shared Preferences Keys
    private static final String Mobile = "mobile";
    private static final String Cityid = "cityid";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //
    public void setMobile(String S) {
        editor.putString(Mobile, S);
        editor.commit();
    }

    public String getMobile() {
        return pref.getString(Mobile, null);
    }

 //
    public void setCityide(String S) {
        editor.putString(Cityid, S);
        editor.commit();
    }

    public String getCityid() {
        return pref.getString(Cityid, null);
    }




}