package com.meilishuo.baselibrary.widget;

import com.meilishuo.baselibrary.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ListView;

public class BaseListView extends ListView {

	public BaseListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	public BaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BaseListView(Context context) {
		super(context);
		init();
	}
	private void init() {
		setDivider(getResources().getDrawable(R.drawable.nothing));
//		setCacheColorHint(R.color.bg_page);
		setSelector(getResources().getDrawable(R.drawable.nothing));
		
		//设置ListView的背景色，缓存背景和模糊边界
		//背景设为透明色,去除默认背景
		this.setBackgroundColor(Color.TRANSPARENT);//对应xml中的background属性
		
		//将缓存设为透明
		this.setCacheColorHint(Color.TRANSPARENT);//对应xml中的cacheColorHint属性
		
		//设置条目的选择器
//		this.setSelector(resId);//对应xml中的selector属性
		
		//此设置对应了fadingEdge="none"属性去掉ListView的模糊效果
		this.setVerticalFadingEdgeEnabled(false);//对应xml中的fadingEdge="none"
		
	}

}
