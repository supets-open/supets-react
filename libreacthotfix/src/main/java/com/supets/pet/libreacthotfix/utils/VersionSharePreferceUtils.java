package com.supets.pet.libreacthotfix.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.supets.lib.supetscontext.App;

/**
 * Created by b.s.lee on 2016/2/25.
 * <p/>
 * module   name:
 * module action:
 */
public class VersionSharePreferceUtils {

    public static final String BUNDLE_VERSION = "bundle_version";
    public static final String DEFAULT_BUNDLE_VERSION = "0.0.1";
    public static final String VersionName = "bundle_version_config";

    public static String getBundleVersion() {
        SharedPreferences sharedPreferences = App.INSTANCE.getSharedPreferences(VersionName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(BUNDLE_VERSION, DEFAULT_BUNDLE_VERSION);
    }

    public static void setBundleVersion(String latestVersion) {
        SharedPreferences sharedPreferences =  App.INSTANCE.getSharedPreferences(VersionName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BUNDLE_VERSION, latestVersion);
        editor.apply();
        editor.commit();
    }


}
