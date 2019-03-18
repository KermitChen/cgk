package com.dyst.test;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.xfire.client.Client;
//import static org.elasticsearch.index.query.QueryBuilders.*;
//import static org.elasticsearch.index.query.FilterBuilders.*;
import org.junit.Test;

import com.dyst.elasticsearch.util.ESClientManager;
import com.dyst.webservice.InAccessServiceImpl;

public class JunitTest {
	/**
	 * 查询轨迹测试
	 */
	@Test
	public void testGcgj() {
		Date date1 = null;
		date1 = new Date();
		String str_xml = "";
		// 跨库测试查询
		str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root><head><pagesize>50</pagesize><from>0</from><sort>tgsj</sort>"
				+ "<sortType>desc</sortType></head><body><data><hphm></hphm><kssj>2017-07-12 00:00:00</kssj>"
				+ "<jssj>2017-10-17 23:59:59</jssj><tpid></tpid><jcdid></jcdid><hpzl></hpzl>"
				+ "<cplx></cplx><cd></cd><cb></cb><sd></sd><hmdCphm></hmdCphm></data></body></root>";

		Client client = null;
		try {
//			String ip = "http://100.100.37.37:8080/dyst/services/InAccess?wsdl";// 本地服务
			String ip = "http://10.235.58.104:8080/dyst/services/InAccess?wsdl";// 本地服务
			client = new Client(new URL(ip));

			Date date2 = new Date();
			// 建立Webservice耗时
			double d = (date2.getTime() - date1.getTime());
			System.out.println("建立Webservice耗时：" + d / 1000);

			date1 = new Date();
			Object[] s = client.invoke("executes", new String[] { "01", "01", "hello,world", "1", str_xml });
			System.out.println(s[0] + "=======================");

			date2 = new Date();
			d = (date2.getTime() - date1.getTime());
			System.out.println("查询Webservice耗时：" + d / 1000);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}
//
//	/**
//	 * 图片调用测试
//	 */
//	@Test
//	public void testPic() {
//
//	}
//
//	/**
//	 * 更新识别记录测试
//	 */
//	@Test
//	public void testUpdateSb() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date1 = new Date();
//		System.out.println("初始化时间：" + sdf.format(date1));
//		// xml报文
//		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
//				+ "<head></head><body><data>"
//				+ "<tpid>20130803000032522050141251</tpid>"
//				+ "<hphm>2222222</hphm>" + "<cplx>9</cplx>"
//				+ "</data></body></root>";
//		Client client;
//		String ip = "http://100.100.3.75:8080/dyst/services/InAccess?wsdl";// 本地服务
//		try {
//			client = new Client(new URL(ip));
//			date1 = new Date();
//			Object[] s = client.invoke("executes", new String[] { "01", "06",
//					"hello,world", "0", str_xml });
//			System.out.println(s[0]);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		Date date2 = new Date();
//		System.out.println("查询结束时间：" + sdf.format(date2));
//		double d = (date2.getTime() - date1.getTime());
//		System.out.println(d / 1000);
//	}
//
//	/**
//	 * 统计测试
//	 * @throws InterruptedException
//	 */
//	@Test
//	public void testTj() throws InterruptedException {
//		Date date1 = null;
//		date1 = new Date();
//		String str_xml = "";
//		// 跨库测试查询
//		str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
//				+ "<head><groupName>jcdid</groupName><type>01</type><sbzt>1</sbzt></head><body>"
//				+ "<data><hphm></hphm><kssj>2014-4-8 09:13:59</kssj>"
//				+ "<jssj>2014-4-10 09:14:12</jssj>"
//				+ "<tpid></tpid><jcdid></jcdid><hpzl></hpzl><cplx>0</cplx>"
//				+ "<cd></cd><cb></cb><sd></sd><hmdCphm></hmdCphm>"
//				+ "</data></body></root>";
//
//		Client client = null;
//		try {
//			String ip = "";
//			ip = "http://100.100.3.135:8080/dystField/services/InAccess?wsdl";// 本地服务
//
//			client = new Client(new URL(ip));
//			// date1 = new Date();
//			// 建立Webservice耗时
//			Date date2 = new Date();
//			// System.out.println("建立Webservice耗时："+sdf.format(date2));
//			double d = (date2.getTime() - date1.getTime());
//			System.out.println("建立Webservice耗时：" + d / 1000);
//			date1 = new Date();
//			Object[] s = client.invoke("executes", new String[] { "01", "08",
//					"hello,world", "0", str_xml });
//			System.out.println(s[0]);
//			date2 = new Date();
//			d = (date2.getTime() - date1.getTime());
//			System.out.println("查询Webservice耗时：" + d / 1000);
//
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} finally {
//			if (client != null) {
//				client.close();
//				// System.out.println("最后执行");
//			}
//		}
//	}
//
//	/**
//	 * 碰撞分析测试
//	 * @throws InterruptedException
//	 */
//	@Test
//	public void testPzfx() throws InterruptedException {
//		Date date1 = null;
//		date1 = new Date();
//		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root><head>"
//				+ "<frequency>0.5</frequency><maxReturnRecord>10000</maxReturnRecord></head>"
//				+ "<body>"
//				+ "<data><jcdid>20501810</jcdid><kssj>2015-09-24 11:00:00</kssj><jssj>2015-09-25 13:00:00</jssj></data>"
//				+ "<data><jcdid>10300607</jcdid><kssj>2015-09-24 11:00:00</kssj><jssj>2015-09-25 13:00:00</jssj></data>"
//				+ "<data><jcdid>10100609</jcdid><kssj>2015-09-24 11:00:00</kssj><jssj>2015-09-25 13:00:00</jssj></data>"
//				+ "<data><jcdid>10100611</jcdid><kssj>2015-09-24 11:00:00</kssj><jssj>2015-09-25 13:00:00</jssj></data>"
//				+ "<data><jcdid>10200613</jcdid><kssj>2015-09-24 11:00:00</kssj><jssj>2015-09-25 13:00:00</jssj></data>"
//				+ "<data><jcdid>10300207</jcdid><kssj>2015-09-24 11:00:00</kssj><jssj>2015-09-25 13:00:00</jssj></data>"
//				+ "<data><jcdid>10100603</jcdid><kssj>2015-09-24 11:00:00</kssj><jssj>2015-09-25 13:00:00</jssj></data>"
//				+ "</body></root>";
//
//		Client client = null;
//		try {
//			String ip = "http://localhost:8080/dyst/services/InAccess?wsdl";// 本地服务
//
//			client = new Client(new URL(ip));
//			date1 = new Date();
//			// 建立Webservice耗时
//			Date date2 = new Date();
//			// System.out.println("建立Webservice耗时："+sdf.format(date2));
//			double d = (date2.getTime() - date1.getTime());
//			System.out.println("建立Webservice耗时：" + d / 1000);
//			date1 = new Date();
//			Object[] s = client.invoke("executes", new String[] { "01", "10",
//					"hello,world", "0", str_xml });
//			System.out.println(s[0]);
//			date2 = new Date();
//			d = (date2.getTime() - date1.getTime());
//			System.out.println("查询Webservice耗时：" + d / 1000);
//
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} finally {
//			if (client != null) {
//				client.close();
//				// System.out.println("最后执行");
//			}
//		}
//	}
//
//	/**
//	 * 频繁出现点分析测试
//	 * @throws InterruptedException
//	 */
//	@Test
//	public void testFrequence() throws InterruptedException {
//		Date date1 = null;
//		date1 = new Date();
//		String requestXml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root><head><maxReturnCount>20</maxReturnCount>"
//				+ "</head><body><data><hphm>粤B029PL</hphm><kssj>2014-07-10 09:00:00</kssj>"
//				+ "<jssj>2014-07-11 14:55:00</jssj></data></body></root>";
//
//		Client client = null;
//		try {
//			String ip = "http://localhost:8080/dyst/services/InAccess?wsdl";// 本地服务
//
//			client = new Client(new URL(ip));
//			date1 = new Date();
//			// 建立Webservice耗时
//			Date date2 = new Date();
//			// System.out.println("建立Webservice耗时："+sdf.format(date2));
//			double d = (date2.getTime() - date1.getTime());
//			System.out.println("建立Webservice耗时：" + d / 1000);
//			date1 = new Date();
//			Object[] s = client.invoke("executes", new String[] { "01", "09",
//					"hello,world", "0", requestXml });
//			System.out.println(s[0]);
//			date2 = new Date();
//			d = (date2.getTime() - date1.getTime());
//			System.out.println("查询Webservice耗时：" + d / 1000);
//
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} finally {
//			if (client != null) {
//				client.close();
//				// System.out.println("最后执行");
//			}
//		}
//	}
	
	@Test
	public void TestPic() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = new Date();
    	System.out.println("初始化时间："+sdf.format(date1));
		// xml报文
    	String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
    		+ "<head></head><body><tpid>pic?0ddc031ae-05eda11i523cp9--d1c45cf623238i2b9*=5d9s9*=5dpi*=1d1i7t303*m5-02=2786=0i908_113</tpid></body></root>";
    	Client client;
    	String ip = "http://10.42.127.81:9080/dyst/services/InAccess?wsdl";//本地服务
		try {
			client = new Client(new URL(ip));
			date1 = new Date();
			Object[] s = client.invoke("executes", new String[] {"01", "02", "hello,world", "1", str_xml} );
			System.out.println(s[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Date date2 = new Date();
		System.out.println("查询结束时间："+sdf.format(date2));
		double d = (date2.getTime()-date1.getTime());
		System.out.println(d/1000);
	}
	
	public static void main(String[] args) {
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		ESClientManager ecclient = ESClientManager.getInstance();
//		org.elasticsearch.client.Client client = ecclient.getConnection("es");//ES数据库连接池,获取数据连接
//		
//		List<FilterBuilder> filterList = new ArrayList<FilterBuilder>();
//		//30分钟后上传的数据
//		filterList.add(scriptFilter("doc['scsj'].value - doc['tgsj'].value > param").addParam("param", 1800000));
//		//车牌号码
//		filterList.add(FilterBuilders.boolFilter().must(FilterBuilders.termFilter("cphm1", "粤SG261R")));
//		
//		FilterBuilder filter = FilterBuilders.boolFilter().must(filterList.toArray(new FilterBuilder[filterList.size()]));
//		QueryBuilder query = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), filter);
//		SearchResponse response = client.prepareSearch("sb").setTypes("sb")
//				.setQuery(query)
//				.setFrom(0).setSize(100)//从开始取，取多少数据
//			    .setExplain(false)//不对查询数据进行解析
//   				.addFields(new String[]{"cphm1","jcdid","cplx1","tgsj","cdid","tpid1","tpid2","tpid3","tpid4","tpid5","sd","cdid","cb","scsj"})
//				.execute().actionGet();
//		SearchHits hits = response.getHits();//获取结果
//		
//		for(final SearchHit hit:hits){
//			final Iterator<SearchHitField> iterator = hit.iterator();
//			String str = "";
//            while(iterator.hasNext()){//一条记录
//            	final SearchHitField hitfield = iterator.next();
//            	if("tgsj".equals(hitfield.getName()) || "scsj".equals(hitfield.getName())){
//            		str += hitfield.getName() + ":" + sdf.format(new Date(Long.parseLong(hitfield.getValue().toString()))) + ",";
//            	} else {
//            		str += hitfield.getName() + ":" + hitfield.getValue().toString() + ",";
//            	}
//            }
//			System.out.println(str);
//		}
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			System.out.println(sdf.parse("2017-01-01 00:00:01").getTime() - sdf.parse("2017-01-01 00:00:00").getTime());
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		
//		String sql_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root><head></head><body><data><kssj>2015-04-26 00:00:00</kssj><jssj>2017-04-28 10:18:45</jssj><jcdid>05000046,05000068,05000083</jcdid><csbz>30</csbz><bcbz>0</bcbz></data></body></root>";
//		
//		InAccessServiceImpl ss = new InAccessServiceImpl();
//		System.out.println(ss.executes("01", "12", "hello,world", "0", sql_xml));;
	}
}