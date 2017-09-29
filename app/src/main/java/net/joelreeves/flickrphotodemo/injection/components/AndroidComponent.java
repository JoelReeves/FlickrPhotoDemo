package net.joelreeves.flickrphotodemo.injection.components;

import net.joelreeves.flickrphotodemo.injection.modules.AndroidModule;
import net.joelreeves.flickrphotodemo.injection.modules.NetworkModule;
import net.joelreeves.flickrphotodemo.injection.modules.PreferenceModule;
import net.joelreeves.flickrphotodemo.ui.activities.PhotosActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AndroidModule.class, NetworkModule.class, PreferenceModule.class})
public interface AndroidComponent {
    void inject(PhotosActivity photosActivity);
}
