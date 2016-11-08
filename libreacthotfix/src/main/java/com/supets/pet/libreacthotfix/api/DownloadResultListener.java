package com.supets.pet.libreacthotfix.api;

import com.supets.pet.libreacthotfix.bean.DownloadFile;

public interface DownloadResultListener {
    void downloadSuccess(DownloadFile file);
    void onError(Exception e);
}
