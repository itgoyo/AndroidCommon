package com.ywg.androidcommon.utils;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class DownloadManagerUtils {
	/**
	 * 根据请求ID获取本地文件的Uri
	 * @return 不存在
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static String getLocalFileUriByRequestId(Context context, long requestId){
		String  result = null;
		if(Build.VERSION.SDK_INT >= 9){
			DownloadManager.Query query = new DownloadManager.Query();
			query.setFilterById(requestId);
			Cursor cursor = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
			if(cursor.moveToFirst()) {   
				result = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
			}
			cursor.close();
		}
		return result;
	}
	
	/**
	 * 根据请求ID判断下载是否完成
	 * @param context
	 * @param requestId
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static  boolean isFinish(Context context, long requestId){
		if(Build.VERSION.SDK_INT >= 9){
			DownloadManager.Query query = new DownloadManager.Query();
			query.setFilterById(requestId);
			Cursor cursor = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
			boolean result = cursor.moveToFirst() &&cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL;
			cursor.close();
			return result;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据请求ID判断是否正在下载
	 * @param context
	 * @param requestId
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static  boolean isDownloading(Context context, long requestId){
		if(Build.VERSION.SDK_INT >= 9){
			DownloadManager.Query query = new DownloadManager.Query();
			query.setFilterById(requestId);
			Cursor cursor = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
			boolean result = cursor.moveToFirst() &&cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_RUNNING;
			cursor.close();
			return result;
		}else{
			return false;
		}
	}
}