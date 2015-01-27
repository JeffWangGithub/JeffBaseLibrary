package com.meilishuo.baselibrary.log;

import android.util.Log;

public class LogUtils {
	// 日志输出的级别
	public static int LEVEL_NONE = 0;
	public static int LEVEL_VERBOSE = 1;
	public static int LEVEL_DEBUG = 1;
	public static int LEVEL_INFO = 1;
	public static int LEVEL_WARN = 1;
	public static int LEVEL_ERROR = 1;

	/** 当项目发布时不想要输出调试日志，只需要将此值改为-1即可 */
	private static int mDebuggable = LEVEL_ERROR;

	/**
	 * 是否屏蔽所有的输出日志
	 * @param 传递true表示允许输出；false表示不允许输出。
	 */
	public static void allowD(boolean isAllow){
		if(isAllow){
			mDebuggable = LEVEL_ERROR;
		}else {
			//屏蔽所有的输出信息
			mDebuggable = -1;
		}
		
	}
	
	
	/**
	 * 输出verbose日志信息
	 * @param mTag
	 * @param str
	 */
	public static void v(String mTag, String str) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.v(mTag, str);
		}
	}

	/**
	 * 输出debug日志信息
	 * @param mTag
	 * @param str
	 */
	public static void d(String mTag, String str) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.d(mTag, str);
		}
	}

	/**
	 * 输出info日志信息
	 * 
	 * @param mTag
	 * @param str
	 */
	public static void i(String mTag, String str) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.i(mTag, str);
		}
	}

	/**
	 * 输出warning日志信息
	 * @param mTag
	 * @param str
	 */
	public static void w(String mTag, String str) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.w(mTag, str);
		}
	}

	/**
	 * 输出error级别的日志信息
	 * @param mTag
	 * @param str
	 */
	public static void e(String mTag, String str) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.e(mTag, str);
		}
	}
	
	/** 以级别为 e 的形式输出Throwable */
	public static void e(String tag,Throwable tr) {
		if (mDebuggable >= LEVEL_ERROR) {
			Log.e(tag, "", tr);
		}
	}

	/** 以级别为 e 的形式输出LOG信息和Throwable */
	public static void e(String tag,String msg, Throwable tr) {
		if (mDebuggable >= LEVEL_ERROR && null != msg) {
			Log.e(tag, msg, tr);
		}
	}
	
}
