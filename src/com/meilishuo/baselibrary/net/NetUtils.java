package com.meilishuo.baselibrary.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



/**
 * 判断用户联网方式的工具类
 * @author Jeff
 */
public class NetUtils {

	/**检查网络是否处于可用状态
	 * @param context
	 * @return 返回true表示联网正常；false表示没有联网。
	 */
	public static boolean checkNetWork(Context context){
		//1，判断wifi是否处于可用状态
		//2，判断基站是否处于可用状态
		boolean isWifi = isWifiConnection(context);
		boolean isBaseStation = isBaseStationConnection(context);

		if(!isWifi && !isBaseStation){
			//均不可用
			return false;			
		}
		
		return true;
	}

	/**
	 * 判断手机基站网络是否可用
	 * @param context
	 */
	public static boolean isBaseStationConnection(Context context) {
	
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(networkInfo != null){
			//手机基站网络处于可用状态
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * 判断wifi网络是否可用
	 * @param context
	 */
	public static boolean isWifiConnection(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(networkInfo != null){
			//Wifi网络处于可用状态
			return networkInfo.isConnected();			
		}
		return false;
	}
	
	/**
	 * 判断当前网络的类型
	 * @param context
	 * @return 返回wifi表示：当前为wifi网络；返回mobile表示当前网络为手机基站网络；
	 * 		   返回null表示当前没有开启网络服务。
	 */
	public static String checkNetType(Context context){
		String netType = null;
		boolean isNetWorking = checkNetWork(context);
		if(isNetWorking){
			//当前网络可用
			boolean isWifi = isWifiConnection(context);
			netType = isWifi ? "wifi" : "mobile";
			return netType;
		}
		return netType;
	}
	
}
