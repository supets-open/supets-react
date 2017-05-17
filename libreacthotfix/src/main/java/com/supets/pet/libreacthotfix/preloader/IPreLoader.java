package com.supets.pet.libreacthotfix.preloader;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.react.ReactRootView;


public interface IPreLoader {

    ReactRootView getRootView(String componentName);

    void setLaunchOptions(Object obj, Bundle launchOptions);

    void setModuleName(Object obj, String moduleName);

    void init(Activity activity, String componentName, Bundle bundle);

    void onDestroy(String componentName);

    void clear();

}
