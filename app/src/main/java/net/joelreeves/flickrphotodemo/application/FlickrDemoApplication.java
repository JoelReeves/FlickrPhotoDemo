package net.joelreeves.flickrphotodemo.application;

import android.app.Application;

import net.joelreeves.flickrphotodemo.BuildConfig;
import net.joelreeves.flickrphotodemo.application.components.AndroidComponent;
import net.joelreeves.flickrphotodemo.application.components.DaggerAndroidComponent;
import net.joelreeves.flickrphotodemo.application.modules.AndroidModule;
import net.joelreeves.flickrphotodemo.application.modules.NetworkModule;
import net.joelreeves.flickrphotodemo.services.FlickrPhotoRepository;

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
