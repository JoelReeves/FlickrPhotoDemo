package net.joelreeves.flickrphotodemo.services;

import android.support.annotation.NonNull;

import net.joelreeves.flickrphotodemo.models.Photo;
import net.joelreeves.flickrphotodemo.models.PhotoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlickrPhotoRepository {

    public interface PhotoRepositoryListener {
        void onSuccess(@NonNull ArrayList<Photo> photoList);
        void onFailure(@NonNull String errorMessage);
    }

    public static final String BASE_URL = " https://api.flickr.com";
    private static final String API_KEY = "c28ab4cafc09aff5feffbabcbc92af9f";
    private static final String API_REQUEST = "flickr.photos.getRecent";
    private static final String API_FORMAT = "json";
    private static final String API_JSON_CALLBACK = "1";
    private static final String API_EXTRAS = "url_s";

    private final FlickrService flickrService;
    private ArrayList<Photo> photoList;
    private PhotoRepositoryListener photoRepositoryListener;

    public FlickrPhotoRepository(FlickrService flickrService) {
        this.flickrService = flickrService;
        photoList = new ArrayList<>();
    }

    public void getRecentPhotos() {
        flickrService.getRecentPhotos(API_REQUEST, API_KEY, API_FORMAT, API_JSON_CALLBACK, API_EXTRAS).enqueue(recentPhotoCallback);
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    private final Callback<PhotoResponse> recentPhotoCallback = new Callback<PhotoResponse>() {
        @Override
        public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
            if (response.isSuccessful()) {
                photoList.clear();
                photoList = response.body().getPhotos().getPhoto();
                if (photoRepositoryListener != null) {
                    photoRepositoryListener.onSuccess(photoList);
                }
            } else {
                if (photoRepositoryListener != null) {
                    photoRepositoryListener.onFailure(response.message());
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoResponse> call, Throwable t) {
            if (photoRepositoryListener != null) {
                photoRepositoryListener.onFailure(t.getMessage());
            }
        }
    };

    public void setPhotoRepositoryListener(PhotoRepositoryListener photoRepositoryListener) {
        this.photoRepositoryListener = photoRepositoryListener;
    }
}
