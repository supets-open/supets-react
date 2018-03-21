package com.supets.cloudpet.module.web;

import android.content.Context;
import android.content.Intent;

import com.supets.cloudpet.module.web.activity.MYWebViewActivity;

public class InnerNavigation {

    public static void startWebViewActivity(Context context, String url, String title, boolean showClose, boolean showShare) {
        Intent intent = new Intent(context, MYWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("show_close", showClose);
        intent.putExtra("show_share", showShare);
        context.startActivity(intent);
    }

}
