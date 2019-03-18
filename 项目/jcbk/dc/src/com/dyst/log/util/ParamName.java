package com.dyst.log.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ParamName {

	public static Properties paramNameProps = null;
	/**
	 * 获取参数文件
	 * @return
	 */
	public static Properties getParamNameProps() {
		if(paramNameProps == null){
			InputStream in = null;
			try {
				paramNameProps = new Properties();
				in = ParamName.class.getResourceAsStream("/com/dyst/log/util/paramName.properties");
				paramNameProps.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return paramNameProps;
	}
}
