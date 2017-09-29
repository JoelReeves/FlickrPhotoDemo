package net.joelreeves.flickrphotodemo.injection.modules;

import android.content.Context;
import android.content.SharedPreferences;

import net.joelreeves.flickrphotodemo.data.preferences.BooleanPreference;
import net.joelreeves.flickrphotodemo.data.preferences.qualifiers.ViewPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module
public class PreferenceModule {

    private static final String PREF_NAME = "flickrphotodemo_preferences";

    private static final String GRID_KEY = "grid_key";

    @Provides @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    }

    @Provides @Singleton @ViewPreference
    BooleanPreference provideGridPreference(SharedPreferences sharedPreferences) {
        return new BooleanPreference(sharedPreferences, GRID_KEY, true);
    }
}
