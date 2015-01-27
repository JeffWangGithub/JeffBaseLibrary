package com.meilishuo.baselibrary.widget;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;


/**
 * 封装了BaseAdapter，只需给此Adapter传递数据和AbsListView即可
 * @author Jeff
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
	
	
	private List<T> mData;
	private AbsListView mListView;
	private BaseHolder holder;
	public MyBaseAdapter(List<T> mData, AbsListView mListView) {
		this.mListView = mListView;
		setData(mData);
	}
	
	public void setData(List<T> mData) {
		this.mData = mData;
	}

	public List<T> getData(){
		return mData;
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView != null && convertView.getTag() instanceof BaseHolder){
			holder = (BaseHolder) convertView.getTag();
		}else{
			holder = getHolder();
		}
		
		holder.setData(mData.get(position));
		
		return holder.getRootView();
	}

	public abstract BaseHolder getHolder();	//拿到一个BaseHolder，BaseHolder中可以封装了数据和每一个item中对应的view
	

}
