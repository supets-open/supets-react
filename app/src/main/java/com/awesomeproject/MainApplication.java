package com.awesomeproject;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.soloader.SoLoader;
import com.supets.pet.libreacthotfix.react.AppReactInstanceManager;
import com.supets.pet.libreacthotfix.utils.ReactDevelopTools;

public class MainApplication extends Application implements ReactApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
        ReactDevelopTools.setDebugHost(this, "10.0.0.30", "8081", true);
    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return AppReactInstanceManager.getInstance(this);
    }

}
