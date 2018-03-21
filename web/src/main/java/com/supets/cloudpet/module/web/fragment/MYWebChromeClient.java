package com.supets.cloudpet.module.web.fragment;

import android.net.Uri;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.supets.cloudpet.module.web.core.ResponseCallBack;


public class MYWebChromeClient extends WebChromeClient {

    public ResponseCallBack mListener;

    public  MYWebChromeClient(ResponseCallBack mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (mListener!=null){
            mListener.onReceivedTitle(view,title);
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mListener!=null){
            mListener.onProgressChanged(view,newProgress);
        }
    }

    @Override
    public void onCloseWindow(WebView view) {
        if (mListener!=null){
            mListener.onCloseWindow(view);
        }
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        if (consoleMessage != null && consoleMessage.message() != null) {
            String msg = consoleMessage.message();
            if (mListener!=null){
               // JSBridge.jsCallBackApp(msg, mListener);
            }
        }
        return super.onConsoleMessage(consoleMessage);
    }


    // Android > 4.1.1 调用这个方法
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg);
    }

    // 3.0 + 调用这个方法
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        openFileChooser(uploadMsg);
    }

    // Android < 3.0 调用这个方法
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        if (mListener!=null){
            mListener.onFileCallback(uploadMsg);
        }
     }

}