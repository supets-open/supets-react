package com.awesomeproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.awesomeproject.R;
import com.supets.lib.supetscontext.App;
import com.supets.pet.libreacthotfix.api.JsBundleCallback;
import com.supets.pet.libreacthotfix.api.UpDateBundleApi;
import com.supets.pet.libreacthotfix.bean.AppVersion;
import com.supets.pet.libreacthotfix.preloader.ReactPreLoader;
import com.supets.pet.libreacthotfix.utils.VersionSharePreferceUtils;

public class AppStartActivity extends Activity implements JsBundleCallback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appstart);


        AppVersion appVersion = new AppVersion();
        appVersion.setDownloadUrl("https://raw.githubusercontent.com/supets-open/supets-java/master/patch110");
        appVersion.setLastBundleVersion("1.1.0");

        if (appVersion.isUpdate()) {
            UpDateBundleApi.downloadAsync(appVersion, this);
        } else {
            onNoUpdate();
        }
    }

    private void startUi() {

        ReactPreLoader.clear();
        UpDateBundleApi.patch();

        ReactPreLoader.init(this, "pageCache", null);

        Intent intent = new Intent(this, ReactTestActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDownloading(int progress) {

    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(App.INSTANCE, "Exception", Toast.LENGTH_SHORT).show();
        startUi();
    }

    @Override
    public void onNoUpdate() {
        startUi();
        Toast.makeText(App.INSTANCE, "onNoUpdate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateSuccess() {
        Toast.makeText(App.INSTANCE, "success", Toast.LENGTH_SHORT).show();

        VersionSharePreferceUtils.setBundleVersion("1.1.0");

        startUi();
    }


}
