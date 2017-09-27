package net.joelreeves.flickrphotodemo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.joelreeves.flickrphotodemo.R;
import net.joelreeves.flickrphotodemo.adapters.viewholders.PhotoHolder;
import net.joelreeves.flickrphotodemo.models.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

    public interface PhotoAdapterListener {
        void onClick(@NonNull Photo photo);
    }

    private List<Photo> photoList;
    private PhotoAdapterListener photoAdapterListener;

    public PhotoAdapter(List<Photo> photoList) {
        this.photoList = photoList;
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, int position) {
        holder.setPhotoHolderListener(photoHolderListener);
        holder.bindPhoto(photoList.get(position));
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void setPhotos(@NonNull ArrayList<Photo> photos) {
        notifyDataSetChanged();
    }

    private final PhotoHolder.PhotoHolderListener photoHolderListener = new PhotoHolder.PhotoHolderListener() {
        @Override
        public void onPhotoClick(int position) {
            if (photoAdapterListener != null) {
                photoAdapterListener.onClick(photoList.get(position));
            }
        }
    };

    public void setPhotoAdapterListener(PhotoAdapterListener photoAdapterListener) {
        this.photoAdapterListener = photoAdapterListener;
    }
}
