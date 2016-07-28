package com.ywg.androidcommon.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.utils.systemimageselector.ImageCropper;
import com.ywg.androidcommon.utils.systemimageselector.ImageFileSelector;

import java.io.File;

public class SystemImageSelectorActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;
    private TextView mTvPath;
    private ImageFileSelector mImageFileSelector;

    private EditText mEtWidth;
    private EditText mEtHeight;

    private ImageCropper mImageCropper;
    private Button mBtnCrop;

    private File mCurrentSelectFile;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_image_selector);

        ImageFileSelector.setDebug(true);

        findViewById(R.id.btn_from_sdcard).setOnClickListener(this);
        findViewById(R.id.btn_from_camera).setOnClickListener(this);
        findViewById(R.id.btn_crop).setOnClickListener(this);

        mImageView = (ImageView) findViewById(R.id.iv_image);
        mTvPath = (TextView) findViewById(R.id.tv_path);
        mEtWidth = (EditText) findViewById(R.id.et_width);
        mEtHeight = (EditText) findViewById(R.id.et_height);
        mBtnCrop = (Button) findViewById(R.id.btn_crop);

        //初始化
        mImageFileSelector = new ImageFileSelector(this);
        mImageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onSuccess(final String file) {
                // 选取图片成功
                if (!TextUtils.isEmpty(file)) {
                    loadImage(file);
                    mCurrentSelectFile = new File(file);
                    mBtnCrop.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(SystemImageSelectorActivity.this, "select image file error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError() {
                // 选取图片失败
                Toast.makeText(SystemImageSelectorActivity.this, "select image file error", Toast.LENGTH_LONG).show();
            }
        });

        //初始化
        mImageCropper = new ImageCropper(this);
        mImageCropper.setCallback(new ImageCropper.ImageCropperCallback() {
            @Override
            public void onCropperCallback(ImageCropper.CropperResult result, File srcFile, File outFile) {
                mCurrentSelectFile = null;
                mBtnCrop.setVisibility(View.GONE);
                if (result == ImageCropper.CropperResult.success) {
                    // 成功
                    loadImage(outFile.getPath());
                } else if (result == ImageCropper.CropperResult.error_illegal_input_file) {
                    // 输入的文件失败
                    Toast.makeText(SystemImageSelectorActivity.this, "input file error", Toast.LENGTH_LONG).show();
                } else if (result == ImageCropper.CropperResult.error_illegal_out_file) {
                    // 输出文件失败
                    Toast.makeText(SystemImageSelectorActivity.this, "output file error", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void loadImage(final String file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = BitmapFactory.decodeFile(file);
                File imageFile = new File(file);
                final StringBuilder builder = new StringBuilder();
                builder.append("path: ");
                builder.append(file);
                builder.append("\n\n");
                builder.append("length: ");
                builder.append((int) (imageFile.length() / 1024d));
                builder.append("KB");
                builder.append("\n\n");
                builder.append("image size: (");
                builder.append(bitmap.getWidth());
                builder.append(", ");
                builder.append(bitmap.getHeight());
                builder.append(")");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                        mTvPath.setText(builder.toString());
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageFileSelector.onActivityResult(requestCode, resultCode, data);
        mImageCropper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mImageFileSelector.onSaveInstanceState(outState);
        mImageCropper.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mImageFileSelector.onRestoreInstanceState(savedInstanceState);
        mImageCropper.onRestoreInstanceState(savedInstanceState);
    }

    // Android 6.0的动态权限
    @SuppressWarnings("NullableProblems")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mImageFileSelector.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initImageFileSelector() {
        int w = 0;
        if (!TextUtils.isEmpty(mEtWidth.getText().toString())) {
            w = Integer.parseInt(mEtWidth.getText().toString());
        }
        int h = 0;
        if (!TextUtils.isEmpty(mEtHeight.getText().toString())) {
            h = Integer.parseInt(mEtHeight.getText().toString());
        }
        // 设置输出文件的尺寸
        mImageFileSelector.setOutPutImageSize(w, h);
        // 设置保存图片的质量 0到100
        mImageFileSelector.setQuality(80);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_from_camera: {
                initImageFileSelector();
                // 拍照选取
                mImageFileSelector.takePhoto(this);
                break;
            }
            case R.id.btn_from_sdcard: {
                initImageFileSelector();
                // 从文件选取
                mImageFileSelector.selectImage(this);
                break;
            }
            case R.id.btn_crop: {
                if (mCurrentSelectFile != null) {
                    // 设置输出文件的尺寸
                    mImageCropper.setOutPut(800, 800);
                    // 设置输出文件的宽高比
                    mImageCropper.setOutPutAspect(1, 1);
                    // 设置是否缩放到指定的尺寸
                    mImageCropper.setScale(true);
                    mImageCropper.cropImage(mCurrentSelectFile);
                }
                break;
            }
        }
    }
}