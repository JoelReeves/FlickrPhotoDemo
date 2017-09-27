package net.joelreeves.flickrphotodemo.application.modules;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import net.joelreeves.flickrphotodemo.services.FlickrPhotoRepository;
import net.joelreeves.flickrphotodemo.services.FlickrService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class AndroidModule {

    private final Application mApplication;

    public AndroidModule(Application application) {
        mApplication = application;
    }

    @Provides @Singleton
    Context provideContext() {
        return mApplication;
    }

    @Provides @NonNull @Singleton
    public FlickrPhotoRepository providePersonRepository(@NonNull FlickrService flickrService) {
        return new FlickrPhotoRepository(flickrService);
    }

    @Provides @NonNull @Singleton
    public Picasso providePicasso(@NonNull Context context, @NonNull OkHttpClient client) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .defaultBitmapConfig(Bitmap.Config.ARGB_8888)
                .build();
    }
}
