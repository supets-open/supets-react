package com.supets.pet.libreacthotfix.api;

import android.os.AsyncTask;
import android.os.Environment;

import com.supets.pet.libreacthotfix.bean.AppVersion;
import com.supets.pet.libreacthotfix.bean.DownloadFile;
import com.supets.pet.libreacthotfix.bean.ReactUpdateRequest;
import com.supets.pet.libreacthotfix.react.AppJSBundleManager;
import com.supets.pet.libreacthotfix.react.Config;
import com.supets.pet.libreacthotfix.utils.FileUtil;
import com.supets.pet.libreacthotfix.utils.VersionSharePreferceUtils;

import java.io.File;

public class UpDateBundleApi {

    private static void upgrade(ReactUpdateRequest request,
                                final DownloadProgressListener process,
                                final DownloadResultListener resultListener) {

        new AsyncTask<ReactUpdateRequest, Integer, DownloadFile>() {
            @Override
            protected DownloadFile doInBackground(ReactUpdateRequest... params) {

                for (int i = 1; i < 11; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(i);
                }
                File file = new File(Environment.getExternalStorageDirectory(), Config.MIA_BUNDLE_NAME);
                if (file.exists()) {
                    return new DownloadFile(file);
                }
                return null;
            }

            @Override
            protected void onPostExecute(DownloadFile downloadFile) {
                if (resultListener != null) {
                    if (downloadFile != null) {
                        resultListener.downloadSuccess(downloadFile);
                    } else {
                        resultListener.onError(new Exception("null point exception"));
                    }
                }
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                if (process != null) {
                    process.onProgressChanged(values[0]);
                }
            }
        }

                .

                        execute(request);


    }

    public static void download(final AppVersion version, ReactUpdateRequest reactUpdateRequest, final JsBundleCallback mCallback) {
        upgrade(reactUpdateRequest, new DownloadProgressListener() {
            @Override
            public void onProgressChanged(int progress) {
                if (mCallback != null) {
                    mCallback.onDownloading(progress);
                }
            }
        }, new DownloadResultListener() {
            @Override
            public void downloadSuccess(DownloadFile file) {
                try {
                    File bundle = new File(AppJSBundleManager.build().getJSBundleFile());
                    if (bundle.exists()) {
                        bundle.delete();
                    }
                    FileUtil.copyFile(file.getFile(),
                            new File(AppJSBundleManager.build().getAssetDir(),
                                    AppJSBundleManager.build().getBundleAssetName()), true);
                    VersionSharePreferceUtils.set(Config.BUNDLE_VERSION, version.getLatestVersion());
                } catch (Exception e) {
                    if (mCallback != null) {
                        mCallback.onError(e);
                    }
                    e.printStackTrace();
                } finally {
                    FileUtil.deleteFile(file.getFile());
                }
                if (mCallback != null) {
                    mCallback.onUpdateSuccess();
                }
            }

            @Override
            public void onError(Exception e) {
                if (mCallback != null) {
                    mCallback.onError(e);
                }
            }
        });
    }

}
