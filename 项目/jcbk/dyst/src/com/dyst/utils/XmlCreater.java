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
	 * ���ݲ���List,ת����ͨ������XML�ĵ��ַ���
	 * @param list
	 * @return XML�����ļ�
	 */
	public String createXml(String homdFlag, List list, SearchHits hits){
		/*
		 * ����listֵ����XML�ļ������ת�����ַ���������
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//����xml�ļ�
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		//���headԪ��
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
	    Element el_message = el_head.addElement("message");
	    el_success.setText("1");
	    
	    //��������
	    int lenES = 0;
	    int lenOralce = 0;
	    if(hits != null){//ES ���ѯ����
	    	lenES = hits.getHits().length;
	    }
	    if(list != null && list.size() > 0){//Oralce���ѯ����
	    	lenOralce = list.size();
	    }
	    el_count.setText("" + (lenOralce + lenES));
	    el_message.setText("��ѯ�ɹ�");
	    
	    //ΪbodyԪ�ظ�ֵ
	    Element  el_body = el_rowdata1.addElement("body");
	    Sbnew sb = new Sbnew();
	    //Oralce���ѯ�����
		if(list != null && list.size() > 0) {
			//�����������Ϊ�գ�����ʧ�ܣ�������countΪ0��messageΪ���ݿ�����ʧ��
			//����Ϊbody�ڵ������	
			for(int i = 0;i < list.size();i++) {	
				sb = (Sbnew)list.get(i);
				
				//�ж��Ƿ����غ�������������غ����������������
				Integer tpzs = sb.getTpzs();
				//homdFlagΪnull��Ϊ�ջ�0ʱ��ֻҪ�ó���Ϊ��������������
				if((homdFlag == null || "".equals(homdFlag) || !"0".equals(homdFlag)) 
						&& JjhomdOracle.hideJjhomd(sb.getCphm1(), sb.getCplx1())){//����
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
				    	
				//��ֵ,һ�ű�ʵ�ַ�ʽ
				el_hphm.setText(sb.getCphm1() == null? "":sb.getCphm1());
				el_cplx.setText(sb.getCplx1() == null? "":sb.getCplx1());
				
				el_jcdid.setText(sb.getJcdid() == null? "":sb.getJcdid());
				el_cdid.setText(sb.getCdid() == null? "":sb.getCdid());
				
				Timestamp tgsj = sb.getTgsj();
				el_sbsj.setText(tgsj == null? "":sdf.format(tgsj));
				Timestamp scsj = sb.getScsj();
				el_scsj.setText(scsj == null? "":sdf.format(scsj));
				
				el_tpzs.setText("" + sb.getTpzs());//ͼƬ����
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
		
		//ES���ѯ�����
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
		            while(iterator.hasNext()){//һ����¼
		            	final SearchHitField hitfield = iterator.next();
		            	map.put(hitfield.getName(), hitfield.getValue().toString());
		            }
		            
		            //�ж��Ƿ����غ�������������غ����������������
		            String tpzs = (String)map.get("tpzs");
		            //homdFlagΪnull��Ϊ�ջ�0ʱ��ֻҪ�ó���Ϊ��������������
					if((homdFlag == null || "".equals(homdFlag) || !"0".equals(homdFlag)) 
							&& JjhomdOracle.hideJjhomd((String)map.get("cphm1"), (String)map.get("cplx1"))){//����
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

				    el_tpzs.setText(tpzs == null? "":tpzs);//ͼƬ����;
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
	
		//���ؽ��
		return doc.asXML();//ת�����ַ���
	}
	
	/**
	 * ����oracle��ES��ѯ�����¼����ͨ��XML��ʽ���ظ��û�<br>
	 * @param oracleCount�� Oracle��ѯ��¼����<br>
	 * @param esCount�� ES��ѯ��¼����<br>
	 * @return  xml ����
	 */
	public String createCountXml(int oracleCount, int esCount){
		/*
		 * ����listֵ����XML�ļ������ת�����ַ���������
		 */
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		//���headԪ��
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
	    Element el_message = el_head.addElement("message");
	    
	    el_success.setText("1") ;
	    el_count.setText("" + (oracleCount + esCount));
	    el_message.setText("��ѯ�ɹ�");
	    
		return doc.asXML();//ת�����ַ���
	}
	
	/**
	 * ��ѯʧ�ܣ�����ָ�������xml�ļ�
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createErrorXml(String message){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");

		el_success.setText("0");//ʧ��
		el_count.setText("0");
		el_message.setText(message);//ʧ����Ϣ����
		String str_xml = doc.asXML();// ת�����ַ���
		return str_xml;
	}
	
	/**
	 * ͼƬ��ѯ�����ص�xml�ļ�
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createPicPath(List<String> listPic){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_rowdata1.addElement("body");

		el_success.setText("1");//ʧ��
		el_message.setText("ͼƬ��ַ��ѯ�ɹ�");//
		if(listPic == null || listPic.size() == 0){//����Ϊ��
			el_count.setText("0");
			return doc.asXML();// ת�����ַ���
		}
		
		//�����ȡ��������򷵻ز�ѯ���
		el_count.setText("" + listPic.size());//��ѯ����
		for (int j = 0;j < listPic.size();j++) {
			Element el_path = el_body.addElement("path");
	    	//��ֵ
			el_path.setText(listPic.get(j));
		}     
		String str_xml = doc.asXML();// ת�����ַ���
		// System.out.println(str_xml);
		return str_xml;
	}
	
	/**
	 * ����ʶ�𣬷��ص�xml�ļ�
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createUpdateXml(String message){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");

		el_success.setText("1");//
		el_count.setText("");
		el_message.setText(message);//��Ϣ����
		String str_xml = doc.asXML();// ת�����ַ���
		return str_xml;
	}
	
	/**
	 * ͳ�Ʋ�ѯ�������
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createTjxml(TermsFacet tf){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("ͳ�Ʋ�ѯ�ɹ�");//
		//�����ȡ��������򷵻ز�ѯ���
		el_count.setText("" + tf.getTotalCount());//��ѯ����
		
		for(TermsFacet.Entry entry:tf){
			Element el_data = el_body.addElement("data");
			Element groupName = el_data.addElement("groupName");
			Element el_value = el_data.addElement("value");
	    	//��ֵ
			groupName.setText(""+entry.getTerm());//�����ֶ�ֵ
			el_value.setText(""+entry.getCount());//����ֵ
		}     
		String str_xml = doc.asXML();// ת�����ַ���
		return str_xml;
	}
	/**
	 * ����Ƶ�����ֵ����
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createFrequently(List<SbC> listC,int count,int maxReturnValue){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("ͳ�Ʋ�ѯ�ɹ�");//
		el_count.setText("" + count);//��ѯ����
		
		int maxValue = 0;
		for(int i=listC.size()-1;maxValue < maxReturnValue && i >= 0;i--){
			SbC pt = listC.get(i);
			Element el_data = el_body.addElement("data");
			Element jcdid = el_data.addElement("jcdid");
			Element frequency = el_data.addElement("frequency");
			Element timeElement = el_data.addElement("time");
			Element ratio = el_data.addElement("ratio");
			Element tpidsElement = el_data.addElement("tpids");
			
		    HashSet<String> set = (HashSet<String>) pt.getSetTpid();//ͼƬid����
		    
			//��ֵ
			jcdid.setText(""+pt.getJcdid());//
			frequency.setText(""+set.size());//
			//���������ܳ��ִ����еı���
			ratio.setText(String.format("%.2f", ((double)set.size()/(double)count)*100));
			timeElement.setText(pt.getDescription());
            for(String tpid:set){
            	Element tpidElement = tpidsElement.addElement("tpid");
            	tpidElement.setText(tpid);
            }
            maxValue++;
		}
		return doc.asXML();// ת�����ַ���
	}
	
	/**
	 * ����Ƶ�����ֵ����
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public  String createPzfx(ArrayList<SbTemp> listC,int count){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("ͳ�Ʋ�ѯ�ɹ�");//
		//�����ȡ��������򷵻ز�ѯ���
		el_count.setText("" + count);//��ѯ����
		
		//���ؽ��������1000���������ʡ�Ե�
		for(int i=0;i < 1000 && i < listC.size();i++){
			SbTemp pt = listC.get(i);
			
			Element el_data = el_body.addElement("data");
			Element cphm1 = el_data.addElement("cphm1");
			Element tpid = el_data.addElement("tpid");
			Element countdata = el_data.addElement("count");
			
			//��ֵ
			cphm1.setText(pt.getCphm1());//
			tpid.setText(pt.getTpid1());
			countdata.setText(pt.getCount()+"");
		}
		return doc.asXML();// ת�����ַ���
	}
	/**
	 * ������ŵ����
	 * @param bucketList
	 * @param total
	 * @return
	 */
	public String createFootHold(List<Bucket> bucketList,long total){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("ͳ�Ʋ�ѯ�ɹ�");//
		//�����ȡ��������򷵻ز�ѯ���
		el_count.setText("" + total);//��ѯ����
		
		for (int i = 0; i < bucketList.size(); i++) {
			Terms.Bucket entry = bucketList.get(i);
			Element el_data = el_body.addElement("data");
			Element key = el_data.addElement("key");
			Element doc_count = el_data.addElement("doc_count");
			//��ֵ
			key.setText(entry.getKey());
			doc_count.setText("" + entry.getDocCount());
		}
		/*for(Terms.Bucket entry:bucketList){
			Element el_data = el_body.addElement("data");
			Element key = el_data.addElement("key");
			Element doc_count = el_data.addElement("doc_count");
			//��ֵ
			key.setText(entry.getKey());
			doc_count.setText("" + entry.getDocCount());
		}*/
		return doc.asXML();// ת�����ַ���
	}
	/**
	 * ��ʼ��������ͼ������
	 * @param bucketList
	 * @param total
	 * @return
	 */
	public String createInitChartData(List<Bucket> bucketList,long total){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("ͳ�Ʋ�ѯ�ɹ�");//
		//�����ȡ��������򷵻ز�ѯ���
		el_count.setText("" + total);//��ѯ����
		
		for (Terms.Bucket entry : bucketList) {
			Element el_data = el_body.addElement("data");
			//����
			Element cd = el_data.addElement("cd");
			cd.setText(entry.getKey());
			
			DateHistogram time_group = entry.getAggregations().get("time_group");
			for(DateHistogram.Bucket ent: time_group.getBuckets()){
				Element child_data = el_data.addElement("childData");
				//ʱ��
				Element el_time = child_data.addElement("time");
				el_time.setText(ent.getKeyAsText().toString());
				//������
				Sum pass_count = ent.getAggregations().get("pass_count");
				Element el_num = child_data.addElement("num");
				el_num.setText(pass_count.getValueAsString());
			}
		}
		return doc.asXML();
	}
	/**
	 * ������ѯ��������Ϣ��xml
	 * @param hits
	 * @return
	 */
	public String createServerInfoXml(SearchHits hits){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("ͳ�Ʋ�ѯ�ɹ�");//
		//�����ȡ��������򷵻ز�ѯ���
		el_count.setText("" + hits.getTotalHits());//��ѯ����
		
		Iterator<SearchHit> it = hits.iterator();
		while(it.hasNext()){
			Element el_data = el_body.addElement("data");
			el_data.setText(it.next().getSourceAsString());
		}
		return doc.asXML();
	}
	/**
	 * �������ҹ��xml
	 * @param hits
	 * @return
	 */
	public String createZfycXml(SearchHits hits){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("ͳ�Ʋ�ѯ�ɹ�");//
		//�����ȡ��������򷵻ز�ѯ���
		el_count.setText("" + hits.getTotalHits());//��ѯ����
		
		Iterator<SearchHit> it = hits.iterator();
		while(it.hasNext()){
			Element el_data = el_body.addElement("data");
			el_data.setText(it.next().getSourceAsString());
		}
		return doc.asXML();
	}
	/**
	 * �����������xml
	 * @param hits
	 * @return
	 */
	public String createCcrcXml(SearchHits hits){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("ͳ�Ʋ�ѯ�ɹ�");//
		//�����ȡ��������򷵻ز�ѯ���
		el_count.setText("" + hits.getTotalHits());//��ѯ����
		
		Iterator<SearchHit> it = hits.iterator();
		while(it.hasNext()){
			Element el_data = el_body.addElement("data");
			el_data.setText(it.next().getSourceAsString());
		}
		return doc.asXML();
	}
	/**
	 * �������ҹ��ͳ�ƽ��
	 * @param bucketList
	 * @return
	 */
	public String createZfycTjRes(List<Bucket> bucketList,double zybl){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("ͳ�Ʋ�ѯ�ɹ�");//
		//�����ȡ��������򷵻ز�ѯ���
		el_count.setText("" + bucketList.size());//��ѯ����
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
	 * ҵ����־
	 * @param hits
	 * @return
	 */
	public String createBusinessLogXml(SearchHits hits){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("��ѯ�ɹ�");//
		//�����ȡ��������򷵻ز�ѯ���
		el_count.setText("" + hits.getTotalHits());//��ѯ����
		
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
	 * ����ҵ����־
	 * @param map
	 * @return
	 */
	public String createBusinessLogXml(Map<String, Object> map){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("��ѯ�ɹ�");//
		//�����ȡ��������򷵻ز�ѯ���
		
		Gson gson = new Gson();
		Element el_data = el_body.addElement("data");
		el_data.setText(gson.toJson(map));
		return doc.asXML();
	}
	
	public String createBusinessLogTjRes(List<Bucket> bucketList){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_root = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("ͳ�Ʋ�ѯ�ɹ�");//
		//�����ȡ��������򷵻ز�ѯ���
		el_count.setText("" + bucketList.size());//��ѯ����
		
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
		// ���headԪ��
		Element el_head = el_root.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_root.addElement("body");

		el_success.setText("1");//
		el_message.setText("ͳ�Ʋ�ѯ�ɹ�");//
		//�����ȡ��������򷵻ز�ѯ���
		el_count.setText("" + (bucketList != null ? bucketList.size() : 0));//��ѯ����
		
		//��װ����
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