package com.awesomeproject.activity;

import android.util.Log;

import com.supets.pet.libreacthotfix.preloader.ReactPreLoader;
import com.supets.pet.libreacthotfix.preloader.SupetReactFragmentActivity;

public class ReactTestActivity extends SupetReactFragmentActivity {


    public void lazy() {
        long start = System.currentTimeMillis();
        ReactPreLoader.init(this, getMainComponentName(), getIntent().getExtras());
        Log.v("lazy-start", System.currentTimeMillis() - start + "");
    }

    @Override
    protected String getMainComponentName() {
        return getIntent().getStringExtra("moduleName");
    }


    @Override
    protected void onDestroy() {
        ReactPreLoader.onDestroy(getMainComponentName());
        ReactPreLoader.clear();
        super.onDestroy();
    }
}
