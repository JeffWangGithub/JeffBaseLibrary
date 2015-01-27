package com.meilishuo.baselibrary.utils;

import com.meilishuo.baselibrary.R;
import com.meilishuo.baselibrary.widget.MyToast;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

/**
 * 处理一些组基本的UI相关的操作
 * @author Jeff
 */
public class BaseUIUtils {

	public static Toast mToast;
	
	/**
	 * 显示自定义的MyToast
	 * @param context
	 * @param msg
	 */
	public static void showMyToast(Context context, String msg){
		MyToast.getInstance().showMyToast(context, msg);
	}
	
	/**
	 * 显示指定时间，指定背景色的Toast
	 * @param context
	 * @param msg
	 * @param time
	 * @param bgColorResId
	 */
	public static void showMyToast(Context context, String msg,long time,int bgColorResId){
		
		MyToast.getInstance().showMyToast(context, msg, time, bgColorResId);
	}
	
	/**
	 * 显示系统自带的Toast，只运行当前应用显示一个Toast
	 */
	public static void showToast(Context mContext, String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
		}
		mToast.setText(msg);
		mToast.show();
	}

	

	/**
	 * 显示自定义的MyToast，只运行当前应用显示一个Toast
	 */
	public static void showCustomerToast(Context mContext, String msg) {
		MyToast.getInstance().showMyToast(mContext, msg, 2000, R.color.green);
	}

	/**dip转换成px，主要用于屏幕适配*/
	public static int dip2px(Context context, int dip) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/** px转换成dip，主要用于屏幕的适配 */
	public static int px2dip(Context context, int px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/** 获取状态栏高度
	 * @return 返回值的单位为像素px
	 */
	public static int getStatusBarHeight(View v) {
		if (v == null) {
			return 0;
		}
		
		Rect frame = new Rect();
		v.getWindowVisibleDisplayFrame(frame);
		return frame.top;
	}
	
	/**获取状态栏高度
	 * @param activity
	 * @return
	 */
	public static int getStatusBarHeight(Activity activity) {
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		return frame.top;
	}


	/** 获取资源文件中的文字数组 */
	public static String[] getStringArray(Context context,int resId) {
		return context.getResources().getStringArray(resId);
	}

	/** 获取dimen */
	public static int getDimens(Context context,int resId) {
		return context.getResources().getDimensionPixelSize(resId);
	}

	/** 获取drawable */
	public static Drawable getDrawable(Context context,int resId) {
		return context.getResources().getDrawable(resId);
	}

	/** 获取颜色 */
	public static int getColor(Context context,int resId) {
		return context.getResources().getColor(resId);
	}

	/** 获取颜色选择器 */
	public static ColorStateList getColorStateList(Context context,int resId) {
		return context.getResources().getColorStateList(resId);
	}
	
}
