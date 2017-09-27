package net.joelreeves.flickrphotodemo.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.joelreeves.flickrphotodemo.R;
import net.joelreeves.flickrphotodemo.adapters.PhotoAdapter;
import net.joelreeves.flickrphotodemo.application.FlickrDemoApplication;
import net.joelreeves.flickrphotodemo.models.Photo;
import net.joelreeves.flickrphotodemo.services.FlickrPhotoRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static net.joelreeves.flickrphotodemo.utils.NetworkUtils.networkIsAvailable;

public class PhotosActivity extends AppCompatActivity {

    @Inject FlickrPhotoRepository flickrPhotoRepository;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    private static final int NUMBER_OF_COLUMNS = 3;
    private static final String PHOTO_LIST_KEY = "photo_list_key";
    private static final String RECYCLERVIEW_KEY = "recyclerview_key";


    private ArrayList<Photo> photoList = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    private Parcelable test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        ButterKnife.bind(this);

        ((FlickrDemoApplication) getApplication()).getAndroidComponent().inject(this);

        setSupportActionBar(toolbar);

        flickrPhotoRepository.setPhotoRepositoryListener(photoRepositoryListener);

        if (savedInstanceState == null) {
            getRecentPhotos();
        } else {
            photoList = savedInstanceState.getParcelableArrayList(PHOTO_LIST_KEY);
            //showRecyclerView();

            test = savedInstanceState.getParcelable(RECYCLERVIEW_KEY);
            //recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (test != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(test);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(PHOTO_LIST_KEY, photoList);
        test = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(RECYCLERVIEW_KEY, test);
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
        if (!networkIsAvailable(this)) {
            showErrorSnackbar(R.string.network_error_no_network_connection, R.string.button_retry, photoErrorClickListener);
        } else {
            flickrPhotoRepository.getRecentPhotos();
        }
    }

    private void showRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS));

        if (!photoList.isEmpty()) {
            photoAdapter = new PhotoAdapter(photoList);
            photoAdapter.setPhotoAdapterListener(photoAdapterListener);
            recyclerView.setAdapter(photoAdapter);
        } else {
            showErrorSnackbar(R.string.network_error_retrieving_photos, R.string.button_retry, photoErrorClickListener);
        }
    }

    private void showErrorSnackbar(@StringRes int stringResId, @StringRes int actionText, @Nullable View.OnClickListener onClickListener) {
        Snackbar snackbar = Snackbar.make(toolbar, stringResId, BaseTransientBottomBar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.white));
        if (onClickListener != null) {
            snackbar.setAction(actionText, onClickListener);
        }
        snackbar.show();
    }

    private final View.OnClickListener photoErrorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getRecentPhotos();
        }
    };

    private final FlickrPhotoRepository.PhotoRepositoryListener photoRepositoryListener = new FlickrPhotoRepository.PhotoRepositoryListener() {
        @Override
        public void onSuccess(@NonNull ArrayList<Photo> photos) {
            photoList = photos;
            Timber.d("Number of photos returned: %d", photoList.size());
            showRecyclerView();
        }

        @Override
        public void onFailure(@NonNull String errorMessage) {
            Timber.e("Error: %s", errorMessage);
            showErrorSnackbar(R.string.network_error_retrieving_photos, R.string.button_retry, photoErrorClickListener);
        }
    };

    private final PhotoAdapter.PhotoAdapterListener photoAdapterListener = new PhotoAdapter.PhotoAdapterListener() {
        @Override
        public void onClick(@NonNull Photo photo) {
            PhotosPagerActivity.startPhotosPagerActivity(PhotosActivity.this, photo);
        }
    };
}
