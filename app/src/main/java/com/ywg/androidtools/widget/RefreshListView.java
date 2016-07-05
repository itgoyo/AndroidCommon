package com.ywg.androidtools.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ywg.androidtools.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RefreshListView extends ListView implements AbsListView.OnScrollListener, View.OnClickListener {
    public static final String TAG = RefreshListView.class.getSimpleName();
    // 下拉刷新
    public static final int STATE_PULL_REFRESH = 0;
    // 松开刷新
    public static final int STATE_RELEASE_REFRESH = 1;
    // 正在刷新
    public static final int STATE_REFRESHING = 2;
    private FrameLayout mHeaderViewCustomLayout;
    private ProgressBar mHeaderViewPb;
    private ImageView mHeaderViewArrow;
    private TextView mHeaderViewState;
    private TextView mHeaderViewUpdateTime;
    private RelativeLayout mHeaderViewRefreshLayout;
    private int mInitPaddingTop;
    private float mDownY;
    private float mDownX;
    private View mHeaderView;

    public int mCurState = STATE_PULL_REFRESH;                        //默认是下拉刷新
    private RotateAnimation mUp2DownAnimation;
    private RotateAnimation mDown2UpAnimation;
    private int mRefreshLayoutHeight;

    private boolean isRefreshLayoutHiden = true;
    private Drawable mSel;
    private OnItemClickListener mListener;

    private boolean isLoadMoreing = false;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addHeaderView(context);
        addFooterView(context);
    }

    private void addFooterView(Context context) {
        View footerView = View.inflate(context, R.layout.inflate_refreshlistview_footerview, null);
        //找孩子
        mFooterViewState = (TextView) footerView.findViewById(R.id.refreshlistview_footerview_state);
        mFooterViewPb = (ProgressBar) footerView.findViewById(R.id.refreshlistview_footerview_pb);
        //加入到listView中的footerView
        this.addFooterView(footerView);

        //监听ListView的滚动
        this.setOnScrollListener(this);
        //设置footerView的点击事件
        footerView.setOnClickListener(this);
    }

    public void addHeaderView(Context context) {
        mHeaderView = View.inflate(context, R.layout.inflate_listview_refresh_headerview, null);

        //先找出所有的孩子

        mHeaderViewPb = (ProgressBar) mHeaderView.findViewById(R.id.refreshHeader_pb);
        mHeaderViewArrow = (ImageView) mHeaderView.findViewById(R.id.refreshHeader_arrow);
        mHeaderViewState = (TextView) mHeaderView.findViewById(R.id.refreshHeader_tv_state);
        mHeaderViewUpdateTime = (TextView) mHeaderView.findViewById(R.id.refreshHeader_tv_updatetime);
        mHeaderViewRefreshLayout = (RelativeLayout) mHeaderView.findViewById(R.id.refreshHeader_rl_root);
        mHeaderViewCustomLayout = (FrameLayout) mHeaderView.findViewById(R.id.refreshHeader_fl_customHeaderView);

        //给listView添加头
        this.addHeaderView(mHeaderView);

        //计算mHeaderViewRefreshLayout的高度
        /*
		 MeasureSpec.UNSPECIFIED-->不确定,该分配多少就分配多少 wrap_content
		 MeasureSpec.AT_MOST -->至多
		 MeasureSpec.EXACTLY-->确切的,明确希望父容器分配多少 match_parent 100dp 100px
		 */
		/*int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		mHeaderViewRefreshLayout.measure(widthMeasureSpec, heightMeasureSpec);
		*/
        mHeaderViewRefreshLayout.measure(0, 0);
        mRefreshLayoutHeight = mHeaderViewRefreshLayout.getMeasuredHeight();
        //		Toast.makeText(context, "" + measuredHeight, 0).show();

        //隐藏下拉刷新头布局-->通过设置对应父容器(headerView)的padding进行隐藏
        mInitPaddingTop = -mRefreshLayoutHeight;
        mHeaderView.setPadding(0, mInitPaddingTop, 0, 0);
    }

    /**
     * 添加自定义的头布局
     *
     * @param customHeader
     * @Time 2016-4-8 下午5:02:09
     */
    public void addCustomHeaderView(View customHeader) {
        mHeaderViewCustomLayout.addView(customHeader);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://注意,如果拖动的是小ViewPager,对应的不会来到ACTION_DOWN
                Log.i(TAG, "ACTION_DOWN");
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE");
                float moveX = ev.getRawX();
                float moveY = ev.getRawY();
                int diffX = (int) (moveX - mDownX + .5f);

                if (mDownX == 0 && mDownY == 0) {//拖动开始的地方是小ViewPager
                    mDownX = moveX;
                    mDownY = moveY;
                }

                int diffY = (int) (moveY - mDownY + .5f);//垂直拖动距离
                /**
                 根据具体情况,拦截和处理action_move
                 下拉刷新布局左下角.Y>=ListView左上角.Y并且垂直拖动距离>0
                 */
                //1.得到下拉刷新布局左上角.Y
                int[] mHeaderViewRefreshLayoutLocationArr = new int[2];
                mHeaderViewRefreshLayout.getLocationOnScreen(mHeaderViewRefreshLayoutLocationArr);
                int mHeaderViewRefreshLayoutTopY = mHeaderViewRefreshLayoutLocationArr[1];
                int mHeaderViewRefreshLayoutBottomY = mHeaderViewRefreshLayoutTopY + mRefreshLayoutHeight;

                int[] refreshListViewLocationArr = new int[2];
                this.getLocationOnScreen(refreshListViewLocationArr);
                int refreshListViewTopY = refreshListViewLocationArr[1];
                if (mHeaderViewRefreshLayoutBottomY >= refreshListViewTopY && diffY > 0) {
                    //清除按下去的效果
                    super.setSelector(new ColorDrawable(Color.TRANSPARENT));
                    //清除item的点击事件
                    super.setOnItemClickListener(null);

                    if (isRefreshLayoutHiden) {
                        //重置downY
                        mDownY = moveY;
                        isRefreshLayoutHiden = false;
                    }

                    diffY = (int) (moveY - mDownY + .5f);//垂直拖动距离

                    //自己拦截和处理了action_move事件,通过修改paddingTop值产生了滚动效果
                    int curPaddingTop = mInitPaddingTop + diffY;

                    //控制下拉刷新头布局的显示隐藏
                    mHeaderView.setPadding(0, curPaddingTop, 0, 0);

                    /**
                     action_move-->padingTop的正负值
                     1.下拉刷新-->padingTop<0
                     2.松开刷新-->padingTop>=0
                     */
                    if (curPaddingTop < 0) {//下拉刷新
                        //记录状态
                        if (mCurState != STATE_PULL_REFRESH) {
                            mCurState = STATE_PULL_REFRESH;
                            Log.i(TAG, "状态-->下拉刷新");

                            //刷新ui
                            refreshHeaderViewByState();
                        }
                    } else {//松开刷新
                        //记录状态
                        if (mCurState != STATE_RELEASE_REFRESH) {
                            mCurState = STATE_RELEASE_REFRESH;
                            Log.i(TAG, "状态-->松开刷新");
                            //刷新ui
                            refreshHeaderViewByState();
                        }

                    }
                    return true;
                } else {
                    return super.onTouchEvent(ev);
                }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "ACTION_UP ACTION_CANCEL");

                if (isRefreshLayoutHiden) {//下拉刷新头布局已经隐藏的时候才去还原
                    Log.i(TAG, "还原效果");
                    //还原按下去效果
                    super.setSelector(mSel);
                    //还原item的点击事件
                    super.setOnItemClickListener(mListener);
                }
                /**
                 action_up-->可以通过padingTop,但是通过当前状态
                 1.下拉刷新-->curState==下拉刷新
                 2.正在刷新-->curState==松开刷新
                 */
                //还原相关的值
                mDownY = 0;
                isRefreshLayoutHiden = true;

                if (mCurState == STATE_PULL_REFRESH) {
                    mCurState = STATE_PULL_REFRESH;
                    //刷新ui
                    refreshHeaderViewByState();

                    //修改下拉刷新头布局的显示隐藏
                    //				mHeaderView.setPadding(0, mInitPaddingTop, 0, 0);
                    changeHeaderViewPaddingTopAnimation(mHeaderView.getPaddingTop(), mInitPaddingTop);

                } else if (mCurState == STATE_RELEASE_REFRESH) {
                    mCurState = STATE_REFRESHING;
                    //刷新ui
                    refreshHeaderViewByState();

                    //修改下拉刷新头布局的显示隐藏
                    //				mHeaderView.setPadding(0, 0, 0, 0);
                    changeHeaderViewPaddingTopAnimation(mHeaderView.getPaddingTop(), 0);

                    //通知外界现在状态已经变成了正在刷新
                    if (mOnRefreshListener != null) {
                        //3.在需要传值的地方,通过接口对象,调用接口方法
                        mOnRefreshListener.onRefresh(this);
                    }
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);//listview根据action_move产生滚动效果
    }


    private void refreshHeaderViewByState() {
        switch (mCurState) {
            case STATE_PULL_REFRESH://状态变化-->下拉刷新
                //进度
                mHeaderViewPb.setVisibility(View.INVISIBLE);
                //箭头
                mHeaderViewArrow.setVisibility(View.VISIBLE);
                if (mUp2DownAnimation == null) {
                    mUp2DownAnimation =
                            new RotateAnimation(180, 360, RotateAnimation.RELATIVE_TO_SELF, .5f,
                                    RotateAnimation.RELATIVE_TO_SELF, .5f);
                    mUp2DownAnimation.setDuration(400);
                    mUp2DownAnimation.setFillAfter(true);
                }
                mHeaderViewArrow.startAnimation(mUp2DownAnimation);

                //下拉刷新的状态
                mHeaderViewState.setText("下拉刷新");
                //下拉刷新的更新时间
                //继续保持之前的文本
                break;
            case STATE_RELEASE_REFRESH://状态变化-->松开刷新
                //进度
                mHeaderViewPb.setVisibility(View.INVISIBLE);
                //箭头
                mHeaderViewArrow.setVisibility(View.VISIBLE);
                if (mDown2UpAnimation == null) {
                    mDown2UpAnimation =
                            new RotateAnimation(0, 180, RotateAnimation.RELATIVE_TO_SELF, .5f,
                                    RotateAnimation.RELATIVE_TO_SELF, .5f);
                    mDown2UpAnimation.setDuration(400);
                    mDown2UpAnimation.setFillAfter(true);
                }
                mHeaderViewArrow.startAnimation(mDown2UpAnimation);

                //下拉刷新的状态
                mHeaderViewState.setText("松开刷新");
                //下拉刷新的更新时间
                //继续保持之前的文本
                break;
            case STATE_REFRESHING://状态变化-->正在刷新
                //进度
                mHeaderViewPb.setVisibility(View.VISIBLE);
                //箭头
                mHeaderViewArrow.setVisibility(View.INVISIBLE);
                mHeaderViewArrow.clearAnimation();
                //下拉刷新的状态
                mHeaderViewState.setText("正在刷新");

                //下拉刷新的更新时间
                mHeaderViewUpdateTime.setText("刷新时间:" + getUpdateTime());
                break;

            default:
                break;
        }
    }


    private String getUpdateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @Override
    public void setSelector(Drawable sel) {
        Log.i(TAG, "setSelector");
        mSel = sel;
        super.setSelector(sel);
    }

    @Override
    public void setOnItemClickListener(android.widget.AdapterView.OnItemClickListener listener) {
        //		Log.i(TAG, "setOnItemClickListener+" + listener.toString());
        mListener = listener;
        //		Log.i(TAG, "mListener+" + mListener.toString());
        super.setOnItemClickListener(listener);
    }


    public void changeHeaderViewPaddingTopAnimation(int start, int end) {
        //		mHeaderView.setPadding(left, top, right, bottom);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.setDuration(400);
        valueAnimator.start();

        //得到动画执行过程中的渐变值
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int paddingTop = (Integer) animator.getAnimatedValue();
                mHeaderView.setPaddingRelative(0, paddingTop, 0, 0);
            }
        });
    }

    //1.定义接口以及接口方法
    public interface OnRefreshListener {
        void onRefresh(RefreshListView refreshListView);

        void onLoadMore(RefreshListView refreshListView);
    }

    //2.在需要传值的模块里面,定义接口的对象
    OnRefreshListener mOnRefreshListener;
    private ProgressBar mFooterViewPb;
    private TextView mFooterViewState;
    private boolean mLoadMoreSuccess;

    //4.暴露公共的放置对接口对象进行赋值
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    /**
     * 开启下拉刷新效果
     *
     * @Time 2016-4-9 下午3:47:24
     */
    public void startRefresh() {
        mCurState = STATE_REFRESHING;
        //刷新ui
        refreshHeaderViewByState();
        //通过修改paddingTop值控制下拉刷新头布局的显示隐藏
        //		mHeaderView.setPadding(0, 0, 0, 0);
        changeHeaderViewPaddingTopAnimation(mHeaderView.getPaddingTop(), 0);
    }

    /**
     * 结束下拉刷新效果
     *
     * @Time 2016-4-9 下午3:47:31
     */
    public void stopRefresh() {
        mCurState = STATE_PULL_REFRESH;
        //刷新ui
        refreshHeaderViewByState();

        //通过修改paddingTop值控制下拉刷新头布局的显示隐藏
        //		mHeaderView.setPadding(0, mInitPaddingTop, 0, 0);
        changeHeaderViewPaddingTopAnimation(mHeaderView.getPaddingTop(), mInitPaddingTop);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (this.getLastVisiblePosition() == getCount() - 1) {
            if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
                if (mOnRefreshListener != null) {
                    if (isLoadMoreing) {//正在加载更多
                        return;
                    }
                    //通知外界
                    mOnRefreshListener.onLoadMore(this);
                    isLoadMoreing = true;
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // TODO

    }


    public void setHasLoadMore(boolean hasLoadMore) {
        if (hasLoadMore) {//有加载更多
            mFooterViewPb.setVisibility(View.VISIBLE);
            mFooterViewState.setText("正在加载更多");
        } else {
            mFooterViewPb.setVisibility(View.GONE);
            mFooterViewState.setText("没有加载更多");
        }
    }


    public void stopLoadMore(boolean loadMoreSuccess) {
        mLoadMoreSuccess = loadMoreSuccess;
        isLoadMoreing = false;
        if (loadMoreSuccess) {
            mFooterViewPb.setVisibility(View.VISIBLE);
            mFooterViewState.setText("正在加载更多");
        } else {//加载更多失败
            mFooterViewPb.setVisibility(View.GONE);
            mFooterViewState.setText("加载更多失败,点击重试");
        }
    }

    @Override
    public void onClick(View v) {
        if (mLoadMoreSuccess) {
            return;
        }
        //重新触发加载更多
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onLoadMore(this);
            //ui跟着更新
            mFooterViewPb.setVisibility(View.VISIBLE);
            mFooterViewState.setText("正在加载更多");
        }
    }
}