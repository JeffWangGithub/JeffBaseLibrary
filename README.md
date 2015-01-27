# JeffBaseLibrary
This is my person android library  filled with useful tools that i have built in MLS, I will improve them as I continue my job as an Android Developers

# Android基础库--工具类
### 一、com.meilishuo.baselibrary.log 日志工具包
    
---
#####1, LogUtils 日志工具类：
---

主要作用是在开放中进行调试日志的输出。方便项目上线时，统一处理不让程序进行日志的输出。

`注意：当项目发布时，不让程序在输出任何日志信，只需调用allowD（）方法，传递false参数即可。`

    /**
     * 是否屏蔽所有的输出日志
     * @param 传递true表示允许输出；false表示不允许输出。
     */
    public static void allowD(boolean isAllow)
    	
         /**
    	 * 输出verbose日志信息
    	 * @param mTag
    	 * @param str
    	 */
    	public static void v(String mTag, String str)
    	
		/**
    	 * 输出debug日志信息
    	 * @param mTag
    	 * @param str
    	 */
    	public static void d(String mTag, String str) 
    	
    	/**
    	 * 输出info日志信息
    	 * @param mTag
    	 * @param str
    	 */
    	public static void i(String mTag, String str)
    	
    	/**
    	 * 输出warning日志信息
    	 * @param mTag
    	 * @param str
    	 */
    	public static void w(String mTag, String str) 
    	
    	/**
    	 * 输出error级别的日志信息
    	 * @param mTag
    	 * @param str
    	 */
    	public static void e(String mTag, String str)
    

### 二、com.meilishuo.baselibrary.net  访问网络相关的模块
---
##### 1，NetUtils 判断用户联网方式的工具类
---

    /**检查网络是否处于可用状态
	 * @param context
	 * @return 返回true表示联网正常；false表示没有联网。
	 */
	public static boolean checkNetWork(Context context)

	/**
	 * 判断手机基站网络是否可用
	 * @param context
	 */
	public static boolean isBaseStationConnection(Context context) 

	/**
	 * 判断wifi网络是否可用
	 * @param context
	 */
	public static boolean isWifiConnection(Context context) 

    /**
	 * 判断当前网络的类型
	 * @param context
	 * @return 返回wifi表示：当前为wifi网络；返回mobile表示当前网络为手机基站网络；
	 * 		返回null表示当前没有开启网络服务。
	 */
	public static String checkNetType(Context context)

###三、第三方库xUtils
xUtils: xUtils 包含了很多实用的android工具.
    
    #1，HttpUtils模块：
        支持同步，异步方式的请求；
        支持大文件上传，上传大文件不会oom；
        支持GET，POST，PUT，MOVE，COPY，DELETE，HEAD，OPTIONS，TRACE，CONNECT请求；
        
    #2，BitmapUtils模块：
        加载bitmap的时候无需考虑bitmap加载过程中出现的oom和android容器快速滑动时候出现的图片错位等现象；
        支持加载网络图片和本地图片；
        内存管理使用lru算法，更好的管理bitmap内存；
        
    #3，ViewUtils模块：
        android中的ioc框架，完全注解方式就可以进行UI，资源和事件绑定；

使用的简单说明。详细使用见：https://github.com/wyouflf/xUtils
        
    /**
    *发送请求，制定请求方式，url，创建回调
    */
    public <T> HttpHandler<T> send(HttpRequest.HttpMethod method, String url,
                                   RequestCallBack<T> callBack) 
    public <T> HttpHandler<T> send(HttpRequest.HttpMethod method, String url, RequestParams params,
                                   RequestCallBack<T> callBack)
    /**
    *进行下载，包含一系列的重载方法
    */
    public HttpHandler<File> download（）

### 四、com.meilishuo.baselibrary.utils  存放公共可用的常用的工具类
---
##### 1，MD5Encoder ：处理字符串的md5加密
---
	
	/**
	 * 进行md5加密
	 * @param value
	 */
	public static String getMD5Value(String value)
---
##### 2，StringUtils :处理字符串的常见操（也可用TextUtils进行处理）
--- 
    /** 判断字符串是否有值，
	 * 如果为null或者是空字符串或者只有空格或者为"null"字符串，
	 * 则返回true，否则则返回false */
	public static boolean isEmpty(String value) 

	/** 判断多个字符串是否相等，
	 * 如果其中有一个为空字符串或者null，则返回false，
	 * 只有全相等才返回true */
	public static boolean isEquals(String... agrs)

    /**
	 * 返回一个高亮spannable对象
	 * @param content 文本内容
	 * @param color   高亮颜色
	 * @param start   起始位置
	 * @param end     结束位置
	 * @return 高亮spannable
	 */
	public static CharSequence getHighLightText(String content, int color, int start, int end) 

	/**
	 * 获取链接样式的字符串，即字符串下面有下划线（类似HTML中超链接的样式）
	 * @param resId 文字资源
	 * @return 返回链接样式的字符串
	 */
	public static Spanned getHtmlStyleString(Context context,int resId) 

	/**
	 * 获取链接样式的字符串，即字符串下面有下划（类似HTML中超链接的样式）
	 * @param 文字字符串
	 * @return 返回链接样式的字符串
	 */
	public static Spanned getHtmlStyleString(String res)

	/**
	 * 格式化文件大小，保留末尾的0，达到长度一致
	 * 类似于android提供的Format.formateFileSize（context，long ）方法 
	 * @param len
	 * @param keepZero
	 */
	public static String formatFileSize(long len, boolean keepZero) 

