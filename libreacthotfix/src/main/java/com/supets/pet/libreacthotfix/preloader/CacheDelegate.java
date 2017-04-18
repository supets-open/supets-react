package com.supets.pet.libreacthotfix.preloader;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import javax.annotation.Nullable;

/**
 * AwesomeProject
 *
 * @user lihongjiang
 * @description
 * @date 2017/4/17
 * @updatetime 2017/4/17
 */

public class CacheDelegate extends SupetReactActivityDelegate {

    private Bundle  bundle;

    public CacheDelegate(Activity activity, @Nullable String mainComponentName) {
        super(activity, mainComponentName);
        bundle=activity.getIntent().getExtras();
    }

    public CacheDelegate(FragmentActivity fragmentActivity, @Nullable String mainComponentName) {
        super(fragmentActivity, mainComponentName);
        bundle=fragmentActivity.getIntent().getExtras();
    }

    @Nullable
    @Override
    protected Bundle getLaunchOptions() {
        return bundle;
    }

}
