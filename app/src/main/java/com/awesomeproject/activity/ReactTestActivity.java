package com.awesomeproject.activity;

import com.supets.pet.libreacthotfix.preloader.ReactPreLoader;
import com.supets.pet.libreacthotfix.preloader.SupetReactActivity;

public class ReactTestActivity extends SupetReactActivity {

    public void lazy() {
        ReactPreLoader.init(this, getMainComponentName(), getIntent().getExtras());
    }

    @Override
    protected String getMainComponentName() {
        String moduleName=getIntent().getStringExtra("moduleName");
        return moduleName==null?"InterView":moduleName;
    }

    @Override
    protected void onDestroy() {
        ReactPreLoader.onDestroy(getMainComponentName());
        super.onDestroy();
    }
}
