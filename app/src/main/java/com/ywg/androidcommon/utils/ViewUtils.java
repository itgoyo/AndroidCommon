package com.ywg.androidcommon.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 视图工具箱
 */
public class ViewUtils {
	/**
	 * 获取一个LinearLayout
	 * @param context 上下文
	 * @param orientation 流向
	 * @param width 宽
	 * @param height 高
	 * @return LinearLayout
	 */
	public static LinearLayout createLinearLayout(Context context, int orientation, int width, int height){
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(orientation);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));
		return linearLayout;
	}
	
	/**
	 * 
	 * 获取一个LinearLayout
	 * @param context 上下文
	 * @param orientation 流向
	 * @param width 宽
	 * @param height 高
	 * @param weight 权重
	 * @return LinearLayout
	 */
	public static LinearLayout createLinearLayout(Context context, int orientation, int width, int height, int weight){
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(orientation);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));
		return linearLayout;
	}
	
	/**
	 * 根据ListView的所有子项的高度设置其高度
	 * @param listView
	 */
	public static void setListViewHeightByAllChildrenViewHeight(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
	    if (listAdapter != null) {  
	    	int totalHeight = 0;  
	    	for (int i = 0; i < listAdapter.getCount(); i++) {  
	    		View listItem = listAdapter.getView(i, null, listView);
	    		listItem.measure(0, 0);  
	    		totalHeight += listItem.getMeasuredHeight();  
	    	}  
	    	
	    	ViewGroup.LayoutParams params = listView.getLayoutParams();
	    	params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
	    	((ViewGroup.MarginLayoutParams)params).setMargins(10, 10, 10, 10);
	    	listView.setLayoutParams(params); 
	    }  
    }
	
	/**
	 * 给给定的视图设置长按提示
	 * @param context 上下文
	 * @param view 给定的视图
	 * @param hintContent 提示内容
	 */
	public static void setLongClickHint(final Context context, View view, final String hintContent){
		view.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				ToastUtil.showLong(context, hintContent);
				return true;
			}
		});
	}
	
	/**
	 * 给给定的视图设置长按提示
	 * @param context 上下文
	 * @param view 给定的视图
	 * @param hintContentId 提示内容的ID
	 */
	public static void setLongClickHint(final Context context, View view, final int hintContentId){
		setLongClickHint(context, view, context.getString(hintContentId));
	}
	
	/**
	 * 设置给定视图的高度
	 * @param view 给定的视图
	 * @param newHeight 新的高度
	 */
	public static void setViewHeight(View view, int newHeight){
		ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();  
		layoutParams.height = newHeight; 
		view.setLayoutParams(layoutParams);
	}
	
	/**
	 * 将给定视图的高度增加一点
	 * @param view 给定的视图
	 * @param increasedAmount 增加多少
	 */
	public static void addViewHeight(View view, int increasedAmount){
		ViewGroup.LayoutParams headerLayoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();  
		headerLayoutParams.height += increasedAmount; 
		view.setLayoutParams(headerLayoutParams);
	}
	
	/**
	 * 设置给定视图的宽度
	 * @param view 给定的视图
	 * @param newWidth 新的宽度
	 */
	public static void setViewWidth(View view, int newWidth){
		ViewGroup.LayoutParams headerLayoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();  
		headerLayoutParams.width = newWidth; 
		view.setLayoutParams(headerLayoutParams);
	}
	
	/**
	 * 将给定视图的宽度增加一点
	 * @param view 给定的视图
	 * @param increasedAmount 增加多少
	 */
	public static void addViewWidth(View view, int increasedAmount){
		ViewGroup.LayoutParams headerLayoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();  
		headerLayoutParams.width += increasedAmount; 
		view.setLayoutParams(headerLayoutParams);
	}
	
	/**
	 * 获取流布局的底部外边距
	 * @param linearLayout
	 * @return
	 */
	public static int getLinearLayoutBottomMargin(LinearLayout linearLayout) {
		return ((LinearLayout.LayoutParams)linearLayout.getLayoutParams()).bottomMargin;
	}
	
	/**
	 * 设置流布局的底部外边距
	 * @param linearLayout
	 * @param newBottomMargin
	 */
	public static void setLinearLayoutBottomMargin(LinearLayout linearLayout, int newBottomMargin) {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)linearLayout.getLayoutParams();
		lp.bottomMargin = newBottomMargin;
		linearLayout.setLayoutParams(lp);
	}
	
	/**
	 * 获取流布局的高度
	 * @param linearLayout
	 * @return
	 */
	public static int getLinearLayoutHiehgt(LinearLayout linearLayout) {
		return ((LinearLayout.LayoutParams)linearLayout.getLayoutParams()).height;
	}
	
	/**
	 * 设置输入框的光标到末尾
	 * @param editText
	 */
	public static final void setEditTextSelectionToEnd(EditText editText){
		Editable editable = editText.getEditableText();
		Selection.setSelection((Spannable) editable, editable.toString().length());
	}
	
	/**
	 * 执行测量，执行完成之后只需调用View的getMeasuredXXX()方法即可获取测量结果
	 * @param view
	 * @return
	 */
	public static final View measure(View view){
		ViewGroup.LayoutParams p = view.getLayoutParams();
	    if (p == null) {
	        p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	    }
	    int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
	    int lpHeight = p.height;
	    int childHeightSpec;
	    if (lpHeight > 0) {
	    	childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
	    } else {
	        childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
	    }
	    view.measure(childWidthSpec, childHeightSpec);
	    return view;
	}
	
	/**
	 * 获取给定视图的测量高度
	 * @param view
	 * @return
	 */
	public static final int getMeasuredHeight(View view){
	    return measure(view).getMeasuredHeight();
	}
	
	/**
	 * 获取给定视图的测量宽度
	 * @param view
	 * @return
	 */
	public static final int getMeasuredWidth(View view){
	    return measure(view).getMeasuredWidth();
	}
	
	/**
	 * 获取视图1相对于视图2的位置，注意在屏幕上看起来视图1应该被视图2包含，但是视图1和视图并不一定是绝对的父子关系也可以是兄弟关系，只是一个大一个小而已
	 * @param view1
	 * @param view2
	 * @return
	 */
	public static final Rect getRelativeRect(View view1, View view2){
		Rect childViewGlobalRect = new Rect();
		Rect parentViewGlobalRect = new Rect();
		view1.getGlobalVisibleRect(childViewGlobalRect);
		view2.getGlobalVisibleRect(parentViewGlobalRect);
		return new Rect(childViewGlobalRect.left - parentViewGlobalRect.left, childViewGlobalRect.top - parentViewGlobalRect.top, childViewGlobalRect.right - parentViewGlobalRect.left, childViewGlobalRect.bottom - parentViewGlobalRect.top);
	}
	
	/**
	 * 删除监听器
	 * @param viewTreeObserver
	 * @param onGlobalLayoutListener
	 */
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static final void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener){
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
			viewTreeObserver.removeGlobalOnLayoutListener(onGlobalLayoutListener);
		}else{
			viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
		}
	}

	/**
	 * 缩放视图
	 * @param view
	 * @param scaleX
	 * @param scaleY
	 * @param originalSize 
	 */
	public static void zoomView(View view, float scaleX, float scaleY, Point originalSize){
		int width = (int) (originalSize.x * scaleX);
		int height = (int) (originalSize.y * scaleY);
		ViewGroup.LayoutParams viewGroupParams = view.getLayoutParams();
		if(viewGroupParams != null){
			viewGroupParams.width = width;
			viewGroupParams.height = height;
		}else{
			viewGroupParams = new ViewGroup.LayoutParams(width, height);
		}
		view.setLayoutParams(viewGroupParams);
	}

	/**
	 * 缩放视图
	 * @param view
	 * @param scaleX
	 * @param scaleY
	 */
	public static void zoomView(View view, float scaleX, float scaleY){
		zoomView(view, scaleX, scaleY, new Point(view.getWidth(), view.getHeight()));
	}

	/**
	 * 缩放视图
	 * @param view
	 * @param scale 比例
	 * @param originalSize 
	 */
	public static void zoomView(View view, float scale, Point originalSize){
		zoomView(view, scale, scale, originalSize);
	}

	/**
	 * 缩放视图
	 * @param view
	 */
	public static void zoomView(View view, float scale){
		zoomView(view, scale, scale, new Point(view.getWidth(), view.getHeight()));
	}


	/**
	 * Measure view's height
	 *
	 * @param view
	 * @return
	 */
	public static int measureViewHeight(View view) {
		view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return view.getMeasuredHeight();
	}

	/**
	 * Get TextView's height, TextView's width is full screen width
	 *
	 * @param textView
	 * @return
	 */
	public static int getTextViewHeight(TextView textView) {
		WindowManager wm =
				(WindowManager) textView.getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		int deviceWidth;

		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Point size = new Point();
			display.getSize(size);
			deviceWidth = size.x;
		} else {
			deviceWidth = display.getWidth();
		}

		return getTextViewHeight(textView, deviceWidth);
	}

	/**
	 * Get TextView's height by textview's width
	 *
	 * @param textView
	 * @param width
	 * @return
	 */
	public static int getTextViewHeight(TextView textView, int width) {
		int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
		int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		textView.measure(widthMeasureSpec, heightMeasureSpec);
		return textView.getMeasuredHeight();
	}

	/**
	 * Find TextView by traverse view tree
	 *
	 * @param view
	 * @return
	 */
	public static TextView findTextView(View view) {
		if (view instanceof TextView) {
			return (TextView) view;
		} else {
			if (view instanceof ViewGroup) {
				return findTextView(((ViewGroup) view).getChildAt(0));
			} else {
				return null;
			}
		}
	}

	/**
	 * Get GridView's horizontal spacing
	 *
	 * @param GridView
	 * @return
	 */
	@Deprecated
	private static int getGridViewHorizontalSpacing(GridView GridView) {
		int spacing = 0;
		try {
			Field field = GridView.getClass().getDeclaredField("mHorizontalSpacing");
			field.setAccessible(true);
			spacing = field.getInt(GridView);
		} catch (Exception e) {
		}
		return spacing;
	}

	/**
	 * Get GridView's request horizontal spacing
	 *
	 * @param GridView
	 * @return
	 */
	private static int getGridViewRequestHorizontalSpacing(GridView GridView) {
		int spacing = 0;
		Field field = null;
		try {
			field = GridView.getClass().getDeclaredField("mRequestedHorizontalSpacing");
			field.setAccessible(true);
			spacing = field.getInt(GridView);
		} catch (Exception e) {
		}
		return spacing;
	}

	/**
	 * Get GridView's height
	 *
	 * @param GridView
	 * @return
	 */
	public static int getGridViewHeight(GridView GridView) {
		if (GridView == null) {
			return 0;
		}
		ListAdapter listAdapter = GridView.getAdapter();
		if (listAdapter == null) {
			return 0;
		}
		int rows = 0;
		int columns = GridView.getNumColumns();
		int horizontalBorderHeight = getGridViewRequestHorizontalSpacing(GridView);
		if (listAdapter.getCount() % columns > 0) {
			rows = listAdapter.getCount() / columns + 1;
		} else {
			rows = listAdapter.getCount() / columns;
		}
		int totalHeight = 0;
		for (int i = 0; i < rows; i++) {
			View listItem = listAdapter.getView(i, null, GridView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		return (totalHeight + horizontalBorderHeight * (rows - 1));
	}

	/**
	 * Set GridView's height
	 *
	 * @param GridView
	 */
	public static void setGridViewHeight(GridView GridView) {
		int h = getGridViewHeight(GridView);
		if (h == 0 || GridView == null) {
			return;
		}
		ViewGroup.LayoutParams params = GridView.getLayoutParams();
		params.height = h;
		GridView.setLayoutParams(params);
	}

	/**
	 * Get ListView's height
	 *
	 * @param ListView
	 * @return
	 */
	public static int getListViewHeight(ListView ListView) {
		if (ListView == null) {
			return 0;
		}

		ListAdapter listAdapter = ListView.getAdapter();
		if (listAdapter == null) {
			return 0;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, ListView);
			listItem.measure(
					View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
					View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
			totalHeight += listItem.getMeasuredHeight();
		}

		return (totalHeight + (ListView.getDividerHeight() * (listAdapter.getCount() - 1)));
	}

	/**
	 * Set ListView's height
	 *
	 * @param ListView
	 * @param height
	 */
	public static void setListViewHeight(ListView ListView, int height) {
		int h = 0;
		if (height == 0) {
			h = getListViewHeight(ListView);
			if (h == 0 || ListView == null) {
				return;
			}
		} else {
			h = height;
		}
		ViewGroup.LayoutParams params = ListView.getLayoutParams();
		params.height = h;
		ListView.setLayoutParams(params);
	}

	/**
	 * Set ListView's height
	 *
	 * @param ListView
	 */
	public static void setListViewHeight(ListView ListView) {
		setListViewHeight(ListView, 0);
	}
}