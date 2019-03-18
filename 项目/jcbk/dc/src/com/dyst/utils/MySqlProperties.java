package com.dyst.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 数据库配置
 * 
 * @author cgk
 *
 */

public class MySqlProperties {
	public static Properties props = new Properties();

	public static Properties getProperties() {
		InputStream in = MySqlProperties.class.getResourceAsStream("/config/jdbc.properties");
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
}