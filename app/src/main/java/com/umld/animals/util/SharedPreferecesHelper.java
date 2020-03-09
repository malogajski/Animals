package com.umld.animals.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferecesHelper {
    private static final String PREF_API_KEY = "";

    private SharedPreferences prefs;

    public SharedPreferecesHelper(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveApiKey(String key) {
        prefs.edit().putString(PREF_API_KEY, key).apply();
    }

    public String getApiKey() {
        return prefs.getString(PREF_API_KEY, null);
    }
}
