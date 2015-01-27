package com.meilishuo.baselibrary.widget;

import android.view.View;

/**
 * BaseHolder中封装了ListView需要显示的数据和每一个item条目中的所有view
 * @author Jeff
 *
 * @param <T>
 */
public abstract class BaseHolder<T> {
	public T mData;
	private View view;
	public BaseHolder(){
		view = initView();	//用户自行进行设置View
		view.setTag(this);	//将携带布局文件中控件的holder设置到View对象中
	}

	public void setData(T data) {
		this.mData = data;
		refreshView();
	}
	
	/**
	 * 给条目设置数据
	 */
	public abstract void refreshView();

	public T getData(){
		return mData;
	}

	public View getRootView(){
		return view;
	}
	/**
	 * 初始化条目的布局
	 * @return
	 */
	public abstract View initView();
	
}
