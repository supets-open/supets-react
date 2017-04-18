/*
 * Copyright (C) 2016 MarkZhai (http://zhaiyifan.cn).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.supets.pet.libreacthotfix.preloader;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.ViewGroup;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactRootView;
import com.supets.pet.libreacthotfix.react.AppReactInstanceManager;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * React Native Bundle Pre-loader.
 *
 * @author markzhai on 16/8/20
 * @version 0.31.0
 */
public class ReactPreLoader {

    private static   ReactRootView  reactRootView;

    public static ReactRootView getRootView(String componentName) {
        return reactRootView;
    }


    //设置启动参数，因为是private，只能通过反射赋值
    public static void setLaunchOptions(Object obj, Bundle launchOptions) {
        try {
            Field field = ReactRootView.class.getDeclaredField("mLaunchOptions");
            field.setAccessible(true);
            field.set(obj, launchOptions);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    //设置模块名称，因为是private，只能通过反射赋值
    public static void setModuleName(Object obj, String moduleName) {
        try {
            Field field = ReactRootView.class.getDeclaredField("mJSModuleName");
            field.setAccessible(true);
            field.set(obj, moduleName);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Pre-load {@link ReactRootView} to local {@link Map}, you may want to
     * load it in previous activity.
     */
    public static void init(Activity activity, String componentName, Bundle bundle) {
        if (reactRootView!= null) {
            setLaunchOptions(reactRootView, bundle);
            setModuleName(reactRootView, componentName);
            return;
        }

        reactRootView = new ReactRootView(activity);
        reactRootView.startReactApplication(
                ((ReactApplication) activity.getApplication()).getReactNativeHost().getReactInstanceManager(),
                componentName,
                bundle);
    }

    /**
     * Remove {@link ReactRootView} from parent.
     */
    public static void onDestroy(String componentName) {
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

    public static void clear() {
        try {
            AppReactInstanceManager.reset();
            reactRootView=null;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
