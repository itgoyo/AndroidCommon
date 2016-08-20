package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.ToolbarPanel.ToolbarPanelLayout;

public class ToolbarPanelLayoutActivity extends AppCompatActivity {

    private ToolbarPanelLayout toolbarPanelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_panel_layout);
        toolbarPanelLayout = (ToolbarPanelLayout) findViewById(
                R.id.sliding_down_toolbar_layout);
        TextView openButton = (TextView) findViewById(R.id.open_button);
        TextView closeButton = (TextView) findViewById(R.id.close_button);

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarPanelLayout.openPanel();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarPanelLayout.closePanel();
            }
        });
    }
}
