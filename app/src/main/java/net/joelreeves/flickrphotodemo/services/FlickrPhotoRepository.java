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

    public interface PhotosListener {
        void onSuccess(@NonNull List<Photo> photoList);
        void onFailure(@NonNull String errorMessage);
    }

    public static final String FLICKR_BASE_URL = " https://api.flickr.com";
    private static final String API_KEY = "c28ab4cafc09aff5feffbabcbc92af9f";
    private static final String FLICKR_API_GET_RECENT_PHOTOS = "flickr.photos.getRecent";
    private static final String FLICKR_API_FORMAT = "json";
    private static final String FLICKR_API_JSON_CALLBACK = "1";
    private static final String FLICKR_API_EXTRAS = "url_s";

    private final FlickrService flickrService;
    private List<Photo> photoList;
    private PhotosListener photosListener;

    public FlickrPhotoRepository(FlickrService flickrService) {
        this.flickrService = flickrService;
        photoList = new ArrayList<>();
    }

    public void getRecentPhotos() {
        flickrService.getRecentPhotos(FLICKR_API_GET_RECENT_PHOTOS, API_KEY, FLICKR_API_FORMAT, FLICKR_API_JSON_CALLBACK, FLICKR_API_EXTRAS).enqueue(recentPhotoCallback);
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
                if (photosListener != null) {
                    photosListener.onSuccess(photoList);
                }
            } else {
                if (photosListener != null) {
                    photosListener.onFailure(response.message());
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoResponse> call, Throwable t) {
            if (photosListener != null) {
                photosListener.onFailure(t.getMessage());
            }
        }
    };

    public void setPhotosListener(PhotosListener photosListener) {
        this.photosListener = photosListener;
    }
}
