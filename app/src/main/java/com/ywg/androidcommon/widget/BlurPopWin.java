package com.ywg.androidcommon.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.utils.FastBlurUtil;

/**
 * Created by jiajiewang on 16/7/30.
 */
public class BlurPopWin {
    private RelativeLayout pop_root_layout;
    private CardView pop_layout;
    private TextView title;
    private TextView content;
    private Builder builder;
    private PopupWindow popupWindow;
    private int radius;
    private float touchY;
    private Bitmap localBit;

    public static final String GRAVITY_BOTTOM = "BOTTOM";
    public static final String GRAVITY_CENTER = "CENTER";

    public BlurPopWin(Builder builder) {
        this.builder = builder;
        builder.blurPopWin = initBlurPopWin(builder);
    }

    @UiThread
    public void show(View view) {
        builder.blurPopWin.popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    @UiThread
    public void dismiss() {
        if (builder != null && builder.blurPopWin != null)
            builder.blurPopWin.popupWindow.dismiss();
    }

    /*
    截取屏幕
    * */
    @Nullable
    private Bitmap getIerceptionScreen() {
        // View是你需要截图的View
        View view = builder.activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        builder.activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // 获取屏幕长和高
        int width = builder.activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = builder.activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap bitmap = Bitmap.createBitmap(b, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        bitmap = FastBlurUtil.doBlur(bitmap, radius);
        if (bitmap != null) {
            return bitmap;
        } else {
            return null;
        }
    }

    /*
    初始化
    * */
    @UiThread
    private BlurPopWin initBlurPopWin(final Builder builder) {
        if (builder != null) {

            View rootView = builder.activity.getLayoutInflater().inflate(R.layout.pop_layout, null, false);
            popupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            pop_layout = (CardView) rootView.findViewById(R.id.pop_layout);
            pop_root_layout = (RelativeLayout) rootView.findViewById(R.id.pop_root_layout);
            title = (TextView) rootView.findViewById(R.id.title);
            content = (TextView) rootView.findViewById(R.id.content);

            if (builder.title != null) {
                title.setText(builder.title);
            }

            if (builder.content != null) {
                content.setText(builder.content);
            }

            if (builder.radius != 0) {
                radius = builder.radius;
            } else {
                radius = 5;
            }

            if (builder.titleTextSize != 0) {
                title.setTextSize(builder.titleTextSize);
            }

            if (builder.contentTextSize != 0) {
                content.setTextSize(builder.contentTextSize);
            }

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (builder.showAtLocationType.equals(GRAVITY_CENTER)) {
                lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            } else {
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            }
            pop_layout.setLayoutParams(lp);

            if (localBit == null) {
                localBit = getIerceptionScreen();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                //Android系统大于等于API16，使用setBackground
                pop_root_layout.setBackground(new BitmapDrawable(localBit));
            } else {
                //Android系统小于API16，使用setBackground
                pop_root_layout.setBackgroundDrawable(new BitmapDrawable(localBit));
            }

            pop_root_layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touchY = motionEvent.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            if (builder.showAtLocationType.equals(GRAVITY_CENTER)) {
                                if (touchY < pop_layout.getTop() || touchY > pop_layout.getBottom()) {
                                    popupWindow.dismiss();
                                }
                            } else if (builder.showAtLocationType.equals(GRAVITY_BOTTOM)) {
                                if (touchY < pop_layout.getTop()) {
                                    popupWindow.dismiss();
                                }
                            }

                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
        } else {
            throw new NullPointerException("---> BlurPopWin ---> initBlurPopWin --->builder=null");
        }
        pop_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (builder.popupCallback != null) {
                    builder.popupCallback.onClick(BlurPopWin.this);
                }
            }
        });
        return this;
    }

    public static class Builder {


        protected BlurPopWin blurPopWin;
        protected int titleTextSize, contentTextSize;
        protected Activity activity;
        protected Context context;
        protected PopupCallback popupCallback;
        protected int radius;
        protected String title, content;
        protected boolean isCancelable;
        protected String showAtLocationType = GRAVITY_CENTER;

        public Builder(@NonNull Context context) {
            this.activity = (Activity) context;
            this.context = context;
            this.isCancelable = true;
        }

        public Builder onClick(PopupCallback popupCallback) {
            this.popupCallback = popupCallback;
            return this;
        }

        /*
        * 设置标题
        * */
        public Builder setTitle(@StringRes int titleRes) {
            setTitle(this.context.getString(titleRes));
            return this;
        }


        public Builder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setTitleTextSize(int size) {
            this.titleTextSize = size;
            return this;
        }

        public Builder setContentTextSize(int size) {
            this.contentTextSize = size;
            return this;
        }

        /*
        * 设置主文内容
        * */
        public Builder setContent(@StringRes int contentRes) {
            setContent(this.context.getString(contentRes));
            return this;
        }

        /*
        * 设置主文内容
        * */
        public Builder setContent(@NonNull String content) {
            this.content = content;
            return this;
        }

        /*
        * 默认居中,手动设置了才在最下面
        * */
        public Builder setshowAtLocationType(int type) {
            if (type == 0) {
                this.showAtLocationType = GRAVITY_CENTER;
            } else if (type == 1) {
                this.showAtLocationType = GRAVITY_BOTTOM;
            }

            return this;
        }

        @UiThread
        public BlurPopWin build() {
            return new BlurPopWin(this);
        }

        @UiThread
        public BlurPopWin show(View view) {
            BlurPopWin blurPopWin = build();
            blurPopWin.show(view);

            return blurPopWin;
        }

    }

    public interface PopupCallback {

        void onClick(@NonNull BlurPopWin blurPopWin);
    }
}