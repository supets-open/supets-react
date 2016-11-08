package com.awesomeproject;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.supets.pet.libreacthotfix.react.AppReactInstanceManager;
import com.supets.pet.libreacthotfix.utils.ReactContextUtils;

public class MainApplication extends Application implements ReactApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ReactContextUtils.setApplication(this);
        ReactContextUtils.setDebugHost("10.0.0.49","8081");
    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return new AppReactInstanceManager(this);
    }


}
