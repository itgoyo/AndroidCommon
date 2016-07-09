package com.ywg.androidtools.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * checked
 */
public class CacheUtil {

    public static void setCache(Context context, String key, String strCache) {
        String encodeName = CipherUtil.encode(key);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            FileUtil.writeFile(context.getExternalCacheDir() + "/" + encodeName, strCache);
        }
    }

    public static String getCache(Context context, String key) {
        String encodeName = CipherUtil.encode(key);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filename = context.getExternalCacheDir() + "/" + encodeName;
            File file = new File(filename);
            return file.exists() ? FileUtil.readFile(filename) : "";
        } else {
            return "";
        }
    }
}