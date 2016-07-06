package com.ywg.androidtools.widget.zoomimageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 可根据点击/多指触控 放大,放小的ImageVIew
 */
public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener
        , View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {

    private static final String TAG = "ZoomImageView";

    /**
     * 是否初始化
     */
    private boolean isInit = false;

    /**
     * 缩放工具
     */
    private Matrix mScaleMatrix;

    /**
     * 初始化时的缩放值
     */
    private float mInitScale;
    /**
     * 双击时的缩放值
     */
    private float mMidScale;
    /**
     * 缩放的最大值
     */
    private float mMaxScale;

    /**
     * 多点手势触 控缩放比率分析器 捕获用户多指触控时缩放的比例，可以判断用户当前是想要放大还是缩小
     */
    private ScaleGestureDetector mScaleGestureDetector;

    //-- 自由移动所需要的全局变量
    /**
     * 记录上一次多点触控的数量
     */
    private int mLastPointereCount;
    //上一次中心点的位置
    private float mLastX;
    private float mLastY;
    //判断是否移动的标准值
    private int mTouchSlop;
    private boolean isCanDrag;
    //是否需要进行左右边界检测
    private boolean isCheckLeftAndRight;
    //是否需要进行进行上下边界检测
    private boolean isCheckTopAndBottom;

    //----双击放大与缩小
    private GestureDetector mGestureDetector;
    private boolean isAutoScale;
    private List<MotionEvent> events;


    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Init
        setScaleType(ScaleType.MATRIX);
        mScaleMatrix = new Matrix();
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isAutoScale || getScale() >= mMaxScale)
                    return true;
                isAutoScale = true;
                float x = e.getX();
                float y = e.getY();

                if (getScale() < mMidScale) {
                    postDelayed(new AutoScaleRunnable(mMidScale, x, y), 16);
                } else {
                    postDelayed(new AutoScaleRunnable(mInitScale, x, y), 16);
                }
                return true;
            }

        });
        setOnTouchListener(this);
        /**
         * getScaledTouchSlop()是一个距离，
         * 表示滑动的时候，手的移动要大于这个距离才开始移动控件。
         * 如果小于这个距离就不触发移动控件
         */
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        events = new ArrayList<>();
    }

    private class AutoScaleRunnable implements Runnable {
        /**
         * 要缩放的目标值
         */
        private float mTargetScale;
        private float x; //缩放的中心点x
        private float y; //缩放的中心点y
        private float tmpScale;

        private final float BIGGER = 1.07f;
        private final float SMALL = 0.93f;

        public AutoScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;

            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
            } else {
                tmpScale = SMALL;
            }
        }

        @Override
        public void run() {
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            float currentScale = getScale();
            if ((tmpScale > 1.0f && currentScale < mTargetScale)
                    || (tmpScale < 1.0f && currentScale > mTargetScale)) {
                postDelayed(this, 16);
            } else {
                float scale = mTargetScale / currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        log("注册了OnGlobalLayoutListener");
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    @SuppressWarnings("deprecation")
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        log("反注册了OnGlobalLayoutListener");
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    /**
     * 全局的布局加载完成后，调用此方法 。 获取ImageView加载完成的图片,使图片居中缩放
     */
    @Override
    public void onGlobalLayout() {
        log("执行了onGlobalLayout| NULL:" + (getDrawable() == null));
        if (getDrawable() == null) return;

        if (!isInit) {
            log("初始化完毕");
            // 得到图片的宽高
            int width = getWidth();
            int height = getHeight();
            float screenWeight = height * 1.0f / width;
            // 拉伸后的宽度.而不是真正图片的宽度
            int imageH = getDrawable().getIntrinsicHeight(); // 图片高度
            int imageW = getDrawable().getIntrinsicWidth(); // 图片宽度
            float imageWeight = imageH * 1.0f / imageW;

            //如果当前屏幕高宽比 大于等于 图片高宽比,就缩放图片
            if (screenWeight > imageWeight) {
                float scale = 1.0f;
                //图片比当前View宽,但是比当前View矮
                if (imageW > width && imageH < height) {
                    scale = width * 1.0f / imageW; //根据宽度缩放
                }

                //图片比当前View窄,但是比当前View高
                if (imageH > height && imageW < width) {
                    scale = height * 1.0f / imageH; //根据高度缩放
                }

                //图片高宽都大于或者小于当前View,那么就根据最小的缩放值来缩放
                if ((imageH > height && imageW > width) || (imageH < height && imageW < width)) {
                    scale = Math.min(width * 1.0f / imageW, height * 1.0f / imageW);
                }

                /**
                 * 设置缩放比率
                 */
                mInitScale = scale;
                mMidScale = mInitScale * 2;
                mMaxScale = mInitScale * 4;

                /**
                 * 把图片移动到中心点去
                 */
                int dx = getWidth() / 2 - imageW / 2;
                int dy = getHeight() / 2 - imageH / 2;

                /**
                 * 设置缩放(全图浏览模式,用最小的缩放比率去查看图片就好了)/移动位置
                 */
                mScaleMatrix.postTranslate(dx, dy);
                mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);

            } else {

                //将宽度缩放至屏幕比例缩放(长图,全图预览)
                float scale = width * 1.0f / imageW;
                /**
                 * 设置缩放比率
                 */
                mMaxScale = scale;
                mMidScale = mMaxScale / 2;
                mInitScale = mMaxScale / 4;

                //因为是长图浏览,所以用最大的缩放比率去加载长图
                mScaleMatrix.postScale(mMaxScale, mMaxScale, 0, 0);
            }

            setImageMatrix(mScaleMatrix);
            isInit = true;
        }
    }


    /**
     * 是否是Debug模式
     */
    private static boolean IS_DEBUG = false;

    /**
     * 打印日志
     *
     * @param value 要打印的日志
     */
    public static void log(String value) {
        if (IS_DEBUG)
            Log.w(TAG, value);
    }

    /**
     * 设置图片URL,自动下载并加载
     * 请注意:
     * 这里的图片下载并加载,未使用缩放!会用原图加载.
     * 请注意OOM异常!
     *
     * @param url 图片URL
     */
   /* public void setImageForUrl(String url) {
        reSetState();
        new ImageDownLoad(getContext(), url)
                .into(this)
                .start();
    }*/

    /**
     * 设置初始化状态为false
     */
    public void reSetState() {
        isInit = false;
        setTag(null);
        mScaleMatrix.reset();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (mGestureDetector.onTouchEvent(motionEvent))
            return true;

        //将触摸事件传递给ScaleGestureDetector
        if (motionEvent.getPointerCount() > 1)
            mScaleGestureDetector.onTouchEvent(motionEvent);


        float x = 0;
        float y = 0;

        int pointerCount = motionEvent.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {
            x += motionEvent.getX(i);
            y += motionEvent.getY(i);
        }

        x /= pointerCount;
        y /= pointerCount;

        if (mLastPointereCount != pointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }

        mLastPointereCount = pointerCount;

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                RectF rectF = getMatrixRectF();
                if ((rectF.width() > getWidth() + 0.01f || (rectF.height() > getHeight() + 0.01f))) {
                    if ((rectF.right != getWidth()) && (rectF.left != 0)) {
                        try {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        } catch (Exception e) {
                            log(e.toString());
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {

                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }

                if (isCanDrag) {
                    RectF rectF = getMatrixRectF();

                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;

                        if (rectF.width() <= getWidth()) {
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }

                        if (rectF.height() <= getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }

                        mScaleMatrix.postTranslate(dx, dy);
                        checkBorderWhenTranslate();
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;

                RectF rect = getMatrixRectF();
                if ((rect.width() > getWidth() + 0.01f || (rect.height() > getHeight() + 0.01f))) {
                    if ((rect.right != getWidth()) && (rect.left != 0)) {
                        try {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        } catch (Exception e) {
                            log(e.toString());
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                mLastPointereCount = 0;
                break;
            }
        }
        return true;
    }

    /**
     * 在移动图片的时候进行边界检查
     */
    private void checkBorderWhenTranslate() {
        RectF rectF = getMatrixRectF();

        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }

        if (rectF.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectF.bottom;
        }


        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }

        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
        setImageMatrix(mScaleMatrix);
    }

    /**
     * 判断是否足以触发移动事件
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        float scaleFactor = detector.getScaleFactor();//获取用户手势判断出来的缩放值
        float scale = getScale();

        /**
         * 没有图片
         */
        if (getDrawable() == null) return true;

        //缩放范围控制
        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scaleFactor * scale < mInitScale) {
                scaleFactor = mInitScale / scale;
            }

            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }

            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }
        return true;
    }

    /**
     * 在缩放的时候进行边界,位置 检查
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();

        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        if (rectF.width() >= width) {
            if (rectF.left > 0)
                deltaX = -rectF.left;
            if (rectF.right < width)
                deltaX = width - rectF.right;
        }

        if (rectF.height() >= height) {
            if (rectF.top > 0)
                deltaY = 0;
            if (rectF.bottom < height)
                deltaY = height - rectF.bottom;
        }

        if (rectF.width() < width) {
            deltaX = width / 2f - rectF.right + rectF.width() / 2;
        }

        if (rectF.height() < height) {
            deltaY = height / 2f - rectF.bottom + rectF.height() / 2;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
        setImageMatrix(mScaleMatrix);
    }

    /**
     * 获取图片放大缩小后的宽高/top/left/right/bottom
     *
     * @return
     */
    private RectF getMatrixRectF() {
        RectF rectF = new RectF();
        Drawable drawable = getDrawable();

        if (drawable != null) {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mScaleMatrix.mapRect(rectF);
        }

        return rectF;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return true; //缩放开始,返回true 用于接收后续时间
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    /**
     * 获取当前的缩放比率
     *
     * @return
     */
    private float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }
}