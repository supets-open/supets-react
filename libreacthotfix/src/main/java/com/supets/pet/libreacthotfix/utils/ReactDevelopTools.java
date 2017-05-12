package com.supets.pet.libreacthotfix.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.supets.pet.libreacthotfix.react.Config;

/**
 * AwesomeProject
 *
 * @user lihongjiang
 * @description
 * @date 2016/11/7
 * @updatetime 2016/11/7
 */

public class ReactDevelopTools {

    public static void setDebugHost(Context context, String host, String port, boolean develop) {
        Config.DEVELOP = develop;
        SharedPreferences mPreferneces = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = mPreferneces.edit();
        editor.putString("debug_http_host", host.concat(":").concat(port));
        editor.apply();
        editor.commit();
    }
}
