package com.supets.pet.libreacthotfix.react;


import android.app.Application;
import android.util.Log;

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
    public boolean getUseDeveloperSupport() {
        return Config.DEVELOP;
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
                .getJSBundleFile();
    }

    private static AppReactInstanceManager mReactNativeHost;

    public static AppReactInstanceManager getInstance(Application context) {
        if (mReactNativeHost == null) {
            synchronized (AppReactInstanceManager.class) {
                if (mReactNativeHost == null) {
                    mReactNativeHost = new AppReactInstanceManager(context);
                }
            }
        }
        return mReactNativeHost;
    }

    public static void reset() {
        mReactNativeHost = null;
    }

}