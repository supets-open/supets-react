package com.supets.pet.libreacthotfix.module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.Set;

/**
 * 数据类型使用 String boolean Number(Double)
 */
public class MyIntentModule extends ReactContextBaseJavaModule {

    public static final String REACTCLASSNAME = "MyIntentModule";
    private Context mContext;

    public MyIntentModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return REACTCLASSNAME;
    }


    /**
     * //这是React的生命周期函数，会在界面加载完成后执行一次
     * componentDidMount(){
     * React.NativeModules.MyIntentModule.getDataFromIntent(
     * (successMsg)=>{
     * this.setState({TEXT:successMsg,}); //状态改变的话重新绘制界面
     * },
     * (erroMsg)=>{alert(erroMsg)}
     * );
     * }
     *
     * @param successBack
     * @param erroBack
     */
    @ReactMethod
    public void getDataFromIntent(Callback successBack, Callback erroBack) {
        try {
            Activity currentActivity = getCurrentActivity();
            Bundle result = currentActivity.getIntent().getExtras();//会有对应数据放入
            successBack.invoke(IntentToBundle(result));
        } catch (Exception e) {
            erroBack.invoke(e.getMessage());
        }
    }

    @NonNull
    private WritableNativeMap IntentToBundle(Bundle result) {
        WritableNativeMap bundle = new WritableNativeMap();

        Set<String> iterable = result.keySet();
        for (String key : iterable) {
            if (result.get(key) instanceof Boolean) {
                bundle.putBoolean(key, (Boolean) result.get(key));
            }

            if (result.get(key) instanceof Number) {
                bundle.putDouble(key, ((Number) result.get(key)).doubleValue());
            }

            if (result.get(key) instanceof String) {
                bundle.putString(key, (String) result.get(key));
            }
        }
        return bundle;
    }

    /**
     * React.NativeModules.MyIntentModule.finishActivity(this.state.TEXT);
     *
     * @param result
     */
    @ReactMethod
    public void finishActivity(String result) {
        Activity currentActivity = getCurrentActivity();
        Intent intent = new Intent();
        intent.putExtra("result", result);
        currentActivity.setResult(11, intent);
        currentActivity.finish();
    }

    /**
     * 在JS里调用
     * React.NativeModules.MyIntentModule..startActivityByString("com.example.SecondActivity")
     *
     * @param activityName
     */
    @ReactMethod
    public void startActivityByString(String activityName) {
        try {
            Activity currentActivity = getCurrentActivity();
            if (null != currentActivity) {
                Class aimActivity = Class.forName(activityName);
                Intent intent = new Intent(currentActivity, aimActivity);
                currentActivity.startActivity(intent);
            }
        } catch (Exception e) {
            throw new JSApplicationIllegalArgumentException(
                    "Could not open the activity : " + e.getMessage());
        }
    }

    @ReactMethod
    public void startActivityByStringWithModule(String activityName, String moduleName) {
        try {
            Activity currentActivity = getCurrentActivity();
            if (null != currentActivity) {
                Class aimActivity = Class.forName(activityName);
                Intent intent = new Intent(currentActivity, aimActivity);
                intent.putExtra("moduleName", moduleName);
                currentActivity.startActivity(intent);
            }
        } catch (Exception e) {
            throw new JSApplicationIllegalArgumentException(
                    "Could not open the activity : " + e.getMessage());
        }
    }

    @ReactMethod
    public void startActivityByStringWithBundle(String activityName, ReadableMap map) {
        try {
            Activity currentActivity = getCurrentActivity();

            if (null != currentActivity) {
                Class aimActivity = Class.forName(activityName);
                Intent intent = new Intent(currentActivity, aimActivity);
                BundleToIntent(map, intent);
                currentActivity.startActivity(intent);
            }
        } catch (Exception e) {
            throw new JSApplicationIllegalArgumentException(
                    "Could not open the activity : " + e.getMessage());
        }
    }

    private void BundleToIntent(ReadableMap map, Intent intent) {
        if (map != null) {
            ReadableMapKeySetIterator iterator = map.keySetIterator();
            if (iterator != null) {
                while (iterator.hasNextKey()) {
                    String key = iterator.nextKey();
                    ReadableType type = map.getType(key);
                    if (type == ReadableType.Boolean) {
                        intent.putExtra(key, map.getBoolean(key));
                    }
                    if (type == ReadableType.String) {
                        intent.putExtra(key, map.getString(key));
                    }

                    if (type == ReadableType.Number) {
                        intent.putExtra(key, map.getDouble(key));
                    }
                }
            }
        }
    }

    /**
     * 在JS端调用
     * React.NativeModules.MyIntentModule.startActivityForResult(
     * "com.example.SecondActivity",100,
     * (successMsg)=>{
     * this.setState({TEXT:successMsg,});
     * },
     * (erroMsg)=>{alert(erroMsg)}
     * );
     *
     * @param activityName
     * @param requestCode
     * @param successCallback
     * @param erroCallback
     */
    @ReactMethod
    public void startActivityForResult(String activityName, int requestCode, Callback successCallback, Callback erroCallback) {
        try {
            Activity currentActivity = getCurrentActivity();
            if (null != currentActivity) {
                Class aimActivity = Class.forName(activityName);
                Intent intent = new Intent(currentActivity, aimActivity);
                currentActivity.startActivityForResult(intent, requestCode);
                MyConstants.myBlockingQueue.clear();
                String result = MyConstants.myBlockingQueue.take();
                successCallback.invoke(result);
            }
        } catch (Exception e) {
            erroCallback.invoke(e.getMessage());
            throw new JSApplicationIllegalArgumentException(
                    "Could not open the activity : " + e.getMessage());
        }
    }

    @ReactMethod
    public void startActivityForResultWithBundle(String activityName, int requestCode, Callback successCallback, Callback erroCallback, ReadableMap map) {
        try {
            Activity currentActivity = getCurrentActivity();
            if (null != currentActivity) {
                Class aimActivity = Class.forName(activityName);
                Intent intent = new Intent(currentActivity, aimActivity);
                BundleToIntent(map, intent);
                currentActivity.startActivityForResult(intent, requestCode);
                MyConstants.myBlockingQueue.clear();
                String result = MyConstants.myBlockingQueue.take();
                successCallback.invoke(result);
            }
        } catch (Exception e) {
            erroCallback.invoke(e.getMessage());
            throw new JSApplicationIllegalArgumentException(
                    "Could not open the activity : " + e.getMessage());
        }
    }
}