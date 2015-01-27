package com.meilishuo.baselibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.meilishuo.baselibrary.R;

public abstract class LoadingPager extends FrameLayout {
	public static final int STATE_UNLOADED = 0;//未加载状态（初始状态）
	public static final int STATE_LOADING = 1;//加载状态
	public static final int STATE_ERROR = 3;//加载完毕，但是出错状态
	public static final int STATE_EMPTY = 4;//加载完毕，但是没有数据状态
	public static final int STATE_SUCCEED = 5;//加载成功
	
	private int mCurrentState = 0 ;//当前的状态
	
	private View mLoadingView;//加载时显示的View
	private View mErrorView;//加载出错显示的View
	private View mEmptyView;//加载没有数据显示的View
	private View mSucceedView;//加载成功显示的View
	
	public Context context;
	
	public LoadingPager(Context context) {
		super(context);
		this.context = context;
		init();
	}
	
	public LoadingPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}
	
	public LoadingPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}

	private void init() {
		this.setBackgroundColor(context.getResources().getColor(R.color.bg_page));//设置背景颜色
		mCurrentState = STATE_UNLOADED;//设置初始状态
		
		//创建各状态布局
		mLoadingView = createLoadingView();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		if(null != mLoadingView){
			addView(mLoadingView, params);//创建正在加载的布局
		}
		
		mErrorView = createErrorView();
		if(null != mErrorView){
			addView(mErrorView,params);
		}
		
		mEmptyView = createEmptyView();
		if(null != mEmptyView){
			addView(mEmptyView, params);
		}
	}
	
	/**
	 * 外部调用设置状态并根据状态进行显示
	 * @param state
	 */
	public void setStateAndShow(int state){
		mCurrentState = state;
		showPageView();		
	}

	/**
	 * 根据状态显示不同的布局
	 */
	private void showPageView() {
		
		if(null != mSucceedView && mCurrentState == STATE_LOADING){
			this.bringChildToFront(mLoadingView);
			mLoadingView.setVisibility(View.VISIBLE);
			mSucceedView.setVisibility(View.VISIBLE);
			return;
		}
		
		if (null != mLoadingView) {
//			this.bringChildToFront(mLoadingView);
			mLoadingView.setVisibility(mCurrentState == STATE_UNLOADED || mCurrentState == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
		}
		if (null != mErrorView) {
			mErrorView.setVisibility(mCurrentState == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
		}
		if (null != mEmptyView) {
			mEmptyView.setVisibility(mCurrentState == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
		}
		
		// 只有数据成功返回了，才知道成功的View该如何显示，因为该View的显示依赖加载完毕的数据
		if (mCurrentState == STATE_SUCCEED && mSucceedView == null) {
			mSucceedView = createLoadedView();
			addView(mSucceedView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		if (null != mSucceedView) {
			mSucceedView.setVisibility(mCurrentState == STATE_SUCCEED ? View.VISIBLE : View.INVISIBLE);
		}
	}
	
	/**
	 * 恢复初始状态
	 */
	public void reset(){
		mCurrentState = STATE_UNLOADED;
		showPageView();
	}
	
	protected boolean needReset(){
		return mCurrentState == STATE_ERROR || mCurrentState == STATE_EMPTY;
	}
	
	/**
	 * 加载成功的View
	 */
	public abstract View createLoadedView() ;

	/**
	 * 创建空布局
	 */
	private View createEmptyView() {
		return View.inflate(context, R.layout.loading_page_empty, null);
	}

	/**
	 * 创建加载错误的布局
	 */
	private View createErrorView() {
		View view = View.inflate(context, R.layout.loading_page_error, null);
		Button mReloadBtn = (Button) view.findViewById(R.id.bt_reload);
		mReloadBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setReload();
			}
		});
		
		return view;
	}

	/**
	 * 当加载失败时，点击重新加载，此方法被调用
	 */
	public void setReload() {
	}

	/**
	 * 创建正在加载中的布局
	 */
	private View createLoadingView() {
		return View.inflate(context, R.layout.loading_page_loading, null);
	}
}
