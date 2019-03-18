package com.dyst.activemq.cas;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

public class JsonUtil {
	 private static SerializeConfig mapping = new SerializeConfig();
	 public static final String dateFormat = "yyyy-MM-dd";
	 public static final String datetimeFormat = "yyyy-MM-dd HH:mm:ss";
	 static {
		 mapping.put(java.sql.Date.class, new SimpleDateFormatSerializer(dateFormat));
		 mapping.put(java.util.Date.class, new SimpleDateFormatSerializer(datetimeFormat));
		 mapping.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer(datetimeFormat));
	 }
	    
	// 把JSON文本parse为JavaBean 
	public static final <T> T parseObject(String text, Class<T> clazz){
		return JSON.parseObject(text, clazz);
	}

	public static final <T> List<T> parseList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }
	// 将JavaBean序列化为JSON文本    默認处理时间
	public static final String toJSON(Object object){
		//对于Date 类型，转换为 yyyy-MM-dd
		 return JSON.toJSONString(object,mapping);
	} 
	
	/**
	 * 根据properties 动态过滤只显示的字段数据
	 * @param object
	 * @param properties
	 * @return
	 */
  	public static String toJSON2(Object object, final String... properties) {
		if(properties.length>0)
		{
			PropertyFilter filter = new PropertyFilter() {  
	            public boolean apply(Object source, String name, Object value) {  
	                if(ArrayUtils.contains(properties, name)){  
	                    return true;  
	                }  
	                return false;  
	            }  
	        };
	        return JSON.toJSONString(object, filter);
		}
		return JSON.toJSONString(object);
	}
}
