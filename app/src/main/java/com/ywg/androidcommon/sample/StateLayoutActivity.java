package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.StateLayout;

public class StateLayoutActivity extends AppCompatActivity implements View.OnClickListener{


    private StateLayout mStateLayout;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_layout);
        mInflater = LayoutInflater.from(this);

        mStateLayout = (StateLayout) findViewById(R.id.sl_layout_state);
        View contentView = mInflater.inflate(R.layout.state_layout_view_content,mStateLayout,false);
        View emptyView = mInflater.inflate(R.layout.state_layout_view_empty,mStateLayout,false);
        View errorView = mInflater.inflate(R.layout.state_layout_view_error,mStateLayout,false);
        View loadingView = mInflater.inflate(R.layout.state_layout_view_loading,mStateLayout,false);

        mStateLayout.setEmptyView(emptyView)
                .setContentView(contentView)
                .setErrorView(errorView)
                .setLoadingView(loadingView)
                .initState(StateLayout.VIEW_LOADING);

        findViewById(R.id.btn_content).setOnClickListener(this);
        findViewById(R.id.btn_empty).setOnClickListener(this);
        findViewById(R.id.btn_error).setOnClickListener(this);
        findViewById(R.id.btn_loading).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_content:
                mStateLayout.setState(StateLayout.VIEW_CONTENT);
                break;

            case R.id.btn_empty:
                mStateLayout.setState(StateLayout.VIEW_EMPTY);
                break;

            case R.id.btn_error:
                mStateLayout.setState(StateLayout.VIEW_ERROR);
                break;

            case R.id.btn_loading:
                mStateLayout.setState(StateLayout.VIEW_LOADING);
                break;
        }
    }
}
