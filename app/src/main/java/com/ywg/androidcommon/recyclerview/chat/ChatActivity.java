package com.ywg.androidcommon.recyclerview.chat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.recyclerview.DividerDecoration;
import com.ywg.androidcommon.recyclerview.MockService;

public class ChatActivity extends AppCompatActivity {

    private SwipeRefreshLayout chatSrl;
    private RecyclerView chatRv;
    private ChatAdapter chatAdapter;
    private MockService mockService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initData();

        initView();
    }

    private void initData() {
        mockService = new MockService();
        chatAdapter = new ChatAdapter(this);
        chatAdapter.fillList(mockService.getChatMsgList());
    }

    private void initView() {
        chatSrl = (SwipeRefreshLayout) findViewById(R.id.chat_srv);
        chatSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMoreData();
            }
        });
        chatSrl.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLACK, Color.BLUE);

        chatRv = (RecyclerView) findViewById(R.id.chat_rv);
        chatRv.setLayoutManager(new LinearLayoutManager(this));
        chatRv.addItemDecoration(new DividerDecoration(this, DividerDecoration.VERTICAL_LIST));
        chatRv.setAdapter(chatAdapter);
    }

    private void loadMoreData() {
        chatAdapter.appendList(mockService.getChatMsgList());
        chatAdapter.notifyDataSetChanged();
        chatSrl.setRefreshing(false);
    }
}