package net.joelreeves.flickrphotodemo.adapters.viewholders;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.joelreeves.flickrphotodemo.R;
import net.joelreeves.flickrphotodemo.models.Photo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoHolder extends RecyclerView.ViewHolder {

    public interface PhotoHolderListener {
        void onPhotoClick(int position);
    }

    @BindView(R.id.photo) ImageView photoImageView;

    private static final int PHOTO_DIMENSIONS = 150;

    private PhotoHolderListener photoHolderListener;

    public PhotoHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindPhoto(@NonNull Photo photo) {
        String photoUrl = photo.getUrl();
        if (TextUtils.isEmpty(photoUrl)) {
            Picasso.with(itemView.getContext())
                    .load(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .resize(PHOTO_DIMENSIONS, PHOTO_DIMENSIONS)
                    .centerCrop()
                    .into(photoImageView);
        } else {
            Picasso.with(itemView.getContext())
                    .load(Uri.parse(photo.getUrl()))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .resize(PHOTO_DIMENSIONS, PHOTO_DIMENSIONS)
                    .centerCrop()
                    .into(photoImageView);
        }
    }

    @OnClick(R.id.photo)
    protected void photoClick() {
        if (photoHolderListener != null) {
            photoHolderListener.onPhotoClick(getAdapterPosition());
        }
    }

    public void setPhotoHolderListener(PhotoHolderListener photoHolderListener) {
        this.photoHolderListener = photoHolderListener;
    }
}
