package com.smippatel35.demo.demo.utils;

public class LibraryUtils {

    public static boolean doesStringValueExist(String str) {
        if (str != null && str.trim().length() > 0){
            return true;
        } else {
            return false;
        }
    }
}
