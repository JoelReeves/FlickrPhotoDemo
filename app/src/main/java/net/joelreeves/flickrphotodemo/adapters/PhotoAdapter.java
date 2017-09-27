package net.joelreeves.flickrphotodemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.joelreeves.flickrphotodemo.R;
import net.joelreeves.flickrphotodemo.adapters.viewholders.PhotoHolder;
import net.joelreeves.flickrphotodemo.models.Photo;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

    private List<Photo> photoList;

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
        holder.bindPhoto(photoList.get(position));
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
