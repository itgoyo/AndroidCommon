package com.ywg.androidtools.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * checked
 */
public class CacheUtils {

    public static void setCache(Context context, String key, String strCache) {
        String encodeName = CipherUtils.encode(key);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            FileUtils.writeFile(context.getExternalCacheDir() + "/" + encodeName, strCache);
        }
    }

    public static String getCache(Context context, String key) {
        String encodeName = CipherUtils.encode(key);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filename = context.getExternalCacheDir() + "/" + encodeName;
            File file = new File(filename);
            return file.exists() ? FileUtils.readFile(filename) : "";
        } else {
            return "";
        }
    }
}