package com.ywg.androidcommon.recyclerview.multi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.recyclerview.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

public class MultiActivity extends AppCompatActivity {

    private RecyclerView multiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);

        multiList = (RecyclerView) findViewById(R.id.multi_list);
        List<MultiEntity> entityList = new ArrayList<>();
        MultiAdapter multiAdapter = new MultiAdapter(this);
        for (int i = 0; i < 50; i++) {
            if (i%3 == 0) {
                MultiText text = new MultiText();
                text.setContent("MultiText-" + i);
                entityList.add(text);
            } else {
                MultiImage image = new MultiImage();
                image.setResId(R.mipmap.ic_launcher);
                entityList.add(image);
            }
        }
        multiAdapter.fillList(entityList);

        multiList.setLayoutManager(new LinearLayoutManager(this));
        multiList.addItemDecoration(new DividerDecoration(this, DividerDecoration.VERTICAL_LIST));
        multiList.setAdapter(multiAdapter);
    }
}