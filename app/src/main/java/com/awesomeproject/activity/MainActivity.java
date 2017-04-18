package com.awesomeproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.awesomeproject.R;
import com.supets.pet.libreacthotfix.api.JsBundleCallback;
import com.supets.pet.libreacthotfix.api.UpDateBundleApi;
import com.supets.pet.libreacthotfix.bean.AppVersion;
import com.supets.pet.libreacthotfix.bean.ReactUpdateRequest;
import com.supets.pet.libreacthotfix.preloader.ReactPreLoader;

import java.util.Random;

public class MainActivity extends Activity  implements JsBundleCallback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.react_test_main);


        findViewById(R.id.startWeb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                ReactPreLoader.init(MainActivity.this,"familyAddress");

                Intent intent = new Intent(MainActivity.this, ReactTestActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("moduleName", "familyAddress");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        findViewById(R.id.startWeb2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ReactTestActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("moduleName", "mainpage");
                bundle.putString("moduleName2", System.currentTimeMillis()+"");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

//        AppVersion appVersion = new AppVersion();
//        appVersion.setDownloadUrl("http://m.supets.com");
//        appVersion.setLastBundleVersion("0.0.1");
//
//        if (appVersion.isUpdate()) {
//            ReactUpdateRequest reactUpdateRequest = new ReactUpdateRequest();
//            reactUpdateRequest.setDownloadUrl(appVersion.getDownloadUrl());
//            UpDateBundleApi.download(appVersion, reactUpdateRequest, this);
//        }else{
//            onNoUpdate();
//        }

    }

    @Override
    public void onDownloading(int progress) {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onNoUpdate() {

    }

    @Override
    public void onUpdateSuccess() {

    }
}
