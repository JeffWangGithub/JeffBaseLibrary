package com.meilishuo.baselibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class BaseWebView extends WebView {

	private Context context;
	private WebSettings settings;

	public BaseWebView(Context context) {
		// this(context, null);
		super(context);
		this.context = context;
		init();
	}

	public BaseWebView(Context context, AttributeSet attrs) {
		// this(context, attrs, 0);
		super(context, attrs);
		this.context = context;
		init();
	}

	public BaseWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}

	private void init() {
		settings = this.getSettings();
		settings.setJavaScriptEnabled(true); // 设置支持JS
//		settings.setBlockNetworkImage(false); // 设置显示图片，4.4版本以上如果不设置，可能不显示图片
//		
//		settings.setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式
//		settings.setDatabaseEnabled(true);//开启DOM storage 
//		settings.setDatabaseEnabled(true);//开启database storage功能
		
		//应用缓存目录
//		String cacheDirPath = context.getFilesDir().getAbsoluteFile() + "/app_lzz_webView";
//		
//		settings.setAppCachePath(cacheDirPath);
//		settings.setAppCacheEnabled(true);

	}

	/**
	 * 给WebView设置UA
	 * 
	 * @param ua
	 */
	public void setUA(String ua) {
		settings.setUserAgentString(ua);
	}

	/**
	 * 设置Cookie信息
	 */
	public void synCookies(String url, String cookie) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		cookieManager.removeSessionCookie();// 移除Cookie
		cookieManager.setCookie(url, cookie);
		CookieSyncManager.getInstance().sync();// 获取CookieSyncManager对象，并调用同步方法。getInstance之前需调用createInstance()否则报异常
	}

}
