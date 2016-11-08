package com.supets.pet.libreacthotfix.react;


import android.app.Application;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.supets.pet.libreacthotfix.module.AppReactPackage;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

public class AppReactInstanceManager extends ReactNativeHost {

    public AppReactInstanceManager(Application application) {
        super(application);
    }

    @Override
    protected boolean getUseDeveloperSupport() {
        return false;
    }

    @Override
    protected List<ReactPackage> getPackages() {
        return Arrays.asList(new AppReactPackage(), new MainReactPackage());
    }

    @Override
    protected String getJSMainModuleName() {
        return Config.DEFAULT_JS_MAIN_MODULE_NAME;
    }

    @Nullable
    protected String getBundleAssetName() {
        return Config.MIA_BUNDLE_NAME;
    }

    @Nullable
    @Override
    protected String getJSBundleFile() {
        return AppJSBundleManager.build()
                .setBundleAssetName(Config.MIA_BUNDLE_NAME)
                .setAssetDir(getApplication().getFilesDir())
                .getJSBundleFile();
    }

}