package com.meilishuo.baselibrary.engine;

import java.io.File;

import com.meilishuo.baselibrary.utils.FileUtils;

import android.content.Context;
import android.text.format.Formatter;

/**
 * 处理WebView缓存的类
 * @author Jeff
 *
 */
public class WebViewCacheEngine {

	private Context context;

	public WebViewCacheEngine(Context context) {
		this.context = context;
	}

	/**
	 * 返回WebView缓存文件的大小，单位为字节
	 * 
	 * @return 单位为字节
	 */
	public long getWebViewCacheSize() {
		long webviewCacheSize = 0;
		try {
			// 高版本下WebView生产的缓存目录
			File app_webview = getWebviewCacheHighVersion();
			if (app_webview != null) {
				webviewCacheSize += FileUtils.getFileSize(app_webview);
			}
			// 低版本下生成的WebView缓存目录
			File cacheWebViewCache = getWebViewCacheLowVersion();
			if (cacheWebViewCache != null) {
				webviewCacheSize += FileUtils.getFileSize(cacheWebViewCache);
			}
			// 低版本下生成的WebView的缓存数据库
			File[] cacheWebViewDatabases = getWebViewCacheDatabaseLowVersion();
			if (cacheWebViewDatabases[0] != null) {
				webviewCacheSize += FileUtils.getFileSize(cacheWebViewDatabases[0]);
			}
			if (cacheWebViewDatabases[1] != null) {
				webviewCacheSize += FileUtils.getFileSize(cacheWebViewDatabases[1]);
			}
		} catch (Exception e) {
		}

		return webviewCacheSize;
	}

	/**
	 * 返回大小格式化的WebView缓存文件的大小，单位为M
	 * 
	 * @return 返回数据单位为M
	 */
	public String getFormatWebViewCacheSize() {
		long webviewCacheSize = getWebViewCacheSize();
		
		return Formatter.formatFileSize(context,webviewCacheSize);
	}

	/**
	 * 返回WebView的缓存目录：/data/data/packageName/app_webview/Cache，
	 * 因为在高版本的WebView上缓存目录是这个，而不是/data/data/packageName/cache/webviewCache
	 * 
	 * @return
	 */
	public File getWebviewCacheHighVersion() {
		String dataFilePath = context.getCacheDir().getParent();

		File app_webview = new File(dataFilePath + "/app_webview/Cache");

		if (app_webview.exists()) {
			return app_webview;
		}
		return null;
	}

	/**
	 * 返回WebView的缓存目录：/data/data/packageName/cache/webviewCache，
	 * 因为在低版本（4.0一下），WebView的缓存目录为以上目录
	 * 
	 * @return
	 */
	public File getWebViewCacheLowVersion() {

		File cacheDir = context.getCacheDir();
//		File webViewCache = new File(cacheDir, "webviewCache");
		if (cacheDir.exists()) {
			return cacheDir;
		}
		return null;
	}

	/**
	 * 获取低版本下下WebView的缓存数据库（即/data/data/packageName/database下的webview.
	 * db和webviewCache.db文件）
	 * 
	 * @return File[0]为webview.db文件；File[1]为webviewCache.db文件
	 */
	public File[] getWebViewCacheDatabaseLowVersion() {
		File[] webViewDbs = new File[2];
		File webviewDB = context.getDatabasePath("webview.db");
		File webviewCacheDB = context.getDatabasePath("webviewCache.db");
		if (webviewDB.exists()) {
			webViewDbs[0] = webviewDB;
		}
		if (webviewCacheDB.exists()) {
			webViewDbs[1] = webviewCacheDB;
		}
		return webViewDbs;
	}
	
	
	/**
	 * 清理WebView的缓存
	 */
	public void clearWebViewCache(){
		
		File cacheWebviewHighersion = getWebviewCacheHighVersion();
		if(cacheWebviewHighersion != null){
			FileUtils.deleteAllFileFromDir(cacheWebviewHighersion);
		}
		
		File cacheWebViewLowVersion = getWebViewCacheLowVersion();
		if(cacheWebViewLowVersion != null){
			FileUtils.deleteAllFileFromDir(cacheWebViewLowVersion);
		}
		
		File[] webViewCacheDatabaseLowVersion = getWebViewCacheDatabaseLowVersion();

		for (File file : webViewCacheDatabaseLowVersion) {
			if(file != null){
				context.deleteDatabase(file.getName());
			}
		}
		
	}
}
