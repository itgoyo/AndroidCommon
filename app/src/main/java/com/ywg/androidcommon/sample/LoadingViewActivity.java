package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ywg.androidcommon.widget.LoadingView.LVCircular;
import com.ywg.androidcommon.widget.LoadingView.LVCircularCD;
import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.LoadingView.LVCircularJump;
import com.ywg.androidcommon.widget.LoadingView.LVCircularRing;
import com.ywg.androidcommon.widget.LoadingView.LVCircularSmile;
import com.ywg.androidcommon.widget.LoadingView.LVCircularZoom;
import com.ywg.androidcommon.widget.LoadingView.LVEatBeans;
import com.ywg.androidcommon.widget.LoadingView.LVNews;
import com.ywg.androidcommon.widget.LoadingView.LVPlayBall;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingViewActivity extends AppCompatActivity {

    LVPlayBall mLVPlayBall;
    LVCircularRing mLVCircularRing;
    LVCircular mLVCircular;
    LVCircularJump mLVCircularJump;
    LVCircularZoom mLVCircularZoom;
    LVEatBeans mLVEatBeans;
    LVCircularCD mLVCircularCD;
    LVCircularSmile mLVCircularSmile;
    LVNews mLVNews;

    int mValueLVNews = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_view);
        mLVPlayBall = (LVPlayBall) findViewById(R.id.lv_playball);
        mLVCircularRing = (LVCircularRing) findViewById(R.id.lv_circularring);
        mLVCircular = (LVCircular) findViewById(R.id.lv_circular);
        mLVCircularJump = (LVCircularJump) findViewById(R.id.lv_circularJump);
        mLVCircularZoom = (LVCircularZoom) findViewById(R.id.lv_circularZoom);
        mLVEatBeans = (LVEatBeans) findViewById(R.id.lv_eatBeans);
        mLVCircularCD = (LVCircularCD) findViewById(R.id.lv_circularCD);
        mLVCircularSmile = (LVCircularSmile) findViewById(R.id.lv_circularSmile);

        mLVNews = (LVNews) findViewById(R.id.lv_news);
    }

    public void startAnim(View v) {

        stopAll();
        if (v instanceof LVCircular) {
            ((LVCircular) v).startAnim();
        } else if (v instanceof LVCircularCD) {
            ((LVCircularCD) v).startAnim();
        } else if (v instanceof LVCircularSmile) {
            ((LVCircularSmile) v).startAnim();
        } else if (v instanceof LVCircularRing) {
            ((LVCircularRing) v).startAnim();
        } else if (v instanceof LVCircularZoom) {
            ((LVCircularZoom) v).startAnim();
        } else if (v instanceof LVCircularJump) {
            ((LVCircularJump) v).startAnim();
        } else if (v instanceof LVEatBeans) {
            ((LVEatBeans) v).startAnim();
        } else if (v instanceof LVPlayBall) {
            ((LVPlayBall) v).startAnim();
        }else if (v instanceof LVNews) {
//            ((LVNews) v).startAnim();
            startLVNewsAnim();
        }

    }

    public void startAnimAll(View v) {
        mLVCircular.startAnim();
        mLVCircularRing.startAnim();
        mLVPlayBall.startAnim();
        mLVCircularJump.startAnim();
        mLVCircularZoom.startAnim();
        mLVEatBeans.startAnim();
        mLVCircularCD.startAnim();
        mLVCircularSmile.startAnim();
        startLVNewsAnim();
    }


    public void stopAnim(View v) {
        stopAll();

    }

    private void stopAll() {
        mLVCircular.stopAnim();
        mLVPlayBall.stopAnim();
        mLVCircularJump.stopAnim();
        mLVCircularZoom.stopAnim();
        mLVCircularRing.stopAnim();
        mLVEatBeans.stopAnim();
        mLVCircularCD.stopAnim();
        mLVCircularSmile.stopAnim();
        stopLVNewsAnim();
    }

    public Timer mTimerLVNews = new Timer();// 定时器

    private void startLVNewsAnim() {
        mValueLVNews = 0;
        if (mTimerLVNews != null) {
            mTimerLVNews.cancel();
        }
        mTimerLVNews = new Timer();
        timerTaskLVNews();
    }

    private void stopLVNewsAnim() {
        mLVNews.stopAnim();
        if (mTimerLVNews != null) {
            mTimerLVNews.cancel();
        }
    }

    public void timerTaskLVNews() {
        mTimerLVNews.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mValueLVNews < 100) {
                    mValueLVNews++;
                    Message msg = mHandle.obtainMessage(1);
                    msg.arg1 = mValueLVNews;
                    mHandle.sendMessage(msg);
                } else {
                    mTimerLVNews.cancel();
                }
            }
        }, 0, 10);
    }

    private Handler mHandle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mLVNews.setValue(msg.arg1);
            }
        }
    };

}
