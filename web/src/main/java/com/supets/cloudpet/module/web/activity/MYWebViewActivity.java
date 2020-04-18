package com.supets.cloudpet.module.web.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.supets.cloudpet.module.web.R;
import com.supets.cloudpet.module.web.fragment.WebViewActionListener;
import com.supets.cloudpet.module.web.fragment.WebViewFragment;
import com.supets.commons.widget.CommonHeader;

import java.util.HashMap;

public class MYWebViewActivity extends FragmentActivity implements WebViewActionListener, View.OnClickListener {


    private TextView mTitle;
    private ProgressBar mProgressBar;

    private TextView mBack;
    private ImageView mClose;
    private TextView mShare;

    private WebViewFragment mFragment;

    private String mTitleText;
    private HashMap<String, String> mTitleCache;
    private CommonHeader mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.m_web_activity_webview);
        initView();

        Intent intent = getIntent();
        Uri uri = intent.getData();

        String url = uri != null ? uri.getQueryParameter("url") : intent.getStringExtra("url");

        //url = "http://m.supets.com/demo";

        if (TextUtils.isEmpty(url)) {
            finish();
        }

        mFragment = WebViewFragment.newInstance(url);
        getSupportFragmentManager().beginTransaction().add(R.id.container, mFragment).commitAllowingStateLoss();

    }

    private void initView() {
        mHeader = (CommonHeader) findViewById(R.id.commonHeader);

        mBack = mHeader.getLeftButton();
        mBack.setOnClickListener(this);

        mClose = new ImageView(this);
        mClose.setImageResource(R.drawable.m_web_btn_title_bar_close_webview_selector);
        mHeader.getLeftContainer().addView(mClose);
        mClose.setOnClickListener(this);

        mShare = mHeader.getRightButton();
        mShare.setBackgroundResource(R.drawable.m_web_btn_title_bar_share);
        mShare.setOnClickListener(this);

        mTitle = mHeader.getTitleTextView();
        mTitleCache = new HashMap<>();

        mTitleText = getIntent().getStringExtra("title");
        mTitle.setText(mTitleText);

        boolean showClose = getIntent().getBooleanExtra("show_close", false);
        mClose.setVisibility(showClose ? View.VISIBLE : View.GONE);

        boolean showShare = getIntent().getBooleanExtra("show_share", false);
        mShare.setVisibility(showShare ? View.VISIBLE : View.GONE);

        mProgressBar = (ProgressBar) findViewById(R.id.progress);
    }


    @Override
    public void onPageStarted(String url) {
        showShareButtonByUrl(url);
    }

    @Override
    public void onReceivedTitle(String url, String title) {
        if (TextUtils.isEmpty(mTitleText)) {
            mTitle.setText(title);
            mTitleCache.put(url, title);
        }
    }

    @Override
    public void onProgressChanged(int newProgress) {
        mProgressBar.setProgress(newProgress);
        mProgressBar.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onCloseWindow() {
        finish();
    }

    @Override
    public void shouldShowShareBtn(boolean show) {
        if (show) {
            mShare.setVisibility(View.VISIBLE);
        }
    }


    private void showShareButtonByUrl(String url) {
        try {
            String miaShare = Uri.parse(url).getQueryParameter("supetsShare");
            boolean showShare = "show".equals(miaShare.trim());
            mShare.setVisibility(showShare ? View.VISIBLE : View.GONE);
        } catch (Exception e) {
            mShare.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragment.canGoBack()) {
            /****未指定固定标题****/
            if (TextUtils.isEmpty(mTitleText)) {
                String forwardUrl = mFragment.getForwardUrl();
                String forwardTitle = mTitleCache.get(forwardUrl);
                mTitle.setText(forwardTitle == null ? mTitle.getText() : forwardTitle);
                showShareButtonByUrl(forwardUrl);
            }
            mFragment.goBack();
        } else {
            finish();
        }
    }

    private void onShareClick() {
//        JsEx.appCallBackJs_Methed("getShareInfo",
//                JsEx.CALLBACK_SHARE_INFO, mFragment.getWebView());
    }

    @Override
    public void onClick(View v) {
        if (v == mBack) {
            onBackPressed();
        }
        if (v == mShare) {
            onShareClick();
        }
        if (v == mClose) {
            finish();
        }
    }

}
