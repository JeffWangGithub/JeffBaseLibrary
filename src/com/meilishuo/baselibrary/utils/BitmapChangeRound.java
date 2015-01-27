package com.meilishuo.baselibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * 将一个Bitmap图形转换成圆形
 * 
 * @author Jeff
 */
public class BitmapChangeRound {
	/** 
     * 转换图片成圆形 
     * 这个方法是根据传入的图片的高度（height）和宽度（width）决定的，如果是 width <= height 时，则会裁剪高度，
     * 裁剪的区域是宽度不变高度从顶部到宽度width的长度；如果 width > height，则会裁剪宽度，裁剪的区域是高度不变，
     * 宽度是取的图片宽度的中心区域，不过不同的业务需求，对裁剪图片要求不一样，可以根据业务的需求来调整裁剪的区域
     * @param bitmap 
     *            传入Bitmap对象 
     * @return 转换成圆形的Bitmap对象
     */  
    public static Bitmap toRoundBitmap(Bitmap bitmap) {  
        int width = bitmap.getWidth();  
        int height = bitmap.getHeight();  
        float roundPx;  
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;  
        if (width <= height) {  
            roundPx = width / 2;  
            left = 0;  
            top = 0;  
            right = width;  
            bottom = width;  
            height = width;  
            dst_left = 0;  
            dst_top = 0;  
            dst_right = width;  
            dst_bottom = width;  
        } else {  
            roundPx = height / 2;  
            float clip = (width - height) / 2;  
            left = clip;  
            right = width - clip;  
            top = 0;  
            bottom = height;  
            width = height;  
            dst_left = 0;  
            dst_top = 0;  
            dst_right = height;  
            dst_bottom = height;  
        }  
  
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);  
        Canvas canvas = new Canvas(output);  
  
        final int color = 0xff424242;  
        final Paint paint = new Paint();  
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);  
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);  
        final RectF rectF = new RectF(dst);  
  
        paint.setAntiAlias(true);// 设置画笔无锯齿  
  
        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas  
        paint.setColor(color);  
  
        // 以下有两种方法画圆,drawRounRect和drawCircle  
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。  
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);  
  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452  
        canvas.drawBitmap(bitmap, src, dst, paint); //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle  
          
        return output;  
    }  
	

}
