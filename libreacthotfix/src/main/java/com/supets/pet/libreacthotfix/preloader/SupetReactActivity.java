/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 * <p>
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.supets.pet.libreacthotfix.preloader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.supets.pet.libreacthotfix.module.MyConstants;

import java.util.concurrent.ArrayBlockingQueue;

import javax.annotation.Nullable;

/**
 * Base Activity for React Native applications.
 */
public abstract class SupetReactActivity extends Activity
        implements DefaultHardwareBackBtnHandler, PermissionAwareActivity {

    private CacheDelegate mDelegate;

    protected SupetReactActivity() {

    }

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     * e.g. "MoviesApp"
     */
    protected
    @Nullable
    String getMainComponentName() {
        return null;
    }

    /**
     * Called at construction time, override if you have a custom delegate implementation.
     */
    protected CacheDelegate createReactActivityDelegate() {
        return new CacheDelegate(this, getMainComponentName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lazy","lazy");
        lazy();
        mDelegate = createReactActivityDelegate();
        mDelegate.onCreate(savedInstanceState);
    }

    protected abstract void lazy();

    @Override
    protected void onPause() {
        super.onPause();
        mDelegate.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDelegate.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReactPreLoader.onDestroy(getMainComponentName());
        mDelegate.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mDelegate.onActivityResult(requestCode, resultCode, data);
        onActivityResultex(requestCode, resultCode, data);
    }

    public void onActivityResultex(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == 100) {
            String result = data.getStringExtra("result");
            if (!TextUtils.isEmpty(result)) {
                MyConstants.myBlockingQueue.add(result);
            } else {
                MyConstants.myBlockingQueue.add("无数据传回");
            }

        } else {
            MyConstants.myBlockingQueue.add("没有");
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mDelegate.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (!mDelegate.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (!mDelegate.onNewIntent(intent)) {
            super.onNewIntent(intent);
        }
    }

    @Override
    public void requestPermissions(
            String[] permissions,
            int requestCode,
            PermissionListener listener) {
        mDelegate.requestPermissions(permissions, requestCode, listener);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {
        mDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected final ReactNativeHost getReactNativeHost() {
        return mDelegate.getReactNativeHost();
    }

    protected final ReactInstanceManager getReactInstanceManager() {
        return mDelegate.getReactInstanceManager();
    }

    protected final void loadApp(String appKey) {
        mDelegate.loadApp(appKey);
    }
}
