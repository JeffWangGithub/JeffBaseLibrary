package com.meilishuo.baselibrary.utils;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {
	
	/**
	 * 获取指定名称的SharedPreferences对象
	 * 注意:
	 * 	1,默认的方位模式为Context.MODE_PRIVATE.
	 *  2,SP何时会创建:当指定名称的文件不存在的时候,只有执行了Editor.commit()方法后才回进行创建
	 */
	public static SharedPreferences getSPByName(Context context, String name){
		
		return context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}
	/**
	 * 向SharedPreference的文件中添加数据
	 * 数据的类型仅限SharedPreference支持的类型
	 * @param context
	 * @param spName SharedPreference的名字
	 * @param key
	 * @param value 单个数据或者Set<String>集合数据
	 */
	public static <T> void putValue(Context context, String spName, String key, T value){
		SharedPreferences mSP = getSPByName(context, spName);
		Editor editor = mSP.edit();
		if(value instanceof Integer || value.getClass()==int.class){
			editor.putInt(key, (Integer) value);
		}else if (value instanceof Boolean || value.getClass()==boolean.class){
			editor.putBoolean(key, (Boolean) value);
		}else if(value instanceof Float || value.getClass() == float.class){
			editor.putFloat(key, (Float) value);
		}else if(value instanceof Long || value.getClass()==long.class){
			editor.putLong(key, (Long) value);
		}else if(value instanceof String){
			editor.putString(key, (String) value);
		}else if(value instanceof Set){
			try{
				editor.putStringSet(key, (Set<String>) value);
			}catch(Exception e){
				throw new RuntimeException("Set集合中类型必须为String类型");
			}
		}else{
			throw new RuntimeException("不支持此类型的value数据");
		}
		
		editor.commit();
	}
	
	/** 向指定名称的SharedPreferences中添加boolean数据*/
	public static void putBoolean(Context context, String spName, String key, boolean value){
		SharedPreferences mSP = getSPByName(context, spName);
		mSP.edit().putBoolean(key, value).commit();
	}
	
	/** 向指定名称的SharedPreferences中添加float数据*/
	public static void putFloat(Context context, String spName, String key, float value){
		SharedPreferences mSP = getSPByName(context, spName);
		mSP.edit().putFloat(key, value).commit();
	}
	
	/** 向指定名称的SharedPreferences中添加int数据*/
	public static void putInt(Context context, String spName, String key, int value){
		SharedPreferences mSP = getSPByName(context, spName);
		mSP.edit().putInt(key, value).commit();
	}
	
	/** 向指定名称的SharedPreferences中添加long数据*/
	public static void putLong(Context context, String spName, String key, long value){
		SharedPreferences mSP = getSPByName(context, spName);
		mSP.edit().putLong(key, value).commit();
	}
	
	/** 向指定名称的SharedPreferences中添加String数据*/
	public static void putString(Context context, String spName, String key, String value){
		SharedPreferences mSP = getSPByName(context, spName);
		mSP.edit().putString(key, value).commit();
	}
	
	/** 向指定名称的SharedPreferences中添加Set<String>数据*/
	public static void putStringSet(Context context, String spName, String key, Set<String> values){
		SharedPreferences mSP = getSPByName(context, spName);
		mSP.edit().putStringSet(key, values).commit();
	}

}
