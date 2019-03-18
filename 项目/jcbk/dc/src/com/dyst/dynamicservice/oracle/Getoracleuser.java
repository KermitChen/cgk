package com.dyst.dynamicservice.oracle;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 动态勤务数据库配置 
 * @author Administrator
 *
 */
public class Getoracleuser {
	public static Properties props = new Properties();

	public static Properties getProperties() {
		InputStream in = Getoracleuser.class.getResourceAsStream("/com/dyst/dynamicservice/oracle/jlbb_jdbc.properties");
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
}
