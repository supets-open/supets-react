package com.supets.pet.libreacthotfix.utils;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {


    public static void deleteFile(File file) {
        if (file == null) {
            return;
        }
        if (file.isFile() && file.exists()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteFile(f);
            }
            file.delete();
        }
    }

    // 复制文件
    public static void copyFile(File fromFile, File toFile, Boolean rewrite) throws Exception {
        if (!fromFile.exists()) {
            throw new Exception("msg");
        }
        if (!fromFile.isFile()) {
            throw new Exception("msg");
        }
        if (!fromFile.canRead()) {
            throw new Exception("msg");
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }
        java.io.FileInputStream fosfrom = new java.io.FileInputStream(fromFile);
        FileOutputStream fosto = new FileOutputStream(toFile);
        byte bt[] = new byte[1024];
        int c;
        while ((c = fosfrom.read(bt)) > 0) {
            fosto.write(bt, 0, c); //将内容写到新文件当中
        }
        fosfrom.close();
        fosto.close();
    }

}
