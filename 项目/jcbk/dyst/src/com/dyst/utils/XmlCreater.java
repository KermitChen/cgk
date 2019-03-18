package com.dyst.utils;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.facet.terms.TermsFacet;

import com.dyst.entites.SbC;
import com.dyst.entites.SbTemp;
import com.dyst.entites.Sbnew;
import com.dyst.oracle.JjhomdOracle;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class XmlCreater {
	/**
	 * 根据参数List,转换成通行数据XML文档字符串
	 * @param list
	 * @return XML生成文件
	 */
	public String createXml(String homdFlag, List list, SearchHits hits){
		/*
		 * 根据list值创建XML文件，最后转换成字符串，返回
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//创建xml文件
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		//添加head元素
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
	    Element el_message = el_head.addElement("message");
	    el_success.setText("1");
	    
	    //数据总数
	    int lenES = 0;
	    int lenOralce = 0;
	    if(hits != null){//ES 库查询总数
	    	lenES = hits.getHits().length;
	    }
	    if(list != null && list.size() > 0){//Oralce库查询总数
	    	lenOralce = list.size();
	    }
	    el_count.setText("" + (lenOralce + lenES));
	    el_message.setText("查询成功");
	    
	    //为body元素赋值
	    Element  el_body = el_rowdata1.addElement("body");
	    Sbnew sb = new Sbnew();
	    //Oralce库查询结果集
		if(list != null && list.size() > 0) {
			//如果返回数据为空，或者失败，则设置count为0，message为数据库连接失败
			//下面为body节点的数据	
			for(int i = 0;i < list.size();i++) {	
				sb = (Sbnew)list.get(i);
				
				//判断是否隐藏红名单，如果隐藏红名单，则更换数据
				Integer tpzs = sb.getTpzs();
				//homdFlag为null或为空或0时，只要该车牌为红名单，则隐藏
				if((homdFlag == null || "".equals(homdFlag) || !"0".equals(homdFlag)) 
						&& JjhomdOracle.hideJjhomd(sb.getCphm1(), sb.getCplx1())){//隐藏
					sb = new Sbnew();
					sb.setCphm1("******");
					sb.setCplx1("******");
					sb.setJcdid("******");
					sb.setCdid("******");
					sb.setTgsj(null);
					sb.setScsj(null);
					sb.setTpzs(tpzs);
					sb.setTpid1("00000000000000000000000000000000000");
					sb.setTpid2("");
					sb.setTpid3("");
					sb.setTpid4("");
					sb.setTpid5("");
					sb.setCb("******");
					sb.setSd(null);
				}
				
				Element el_data = el_body.addElement("data");
				Element el_hphm = el_data.addElement("hphm");
				Element el_cplx = el_data.addElement("cplx");
				Element el_jcdid = el_data.addElement("jcdid");
				Element el_cdid = el_data.addElement("cdid");
				Element el_sbsj = el_data.addElement("sbsj");
				Element el_scsj = el_data.addElement("scsj");
				Element el_tpzs = el_data.addElement("tpzs");
				Element el_tp1 = el_data.addElement("tp1");
			    Element el_tp2 = el_data.addElement("tp2");
				Element el_tp3 = el_data.addElement("tp3");
				Element el_tp4 = el_data.addElement("tp4");
				Element el_tp5 = el_data.addElement("tp5");
				Element el_cb = el_data.addElement("cb");
				Element el_sd = el_data.addElement("sd");
				    	
				//赋值,一张表实现方式
				el_hphm.setText(sb.getCphm1() == null? "":sb.getCphm1());
				el_cplx.setText(sb.getCplx1() == null? "":sb.getCplx1());
				
				el_jcdid.setText(sb.getJcdid() == null? "":sb.getJcdid());
				el_cdid.setText(sb.getCdid() == null? "":sb.getCdid());
				
				Timestamp tgsj = sb.getTgsj();
				el_sbsj.setText(tgsj == null? "":sdf.format(tgsj));
				Timestamp scsj = sb.getScsj();
				el_scsj.setText(scsj == null? "":sdf.format(scsj));
				
				el_tpzs.setText("" + sb.getTpzs());//图片张数
				String tpid1 = sb.getTpid1();
				String tpid2 = sb.getTpid2();
				String tpid3 = sb.getTpid3();
				String tpid4 = sb.getTpid4();
				String tpid5 = sb.getTpid5();
				el_tp1.setText(tpid1 == null? "":tpid1);
				el_tp2.setText(tpid2 == null? "":tpid2);
				el_tp3.setText(tpid3 == null? "":tpid3);
				el_tp4.setText(tpid4 == null? "":tpid4);
				el_tp5.setText(tpid5 == null? "":tpid5);
				
				String cb = sb.getCb();
				el_cb.setText(cb == null? "":cb);
				
				Double sd = sb.getSd();
				el_sd.setText(sd == null || sd < 0 ? "":sd.toString());
			}
		}
		
		//ES库查询结果集
		if(lenES > 0){
			Map<String, String> map = null;
 			for(final SearchHit hit:hits){
				try {
					Element el_data=el_body.addElement("data");
					Element el_hphm=el_data.addElement("hphm");
					Element el_cplx=el_data.addElement("cplx");
					Element el_jcdid=el_data.addElement("jcdid");
					Element el_cdid=el_data.addElement("cdid");
					Element el_sbsj=el_data.addElement("sbsj");
					Element el_scsj=el_data.addElement("scsj");
					Element el_tpzs=el_data.addElement("tpzs");
				    Element el_tp1=el_data.addElement("tp1");
				    Element el_tp2=el_data.addElement("tp2");
				    Element el_tp3=el_data.addElement("tp3");
				    Element el_tp4=el_data.addElement("tp4");
				    Element el_tp5=el_data.addElement("tp5");
				    Element el_cb = el_data.addElement("cb");
					Element el_sd = el_data.addElement("sd");
					
					map = new HashMap<String, String>();
		            final Iterator<SearchHitField> iterator = hit.iterator();	 	
		            while(iterator.hasNext()){//一条记录
		            	final SearchHitField hitfield = iterator.next();
		            	map.put(hitfield.getName(), hitfield.getValue().toString());
		            }
		            
		            //判断是否隐藏红名单，如果隐藏红名单，则更换数据
		            String tpzs = (String)map.get("tpzs");
		            //homdFlag为null或为空或0时，只要该车牌为红名单，则隐藏
					if((homdFlag == null || "".equals(homdFlag) || !"0".equals(homdFlag)) 
							&& JjhomdOracle.hideJjhomd((String)map.get("cphm1"), (String)map.get("cplx1"))){//隐藏
						map = new HashMap<String, String>();
						map.put("cphm1", "******");
						map.put("cplx1", "******");
						map.put("jcdid", "******");
						map.put("cdid", "******");
						map.put("tgsj", "");
						map.put("scsj", "");
						map.put("tpid1", "00000000000000000000000000000000000");
						map.put("tpid2", "");
						map.put("tpid3", "");
						map.put("tpid4", "");
						map.put("tpid5", "");
						map.put("cb", "******");
						map.put("sd", "");
						map.put("tpzs", tpzs);
					}
					
					String cphm1 = (String)map.get("cphm1");
			    	el_hphm.setText(cphm1 == null? "":cphm1);
			    	String cplx1 = (String)map.get("cplx1");
			    	el_cplx.setText(cplx1 == null? "":cplx1);
			    	String jcdid = (String)map.get("jcdid");
				    el_jcdid.setText(jcdid == null? "":jcdid);
				    String cdid = (String)map.get("cdid");
				    el_cdid.setText(cdid == null? "":cdid);
				    
				    el_sbsj.setText("".equals(map.get("tgsj")) ? "":sdf.format(new Date(Long.parseLong(map.get("tgsj")))));
				    el_scsj.setText("".equals(map.get("scsj")) ? "":sdf.format(new Date(Long.parseLong(map.get("scsj")))));

				    el_tpzs.setText(tpzs == null? "":tpzs);//图片张数;
				    Object tpid1 = map.get("tpid1");
				    Object tpid2 = map.get("tpid2");
				    Object tpid3 = map.get("tpid3");
				    Object tpid4 = map.get("tpid4");
				    Object tpid5 = map.get("tpid5");
				    el_tp1.setText(tpid1 == null? "":(String)tpid1);
				    el_tp2.setText(tpid2 == null? "":(String)tpid2);
				    el_tp3.setText(tpid3 == null? "":(String)tpid3);
				    el_tp4.setText(tpid4 == null? "":(String)tpid4);
				    el_tp5.setText(tpid5 == null? "":(String)tpid5);
				    
				    String cb = (String)map.get("cb");
					el_cb.setText(cb == null? "":cb);
					
					Object sd = map.get("sd");
					el_sd.setText(sd == null? "":sd.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}     				
		}
	
		//返回结果
		return doc.asXML();//转换成字符串
	}
	
	/**
	 * 根据oracle和ES查询结果记录总数通过XML形式返回给用户<br>
	 * @param oracleCount： Oracle查询记录总数<br>
	 * @param esCount： ES查询记录总数<br>
	 * @return  xml 报文
	 */
	public String createCountXml(int oracleCount, int esCount){
		/*
		 * 根据list值创建XML文件，最后转换成字符串，返回
		 */
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		//添加head元素
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
	    Element el_message = el_head.addElement("message");
	    
	    el_success.setText("1") ;
	    el_count.setText("" + (oracleCount + esCount));
	    el_message.setText("查询成功");
	    
		return doc.asXML();//转换成字符串
	}
	
	/**
	 * 查询失败，返回指定错误的xml文件
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createErrorXml(String message){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		// 添加head元素
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");

		el_success.setText("0");//失败
		el_count.setText("0");
		el_message.setText(message);//失败信息描述
		String str_xml = doc.asXML();// 转换成字符串
		return str_xml;
	}
	
	/**
	 * 图片查询，返回的xml文件
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createPicPath(List<String> listPic){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		// 添加head元素
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_rowdata1.addElement("body");

		el_success.setText("1");//失败
		el_message.setText("图片地址查询成功");//
		if(listPic == null || listPic.size() == 0){//集合为空
			el_count.setText("0");
			return doc.asXML();// 转换成字符串
		}
		
		//如果获取到结果，则返回查询结果
		el_count.setText("" + listPic.size());//查询总数
		for (int j = 0;j < listPic.size();j++) {
			Element el_path = el_body.addElement("path");
	    	//赋值
			el_path.setText(listPic.get(j));
		}     
		String str_xml = doc.asXML();// 转换成字符串
		// System.out.println(str_xml);
		return str_xml;
	}
	
	/**
	 * 更新识别，返回的xml文件
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createUpdateXml(String message){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		// 添加head元素
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");

		el_success.setText("1");//
		el_count.setText("");
		el_message.setText(message);//信息描述
		String str_xml = doc.asXML();// 转换成字符串
		return str_xml;
	}
	
	/**
	 * 统计查询结果返回
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createTjxml(TermsFacet tf){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// 添加head元素
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("统计查询成功");//
		//如果获取到结果，则返回查询结果
		el_count.setText("" + tf.getTotalCount());//查询总数
		
		for(TermsFacet.Entry entry:tf){
			Element el_data = el_body.addElement("data");
			Element groupName = el_data.addElement("groupName");
			Element el_value = el_data.addElement("value");
	    	//赋值
			groupName.setText(""+entry.getTerm());//分组字段值
			el_value.setText(""+entry.getCount());//分组值
		}     
		String str_xml = doc.asXML();// 转换成字符串
		return str_xml;
	}
	/**
	 * 车辆频繁出现点分析
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createFrequently(List<SbC> listC,int count,int maxReturnValue){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// 添加head元素
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("统计查询成功");//
		el_count.setText("" + count);//查询总数
		
		int maxValue = 0;
		for(int i=listC.size()-1;maxValue < maxReturnValue && i >= 0;i--){
			SbC pt = listC.get(i);
			Element el_data = el_body.addElement("data");
			Element jcdid = el_data.addElement("jcdid");
			Element frequency = el_data.addElement("frequency");
			Element timeElement = el_data.addElement("time");
			Element ratio = el_data.addElement("ratio");
			Element tpidsElement = el_data.addElement("tpids");
			
		    HashSet<String> set = (HashSet<String>) pt.getSetTpid();//图片id集合
		    
			//赋值
			jcdid.setText(""+pt.getJcdid());//
			frequency.setText(""+set.size());//
			//单个点在总出现次数中的比率
			ratio.setText(String.format("%.2f", ((double)set.size()/(double)count)*100));
			timeElement.setText(pt.getDescription());
            for(String tpid:set){
            	Element tpidElement = tpidsElement.addElement("tpid");
            	tpidElement.setText(tpid);
            }
            maxValue++;
		}
		return doc.asXML();// 转换成字符串
	}
	
	/**
	 * 车辆频繁出现点分析
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public  String createPzfx(ArrayList<SbTemp> listC,int count){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// 添加head元素
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("统计查询成功");//
		//如果获取到结果，则返回查询结果
		el_count.setText("" + count);//查询总数
		
		//返回结果集限制1000条，后面的省略掉
		for(int i=0;i < 1000 && i < listC.size();i++){
			SbTemp pt = listC.get(i);
			
			Element el_data = el_body.addElement("data");
			Element cphm1 = el_data.addElement("cphm1");
			Element tpid = el_data.addElement("tpid");
			Element countdata = el_data.addElement("count");
			
			//赋值
			cphm1.setText(pt.getCphm1());//
			tpid.setText(pt.getTpid1());
			countdata.setText(pt.getCount()+"");
		}
		return doc.asXML();// 转换成字符串
	}
	/**
	 * 车辆落脚点分析
	 * @param bucketList
	 * @param total
	 * @return
	 */
	public String createFootHold(List<Bucket> bucketList,long total){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// 添加head元素
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("统计查询成功");//
		//如果获取到结果，则返回查询结果
		el_count.setText("" + total);//查询总数
		
		for (int i = 0; i < bucketList.size(); i++) {
			Terms.Bucket entry = bucketList.get(i);
			Element el_data = el_body.addElement("data");
			Element key = el_data.addElement("key");
			Element doc_count = el_data.addElement("doc_count");
			//赋值
			key.setText(entry.getKey());
			doc_count.setText("" + entry.getDocCount());
		}
		/*for(Terms.Bucket entry:bucketList){
			Element el_data = el_body.addElement("data");
			Element key = el_data.addElement("key");
			Element doc_count = el_data.addElement("doc_count");
			//赋值
			key.setText(entry.getKey());
			doc_count.setText("" + entry.getDocCount());
		}*/
		return doc.asXML();// 转换成字符串
	}
	/**
	 * 初始化车流量图表数据
	 * @param bucketList
	 * @param total
	 * @return
	 */
	public String createInitChartData(List<Bucket> bucketList,long total){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// 添加head元素
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("统计查询成功");//
		//如果获取到结果，则返回查询结果
		el_count.setText("" + total);//查询总数
		
		for (Terms.Bucket entry : bucketList) {
			Element el_data = el_body.addElement("data");
			//车道
			Element cd = el_data.addElement("cd");
			cd.setText(entry.getKey());
			
			DateHistogram time_group = entry.getAggregations().get("time_group");
			for(DateHistogram.Bucket ent: time_group.getBuckets()){
				Element child_data = el_data.addElement("childData");
				//时间
				Element el_time = child_data.addElement("time");
				el_time.setText(ent.getKeyAsText().toString());
				//过车量
				Sum pass_count = ent.getAggregations().get("pass_count");
				Element el_num = child_data.addElement("num");
				el_num.setText(pass_count.getValueAsString());
			}
		}
		return doc.asXML();
	}
	/**
	 * 创建查询服务器信息的xml
	 * @param hits
	 * @return
	 */
	public String createServerInfoXml(SearchHits hits){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// 添加head元素
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("统计查询成功");//
		//如果获取到结果，则返回查询结果
		el_count.setText("" + hits.getTotalHits());//查询总数
		
		Iterator<SearchHit> it = hits.iterator();
		while(it.hasNext()){
			Element el_data = el_body.addElement("data");
			el_data.setText(it.next().getSourceAsString());
		}
		return doc.asXML();
	}
	/**
	 * 创建昼伏夜出xml
	 * @param hits
	 * @return
	 */
	public String createZfycXml(SearchHits hits){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// 添加head元素
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("统计查询成功");//
		//如果获取到结果，则返回查询结果
		el_count.setText("" + hits.getTotalHits());//查询总数
		
		Iterator<SearchHit> it = hits.iterator();
		while(it.hasNext()){
			Element el_data = el_body.addElement("data");
			el_data.setText(it.next().getSourceAsString());
		}
		return doc.asXML();
	}
	/**
	 * 创建初次入城xml
	 * @param hits
	 * @return
	 */
	public String createCcrcXml(SearchHits hits){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// 添加head元素
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("统计查询成功");//
		//如果获取到结果，则返回查询结果
		el_count.setText("" + hits.getTotalHits());//查询总数
		
		Iterator<SearchHit> it = hits.iterator();
		while(it.hasNext()){
			Element el_data = el_body.addElement("data");
			el_data.setText(it.next().getSourceAsString());
		}
		return doc.asXML();
	}
	/**
	 * 创建昼伏夜出统计结果
	 * @param bucketList
	 * @return
	 */
	public String createZfycTjRes(List<Bucket> bucketList,double zybl){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// 添加head元素
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("统计查询成功");//
		//如果获取到结果，则返回查询结果
		el_count.setText("" + bucketList.size());//查询总数
		for (Bucket bucket : bucketList) {
			Sum zf = bucket.getAggregations().get("zf_times");
			Sum yc = bucket.getAggregations().get("yc_times");
			if(yc.getValue() > 0){
				if(zf.getValue()/yc.getValue() <= zybl ){
					Element el_data = el_body.addElement("data");
					el_data.addElement("cphm").setText(bucket.getKey());
					el_data.addElement("zfcs").setText((int)zf.getValue()+"");
					el_data.addElement("yccs").setText((int)yc.getValue()+"");
				}
			}
		}
		return doc.asXML();
	}
	/**
	 * 业务日志
	 * @param hits
	 * @return
	 */
	public String createBusinessLogXml(SearchHits hits){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// 添加head元素
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("查询成功");//
		//如果获取到结果，则返回查询结果
		el_count.setText("" + hits.getTotalHits());//查询总数
		
		SearchHit[] hitArr = hits.getHits();
		Gson gson = new Gson();
		Map<String, String> map = null;
		String id = "";
		SearchHit hit = null;
		for (int i = 0; i < hitArr.length; i++) {
			Element el_data = el_body.addElement("data");
			hit = hitArr[i];
			id = hit.getId();
			map = gson.fromJson(hit.getSourceAsString(), new TypeToken<Map<String, String>>(){}.getType());
			map.put("id", id);
			el_data.setText(gson.toJson(map));
		}
		return doc.asXML();
	}
	/**
	 * 单条业务日志
	 * @param map
	 * @return
	 */
	public String createBusinessLogXml(Map<String, Object> map){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// 添加head元素
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("查询成功");//
		//如果获取到结果，则返回查询结果
		
		Gson gson = new Gson();
		Element el_data = el_body.addElement("data");
		el_data.setText(gson.toJson(map));
		return doc.asXML();
	}
	
	public String createBusinessLogTjRes(List<Bucket> bucketList){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// 添加head元素
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("统计查询成功");//
		//如果获取到结果，则返回查询结果
		el_count.setText("" + bucketList.size());//查询总数
		
		for (Bucket bucket : bucketList) {
			Terms terms = bucket.getAggregations().get("terms_group");
			for (Bucket buc:terms.getBuckets()) {
				Element el_data = el_body.addElement("data");
				el_data.addElement("moduleName").setText(bucket.getKey());
				el_data.addElement("tjWord").setText(buc.getKey());
				el_data.addElement("count").setText(Long.toString(buc.getDocCount()));
			}
		}
		return doc.asXML();
	}
	
	public String createSccsByJcdGroupRes(List<Bucket> bucketList){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// 添加head元素
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("统计查询成功");//
		//如果获取到结果，则返回查询结果
		el_count.setText("" + (bucketList != null ? bucketList.size() : 0));//查询总数
		
		//封装数据
		if(bucketList != null && bucketList.size() > 0){
			for (Bucket bucket : bucketList) {
				Element el_data = el_body.addElement("data");
				el_data.addElement("groupName").setText(bucket.getKey());
				el_data.addElement("value").setText(Long.toString(bucket.getDocCount()));
			}
		}
		return doc.asXML();
	}
}