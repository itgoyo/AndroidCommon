package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.MarqueeView;

import java.util.ArrayList;
import java.util.List;

public class MarqueeViewActivity extends AppCompatActivity {

    private MarqueeView marqueeView;
    private MarqueeView marqueeView1;
    private MarqueeView marqueeView2;
    private MarqueeView marqueeView3;
    private MarqueeView marqueeView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee_view);

        marqueeView = (MarqueeView) findViewById(R.id.marqueeView);
        marqueeView1 = (MarqueeView) findViewById(R.id.marqueeView1);
        marqueeView2 = (MarqueeView) findViewById(R.id.marqueeView2);
        marqueeView3 = (MarqueeView) findViewById(R.id.marqueeView3);
        marqueeView4 = (MarqueeView) findViewById(R.id.marqueeView4);

        List<String> info = new ArrayList<>();
        info.add("1. 大家好，我是孙福生。");
        info.add("2. 欢迎大家关注我哦！");
        info.add("3. GitHub帐号：sfsheng0322");
        info.add("4. 新浪微博：孙福生微博");
        info.add("5. 个人博客：sunfusheng.com");
        info.add("6. 微信公众号：孙福生");
        marqueeView.startWithList(info);

        marqueeView1.startWithText(getString(R.string.marquee_texts));
        marqueeView2.startWithText(getString(R.string.marquee_texts));
        marqueeView3.startWithText(getString(R.string.marquee_texts));
        marqueeView4.startWithText(getString(R.string.marquee_text));

        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Toast.makeText(getApplicationContext(), textView.getText() + "", Toast.LENGTH_SHORT).show();
            }
        });

        marqueeView1.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Toast.makeText(getApplicationContext(), String.valueOf(marqueeView1.getPosition()) + ". " + textView.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
