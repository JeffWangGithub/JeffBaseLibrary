package com.meilishuo.baselibrary.image;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.meilishuo.baselibrary.log.LogUtils;
import com.meilishuo.baselibrary.utils.MD5Encoder;
import com.meilishuo.baselibrary.utils.SystemUtils;
import com.meilishuo.baselibrary.widget.ZoomableImageView;

/**
 * 可以放置OOM的ImageLoader
 * @author Jeff
 *
 */
public class ImageLoader2 {
	
	private String cacheDir ;
	private int memoryCacheSize;
	private Context context;
	
	private LruCache<String, Bitmap> mMemoryCache; //缓存到内存的LruCache类
	
	
	public ImageLoader2(Context context,int memoryCacheSize,String cacheDir) {
		this.context = context;
		this.memoryCacheSize = memoryCacheSize;
		this.cacheDir = cacheDir;
		init();
	}
	
	private void init() {
		
		long maxMemory = Runtime.getRuntime().maxMemory();//获取模拟器运行时可以使用的内存大小，默认16M
		
		int cacheSize = (int) (maxMemory/4);//大小的单位是字节
		
		if(memoryCacheSize < cacheSize){
			cacheSize = memoryCacheSize;
		}
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				//返回当前图片的大小，给Lrucache去计算当前缓存的内存大小
				return value.getRowBytes() * value.getHeight();
			}
		};
	}
	
	
	/**
	 * 加载并显示图片
	 * @param view
	 * @param url
	 */
	public void loadImage(View view,String url,String cookie){
		//从内存获取图片
		Bitmap bitmap = loadFromMemory(url);
		if(bitmap == null){
			//从本地加载
			bitmap = loadFromDisk(url);
		}
		
		if(bitmap == null){
			//从网络获取
			loadFromNet(view,url,cookie);
		}else {
			//如果从内存或者本地加载到直接设置给view
			if(view instanceof ZoomableImageView){
				((ZoomableImageView)view).setImageBitmap(bitmap);
			}else{
				((ImageView)view).setImageBitmap(bitmap);
			}
		}
	}

	/**
	 * 从网络获取图片
	 * 注意：此处使用的时投机的方法，先下载然后从本地加载
	 * @param url
	 */
	private void loadFromNet(final View view,final String url,String cookie) {
		
		String imageName = MD5Encoder.getMD5Value(url).substring(0,10)+".jpg";
		File file = new File(cacheDir, imageName);
		String target = file.getAbsolutePath();
		
		RequestParams params = new RequestParams();
		params.addHeader("Cookie", cookie);
		
		HttpUtils httpUtils = new HttpUtils();
		
		httpUtils.download(url, target, params, new RequestCallBack<File>() {
			@Override
			public void onSuccess(ResponseInfo<File> response) {
				Bitmap bitmap = loadFromDisk(url);
				if(view instanceof ZoomableImageView){
					((ZoomableImageView)view).setImageBitmap(bitmap);
				}else{
					((ImageView)view).setImageBitmap(bitmap);
				}
//				cacheToMemory(url,bitmap);
			}
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				LogUtils.d("ImageLoader2", "保存图片失败");
			}
		});
	}

	/**
	 * 将图片保存到内存
	 */
	protected void cacheToMemory(String url, Bitmap bitmap) {
		mMemoryCache.put(url, bitmap);
	}

	/**
	 * 从外部存储设备获取图片
	 * @param url
	 * @return
	 */
	private Bitmap loadFromDisk(String url) {
		
		String imageName = MD5Encoder.getMD5Value(url).substring(0,10)+".jpg";
		File file = new File(cacheDir, imageName);
		if(!file.exists()){	//如果该图片不存在直接返回null
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//进解析图片的配置信息
		BitmapFactory.decodeFile(file.getAbsolutePath(), options);
		
		//屏幕的高宽
		int displayWidth = SystemUtils.getDisplayWidth(context);
		int displayHeight = SystemUtils.getDisplayHeight(context);
//		System.out.println("displayWidth===="+displayWidth);
//		System.out.println("displayHeight===="+displayHeight);
		int inSampleSize = calculateInSampleSize(options, displayWidth, displayHeight);
//		int inSampleSize = 1;
		
		options.inJustDecodeBounds = false;//指定解析图片
		options.inSampleSize =inSampleSize;
		
		Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
		return bitmap;
	}

	/**
	 * 从内存获取图片
	 * @param url
	 * @return
	 */
	private Bitmap loadFromMemory(String url) {
		
		return mMemoryCache.get(url);
	}
	
    /**
     * 获取图片的缩放比
     * @param options
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public int calculateInSampleSize(BitmapFactory.Options options, int maxWidth, int maxHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (width > maxWidth || height > maxHeight) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) maxHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) maxWidth);
            }

            final float totalPixels = width * height;

            final float maxTotalPixels = maxWidth * maxHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > maxTotalPixels) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

}
