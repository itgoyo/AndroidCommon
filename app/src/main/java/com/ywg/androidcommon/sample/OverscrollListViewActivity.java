package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.OverscrollListView;

public class OverscrollListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overscroll_list_view);

        OverscrollListView listView = (OverscrollListView)findViewById(R.id.list);

        // Defined Array values to show in ListView
        String[] values = new String[] {
                "List item 1",
                "List item 2",
                "List item 3",
                "List item 4",
                "List item 5",
                "List item 6",
                "List item 7",
                "List item 8"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
    }
}