---
##### 3，BaseUIUtils 处理一些组基本的UI相关的操作
---
	
	/**显示Toast，只运行当前应用显示一个Toast*/
	public static void showToast(Context mContext, String msg)

	/**dip转换成px，主要用于屏幕适配*/
	public static int dip2px(Context context, int dip)
	
	/**px转换成dip，主要用于屏幕的适 */
	public static int px2dip(Context context, int px) 

	/** 获取状态栏高度
	 * @return 返回值的单位为像素px
	 */
	public static int getStatusBarHeight(View v)
	
    /** 获取资源文件中的文字数组 */
	public static String[] getStringArray(Context context,int resId)

    /** 获取dimen */
	public static int getDimens(Context context,int resId)

	/** 获取drawable */
	public static Drawable getDrawable(Context context,int resId) 
	
	/** 获取颜色 */
	public static int getColor(Context context,int resId) 

	/** 获取颜色选择器 */
	public static ColorStateList getColorStateList(Context context,int resId)

---
####4，GenometryUtils 简单的几何工具类
---
	/**
	 * 获得两点之间的距离
	 * @return 返回值单位为像素
	 */
	public static float getDistanceBetween2Points(PointF p0, PointF p1)

	/**
	 * Get middle point between p1 and p2.
	 * 获得两点连线的中点
	 */
	public static PointF getMiddlePoint(PointF p1, PointF p2)

	/**
	 * Get point between p1 and p2 by percent.
	 * 根据百分比获取两点之间的某个点坐标
	 * @param p1 第一个点
	 * @param p2 第二个点
	 * @param percent  百分比
	 */
	public static PointF getPointByPercent(PointF p1, PointF p2, float percent)

	/**
	 * Get the point of intersection between circle and line.
	 * 获取 通过指定圆心，斜率为lineK的直线与圆的交点。
	 * @param pMiddle The circle center point.
	 * @param radius The circle radius.
	 * @param lineK The slope of line which cross the pMiddle.
	 * @return
	 */
	public static PointF[] getIntersectionPoints(PointF pMiddle, float radius, Double lineK) 

	/**
	 * 根据分度值，计算从start到end中，fraction位置的值。fraction范围为0 -> 1
	 * @param fraction: end和start间距的百分比
	 * @param start
	 * @param end
	 * @return :返回间距百分比所对应点实际位置
	 * eg: 1~5 之间,fraction=1/4, 那么返回值为1+4*fraction = 2.
	 */
	public static float evaluateValue(float fraction, Number start, Number end)

	/**
	 * 获取 通过指定圆心，斜率为lineK的直线与圆的交点。
	 * @param pMiddle: The circle center point.
	 * @param radius: The circle radius.
	 * @param lineK: The slope of line which cross the pMiddle.
	 */
	public static PointF[] getIntersectionPoints(PointF pMiddle, float radius, Double lineK) 

