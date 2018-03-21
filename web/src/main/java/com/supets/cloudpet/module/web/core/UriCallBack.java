package com.supets.cloudpet.module.web.core;

import android.webkit.WebView;

public interface UriCallBack {
    void onLoadHttp(String url);

    void onLoadOtherUri(String url);

    void onLoadAppUri(String url);


    void onPageStarted(WebView view, String url);

    void onPageFinished(WebView view, String url);

    void onReceivedError(WebView view, int errorCode);
}
