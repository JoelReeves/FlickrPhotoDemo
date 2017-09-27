package net.joelreeves.flickrphotodemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.joelreeves.flickrphotodemo.R;
import net.joelreeves.flickrphotodemo.application.FlickrDemoApplication;
import net.joelreeves.flickrphotodemo.models.Photo;

import butterknife.ButterKnife;

public class PhotosPagerActivity extends AppCompatActivity {

    private static final String PHOTO_EXTRA = "photo_extra";

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
            // TODO
        }
    }
}
