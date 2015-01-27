package com.meilishuo.baselibrary.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CollectionUtils {

	/**
	 * 将数组转换成List
	 * 
	 * @param list
	 * @param array
	 */
	public static <T> void arrayToList(List<T> list, T[] array) {
		Arrays.asList(array);
	}

	/**
	 * 将数组转换成Set
	 * 
	 * @param set
	 * @param array
	 */
	public static <T> void arrayToSet(Set<T> set, T[] array) {
		for (T t : array) {
			set.add(t);
		}
	}

	/**
	 * 集合转换成数组
	 * 
	 * @param collection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object[] CollectionToArray(Collection collection) {
		return collection.toArray();
	}

	/**
	 * 去除List中的重复数据
	 * @param list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List removeDuplicate(List list) {
		  Set set  =   new  HashSet();
	       List newList  =   new  ArrayList();
	    for  (Iterator iter  =  list.iterator(); iter.hasNext();)   {
	          Object element  =  iter.next();
	          if  (set.add(element))
	             newList.add(element);
	      }
//	      list.addAll(newList);
	    return newList;
	}

}
