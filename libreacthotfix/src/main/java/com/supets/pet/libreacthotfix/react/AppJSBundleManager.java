package com.supets.pet.libreacthotfix.react;

import android.support.annotation.NonNull;
import com.supets.pet.libreacthotfix.utils.ReactContextUtils;

import java.io.File;

public class AppJSBundleManager {

    private String mBundleAssetName = Config.MIA_BUNDLE_NAME;
    private File mAssetDir;

    public File getAssetDir() {
        return mAssetDir;
    }

    public String getBundleAssetName() {
        return mBundleAssetName;
    }

    private AppJSBundleManager() {
        mAssetDir = ReactContextUtils.getApplication().getFilesDir();
    }

    public String getJSBundleFile() {
        File assetFile = new File(mAssetDir, mBundleAssetName);
        if (assetFile.exists()) {
            return assetFile.getAbsolutePath();
        }
        return "assets://" + mBundleAssetName;
    }

    public static AppJSBundleManager build() {
        return new AppJSBundleManager();
    }

    public AppJSBundleManager setBundleAssetName(@NonNull final String bundleAssetName) {
        mBundleAssetName = bundleAssetName;
        return this;
    }

    public AppJSBundleManager setAssetDir(@NonNull final File assetDir) {
        mAssetDir = new File(assetDir, "assets");
        return this;
    }

}