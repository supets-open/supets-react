package com.supets.pet.libreacthotfix.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by b.s.lee on 2016/2/25.
 * <p/>
 * module   name:
 * module action:
 */
public class VersionSharePreferceUtils {

    public static String getString(String bundleVersion, String defaultBundleVersion) {
        SharedPreferences sharedPreferences = ReactContextUtils.getApplication().getSharedPreferences("mia_bundle", Context.MODE_PRIVATE); //私有数据
        return sharedPreferences.getString(bundleVersion,defaultBundleVersion);
    }

    public static void set(String bundleVersion, String latestVersion) {
        SharedPreferences sharedPreferences =ReactContextUtils.getApplication().getSharedPreferences("mia_bundle", Context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString(bundleVersion, latestVersion);
        editor.apply();//提交修改
    }
}
