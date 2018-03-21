package com.supets.cloudpet.module.web.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.supets.cloudpet.module.web.core.ResponseCallBack;
import com.supets.cloudpet.module.web.core.UriCallBack;

public abstract class BaseWebViewFragment extends Fragment implements ResponseCallBack, UriCallBack{

    protected String getStringArgument(String key) {
        Bundle arguments = getArguments();
        return arguments != null ? arguments.getString(key) : null;
    }

    public WebViewActionListener getWebViewActionListener() {
        return mCallBack;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mCallBack = (WebViewActionListener) getActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private WebViewActionListener mCallBack;
    private WebView mWebView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         mWebView = new WebView(getActivity());
        initWebView();

        String mUrl = getStringArgument("url");

        if (mUrl != null) {
            mWebView.loadUrl(mUrl);
        }

        //mWebView.reload();

        return mWebView;
    }


    public void initWebView() {
        String userAgent = mWebView.getSettings().getUserAgentString();
        mWebView.getSettings().setUserAgentString(userAgent + "/tuzitest:1.0 ");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setDomStorageEnabled(true); // 解决微信页面点击‘阅读原文’无反应的bug

          /* 解决空白页问题 */
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);

        if (Build.VERSION.SDK_INT > 11) {
            mWebView.removeJavascriptInterface("accessibility");
            mWebView.removeJavascriptInterface("accessibilityTraversal");
            mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        }

        mWebView.setWebViewClient(new MYWebViewClient(this));
        mWebView.setWebChromeClient(new MYWebChromeClient(this));
        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onPageStarted(WebView view, String url) {
        if (mCallBack != null) {
            mCallBack.onPageStarted(url);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
//        JsEx.appCallBackJs_Methed_Check("getShareInfo",
//                JsEx.CALLBACK_SHOW_SHARE, view);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode) {
        if (errorCode < 0) {
            //mPageLoadingView.showNetworkError();
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (mCallBack != null) {
            mCallBack.onReceivedTitle(view.getUrl(), title);
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mCallBack != null) {
            mCallBack.onProgressChanged(newProgress);
        }
    }

    @Override
    public void onCloseWindow(WebView window) {
        if (mCallBack != null) {
            mCallBack.onCloseWindow();
        }
    }

    @Override
    public void onFileCallback(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "完成操作需要使用"), FILE_CHOOSER_RESULT_CODE);
    }

    /**
     * 生命周期处理
     */
    private Boolean mLoginStatus;

    @Override
    public void onPause() {
        super.onPause();
        //mLoginStatus = SessionManager.isLogin();
        try {
            mWebView.getClass().getMethod("onPause").invoke(mWebView);
        } catch (Exception e) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            mWebView.getClass().getMethod("onResume").invoke(mWebView);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (mLoginStatus != null && mLoginStatus != SessionManager.isLogin()) {
//            String url = mWebView.getUrl();
//            if (url != null) {
//                mWebView.loadUrl(url,SessionManager.addUserArgs2(url));
//            }
//        }
    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.destroy();
            removeCookie();
        }
        super.onDestroy();
    }


    private void removeCookie() {
        CookieSyncManager.createInstance(getActivity());
        CookieManager.getInstance().removeAllCookie();
    }

//    public void onAppLoginSuccess() {
//        mLoginStatus = true;
//        String uid = SessionManager.getCurrentUserId();
//        String sessionId = SessionManager.getAuthSession();
//        JsEx.miababay_syn_login(mWebView, uid, sessionId);
//    }
    /**
     * 文件上传
     */

    private static final int FILE_CHOOSER_RESULT_CODE = 10010;
    private ValueCallback<Uri> mUploadMessage;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == mUploadMessage) {
                return;
            }
            mUploadMessage.onReceiveValue(data != null ? data.getData() : null);
            mUploadMessage = null;
        }

    }


    /**
     * 回退处理
     */
    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public void goBack() {
        //mPageLoadingView.showContent();
        mWebView.goBack();
    }

    public String getForwardUrl() {
        if (!mWebView.canGoBack()) {
            return null;
        }
        WebBackForwardList list = mWebView.copyBackForwardList();
        return list.getItemAtIndex(list.getCurrentIndex() - 1).getUrl();
    }

    public WebView getWebView() {
        return mWebView;
    }

}
