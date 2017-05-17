package com.supets.pet.libreacthotfix.react;

import com.supets.lib.supetscontext.App;
import com.supets.pet.libreacthotfix.utils.FileUtil;
import com.supets.pet.libreacthotfix.utils.VersionSharePreferceUtils;

import java.io.File;

public class AppJSBundleManager {

    private String mBundleAssetName = Config.MIA_BUNDLE_NAME;
    private File mAssetDir;

    private AppJSBundleManager() {
        mAssetDir = FileUtil.getDiskFileDir(App.INSTANCE);
    }

    public String getJSBundleFile() {
        File assetFile = new File(mAssetDir, mBundleAssetName);
        if (assetFile.exists()) {
            return assetFile.getAbsolutePath();
        }
        VersionSharePreferceUtils.setBundleVersion(VersionSharePreferceUtils.DEFAULT_BUNDLE_VERSION);
        return "assets://" + mBundleAssetName;
    }

    public String getJSBundleFileDir() {
        File assetFile = new File(mAssetDir, mBundleAssetName);
        return assetFile.getAbsolutePath();
    }

    public static AppJSBundleManager build() {
        return new AppJSBundleManager();
    }

}