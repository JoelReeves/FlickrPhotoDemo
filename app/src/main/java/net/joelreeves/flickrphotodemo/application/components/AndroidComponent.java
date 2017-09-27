package net.joelreeves.flickrphotodemo.application.components;

import net.joelreeves.flickrphotodemo.activities.PhotosActivity;
import net.joelreeves.flickrphotodemo.application.modules.AndroidModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AndroidModule.class})
public interface AndroidComponent {
    void inject(PhotosActivity photosActivity);
}
