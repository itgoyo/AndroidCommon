package com.ywg.androidcommon.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * 相机操作
 *
 * @author venshine
 */
public class CameraUtil {

    public static final int CAMERA_REQ_CODE = 0x0011;

    /**
     * Open camera
     *
     * @param activity
     * @param path
     */
    public void openCamera(Activity activity, String path) {
        openCamera(activity, path, "IMG_" + System.currentTimeMillis() + ".jpg");
    }

    /**
     * Open camera
     *
     * @param activity
     * @param path
     * @param fileName
     */
    public void openCamera(Activity activity, String path, String fileName) {
        FileUtil.makeDirs(path);
        File cameraFile = new File(path, fileName);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
        activity.startActivityForResult(intent, CAMERA_REQ_CODE);
    }

}