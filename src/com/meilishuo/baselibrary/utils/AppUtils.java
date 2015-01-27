package com.meilishuo.baselibrary.utils;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public class AppUtils {
	
	
	
	/**
	 *  调用系统的安装器安装应用程序
	 * @param context
	 * @param appPath 应用程序所在的路径
	 */
	public static void installApp(Context context,String appPath){
		
    	File file = new File(appPath);
    	//1,设置隐式意图
    	Intent intent = new Intent();
    	intent.setAction("android.intent.action.VIEW");
    	
    	//2,摄者数据和类型
    	Uri data = Uri.fromFile(file);
    	String type = "application/vnd.android.package-archive";
    	intent.setDataAndType(data, type);
    	
    	//3，启动Activity
    	context.startActivity(intent);    	
	}
	
	/**
	 * 下载应用程序
	 * @param context
	 * @param appPath
	 */
	public static void uninstallApp(Context context,String appPath){
		
    	//1,设置隐式意图
    	Intent intent = new Intent();
    	intent.setAction("android.intent.action.DELETE");
    	
    	//2,摄者数据
    	Uri data = Uri.parse("package:"+appPath);
    	intent.setData(data);
    	
    	//3，启动Activity
    	context.startActivity(intent);    	
	}
	
	/**
	 * 根据应用程序的报名获取应用程序的versionName
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getVersionName(Context context, String packageName){
		
		String versionName = null;
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
			versionName = packageInfo.versionName;//版本名称
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}
	
	
	/**
	 * 根据应用程序的报名获取当前应用的versionCode
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static int getVersionCode(Context context, String packageName){
		int versionCode = -1;
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
			versionCode = packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		return versionCode;
	}

}
