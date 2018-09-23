package com.manoj.transformersae.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.manoj.transformersae.App;
import com.manoj.transformersae.R;

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
public class AppUtill {
    public static String VIEW_TYPE_KEY = "VIEW_TYPE";
    public static String  BOT_MODEL_KEY = "BOT";
    public static String TEAM_A_KEY = "A";
    public static String TEAM_D_KEY = "D";
    public enum TYPE {

        UPDATE(1), CREATE(2), VIEW(3);

        TYPE(int i) {
            value = i;
        }
        private final int value;

        public final int getValue() { return value; }
    }
    private static String PREFERENCE_NAME = "PREF";
    private static String PREFERENCE_TOKEN = "TOKEN";
    @VisibleForTesting
    private static boolean mIsForTesting = false;
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

    public static void bindImage(String url, ImageView target, boolean centerCrop) {
        Drawable drawable = ContextCompat.getDrawable(target.getContext(), R.drawable.ic_image_24dp);
        DrawableRequestBuilder<String> builder = Glide.with(App.getContext())
                .load(url)
                .error(R.drawable.ic_broken_image_24dp)
                .placeholder(drawable)
                .crossFade();
        if (centerCrop) builder.centerCrop();
        builder.into(target);
    }
    @VisibleForTesting
    public static void setTesting(boolean testing) {
        mIsForTesting = testing;
    }
    @VisibleForTesting
    public static boolean getIsTesting() {
        return mIsForTesting;
    }

}
