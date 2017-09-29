package net.joelreeves.flickrphotodemo.ui.adapters.viewholders;

import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    @BindView(R.id.photo_title) TextView photoTitle;

    private static final String DEFAULT_TITLE = "No Title";

    private PhotoHolderListener photoHolderListener;
    private CropCircleTransformation cropCircleTransformation;
    private ScrollingMovementMethod scrollingMovementMethod;
    private Resources resources;
    private boolean isGrid;

    public PhotoHolder(View itemView, boolean isGrid) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        cropCircleTransformation = new CropCircleTransformation();
        scrollingMovementMethod = new ScrollingMovementMethod();
        resources = itemView.getResources();
        this.isGrid = isGrid;
        photoTitle.setMovementMethod(scrollingMovementMethod);
    }

    public void bindPhoto(@NonNull Photo photo) {
        final String photoUrl = TextUtils.isEmpty(photo.getUrl()) ? "" : photo.getUrl();
        final int photoDimensions = isGrid ? resources.getDimensionPixelSize(R.dimen.photo_grid_dimensions)
                : resources.getDimensionPixelSize(R.dimen.photo_individual_dimensions);
        Picasso.with(itemView.getContext())
                .load(Uri.parse(photoUrl))
                .placeholder(R.drawable.ic_photo_loading)
                .error(R.drawable.ic_photo_error)
                .resize(photoDimensions, photoDimensions)
                .transform(cropCircleTransformation)
                .centerCrop()
                .into(photoImageView);

        final String title = TextUtils.isEmpty(photo.getTitle()) ? DEFAULT_TITLE : photo.getTitle();
        photoTitle.setText(title);
        photoTitle.setVisibility(isGrid ? View.GONE : View.VISIBLE);
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
