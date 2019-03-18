package com.dyst.test;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.xfire.client.Client;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
//import static org.elasticsearch.index.query.QueryBuilders.*;
//import static org.elasticsearch.index.query.FilterBuilders.*;
import org.junit.Test;

public class JunitTest {
	public static void main(String[] args) {
		for(int i=1;i <= 100000;i++) {
			TestPic();
		}
	}
	
	@Test
	public static void TestPic() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = new Date();
    	System.out.println("初始化时间："+sdf.format(date1));
		// xml报文
    	String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
    		+ "<head></head><body><tpid>pic?0dd208581-94e6d11i39*dp=*7pdi=*1s5i8=37b2ied33*6f2543162-8c6395--t=0d2m564d8810i42=_109</tpid></body></root>";
    	Client client;
    	String ip = "http://190.15.0.57:8989/picServer/services/InAccess?wsdl";//本地服务
		try {
			client = new Client(new URL(ip));
			date1 = new Date();
			Object[] s = client.invoke("executes", new String[] {"01", "02", "hello,world", "1", str_xml} );
			if(s != null && s.length > 0) {
				Document document = DocumentHelper.parseText(s[0].toString());
				Element root = document.getRootElement();
				Element body = (Element) root.selectNodes("body").get(0);
				System.out.println(body.element("path").getText());
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		Date date2 = new Date();
		System.out.println("查询结束时间："+sdf.format(date2));
		double d = (date2.getTime()-date1.getTime());
		System.out.println(d/1000);
	}
}