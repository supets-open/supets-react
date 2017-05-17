
package com.supets.pet.libreacthotfix.preloader;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.react.ReactRootView;

/**
 * React Native Bundle Pre-loader.
 *
 * @author markzhai on 16/8/20
 * @version 0.31.0
 */
public class ReactPreLoader {

    public static IPreLoader preLoader = new ReactPreLoaderMulti();

    public static ReactRootView getRootView(String componentName) {
        return preLoader.getRootView(componentName);
    }

    public static void init(Activity activity, String componentName, Bundle bundle) {
        preLoader.init(activity, componentName, bundle);
    }

    public static void onDestroy(String componentName) {
        preLoader.onDestroy(componentName);
    }

    public static void clear() {
        preLoader.clear();
    }

}
