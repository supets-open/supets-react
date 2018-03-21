package com.supets.cloudpet.module.web.fragment;

import android.net.Uri;
import android.os.Bundle;

public class WebViewFragment extends BaseWebViewFragment {


    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResponse(String consoleMessage) {

    }

    @Override
    public void onLoadHttp(String url) {
        // webview in WebViewActivity
        if (getParentFragment() == null) {
            getWebView().loadUrl(url);
        } else {
            // webview not in WebViewActivity
            boolean targetSelf = false; // true: load url in current webview
            try {
                String target = Uri.parse(url).getQueryParameter("target");
                targetSelf = "normal".equals(target);
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            if (targetSelf) {
                getWebView().loadUrl(url);
            } else {


            }
        }
    }

    @Override
    public void onLoadOtherUri(String url) {

    }

    @Override
    public void onLoadAppUri(String url) {

    }


}
