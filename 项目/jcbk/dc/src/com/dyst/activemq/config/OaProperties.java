package com.dyst.activemq.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * oa配置
 * 
 * @author cgk
 *
 */

public class OaProperties {
	public static Properties props = new Properties();

	public static Properties getProperties() {
		InputStream in = OaProperties.class.getResourceAsStream("/com/dyst/activemq/config/oa.properties");
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
}