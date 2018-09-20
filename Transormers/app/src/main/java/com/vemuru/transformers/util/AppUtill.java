package com.vemuru.transformers.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
public class AppUtill {
    private static String PREFERENCE_NAME = "PREF";
    private static String PREFERENCE_TOKEN = "TOKEN";

    public static void saveToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERENCE_TOKEN, token);
        editor.apply();
    }

    public static String getSavedToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String token = prefs.getString(PREFERENCE_TOKEN, null);

        return token;
    }
}
