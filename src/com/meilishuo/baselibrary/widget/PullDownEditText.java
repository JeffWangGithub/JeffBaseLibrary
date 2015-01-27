package com.meilishuo.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.meilishuo.baselibrary.R;

/**
 * 实现了EditText记录登录过的用户，点击左侧的按钮会弹出PopupWindow
 * @author Jeff
 *
 */
public class PullDownEditText extends RelativeLayout{

	private EditText mEditText;
	private ImageView mPullDownImageView;

	public PullDownEditText(Context context) {
		super(context);
		init();
	}

	public PullDownEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		initAttr(context,attrs);//初始化属性
	}

	public PullDownEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		initAttr(context,attrs);//初始化属性
	}

	private void init() {
		RelativeLayout view = (RelativeLayout) View.inflate(getContext(), R.layout.pulldown_edittext, this);
		mEditText = (EditText) view.findViewById(R.id.et_input);
		mPullDownImageView = (ImageView) view.findViewById(R.id.iv_pulldown);
		
	}
	
	private void initAttr(Context context,AttributeSet attrs) {
		
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PullDownEditText);
		String hint = typedArray.getString(R.styleable.PullDownEditText_hint);
		String text = typedArray.getString(R.styleable.PullDownEditText_text);
		Drawable background = typedArray.getDrawable(R.styleable.PullDownEditText_rightImageBg);
		Drawable src = typedArray.getDrawable(R.styleable.PullDownEditText_rightImageSrc);
		
		//根据用户配置的属性初始化
		if(!TextUtils.isEmpty(hint)){
			getInputEditText().setHint(hint);
		}
		if(!TextUtils.isEmpty(text)){
			getInputEditText().setText(text);
		}
		
		if(null != background){
			getPullDownImageView().setBackground(background);
		}
		
		if(null != src){
			getPullDownImageView().setImageDrawable(src);
		}
	}
	
	/**
	 * 获取到输入框EditText
	 * @return EditText对象
	 */
	public EditText getInputEditText() {
		return mEditText;
	}

	/**
	 * 获取下拉按钮ImageView
	 * @return ImageView对象
	 */
	public ImageView getPullDownImageView() {
		return mPullDownImageView;
	}
	
	/**
	 * 获取EditText中的文本
	 * @return
	 */
	public Editable getText(){
		
		return mEditText.getText();
	}
	
	/**
	 * 设置EditText的内容
	 * @param text
	 */
	public void setText(CharSequence text){
		mEditText.setText(text);
	}
	
	/**
	 * 设置下拉按钮的点击时间
	 * @param listener
	 */
	public void setOnPullDownClick(OnClickListener listener){
		
		mPullDownImageView.setOnClickListener(listener);
	}
	
}
