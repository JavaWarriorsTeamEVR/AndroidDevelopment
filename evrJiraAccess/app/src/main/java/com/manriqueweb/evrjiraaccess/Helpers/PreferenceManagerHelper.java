package com.manriqueweb.evrjiraaccess.Helpers;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.manriqueweb.evrjiraaccess.Utils.Constants;

/**
 * Created by omar on 24/05/16.
 */
public class PreferenceManagerHelper {
    private static SharedPreferences preferences = null;

    private static PreferenceManagerHelper instance = null;

    private PreferenceManagerHelper(){
    }

    private PreferenceManagerHelper(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static synchronized PreferenceManagerHelper getInstance(Context context) {
        if (instance==null){
            instance = new PreferenceManagerHelper(context);
        }
        return instance;
    }

    public String getPreferenceValue(String theKey) {
        return preferences.getString(theKey, Constants.STR_NOTAVAILABLE);
    }

    public float getPreferenceValueFloat(String theKey) {
        return preferences.getFloat(theKey, Float.MIN_VALUE);
    }

    public long getPreferenceValueLong(String theKey) {
        return preferences.getLong(theKey, Long.MIN_VALUE);
    }

    public int getPreferenceValueInt(String theKey) {
        return preferences.getInt(theKey, Integer.MIN_VALUE);
    }

    public void updatePreferenceValue(String theKey, String theValue) {
        Editor edit = preferences.edit();
        edit.putString(theKey, theValue);
        edit.apply();
    }

    public void updatePreferenceValueFloat(String theKey, float theValue) {
        Editor edit = preferences.edit();
        edit.putFloat(theKey, theValue);
        edit.apply();
    }

    public void updatePreferenceValueLong(String theKey, long theValue) {
        Editor edit = preferences.edit();
        edit.putLong(theKey, theValue);
        edit.apply();
    }

    public void updatePreferenceValueInt(String theKey, int theValue) {
        Editor edit = preferences.edit();
        edit.putInt(theKey, theValue);
        edit.apply();
    }
}
