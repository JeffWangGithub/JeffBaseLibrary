package com.meilishuo.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meilishuo.baselibrary.R;

/**
 * 自定义组合控件SettingEnterView
 * 
 * @author Jeff
 * 
 */
public class SettingEnterView extends RelativeLayout {

	private View view;
	private TextView tv_title;
	private TextView tv_des;
	private String title;
	private String des;

	public SettingEnterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SettingEnterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// 拿到自定义属性的值
		title = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.rolfwang.mobilesafe",
				"title");
		des = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.rolfwang.mobilesafe",
				"des");

		// 获取自定义属性方式二：
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.baseLibrary);

		title = typedArray.getString(R.styleable.baseLibrary_titleTop);
		des= typedArray.getString(R.styleable.baseLibrary_des);
		typedArray.recycle();// 注意使用完typeArray之后，需要进行资源的释放

		// 设置默认值
		tv_title.setText(title);
		tv_des.setText(des);

	}

	public SettingEnterView(Context context) {
		super(context);
		init();
	}

	private void init() {
		view = View.inflate(getContext(), R.layout.setting_enter_view, this);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_des = (TextView) view.findViewById(R.id.tv_des);
	}

	// 暴露一些方法

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		tv_title.setText(title);
	}

	/**
	 * 设置描述信息
	 * 
	 * @param des
	 */
	public void setDes(String des) {
		tv_des.setText(des);
	}
	
	
	/**
	 * 设置描述信息Des文本的的颜色
	 */
	public void setDesTextColor(int color){
		tv_des.setTextColor(color);
	}

}
