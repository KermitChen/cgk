package com.dyst.base.utils;

import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author liuqiang
 *
 */
public class GetKeyByValue {

	/**
	 * 
	 * @param map map中存放键值对，例如，‘1’:‘有效’，2：已失效
	 * @param value 页面中传过来的值  “有效”
	 * @return 实体类中该类的静态map，有效在数据库中对应的char ‘1’
	 */
	public static String getKeyByValue(Map<String,String> map,String value){
		for(Entry<String, String> entry:map.entrySet()){
		    if(value.equals(entry.getValue())){
		    	return entry.getKey();
		    }
		}
		return null;
	}
}
