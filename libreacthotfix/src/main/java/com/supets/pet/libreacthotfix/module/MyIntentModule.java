package com.supets.pet.libreacthotfix.module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

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
            String result = currentActivity.getIntent().getStringExtra("result");//会有对应数据放入
            if (TextUtils.isEmpty(result)) {
                result = "No Data";
            }
            successBack.invoke(result);
        } catch (Exception e) {
            erroBack.invoke(e.getMessage());
        }
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