package com.vemuru.presto.flickrgallery;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by Manoj Vemuru on 2018-09-18.
 */
public class App extends Application {
    /**
     * app level shared context without memory leak problem
     */
    @SuppressLint("StaticFieldLeak")
    private static Context instance;

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
