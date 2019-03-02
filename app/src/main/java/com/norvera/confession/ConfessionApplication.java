package com.norvera.confession;


import android.app.Application;

import com.facebook.stetho.Stetho;

public class ConfessionApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

}
