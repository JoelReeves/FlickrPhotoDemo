package net.joelreeves.flickrphotodemo.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoResponse implements Serializable {

    @JsonProperty("photos")
    public Photos photos;
    @JsonProperty("stat")
    public String stat;

    public PhotoResponse() {}

    public PhotoResponse(Photos photos, String stat) {
        this.photos = photos;
        this.stat = stat;
    }

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Override
    public String toString() {
        return "PhotoResponse{" +
                "photos=" + photos +
                ", stat='" + stat + '\'' +
                '}';
    }
}