---
#####5，FileUtils 文件相关的工具类
---
    字段:
	/**当前应用在SD卡的跟目录,可根据实际状况进行更改*/
	public static final String ROOT_DIR = "Jeff";
	
	方法:
	/** 判断SD卡是否挂载 */
	public static boolean isSDCardAvailable() 
	
	 /** 获取下载目录 */
	public static String getDownloadDir(Context context)
	
	/** 获取缓存目录 */
	public static String getCacheDir(Context context) 
	
	/** 获取icon目录 */
	public static String getIconDir(Context context) 

    /** 获取应用目录，当SD卡存在时，获取SD卡上的目录，当SD卡不存在时，获取应用的cache目录 */
	public static String getDir(String name,Context context)
	
	
	/** 根据字段Root_DIR,获取SD下的应用目录 */
	public static String getExternalStoragePath()
	
	/** 获取应用的cache目录 */
	public static String getCachePath(Context context)
	
	/** 创建文件夹 */
	public static boolean createDirs(String dirPath)
	
	/** 复制文件，可以选择是否删除源文件 */
	public static boolean copyFile(String srcPath, String destPath, boolean deleteSrc) 
	
	/** 复制文件，可以选择是否删除源文件 */
	public static boolean copyFile(File srcFile, File destFile, boolean deleteSrc) 
	
	/** 判断文件是否可写 */
	public static boolean isWriteable(String path)
	
	/** 修改文件的权限,例如"777"等 */
	public static void chmod(String path, String mode)
	
	/**
	 * 把数据写入文件
	 * @param is       数据流
	 * @param path     文件路径
	 * @param recreate 如果文件存在，是否需要删除重建
	 * @return 是否写入成功
	 */
	public static boolean writeFile(InputStream is, String path, boolean recreate) 
	
    /**
	 * 把字符串数据写入文件
	 * @param content 需要写入的字符串
	 * @param path    文件路径名称
	 * @param append  是否以添加的模式写入
	 * @return 是否写入成功
	 */
	public static boolean writeFile(byte[] content, String path, boolean append) 
    
    /**
	 * 把字符串数据写入文件
	 * @param content 需要写入的字符串
	 * @param path    文件路径名称
	 * @param append  是否以添加的模式写入
	 * @return 是否写入成功
	 */
	public static boolean writeFile(String content, String path, boolean append)
	
    /**
	 * 把键值对写入文件
	 * @param filePath 文件路径
	 * @param key      键
	 * @param value    值
	 * @param comment  该键值对的注释
	 */
	public static void writeProperties(String filePath, String key, String value, String comment) 
	
    /** 根据键读取Properties文件中的值 */
	public static String readProperties(String filePath, String key, String defaultValue) 

    /** 把字符串键值对的map写入文件(Properties文件) */
	public static void writeMap(String filePath, Map<String, String> map, boolean append, String comment)
	
	/** 把字符串键值对的文件读入map */
	public static Map<String, String> readMap(String filePath, String defaultValue) 
	
---    
####6，SystemUtils 系统环境相关工具类
---
	
`手机系统相关的类，可获取设备信息和系统信息`
	
	/** 获取设备显示器的宽度，单位为像素 */
	public static int getDisplayWidth(Context context)
	
	/** 获取设备显示器的高度，单位为像素 */
	public static int getDisplayHeight(Context context) 
	
	/** 获取android系统版本号 */
	public static String getOSVersion() 
	
	/** 获得android系统sdk版本号 */
	@SuppressWarnings("deprecation")
	public static String getOSVersionSDK() 
	
	/** 获得android系统sdk版本号 */
	public static int getOSVersionSDKINT() 
	
	/** 获取手机型号 */
	public static String getDeviceModel()
	
	/** 获取设备的IMEI */
	public static String getIMEI(Context context)
	
	/** sim卡是否可读 */
	public static boolean isCanUseSim(Context context)
	
	/** 取得当前sim手机卡的imsi */
	public static String getIMSI(Context context) 
	
	/** 返回本地手机号码，这个号码不一定能获取到 */
	public static String getNativePhoneNumber(Context context) 
	
	/** 返回手机服务商名字 */
	public static String getProvidersName(Context context) 
	
	/** 获取当前设备的SN */
	public static String getSimSN(Context context) 
	
	/** 获取当前设备的MAC地址 */
	public static String getMacAddress(Context context) 
	
	/** 获得设备ip地址 */
	public static String getLocalAddress() 
	
	/**
	 * 获取屏幕的分辨率
	 * @param context
	 * @return 返回的是一个int类型的数组，int[0]表示屏幕的宽度，int[1]表示屏幕的高度
	 */
	@SuppressWarnings("deprecation")
	public static int[] getResolution(Context context) 

    /** 获得设备的横向dpi */
	public static float getWidthDpi(Context context) 
	
	/** 获得设备的纵向dpi */
	public static float getHeightDpi(Context context) 
	
	/**
	 *  获取设备信息
	 *  获取的是/proc/cpuinfo文件中得头两头信息：processor处理器信息和vendor_id供应商id信息
	 * @return 数组的第0个元素为processor处理器信息；第一个元素为vendor_id供应商id信息
	 */
	public static String[] getDivceInfo() 

    /** 判断手机CPU是否支持ARM NEON指令集 */
	public static boolean isNEON() 
	
	/** 读取CPU信息文件，获取CPU信息 */
	private static String getCPUInfos()
	
	/** 获取当前设备cpu的型号 */
	public static int getCPUModel() 
	
	/** 获取CPU核心数 */
	public static int getCpuCount() 
	
	/** 获取Rom版本 */
	public static String getRomversion()
	
	/** 获取系统配置参数 */
	public static String getSystemProperty(String key) 
	
	/** 获取系统中的Library包 */
	public static List<String> getSystemLibs(Context context)
	
	/** 获取手机外部可用空间大小，单位为byte */
	public static long getExternalTotalSpace() 
	
	/** 判断SD卡是否挂载 */
	public static boolean isSDCardAvailable() 
	
	/** 获取外部存储可用空间，单位为byte */
	public static long getExternalSpace()
	
	/** 获取手机内部空间大小，单位为byte */
	public static long getTotalInternalSpace() 
	
	/** 获取手机内部可用空间大小，单位为byte */
	public static long getAvailableInternalMemorySize() 
	
	/** 获取单个应用最大分配内存，单位为byte */
	public static long getOneAppMaxMemory(Context context)
	
	/** 获取本应用占用的内存，单位为byte */
	public static long getUsedMemory(Context context) 
	
	/** 获取指定包名应用占用的内存，单位为byte */
	public static long getUsedMemory(String packageName,Context context) 
	
	/** 获取手机剩余内存，单位为byte */
	public static long getAvailableMemory(Context context) 
	
	/** 获取手机总内存(运行内存)，单位为byte */
	public static long getTotalMemory() 
	
	/** 根据值读取Properties中得信息 */
	public static String readProperties(String filePath, String key, String defaultValue)
	
	/** 手机低内存运行阀值，当内存低于这个值时,将进行内存回收的操作.单位为byte */
	public static long getThresholdMemory(Context context) 
	
	/** 手机是否处于低内存运行 */
	public static boolean isLowMemory(Context context) 
	
