package net.joelreeves.flickrphotodemo.application;

import android.app.Application;

import net.joelreeves.flickrphotodemo.BuildConfig;
import net.joelreeves.flickrphotodemo.application.components.AndroidComponent;
import net.joelreeves.flickrphotodemo.application.components.DaggerAndroidComponent;
import net.joelreeves.flickrphotodemo.application.modules.AndroidModule;

import timber.log.Timber;

public class FlickrDemoApplication extends Application {

    private AndroidComponent androidComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        androidComponent = DaggerAndroidComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public AndroidComponent getAndroidComponent() {
        return androidComponent;
    }
}
