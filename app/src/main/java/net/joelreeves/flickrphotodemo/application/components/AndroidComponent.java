package net.joelreeves.flickrphotodemo.application.components;

import net.joelreeves.flickrphotodemo.activities.PhotosActivity;
import net.joelreeves.flickrphotodemo.activities.PhotosPagerActivity;
import net.joelreeves.flickrphotodemo.application.modules.AndroidModule;
import net.joelreeves.flickrphotodemo.application.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AndroidModule.class, NetworkModule.class})
public interface AndroidComponent {
    void inject(PhotosActivity photosActivity);
    void inject(PhotosPagerActivity photosPagerActivity);
}