###Android基础库--常用自定义控件

---
#####1, LoadingPager
---

显示不同状态下需要显示的页面，此控件基本上再所有的应用中都会用到，一般有正常加载状态，网络异常状态，加载失败状态等等。


   	//使用：
   		mMainLoadingPager = new LoadingPager(this) {
			@Override
			public View createLoadedView() {
				return view; // 返回一个加载成功的view对象
			}

			@Override
			public void setReload() {
				MainActivity.this.reload();
			}
		};

    
---
#####2, BaseListView
---
去除了ListView的一些缓存背景，因为不同版本下，缓存和点击后效果不同。需要时自己设置背景和点击背景即可，方便app在不同系统版本中运行。
       
    使用和ListView相同。

---
#####3, BaseHolder， MyBaseAdapter
---

实现了ListView的面向Holder的编程。使用时只需要将MyBaseAdapter看做是负责整体的数据和界面，Holder负责单个条目的数据和界面。

---
#####4, MyToast 自定义Toast
---

系统自带的Toast外观丑陋，并且位置固定，不可改变。此自定义MyToast可以更改Toast背景和文字大小，以及相关的位置。


   	//使用：
	/**
	 * 显示自定义的MyToast，只运行当前应用显示一个Toast
	 */
	public static void showCustomerToast(Context mContext, String msg) {
		MyToast.getInstance().showMyToast(mContext, msg, 2000, R.color.green);
	}

    

---
#####5, ZoomableImageView 支持手势缩放的ImageView
---

支持手势缩放的ImageView

   	//使用：
        	ZoomableImageView view = new ZoomableImageView(ShowWebImageActivity.this);
			//注册ImageView的触摸监听事件
			view.setOnImageTouchedListener(ShowWebImageActivity.this);
			view.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mls_logo));

---
#####6, SettingView 
---

设置界面常用的自定义组合控件

    //使用
    <com.meilishuo.baselibrary.widget.SettingView
        xmlns:wgc="http://schemas.android.com/apk/res-auto"
        android:id="@+id/sv_auto_login"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        wgc:des_off="关闭"
        wgc:des_on="开启"
        wgc:titleTop="是否自动登录" />

---
#####7, SettingEnterView 
---

设置界面常用的自定义组合控件

    //使用
    <com.meilishuo.baselibrary.widget.SettingEnterView
        android:id="@+id/sev_cache"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:gravity="clip_vertical"
        wgc:des="0M"
        wgc:titleTop="清理缓存" >
    </com.meilishuo.baselibrary.widget.SettingEnterView>


---
#####8, PullDownEditText  实现点击弹出popupWindow，仿QQ空间多用户登录的控件
---

设置界面常用的自定义组合控件

    //使用
        <com.meilishuo.baselibrary.widget.PullDownEditText
            xmlns:wgc="http://schemas.android.com/apk/res-auto"
            android:id="@+id/pet_username"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:onClick="true"
            wgc:hint="用户名" />
    //代码中使用，详见MLZZ的登录界面

###Android基础库--第三方常用开源控件

---
#####1, XListView和SwipeLayout

---
XListView实现了ListView的下拉刷新和加载更多的操作。

SwipeLayout实现了滑动显示其他操作条目(如删除、分享等)，XListView和SwipeLayout的结合使用就实现了ListView的下拉刷新，加载更多和条目左滑得效果。

```
具体使用：查看SwipeRefreshLoadListView的例子
```
   	
