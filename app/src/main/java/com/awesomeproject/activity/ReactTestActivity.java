package com.awesomeproject.activity;

import android.os.Bundle;

import com.supets.pet.libreacthotfix.preloader.CustomReactActivity;
import com.supets.pet.libreacthotfix.preloader.ReactInfo;
import com.supets.pet.libreacthotfix.react.Config;

import javax.annotation.Nullable;

public class ReactTestActivity extends CustomReactActivity {

    public static final ReactInfo reactInfo = new ReactInfo(Config.MODULE_NAME, null);

    @Override
    protected String getMainComponentName() {
        return reactInfo.getMainComponentName();
    }

    @Override
    public ReactInfo getReactInfo() {
        return reactInfo;
    }

    @Nullable
    @Override
    protected Bundle getLaunchOptions() {
        return getIntent().getExtras();
    }
}
