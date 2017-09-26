package net.joelreeves.flickrphotodemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.joelreeves.flickrphotodemo.R;

import butterknife.ButterKnife;

public class PhotosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        ButterKnife.bind(this);
    }
}
