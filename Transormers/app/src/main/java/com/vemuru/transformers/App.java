package com.vemuru.transformers;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.vemuru.transformers.service.HTTPService;
import com.vemuru.transformers.util.AppUtill;

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
public class App extends Application {
    /**
     * app level shared context without memory leak problem
     */
    @SuppressLint("StaticFieldLeak")
    private static Context instance;

    private String mToken;

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setToken(AppUtill.getSavedToken(this));
        if(mToken == null) {
            HTTPService.Companion.getINSTANCE().requestToken(this);
        }
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }
}
