package com.awesomeproject;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.soloader.SoLoader;
import com.supets.pet.libreacthotfix.preloader.ReactPreLoader;
import com.supets.pet.libreacthotfix.react.AppReactInstanceManager;
import com.supets.pet.libreacthotfix.utils.ReactContextUtils;

public class MainApplication extends Application implements ReactApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ReactPreLoader.clear();
        SoLoader.init(this, /* native exopackage */ false);
        ReactContextUtils.setApplication(this);
       // ReactContextUtils.setDebugHost("10.12.32.47", "8081");
    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return AppReactInstanceManager.getInstance(this);
    }

}
