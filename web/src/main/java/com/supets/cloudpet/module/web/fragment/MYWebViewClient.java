package com.supets.cloudpet.module.web.fragment;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.supets.cloudpet.module.web.core.UriCallBack;


public class MYWebViewClient extends WebViewClient {


    private UriCallBack mListener;

    public MYWebViewClient(UriCallBack mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
        if (mListener!=null){
            mListener.onPageStarted(view, url);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (mListener!=null){
            mListener.onPageFinished(view, url);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (mListener!=null){
            mListener.onReceivedError(view, errorCode);
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //return JSBridge.js_request_to_app(url, mListener);
        return super.shouldOverrideUrlLoading(view,url);
    }



}
