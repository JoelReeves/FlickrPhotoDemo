package net.joelreeves.flickrphotodemo.data.preferences;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class BooleanPreference {

    private final SharedPreferences mSharedPreferences;
    private final String mKey;
    private final boolean mDefaultValue;

    public BooleanPreference(@NonNull SharedPreferences sharedPreferences, @NonNull String key, boolean defaultValue) {
        mSharedPreferences = sharedPreferences;
        mKey = key;
        mDefaultValue = defaultValue;
    }

    public boolean get() {
        return mSharedPreferences.getBoolean(mKey, mDefaultValue);
    }

    public boolean isSet() {
        return mSharedPreferences.contains(mKey);
    }

    public void set(boolean value) {
        mSharedPreferences.edit().putBoolean(mKey, value).apply();
    }

    public void delete() {
        mSharedPreferences.edit().remove(mKey).apply();
    }
}
