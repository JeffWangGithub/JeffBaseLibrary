package com.meilishuo.baselibrary.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Encoder {
	/**
	 * 进行md5加密
	 * @param value
	 */
	public static String getMD5Value(String value){
		if(value == null){
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] md5Value = messageDigest.digest(value.getBytes());
			BigInteger bigInteger = new BigInteger(1, md5Value);
			return bigInteger.toString(16);
		} catch (Exception e) {
			return value;
		}
	}
	
}
