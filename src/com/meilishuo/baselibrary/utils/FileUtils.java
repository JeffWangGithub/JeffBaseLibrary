package com.meilishuo.baselibrary.utils;

import android.content.Context;
import android.os.Environment;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.meilishuo.baselibrary.log.LogUtils;

/**
 * 文件相关的工具类
 * Created by Jeff
 */
public class FileUtils {
	/**当前应用在SD卡的跟目录*/
	public static final String ROOT_DIR = "Jeff";
	public static final String DOWNLOAD_DIR = "download";
	public static final String CACHE_DIR = "cache";
	public static final String ICON_DIR = "icon";
	
	private static final String mTag = "FileUtils";

	/** 判断SD卡是否挂载 */
	public static boolean isSDCardAvailable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return true;
		} else {
			return false;
		}
	}

	/** 获取下载目录 */
	public static String getDownloadDir(Context context) {
		return getDir(DOWNLOAD_DIR,context);
	}

	/** 获取缓存目录 */
	public static String getCacheDir(Context context) {
		return getDir(CACHE_DIR,context);
	}

	/** 获取icon目录 */
	public static String getIconDir(Context context) {
		return getDir(ICON_DIR,context);
	}

	/** 获取应用目录，当SD卡存在时，获取SD卡上的目录，当SD卡不存在时，获取应用的cache目录 */
	public static String getDir(String name,Context context) {
		StringBuilder sb = new StringBuilder();
		if (isSDCardAvailable()) {
			sb.append(getExternalStoragePath());
		} else {
			sb.append(getCachePath(context));
		}
		sb.append(name);
		sb.append(File.separator);//File.separator  jdk提供的用于分割路径的分隔符(/),使用此字段不需要考虑不同平台兼容性
		String path = sb.toString();
		if (createDirs(path)) {
			return path;
		} else {
			return null;
		}
	}

	/** 根据字段ROOT_DIR,获取SD下的应用目录 */
	public static String getExternalStoragePath() {
		StringBuilder sb = new StringBuilder();
		sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
		sb.append(File.separator);
		sb.append(ROOT_DIR);
		sb.append(File.separator);
		return sb.toString();
	}

	/** 获取应用的cache目录 */
	public static String getCachePath(Context context) {
		File f = context.getCacheDir();
		if (null == f) {
			return null;
		} else {
			return f.getAbsolutePath() + "/";
		}
	}

	/** 创建文件夹 */
	public static boolean createDirs(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists() || !file.isDirectory()) {
			return file.mkdirs();
		}
		return true;
	}

	/** 复制文件，可以选择是否删除源文件 */
	public static boolean copyFile(String srcPath, String destPath, boolean deleteSrc) {
		File srcFile = new File(srcPath);
		File destFile = new File(destPath);
		return copyFile(srcFile, destFile, deleteSrc);
	}

	/** 复制文件，可以选择是否删除源文件 */
	public static boolean copyFile(File srcFile, File destFile, boolean deleteSrc) {
		if (!srcFile.exists() || !srcFile.isFile()) {
			return false;
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = in.read(buffer)) > 0) {
				out.write(buffer, 0, i);
				out.flush();
			}
			if (deleteSrc) {
				srcFile.delete();
			}
		} catch (Exception e) {
			return false;
		} finally {
			closeIO(out);
			closeIO(in);
		}
		return true;
	}
	
	private static void closeIO(Closeable io){
		if(io != null){
			try {
				io.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** 判断文件是否可写 */
	public static boolean isWriteable(String path) {
		try {
			if (StringUtils.isEmpty(path)) {
				return false;
			}
			File f = new File(path);
			return f.exists() && f.canWrite();
		} catch (Exception e) {
			return false;
		}
	}

	/** 修改文件的权限,例如"777"等 */
	public static void chmod(String path, String mode) {
		try {
			String command = "chmod " + mode + " " + path;
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);
		} catch (Exception e) {
		}
	}

	/**
	 * 把数据写入文件
	 * @param is       数据流
	 * @param path     文件路径
	 * @param recreate 如果文件存在，是否需要删除重建
	 * @return 是否写入成功
	 */
	public static boolean writeFile(InputStream is, String path, boolean recreate) {
		boolean res = false;
		File f = new File(path);
		FileOutputStream fos = null;
		try {
			if (recreate && f.exists()) {
				f.delete();
			}
			if (!f.exists() && null != is) {
				File parentFile = new File(f.getParent());
				parentFile.mkdirs();
				int count = -1;
				byte[] buffer = new byte[1024];
				fos = new FileOutputStream(f);
				while ((count = is.read(buffer)) != -1) {
					fos.write(buffer, 0, count);
				}
				res = true;
			}
		} catch (Exception e) {
			LogUtils.e(mTag,e);
		} finally {
			closeIO(fos);
			closeIO(is);
		}
		return res;
	}

	/**
	 * 把字符串数据写入文件
	 * @param content 需要写入的字符串
	 * @param path    文件路径名称
	 * @param append  是否以添加的模式写入
	 * @return 是否写入成功
	 */
	public static boolean writeFile(byte[] content, String path, boolean append) {
		boolean res = false;
		File f = new File(path);
		RandomAccessFile raf = null;
		try {
			if (f.exists()) {
				if (!append) {
					f.delete();
					f.createNewFile();
				}
			} else {
				f.createNewFile();
			}
			if (f.canWrite()) {
				raf = new RandomAccessFile(f, "rw");
				raf.seek(raf.length());
				raf.write(content);
				res = true;
			}
		} catch (Exception e) {
			LogUtils.e(mTag,e);
		} finally {
			closeIO(raf);
		}
		return res;
	}

	/**
	 * 把字符串数据写入文件
	 * @param content 需要写入的字符串
	 * @param path    文件路径名称
	 * @param append  是否以添加的模式写入
	 * @return 是否写入成功
	 */
	public static boolean writeFile(String content, String path, boolean append) {
		return writeFile(content.getBytes(), path, append);
	}

	/**
	 * 把键值对写入文件(Properties文件)
	 * @param filePath 文件路径
	 * @param key      键
	 * @param value    值
	 * @param comment  该键值对的注释
	 */
	public static void writeProperties(String filePath, String key, String value, String comment) {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(filePath)) {
			return;
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File f = new File(filePath);
		try {
			if (!f.exists() || !f.isFile()) {
				f.createNewFile();
			}
			fis = new FileInputStream(f);
			Properties p = new Properties();
			p.load(fis);// 先读取文件，再把键值对追加到后面
			p.setProperty(key, value);
			fos = new FileOutputStream(f);
			p.store(fos, comment);
		} catch (Exception e) {
			LogUtils.e(mTag,e);
		} finally {
			closeIO(fis);
			closeIO(fos);
		}
	}

	/** 根据键读取Properties文件中的值 */
	public static String readProperties(String filePath, String key, String defaultValue) {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(filePath)) {
			return null;
		}
		String value = null;
		FileInputStream fis = null;
		File f = new File(filePath);
		try {
			if (!f.exists() || !f.isFile()) {
				f.createNewFile();
			}
			fis = new FileInputStream(f);
			Properties p = new Properties();
			p.load(fis);
			value = p.getProperty(key, defaultValue);
		} catch (IOException e) {
			LogUtils.e(mTag,e);
		} finally {
			closeIO(fis);
		}
		return value;
	}

	/** 把字符串键值对的map写入文件(Properties文件) */
	public static void writeMap(String filePath, Map<String, String> map, boolean append, String comment) {
		if (map == null || map.size() == 0 || StringUtils.isEmpty(filePath)) {
			return;
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File f = new File(filePath);
		try {
			if (!f.exists() || !f.isFile()) {
				f.createNewFile();
			}
			Properties p = new Properties();
			if (append) {
				fis = new FileInputStream(f);
				p.load(fis);// 先读取文件，再把键值对追加到后面
			}
			p.putAll(map);
			fos = new FileOutputStream(f);
			p.store(fos, comment);
		} catch (Exception e) {
			LogUtils.e(mTag,e);
		} finally {
			closeIO(fis);
			closeIO(fos);
		}
	}

	/** 把字符串键值对的文件读入map */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static Map<String, String> readMap(String filePath, String defaultValue) {
		if (StringUtils.isEmpty(filePath)) {
			return null;
		}
		Map<String, String> map = null;
		FileInputStream fis = null;
		File f = new File(filePath);
		try {
			if (!f.exists() || !f.isFile()) {
				f.createNewFile();
			}
			fis = new FileInputStream(f);
			Properties p = new Properties();
			p.load(fis);
			map = new HashMap<String, String>((Map) p);// 因为properties继承了map，所以直接通过p来构造一个map
		} catch (Exception e) {
			LogUtils.e(mTag,e);
		} finally {
			closeIO(fis);
		}
		return map;
	}

	/** copy文件 */
	public static boolean copy(String src, String des, boolean delete) {
		File file = new File(src);
		if (!file.exists()) {
			return false;
		}
		File desFile = new File(des);
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(file);
			out = new FileOutputStream(desFile);
			byte[] buffer = new byte[1024];
			int count = -1;
			while ((count = in.read(buffer)) != -1) {
				out.write(buffer, 0, count);
				out.flush();
			}
		} catch (Exception e) {
			LogUtils.e(mTag,e);
			return false;
		} finally {
			closeIO(in);
			closeIO(out);
		}
		if (delete) {
			file.delete();
		}
		return true;
	}
	
	/**
	 * 格式化文件大小，保留末尾的0，达到长度一致
	 * 类似于android提供的Format.formateFileSize（context，long ）方法 
	 * @param len
	 * @param keepZero
	 */
	public static String formatFileSize(long len, boolean keepZero) {
		String size;
		DecimalFormat formatKeepTwoZero = new DecimalFormat("#.00");
		DecimalFormat formatKeepOneZero = new DecimalFormat("#.0");
		if (len < 1024) {
			size = String.valueOf(len + "B");
		} else if (len < 10 * 1024) {
			// [0, 10KB)，保留两位小数
			size = String.valueOf(len * 100 / 1024 / (float) 100) + "KB";
		} else if (len < 100 * 1024) {
			// [10KB, 100KB)，保留一位小数
			size = String.valueOf(len * 10 / 1024 / (float) 10) + "KB";
		} else if (len < 1024 * 1024) {
			// [100KB, 1MB)，个位四舍五入
			size = String.valueOf(len / 1024) + "KB";
		} else if (len < 10 * 1024 * 1024) {
			// [1MB, 10MB)，保留两位小数
			if (keepZero) {
				size = String.valueOf(formatKeepTwoZero.format(len * 100 / 1024 / 1024 / (float) 100)) + "MB";
			} else {
				size = String.valueOf(len * 100 / 1024 / 1024 / (float) 100) + "MB";
			}
		} else if (len < 100 * 1024 * 1024) {
			// [10MB, 100MB)，保留一位小数
			if (keepZero) {
				size = String.valueOf(formatKeepOneZero.format(len * 10 / 1024 / 1024 / (float) 10)) + "MB";
			} else {
				size = String.valueOf(len * 10 / 1024 / 1024 / (float) 10) + "MB";
			}
		} else if (len < 1024 * 1024 * 1024) {
			// [100MB, 1GB)，个位四舍五入
			size = String.valueOf(len / 1024 / 1024) + "MB";
		} else {
			// [1GB, ...)，保留两位小数
			size = String.valueOf(len * 100 / 1024 / 1024 / 1024 / (float) 100) + "GB";
		}
		return size;
	}
	
	
	
	/**
     * 递归删除制定文件夹中所有的文件，（注意只删除文件而不删除文件夹）
     * 
     * @param file
     */ 
    public static void deleteAllFileFromDir(File file) { 
        if (file.exists()) { 
            if (file.isFile()) { 
                file.delete(); 
            } else if (file.isDirectory()) { 
                File files[] = file.listFiles(); 
                for (int i = 0; i < files.length; i++) { 
                	deleteAllFileFromDir(files[i]); 
                } 
            } 
//            file.delete(); 
        } else { 
            LogUtils.e(mTag, "delete file no exists " + file.getAbsolutePath()); 
        } 
    } 
    
    /**
     * 递归删除 文件/文件夹
     * @param file
     */
    public static void deleteFile(File file) { 
        if (file.exists()) { 
            if (file.isFile()) { 
                file.delete(); 
            } else if (file.isDirectory()) { 
                File files[] = file.listFiles(); 
                for (int i = 0; i < files.length; i++) { 
                    deleteFile(files[i]); 
                } 
            } 
            file.delete(); 
        } else { 
            LogUtils.e(mTag, "delete file no exists " + file.getAbsolutePath()); 
        } 
    } 
    
    
	/**
	 * 获取文件大小
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File f) throws Exception// 取得文件夹大小
	{
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}
	
}
