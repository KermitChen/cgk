package com.dyst.test;

import org.junit.BeforeClass;
import org.junit.Test;

import com.dyst.elasticsearch.util.ESClientManager;
import com.dyst.service.AggsService;
import com.dyst.service.ClientServiceQuery;
import com.dyst.utils.Config;

import static org.junit.Assert.*;

public class TestJuint {

	private static AggsService aggs = new AggsService();
	private static ClientServiceQuery query = new ClientServiceQuery();
	@BeforeClass
	public static void beforeClass(){
		//初始化配置文件信息
		Config.getInstance();
		//ES数据库连接池
		ESClientManager.getInstance(); 
	}
	@Test
	public void testTjBusinessLog(){
		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
		+ "<head><pagesize>10</pagesize></head><body>"
		+ "<data><kssj>2016-6-8 09:13:59</kssj>"
		+ "<jssj>2016-7-29 09:14:12</jssj>"
		+ "<tjWord>operator</tjWord>"
		+ "<ip></ip><operator></operator><operateName></operateName>"
		+ "</data></body></root>";
		assertEquals("test", aggs.tjBusinessLog(str_xml));
	}
	
	public void testZfyc(){
		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
				+ "<head><pagesize>10</pagesize></head><body>"
				+ "<data><kssj>2016-6-8 09:13:59</kssj>"
				+ "<jssj>2016-7-29 09:14:12</jssj>"
				+ "<tjWord>operator</tjWord>"
				+ "<ip></ip><operator></operator><operateName></operateName>"
				+ "</data></body></root>";
		assertEquals("test", aggs.tjBusinessLog(str_xml));
	}
}
