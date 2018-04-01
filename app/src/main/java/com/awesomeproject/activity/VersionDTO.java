package com.awesomeproject.activity;

public class VersionDTO {

    public int code;
    public String alert;
    public Version  content;

    public static class Version{
        public String url;
        public String text;
        public String version;
    }

}
