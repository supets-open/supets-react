package com.supets.cloudpet.module.web.fragment;

public interface WebViewActionListener {
        void onPageStarted(String url);

        void onReceivedTitle(String url, String title);

        void onProgressChanged(int newProgress);

        void onCloseWindow();

        void shouldShowShareBtn(boolean show);
    }