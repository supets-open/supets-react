package com.supets.pet.libreacthotfix.bean;

import com.supets.pet.libreacthotfix.utils.VersionSharePreferceUtils;

public class AppVersion {

    private String downloadUrl;
    private String lastBundleVersion;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getLatestVersion() {
        return lastBundleVersion;
    }

    public void setLastBundleVersion(String lastBundleVersion) {
        this.lastBundleVersion = lastBundleVersion;
    }

    public boolean isUpdate() {
        int last = Integer.parseInt(lastBundleVersion.replace(".", ""));
        int old = Integer.parseInt(VersionSharePreferceUtils.getBundleVersion().replace(".", ""));
        return last > old;
    }

}
