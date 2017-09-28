package net.joelreeves.flickrphotodemo.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Photos implements Parcelable {

    @JsonProperty("page")
    public Integer page;
    @JsonProperty("pages")
    public Integer pages;
    @JsonProperty("perpage")
    public Integer perpage;
    @JsonProperty("total")
    public String total;
    @JsonProperty("photo")
    public ArrayList<Photo> photo;

    public Photos() {}

    public Photos(Integer page, Integer pages, Integer perpage, String total, ArrayList<Photo> photo) {
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.photo = photo;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPerpage() {
        return perpage;
    }

    public void setPerpage(Integer perpage) {
        this.perpage = perpage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<Photo> photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Photos{" +
                "page=" + page +
                ", pages=" + pages +
                ", perpage=" + perpage +
                ", total='" + total + '\'' +
                ", photo=" + photo +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.page);
        dest.writeValue(this.pages);
        dest.writeValue(this.perpage);
        dest.writeString(this.total);
        dest.writeTypedList(this.photo);
    }

    protected Photos(Parcel in) {
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pages = (Integer) in.readValue(Integer.class.getClassLoader());
        this.perpage = (Integer) in.readValue(Integer.class.getClassLoader());
        this.total = in.readString();
        this.photo = in.createTypedArrayList(Photo.CREATOR);
    }

    public static final Creator<Photos> CREATOR = new Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel source) {
            return new Photos(source);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
        }
    };
}
