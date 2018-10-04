package com.bolotin.trata.utils;

public class CommonUtils {

    public static String replaceUnderscores(String str) {
        return str.replace("_", "-");
    }

    public static String replaceHyphen(String str) {
        return str.replace("-", "_");
    }
}
