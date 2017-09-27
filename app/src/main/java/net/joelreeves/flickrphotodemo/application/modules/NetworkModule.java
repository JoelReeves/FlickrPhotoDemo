package net.joelreeves.flickrphotodemo.application.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import net.joelreeves.flickrphotodemo.services.FlickrService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class NetworkModule {

    private static final String NETWORK_CACHE = "network_cache";
    private static final int GLOBAL_TIMEOUT = 30; // seconds
    private static final int CACHE_SIZE = 20 * 1024 * 1024; // 20 MB

    @NonNull
    private final HttpUrl endpoint;

    public NetworkModule(@NonNull String endpoint) {
        this(HttpUrl.parse(endpoint));
    }

    public NetworkModule(@NonNull HttpUrl endpoint) {
        this.endpoint = endpoint;
    }

    @Provides @NonNull @Singleton
    public HttpUrl provideEndpoint() {
        return this.endpoint;
    }

    @Provides @NonNull @Singleton @Named(NETWORK_CACHE)
    public File provideNetworkCacheDirectory(@NonNull Context context) {
        return context.getDir(NETWORK_CACHE, Context.MODE_PRIVATE);
    }

    @Provides @NonNull @Singleton
    public Cache provideNetworkCache(@NonNull @Named(NETWORK_CACHE) File cacheDir) {
        return new Cache(cacheDir, CACHE_SIZE);
    }

    @Provides @NonNull @Singleton
    public OkHttpClient provideHttpClient(@NonNull Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(GLOBAL_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(GLOBAL_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(GLOBAL_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides @NonNull @Singleton
    public FlickrService providePhotoResponse(@NonNull OkHttpClient client, @NonNull HttpUrl url) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(FlickrService.class);
    }
}
