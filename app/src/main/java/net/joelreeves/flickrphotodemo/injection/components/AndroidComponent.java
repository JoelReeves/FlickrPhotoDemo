package net.joelreeves.flickrphotodemo.injection.components;

import net.joelreeves.flickrphotodemo.ui.activities.PhotosActivity;
import net.joelreeves.flickrphotodemo.ui.activities.PhotosPagerActivity;
import net.joelreeves.flickrphotodemo.injection.modules.AndroidModule;
import net.joelreeves.flickrphotodemo.injection.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AndroidModule.class, NetworkModule.class})
public interface AndroidComponent {
    void inject(PhotosActivity photosActivity);
    void inject(PhotosPagerActivity photosPagerActivity);
}
