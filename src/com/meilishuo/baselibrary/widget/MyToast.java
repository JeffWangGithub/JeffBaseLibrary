package com.meilishuo.baselibrary.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.meilishuo.baselibrary.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;

public class MyToast {
	
	private  WindowManager windowManager;
	private  View view;
	private  TextView tv;
	
	private  long SHORT = 3000;
	private  ObjectAnimator objectAnimator;
	
	private  MyToast(){}	//使用单利模式
	
	private static MyToast instance = new MyToast(); 
	
	/**
	 * 使用单利模式获取到一个MyToast
	 * @return
	 */
	public static MyToast getInstance(){
		return instance;
	}
	
	/**
	 * 显示自定义Toast，默认背景色为绿色
	 * @param context
	 * @param str
	 */
	public void showMyToast(Context context,String str){
		
		showMyToast(context, str, SHORT, R.color.bg_actionbar);
	}
	
	/**
	 * 根据指定的时间和北京颜色显示自定义Toast
	 * @param context
	 * @param str
	 * @param time
	 * @param bgColorResId
	 */
	public void showMyToast(Context context,String str,long time,int bgColorResId){
		
		hideMyToast();//先移除,否则连续多次单机会挂掉
		
		//1，获取WindowManager
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		
		//2，准备View对象
		view =  View.inflate(context, R.layout.mytoast_layout, null);
		tv = (TextView) view.findViewById(R.id.tv_show);
		
		view.setBackgroundResource(bgColorResId);
		tv.setText(str);
		//3，准备布局参数
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;  //View对象高度
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;		//View对象宽度
        params.format = PixelFormat.TRANSLUCENT;					//半透明
//        params.windowAnimations = android.R.anim.; //动画效果
        params.type = WindowManager.LayoutParams.TYPE_TOAST;		//类型为toast类型
        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON	//保持屏幕
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE			//不能获得焦点
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;		//不能触摸操作
        params.gravity = Gravity.CENTER;
        
		//4，给windowManager添加View对象，并指定布局参数信息
		windowManager.addView(view, params);
		
		objectAnimator = ObjectAnimator.ofFloat(view, "Alpha", 1.0f,1.0f,0);
		objectAnimator.setDuration(time);
		objectAnimator.start();
		
		objectAnimator.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
			}
			@Override
			public void onAnimationRepeat(Animator arg0) {
			}
			@Override
			public void onAnimationEnd(Animator arg0) {
				hideMyToast();
			}
			@Override
			public void onAnimationCancel(Animator arg0) {
			}
		});
	}
	
	/**
	 * 隐藏自定义Toast
	 */
	private void hideMyToast(){
		if(windowManager!=null&&view!=null){
            //5,
			windowManager.removeView(view);
			windowManager = null;
			view = null;
		}
	}	
	
	

}
