package net.joelreeves.flickrphotodemo.core;

import android.app.Application;

import net.joelreeves.flickrphotodemo.BuildConfig;
import net.joelreeves.flickrphotodemo.injection.components.AndroidComponent;
import net.joelreeves.flickrphotodemo.injection.components.DaggerAndroidComponent;
import net.joelreeves.flickrphotodemo.injection.modules.AndroidModule;
import net.joelreeves.flickrphotodemo.injection.modules.NetworkModule;
import net.joelreeves.flickrphotodemo.data.services.FlickrPhotoRepository;

import timber.log.Timber;

public class FlickrDemoApplication extends Application {

    private AndroidComponent androidComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        androidComponent = DaggerAndroidComponent.builder()
                .androidModule(new AndroidModule(this))
                .networkModule(new NetworkModule(FlickrPhotoRepository.BASE_URL))
                .build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public AndroidComponent getAndroidComponent() {
        return androidComponent;
    }
}
