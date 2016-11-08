package com.supets.pet.libreacthotfix.api;

public interface JsBundleCallback {
    void onDownloading(int progress);
    void onError(Exception e);
    void onNoUpdate();
    void onUpdateSuccess();
}