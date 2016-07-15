package com.ywg.androidcommon.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.photopicker.ImagePicker;
import com.ywg.androidcommon.photopicker.bean.ImageItem;
import com.ywg.androidcommon.photopicker.loader.GlideImageLoader;
import com.ywg.androidcommon.photopicker.ui.ImageGridActivity;

import java.util.ArrayList;
import java.util.List;

public class PhotoPickerActivity extends AppCompatActivity implements View.OnClickListener{

    private GridView gridView;
    private ImagePicker imagePicker;
    private ArrayList<ImageItem> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);
        findViewById(R.id.btn_single_picker).setOnClickListener(this);
        findViewById(R.id.btn_multi_picker).setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.grid_view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_single_picker:
                imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new GlideImageLoader());
                imagePicker.setShowCamera(true);
                imagePicker.setSelectLimit(1);
                imagePicker.setCrop(false);
                startActivityForResult(new Intent(this, ImageGridActivity.class), 100);
                break;
            case R.id.btn_multi_picker:
                imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new GlideImageLoader());
                imagePicker.setShowCamera(true);
                imagePicker.setSelectLimit(9);
                imagePicker.setCrop(false);
                startActivityForResult(new Intent(this, ImageGridActivity.class), 101);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && (requestCode == 100 || requestCode == 101)) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                MyAdapter adapter = new MyAdapter(images);
                gridView.setAdapter(adapter);
            } else {
                Toast.makeText(PhotoPickerActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class MyAdapter extends BaseAdapter {

        private List<ImageItem> items;

        public MyAdapter(List<ImageItem> items) {
            this.items = items;
        }

        public void setData(List<ImageItem> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public ImageItem getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            int size = gridView.getWidth() / 3;
            if (convertView == null) {
                imageView = new ImageView(PhotoPickerActivity.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size);
                imageView.setLayoutParams(params);
                imageView.setBackgroundColor(Color.parseColor("#88888888"));
            } else {
                imageView = (ImageView) convertView;
            }
            imagePicker.getImageLoader().displayImage(PhotoPickerActivity.this, getItem(position).path, imageView, size, size);
            return imageView;
        }
    }
}
