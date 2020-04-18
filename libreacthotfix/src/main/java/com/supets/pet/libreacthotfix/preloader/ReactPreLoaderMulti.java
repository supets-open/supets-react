
package com.supets.pet.libreacthotfix.preloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.collection.ArrayMap;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactRootView;
import com.supets.pet.libreacthotfix.react.AppReactInstanceManager;

import java.lang.reflect.Field;
import java.util.Map;

public class ReactPreLoaderMulti implements IPreLoader {

    private static final Map<String, ReactRootView> CACHE_VIEW_MAP =
            new ArrayMap<>();

    public ReactRootView getRootView(String componentName) {
        return CACHE_VIEW_MAP.get(componentName);
    }

    public void setLaunchOptions(Object obj, Bundle launchOptions) {
        try {
            Field field = ReactRootView.class.getDeclaredField("mLaunchOptions");
            field.setAccessible(true);
            field.set(obj, launchOptions);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    //设置模块名称，因为是private，只能通过反射赋值
    public void setModuleName(Object obj, String moduleName) {
        try {
            Field field = ReactRootView.class.getDeclaredField("mJSModuleName");
            field.setAccessible(true);
            field.set(obj, moduleName);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void init(Activity activity, String componentName, Bundle bundle) {
        if (CACHE_VIEW_MAP.get(componentName) != null) {
            setLaunchOptions(CACHE_VIEW_MAP.get(componentName), bundle);
            setModuleName(CACHE_VIEW_MAP.get(componentName), componentName);
            return;
        }

        ReactRootView rootView = new ReactRootView(activity);
        rootView.startReactApplication(
                ((ReactApplication) activity.getApplication()).getReactNativeHost().getReactInstanceManager(),
                componentName,
                bundle);
        CACHE_VIEW_MAP.put(componentName, rootView);
    }

    public void onDestroy(String componentName) {
        try {
            ReactRootView rootView = getRootView(componentName);
            if (rootView != null) {
                ViewGroup parent = (ViewGroup) rootView.getParent();
                if (parent != null) {
                    parent.removeView(rootView);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {
            AppReactInstanceManager.reset();
            for (ReactRootView rootView : CACHE_VIEW_MAP.values()) {
                rootView = null;
            }
            CACHE_VIEW_MAP.clear();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
