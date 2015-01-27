package com.meilishuo.baselibrary.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.meilishuo.baselibrary.utils.MD5Encoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;

/**
 * 图片工具类，用来获取图片，缓存图片
 * 使用xUtils访问网络
 * @author Jeff
 */
public class ImageLoader {
	
	/**从网络成功请求到图片 */
	public static final int LOAD_SUCCESS = 1; 
	/**从网络成功请求到图片 */
	public static final int LOAD_FAIL = 0; 

	private Handler handler;
	private  File cacheDir;	//图片的缓存目录
	private LruCache<String, Bitmap> mMemoryCache; //缓存到内存的LruCache类
	
//	private ExecutorService mExecutorService;
	private FileOutputStream fos;

	public ImageLoader(Handler handler, File cacheDir) {
		
		this.handler = handler;
		this.cacheDir = cacheDir;
		init();
	}
	
	private void init() {
		
		long maxMemory = Runtime.getRuntime().maxMemory();//获取模拟器运行时可以使用的内存大小，默认16M
		int cacheSize = (int) (maxMemory/8);//大小的单位是字节
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				//返回当前图片的大小，给Lrucache去计算当前缓存的内存大小
				return value.getRowBytes() * value.getHeight();
			}
		};
		//Executors管理线程池的工厂类
//		mExecutorService = Executors.newFixedThreadPool(5);//获取一个固定线程为5个的线程池对象
	}
	

	/**
	 * 加载图片，当加载失败时，显示默认的图片defaultImage
	 * #1，从内存中读取； #2，从本地读取； #3从网络读取
	 * @param view	装载所加载图片的ImageView
	 * @param url	需要加载的图片的url
	 * @param defaultImage	当加载失败时，显示默认的图片
	 */
	public Bitmap load(String url, int tag){
		//1,从内存中读取
		Bitmap bitmap = mMemoryCache.get(url);
		if(bitmap != null){
			return bitmap;
		}
		//2,从本地取
		bitmap = getBitmapFromLocal(url);
		if(bitmap != null){
			return bitmap;
		}
		
		//3,从网络取
		getBitmapFromNet(url, tag);//从网络获取图片后，方法内部直接传递给了对应的handler
		
		return null;
	}

	/**
	 * 从本地缓存获取图片
	 * @param url
	 * @return
	 */
	private Bitmap getBitmapFromLocal(String url) {
		try {
			String fileName = MD5Encoder.getMD5Value(url);
			File file = new File(cacheDir, fileName);
			Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 从网络获取图片
	 * @param url
	 * @param tag
	 */
	private void getBitmapFromNet(final String url,final int tag){
		HttpUtils http = new HttpUtils();
		
		String path = cacheDir+"/"+MD5Encoder.getMD5Value(url).substring(0,10)+".jpg";
		
		http.download(url, path, new RequestCallBack<File>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				
			}

			@Override
			public void onSuccess(ResponseInfo<File> responseInfo) {
				File file = responseInfo.result;
				Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
				
				Message msg = handler.obtainMessage();//此方法获取的msg对象，将Message.target = this;
				msg.obj = bitmap;
				msg.arg1 = tag;
				msg.what = LOAD_SUCCESS;
				msg.sendToTarget();//把消息发送给handler
				
				//存储到内存中
				mMemoryCache.put(url, bitmap);
				
				//存储到本地
//				saveToLocal(url, bitmap);
			}
		});
		
		
	}

	/**
	 * 存储到本地
	 * @param url
	 * @param bitmap
	 */
	public void saveToLocal(String url, Bitmap bitmap) {
		
		try {
			String encodeUrl = MD5Encoder.getMD5Value(url);
			String bitmapName = encodeUrl.substring(0, 10);//截取加密字符串的前十位作为图片的名字
			fos = new FileOutputStream(new File(cacheDir,
					bitmapName));
			bitmap.compress(CompressFormat.JPEG, 100, fos);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
