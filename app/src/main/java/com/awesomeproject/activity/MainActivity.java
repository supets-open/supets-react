package com.awesomeproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.awesomeproject.R;
import com.supets.lib.supetscontext.App;
import com.supets.pet.libreacthotfix.api.JsBundleCallback;
import com.supets.pet.libreacthotfix.api.UpDateBundleApi;
import com.supets.pet.libreacthotfix.bean.AppVersion;
import com.supets.pet.libreacthotfix.preloader.ReactPreLoader;

public class MainActivity extends Activity implements JsBundleCallback {


    ListView reactListView;
    ReactAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.react_test_main);
        ReactPreLoader.init(this, "pageCache", null);

        reactListView = (ListView) findViewById(R.id.reactListView);
        adapter = new ReactAdapter();
        adapter.getData().add(new ReactData("pageImage"));
        adapter.getData().add(new ReactData("pageText"));
        adapter.getData().add(new ReactData("pageFlexLayout"));
        adapter.getData().add(new ReactData("pageStyleSheet"));
        adapter.getData().add(new ReactData("pageViewPager"));

        reactListView.setAdapter(adapter);
        reactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReactData data = (ReactData) parent.getAdapter().getItem(position);
                startUi(data);
            }
        });

        AppVersion appVersion = new AppVersion();
        appVersion.setDownloadUrl("https://raw.githubusercontent.com/supets-open/supets-java/master/patch110");
        appVersion.setLastBundleVersion("1.1.0");

        if (appVersion.isUpdate()) {
            UpDateBundleApi.download(appVersion, this);
        }else{
            onNoUpdate();
        }

    }

    private void startUi(ReactData data) {
        ReactPreLoader.init(this, data.moduleName, null);
        Intent intent = new Intent(MainActivity.this, ReactTestActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("moduleName", data.moduleName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDownloading(int progress) {
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(App.INSTANCE,"Exception",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoUpdate() {
        Toast.makeText(App.INSTANCE,"onNoUpdate",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateSuccess() {
        Toast.makeText(App.INSTANCE,"success",Toast.LENGTH_SHORT).show();
        ReactPreLoader.clear();
        ReactPreLoader.init(this, "pageCache", null);
    }
}
