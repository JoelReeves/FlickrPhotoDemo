package net.joelreeves.flickrphotodemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.joelreeves.flickrphotodemo.R;
import net.joelreeves.flickrphotodemo.application.FlickrDemoApplication;
import net.joelreeves.flickrphotodemo.models.Photo;
import net.joelreeves.flickrphotodemo.views.CropCircleTransformation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosPagerActivity extends AppCompatActivity {

    @BindView(R.id.photo_detail) ImageView currentPhotoImageView;

    private static final String PHOTO_EXTRA = "photo_extra";
    private static final int PHOTO_DIMENSIONS = 400;

    private Photo currentPhoto;

    public static void startPhotosPagerActivity(@NonNull Activity activity, @NonNull Photo photo) {
        Intent intent = new Intent(activity, PhotosPagerActivity.class);
        intent.putExtra(PHOTO_EXTRA, photo);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_pager);

        ButterKnife.bind(this);

        ((FlickrDemoApplication) getApplication()).getAndroidComponent().inject(this);

        currentPhoto = getIntent().getParcelableExtra(PHOTO_EXTRA);

        if (currentPhoto != null) {
            final String photoUrl = TextUtils.isEmpty(currentPhoto.getUrl()) ? "" : currentPhoto.getUrl();
            Picasso.with(this)
                    .load(Uri.parse(photoUrl))
                    .placeholder(R.drawable.ic_photo_loading)
                    .error(R.drawable.ic_photo_error)
                    .resize(PHOTO_DIMENSIONS, PHOTO_DIMENSIONS)
                    .transform(new CropCircleTransformation())
                    .centerCrop()
                    .into(currentPhotoImageView);
        }
    }
}
