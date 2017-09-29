package net.joelreeves.flickrphotodemo.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.joelreeves.flickrphotodemo.R;
import net.joelreeves.flickrphotodemo.core.FlickrDemoApplication;
import net.joelreeves.flickrphotodemo.data.models.Photo;
import net.joelreeves.flickrphotodemo.data.preferences.BooleanPreference;
import net.joelreeves.flickrphotodemo.data.preferences.qualifiers.ViewPreference;
import net.joelreeves.flickrphotodemo.data.services.FlickrPhotoRepository;
import net.joelreeves.flickrphotodemo.ui.adapters.PhotoAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static net.joelreeves.flickrphotodemo.utils.NetworkUtils.networkIsAvailable;

public class PhotosActivity extends AppCompatActivity {

    @Inject FlickrPhotoRepository flickrPhotoRepository;
    @Inject @ViewPreference BooleanPreference viewPreference;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    private ArrayList<Photo> photoList = new ArrayList<>();
    private String menuTitleText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        ButterKnife.bind(this);

        ((FlickrDemoApplication) getApplication()).getAndroidComponent().inject(this);

        setSupportActionBar(toolbar);

        flickrPhotoRepository.setPhotoRepositoryListener(photoRepositoryListener);

        initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        if (menu != null) {
            MenuItem menuItem = menu.findItem(R.id.action_show_preference);
            if (menuItem != null) {
                menuItem.setVisible(!flickrPhotoRepository.isEmpty());
                setMenuTitleText();
                menuItem.setTitle(menuTitleText);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                getRecentPhotos();
                return true;
            case R.id.action_show_preference:
                toggleView();
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

    private void initUI() {
        if (viewPreference.get()) {
            final int numberOfColumns = getResources().getInteger(R.integer.grid_columns);
            recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }
        recyclerView.setHasFixedSize(true);

        if (flickrPhotoRepository.isEmpty()) {
            getRecentPhotos();
        } else {
            photoList = flickrPhotoRepository.getPhotoList();
            populateRecyclerView();
        }
    }
    private void populateRecyclerView() {
        if (!photoList.isEmpty()) {
            PhotoAdapter photoAdapter = new PhotoAdapter(photoList, viewPreference.get());
            photoAdapter.setPhotoAdapterListener(photoAdapterListener);
            recyclerView.setAdapter(photoAdapter);
        } else {
            showErrorSnackbar(R.string.network_error_retrieving_photos, R.string.button_retry, photoErrorClickListener);
        }
    }

    private void toggleView() {
        viewPreference.set(!viewPreference.get());
        recreate();
    }

    private void setMenuTitleText() {
        menuTitleText = viewPreference.get() ? getString(R.string.menu_show_individual) : getString(R.string.menu_show_grid);
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

    private void showErrorSnackbar(@StringRes int stringResId) {
        showErrorSnackbar(stringResId, View.NO_ID, null);
    }

    private final View.OnClickListener photoErrorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getRecentPhotos();
        }
    };

    private final FlickrPhotoRepository.PhotoRepositoryListener photoRepositoryListener = new FlickrPhotoRepository.PhotoRepositoryListener() {
        @Override
        public void onSuccess() {
            photoList = flickrPhotoRepository.getPhotoList();
            invalidateOptionsMenu();
            Timber.d("Number of photos returned: %d", photoList.size());
            populateRecyclerView();
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
            final String photoUrl = TextUtils.isEmpty(photo.getUrl()) ? "" : photo.getUrl();
            if (!TextUtils.isEmpty(photoUrl)) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(photoUrl));
                if (webIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntent);
                } else {
                    showErrorSnackbar(R.string.device_intent_error);
                }
            } else {
                showErrorSnackbar(R.string.photo_url_error);
            }
        }
    };
}
