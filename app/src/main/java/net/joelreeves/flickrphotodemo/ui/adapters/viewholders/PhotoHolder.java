package net.joelreeves.flickrphotodemo.ui.adapters.viewholders;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.joelreeves.flickrphotodemo.R;
import net.joelreeves.flickrphotodemo.data.models.Photo;
import net.joelreeves.flickrphotodemo.ui.views.CropCircleTransformation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoHolder extends RecyclerView.ViewHolder {

    public interface PhotoHolderListener {
        void onPhotoClick(int position);
    }

    @BindView(R.id.photo) ImageView photoImageView;

    private static final int PHOTO_DIMENSIONS = 120;

    private PhotoHolderListener photoHolderListener;
    private CropCircleTransformation cropCircleTransformation;

    public PhotoHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        cropCircleTransformation = new CropCircleTransformation();
    }

    public void bindPhoto(@NonNull Photo photo) {
        final String photoUrl = TextUtils.isEmpty(photo.getUrl()) ? "" : photo.getUrl();
        Picasso.with(itemView.getContext())
                .load(Uri.parse(photoUrl))
                .placeholder(R.drawable.ic_photo_loading)
                .error(R.drawable.ic_photo_error)
                .resize(PHOTO_DIMENSIONS, PHOTO_DIMENSIONS)
                .transform(cropCircleTransformation)
                .centerCrop()
                .into(photoImageView);
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
