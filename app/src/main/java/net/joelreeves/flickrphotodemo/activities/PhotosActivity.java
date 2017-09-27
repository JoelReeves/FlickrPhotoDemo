package net.joelreeves.flickrphotodemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.joelreeves.flickrphotodemo.R;
import net.joelreeves.flickrphotodemo.application.FlickrDemoApplication;
import net.joelreeves.flickrphotodemo.services.FlickrPhotoRepository;
import net.joelreeves.flickrphotodemo.utils.NetworkUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosActivity extends AppCompatActivity {

    @Inject FlickrPhotoRepository flickrPhotoRepository;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        ButterKnife.bind(this);

        ((FlickrDemoApplication) getApplication()).getAndroidComponent().inject(this);

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getRecentPhotos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                getRecentPhotos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getRecentPhotos() {
        if (!NetworkUtils.networkIsAvailable(this)) {
            showNoNetworkSnackbar();
        } else {
            // TODO
        }
    }

    private void showNoNetworkSnackbar() {
        Snackbar snackbar = Snackbar.make(toolbar, R.string.network_error_no_network_connection, BaseTransientBottomBar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.setAction("Retry", noNetworkClickListener);
        snackbar.show();
    }

    private final View.OnClickListener noNetworkClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getRecentPhotos();
        }
    };
}
