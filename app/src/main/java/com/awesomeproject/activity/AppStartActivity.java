package com.awesomeproject.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.awesomeproject.R;
import com.supets.lib.supetscontext.App;
import com.supets.pet.libreacthotfix.api.JsBundleCallback;
import com.supets.pet.libreacthotfix.api.UpDateBundleApi;
import com.supets.pet.libreacthotfix.bean.AppVersion;
import com.supets.pet.libreacthotfix.preloader.ReactPreLoader;
import com.supets.pet.libreacthotfix.utils.VersionSharePreferceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class AppStartActivity extends Activity implements JsBundleCallback {

    private VersionDTO mVersion;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appstart);
        progressBar = (ProgressBar) findViewById(R.id.processBar);
        update();
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
        progressBar.setProgress(progress);
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(App.INSTANCE, "更新失敗", Toast.LENGTH_SHORT).show();
        startUi();
    }

    @Override
    public void onNoUpdate() {
        startUi();
    }

    @Override
    public void onUpdateSuccess() {
        Toast.makeText(App.INSTANCE, "更新成功", Toast.LENGTH_SHORT).show();
        VersionSharePreferceUtils.setBundleVersion(mVersion.content.version);
        startUi();
    }

    private void update() {
        try {
            OkHttpUtils.get().url(
                    "https://raw.githubusercontent.com/supets-open/supets-react/master/database/version_update.json")
                    .build()
                    .execute(new StringCallback() {

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            onNoUpdate();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mVersion = JSonUtil.fromJson(response, VersionDTO.class);
                            int last = Integer.parseInt(mVersion.content.version.replace(".", ""));
                            int old = Integer.parseInt(VersionSharePreferceUtils.getBundleVersion().replace(".", ""));
                            if (last > old) {
                                updateData(mVersion);
                            } else {
                                onNoUpdate();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void updateData(final VersionDTO dto) {
        new AlertDialog.Builder(this)
                .setTitle("版本更新")
                .setMessage(dto.content.text)
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppVersion appVersion = new AppVersion();
                        appVersion.setDownloadUrl(dto.content.url);
                        appVersion.setLastBundleVersion(dto.content.version);

                        if (appVersion.isUpdate()) {
                            UpDateBundleApi.downloadAsync(appVersion, AppStartActivity.this);
                        } else {
                            onNoUpdate();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onNoUpdate();
                    }
                })
                .show();

    }


}
