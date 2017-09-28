package net.joelreeves.flickrphotodemo.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoResponse implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.photos, flags);
        dest.writeString(this.stat);
    }

    protected PhotoResponse(Parcel in) {
        this.photos = in.readParcelable(Photos.class.getClassLoader());
        this.stat = in.readString();
    }

    public static final Creator<PhotoResponse> CREATOR = new Creator<PhotoResponse>() {
        @Override
        public PhotoResponse createFromParcel(Parcel source) {
            return new PhotoResponse(source);
        }

        @Override
        public PhotoResponse[] newArray(int size) {
            return new PhotoResponse[size];
        }
    };
}
