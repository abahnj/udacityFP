package com.norvera.confession


import android.app.Application

import com.facebook.stetho.Stetho
import com.google.android.gms.ads.MobileAds

import timber.log.Timber

class ConfessionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        MobileAds.initialize(this, "ca-app-pub-5820676795399080~7872921637")
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }


    class ReleaseTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            //todo Add logging library
        }
    }


}
