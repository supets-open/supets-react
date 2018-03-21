package com.supets.cloudpet.module.web.core;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebView;

public interface ResponseCallBack {

    void onResponse(String consoleMessage);

     void onReceivedTitle(WebView view, String title);

    void onProgressChanged(WebView view, int newProgress);

     void onCloseWindow(WebView view);

    void onFileCallback(ValueCallback<Uri> uploadMsg);
}
