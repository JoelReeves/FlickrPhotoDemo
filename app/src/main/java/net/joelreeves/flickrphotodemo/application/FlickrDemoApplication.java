package net.joelreeves.flickrphotodemo.application;

import android.app.Application;

import net.joelreeves.flickrphotodemo.BuildConfig;

import timber.log.Timber;

public class FlickrDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
