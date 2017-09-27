package net.joelreeves.flickrphotodemo.adapters.viewholders;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.joelreeves.flickrphotodemo.R;
import net.joelreeves.flickrphotodemo.models.Photo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoHolder extends RecyclerView.ViewHolder {

    private static final int PHOTO_DIMENSIONS = 150;

    @BindView(R.id.photo) ImageView photoImageView;

    public PhotoHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindPhoto(@NonNull Photo photo) {
        Picasso.with(itemView.getContext())
                .load(Uri.parse(photo.getUrl()))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .resize(PHOTO_DIMENSIONS, PHOTO_DIMENSIONS)
                .centerCrop()
                .into(photoImageView);
    }
}
