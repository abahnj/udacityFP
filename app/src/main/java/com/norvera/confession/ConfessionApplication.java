package com.norvera.confession;


import android.app.Application;

import com.facebook.stetho.Stetho;
import com.google.android.gms.ads.MobileAds;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class ConfessionApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        MobileAds.initialize(this, "ca-app-pub-5820676795399080~7872921637");
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }


    public static class ReleaseTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, @NotNull String message, Throwable t) {
            //todo Add logging library
        }
    }


}
