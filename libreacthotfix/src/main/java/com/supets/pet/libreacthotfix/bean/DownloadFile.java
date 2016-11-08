package com.supets.pet.libreacthotfix.bean;

import java.io.File;

public class DownloadFile {
    public DownloadFile(File file) {
        this.file = file;
    }
    private File file;
    public File getFile() {
        return file;
    }

}
