package net.joelreeves.flickrphotodemo.services;

import net.joelreeves.flickrphotodemo.models.PhotoResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FlickrService {

    @GET("/services/rest/")
    Call<PhotoResponse> getRecentPhotos();
}
