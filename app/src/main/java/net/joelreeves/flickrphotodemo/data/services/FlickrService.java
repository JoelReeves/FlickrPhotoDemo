package net.joelreeves.flickrphotodemo.data.services;

import net.joelreeves.flickrphotodemo.data.models.PhotoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrService {

    @GET("/services/rest/")
    Call<PhotoResponse> getRecentPhotos(@Query("method") String method, @Query("api_key") String apiKey, @Query("format") String format, @Query("nojsoncallback") String nojson, @Query("extras") String extras);
}
