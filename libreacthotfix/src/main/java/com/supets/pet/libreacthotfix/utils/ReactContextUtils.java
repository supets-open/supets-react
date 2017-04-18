package com.supets.pet.libreacthotfix.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * AwesomeProject
 *
 * @user lihongjiang
 * @description
 * @date 2016/11/7
 * @updatetime 2016/11/7
 */

public class ReactContextUtils {

    public static void setDebugHost(Context context,String host, String port) {
        SharedPreferences mPreferneces = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = mPreferneces.edit();
        editor.putString("debug_http_host", host.concat(":").concat(port));
        editor.apply();
        editor.commit();
    }

}
