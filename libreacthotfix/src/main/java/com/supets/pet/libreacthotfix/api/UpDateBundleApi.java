package com.supets.pet.libreacthotfix.api;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.supets.pet.libreacthotfix.bean.AppVersion;
import com.supets.pet.libreacthotfix.bean.DownloadFile;
import com.supets.pet.libreacthotfix.react.AppJSBundleManager;
import com.supets.pet.libreacthotfix.react.Config;
import com.supets.pet.libreacthotfix.utils.FileUtil;
import com.supets.pet.libreacthotfix.utils.VersionSharePreferceUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpDateBundleApi {

    @SuppressLint("StaticFieldLeak")
    private static void upgrade(String url,
                                final DownloadProgressListener process,
                                final DownloadResultListener resultListener) {

        new AsyncTask<String, Integer, DownloadFile>() {
            @Override
            protected DownloadFile doInBackground(String... params) {

                File mDownloadFile = new File(Environment.getExternalStorageDirectory(), Config.MIA_BUNDLE_NAME);
                if (mDownloadFile.exists()) {
                    mDownloadFile.delete();
                }

                Log.v("jsfile====", mDownloadFile.getAbsolutePath());

                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(params[0]).openConnection();
                    //果然2.2版本以上HttpURLConnection跟服务交互采用了"gzip"压缩
                    //getContentLength()为-1 解决方法
                    conn.setRequestProperty("Accept-Encoding", "identity");
                    conn.setConnectTimeout(10000);
                    conn.connect();

                    long fileLength = conn.getContentLength();
                    publishProgress(0);
                    Log.v("======", "0");
                    Log.v("======", "fileLength" + fileLength);

                    BufferedInputStream inputStream = new BufferedInputStream(conn.getInputStream());
                    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(mDownloadFile));
                    byte[] buffer = new byte[1024 * 100];
                    int length = 0;
                    long DownedFileLength = 0;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                        DownedFileLength += length;
                        int mDownedProgress = (int) (DownedFileLength * 1.0 * 100 / fileLength);
                        publishProgress(mDownedProgress);
                        Log.v("======", "mDownedProgress" + mDownedProgress);
                    }
                    inputStream.close();
                    outputStream.close();
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("======", "excep");
                    return null;
                }

                if (mDownloadFile.exists()) {
                    return new DownloadFile(mDownloadFile);
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
        }.execute(url);

    }

    public static void download(final AppVersion version, final JsBundleCallback mCallback) {
        upgrade(version.getDownloadUrl(), new DownloadProgressListener() {
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
                    File bundle = new File(AppJSBundleManager.build().getJSBundleFileDir());
                    if (bundle.exists()) {
                        bundle.delete();
                    }
                    FileUtil.copyFile(file.getFile(), bundle, true);
                    VersionSharePreferceUtils.setBundleVersion(version.getLatestVersion());
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

    public static void downloadAsync(final AppVersion version, final JsBundleCallback mCallback) {
        upgrade(version.getDownloadUrl(), new DownloadProgressListener() {
            @Override
            public void onProgressChanged(int progress) {
                if (mCallback != null) {
                    mCallback.onDownloading(progress);
                }
            }
        }, new DownloadResultListener() {
            @Override
            public void downloadSuccess(DownloadFile file) {
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


    public static void patch() {
        try {

            File mDownloadFile = new File(Environment.getExternalStorageDirectory(), Config.MIA_BUNDLE_NAME);

            if (mDownloadFile.exists()) {

                File bundle = new File(AppJSBundleManager.build().getJSBundleFileDir());
                if (bundle.exists()) {
                    bundle.delete();
                }

                FileUtil.copyFile(mDownloadFile, bundle, true);

                mDownloadFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
