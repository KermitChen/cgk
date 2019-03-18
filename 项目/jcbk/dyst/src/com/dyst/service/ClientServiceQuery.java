package com.dyst.service;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.ReceiveTimeoutTransportException;

import com.dyst.elasticsearch.ESsearcherFilter;
import com.dyst.elasticsearch.util.ESClientManager;
import com.dyst.elasticsearch.util.ESCountThreadByQuery;
import com.dyst.elasticsearch.util.ESThreadByQuery;
import com.dyst.elasticsearch.util.ESutil;
import com.dyst.entites.Sbnew;
import com.dyst.oracle.CreateSql;
import com.dyst.oracle.OracleCountThread;
import com.dyst.oracle.OracleSearch;
import com.dyst.oracle.OracleThread;
import com.dyst.utils.Config;
import com.dyst.utils.InterUtil;
import com.dyst.utils.PicThread;
import com.dyst.utils.XmlCreater;
import com.google.gson.Gson;
public class ClientServiceQuery {
	private XmlCreater xmlcreate = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private CreateSql sqlInfo = null;//oracle��sql���������
	
	/**
	 * �켣��ѯ����������ҵ����������
	 * @param xml           XML������
	 * @param businessType  ��ѯҵ������
	 * @param flag          1:���ؼ�¼��0:���ؼ�¼������  ����ʶ���¼���Ǽ�¼����
	 * @param ���ؼ�¼��������ʶ���¼
	 */
	public String gjcx(String xml, String businessType, String flag){
		Config config = Config.getInstance();//������Ϣ��
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		
		//��������
		String cphid = null;//���ƺ���
		String hpzl = null;//��������
		String cplx = null;//��������
		String jcdid = null;//����ID
		String gcxh = null;//������ţ�ͼƬid��
		String kssj = null;//��ʼʱ��
		String jssj = null;//��ֹʱ��
		String cd = null;//����
		String cb = null;//����
		String sd = null;//�ٶ�
		String hmdCphm = null;//���������ƺ���
		String strPage = null;//ҳ����ʾ��¼��
		String strFrom = null;//��ʼ��¼��
		String business = null;//ҵ���ѯ����
		String sort = "";//�����ֶ�
		String sortType = "" ;//�����ֶ�����˳�򣬽��������
		
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//��ȡ���ڵ�
			Element head = (Element) root.selectNodes("head").get(0);
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);
			
			strPage = head.element("pagesize").getText();//ҳ����ʾ��¼��
			strFrom = head.element("from").getText();//��ʼ��¼��
			
			kssj = data.element("kssj").getText();//��ʼʱ��
			jssj = data.element("jssj").getText();//��ֹʱ��
			hpzl = data.element("hpzl").getText();//��������
			cphid = data.element("hphm").getText();//���ƺ���
			cplx = data.element("cplx").getText();//��������
			gcxh = data.element("tpid").getText();//�������
			jcdid = data.element("jcdid").getText();//����ID
			cd = data.element("cd").getText();//����
			cb = data.element("cb").getText();//����
			sd = data.element("sd").getText();//�ٶ�
			hmdCphm = data.element("hmdCphm").getText();//���������ƺ���
			business = businessType;//ҵ���ѯ����
			try {
				sort = head.element("sort").getText();//�����ֶ�
				sortType = head.element("sortType").getText();//�����ֶ�
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			//"09:xml�ļ���ʽ�޷����������飡"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		
		//�жϸ���ʱ�䣬��ѯOracle���ݿ⻹��ES��
		Date midDate = null;
		Date ksDate = null;
		Date jzDate = null;
		try {
			ksDate = sdf.parse(kssj);//��ʼʱ��
			jzDate = sdf.parse(jssj);//����ʱ��
			//��ֹʱ�䲻�ܳ�����ǰʱ��
			if(jzDate.getTime() > new Date().getTime()){
				jzDate = new Date();
			}
			
			//Oracle���ES���ѯ�ֽ��,,,����������
			midDate = df.parse(InterUtil.getTime(Integer.parseInt(config.getBeforeDate())));
		} catch (ParseException e) {
			//"03:ʱ���ʽ���Ϸ�����Ϊ�գ����飡"
			return  xmlcreate.createErrorXml(config.getErrorCode03());
		}
		
		//----��ҳ��������-----
		int pagesize = 0;//ÿҳ��ʾ������
		int from = 0;//�ӵڼ�����ʼȡ
		int allowCount = Integer.parseInt(config.getMaxCount());//��������ؼ�¼����
		int pageCount = Integer.parseInt(config.getPageCount());;//��ҳ��ѯ��������صļ�¼��
		int maxOrder = Integer.parseInt(config.getMaxOrder());//������������ܼ�¼��
		
		if(strPage != null && !"".equals(strPage)){
			try {
				pagesize = Integer.parseInt(strPage);
				from = Integer.parseInt(strFrom);
				if(pagesize > 0 && pagesize > pageCount){//����ҳ��¼��
					//"05:��ҳ��ѯ��¼�������������ֵ��"
					return xmlcreate.createErrorXml(config.getErrorCode05());
				} else if(pagesize < 0 || from < 0) {
					//"11:��ҳ�������Ϸ���"
					return xmlcreate.createErrorXml(config.getErrorCode11());
				}
				//���շ�ҳ��ѯ����¼����������ֵʱ�����ٷ��أ���ΪES�����ķ�ҳʱ��
				//���ڲ�ѯ�������⣬���׵����ڴ�����������
				if((pagesize+from) > maxOrder){
					//��ҳ���������޶�ֵ
					return xmlcreate.createErrorXml(config.getErrorCode05());
				}
			} catch (Exception e) {
				//08:������ҳ���������쳣
				return  xmlcreate.createErrorXml(config.getErrorCode08());
			}
		}
		
		SearchHits hits = null;
		FilterBuilder filter = null;
		CountDownLatch threadsSignal;
		int queryCount = 0;//�����ѯ��������
		List<Sbnew> listtx = new ArrayList<Sbnew>();//��Ų�ѯ�����
		
		//oracle��sql���������
		if(sqlInfo == null){
			sqlInfo = new CreateSql();
		}
		
		if(ksDate.after(midDate) || ksDate.equals(midDate)){//�����ʼʱ����ڵ�ǰʱ���ȥn��ǰ��ʱ�䣬��ֻ��ѯoracle��
			try {
				//���ɲ��¼sql
				String sql0 = CreateSql.getSqlByCon(kssj, jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "0", sort);
				//���ɲ�����sql
				String sql1 = CreateSql.getSqlByCon(kssj, jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "1", sort);
				
				//��ѯ���Ͻ��������
				if("0".equals(flag.trim())){
					//ִ�в�ѯ
					queryCount = OracleSearch.getTDCPGJCXCount(sql1);
					//����xml�����ؽ��
					return xmlcreate.createCountXml(queryCount, 0);
				}
				
				//������ǲ�ѯ���������ҳ��ѯ����
				if(pagesize > 0){//��ҳ��ѯ����
					//ִ�в�ѯָ��������¼
					listtx = OracleSearch.TDCPGJCX(sql0, from, pagesize, sort, sortType);
				} else {//���ط��ϲ�ѯ��������������
					//��ѯ�������Ա��ж������Ƿ񳬳���󷵻�����������������򷵻ؼ�¼�����򷵻ش���
					queryCount = OracleSearch.getTDCPGJCXCount(sql1);
					
					//�����ѯ������������������򷵻ش���
					if(queryCount > allowCount){
						return xmlcreate.createErrorXml(config.getErrorCode07());//������������������С������Χ��
					} else{
						//��ȡ���м�¼
						listtx = OracleSearch.TDCPGJCX(sql0, 0, queryCount, sort, sortType);	
					}
				}
			} catch (SQLException e) {
				//"04:��ѯ���ݿ�����쳣������ϵ����Ա��"
				return xmlcreate.createErrorXml(config.getErrorCode04());
			}
			
			//���ؽ��
			return xmlcreate.createXml(hmdCphm, listtx, null);
		} else if(jzDate.before(midDate)){//�����ֹʱ��С�ڵ�ǰʱ���ȥN��ǰ��ʱ�䣬��ֻ��ѯES��
			//����FilterBuilder filter
			filter = ESutil.getFilterByCon(kssj, jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, sort);//׼����ѯ����
			try {
				//��ѯ���Ͻ��������
				if(flag != null && "0".equals(flag.trim())){
					queryCount = ESsearcherFilter.getTdcpgjcxCount(filter);
					return xmlcreate.createCountXml(0, queryCount);
				}
				
				//������ǲ�ѯ���������ҳ��ѯ����
				if(pagesize > 0){
					if(sort != null && !"".equals(sort.trim()) && sortType != null && !"".equals(sortType.trim())){//���������
			    		queryCount = ESsearcherFilter.getTdcpgjcxCount(filter);
			    		if(queryCount > maxOrder){
			    			//��¼����������������ֵ��������
			    			hits = ESsearcherFilter.tdcpgjcx(filter, from, pagesize, business, "", "");
			    		} else{
			    			//������ָ����Χ��
			    			hits = ESsearcherFilter.tdcpgjcx(filter, from, pagesize, business, sort, sortType);
			    		}
			    	} else{
			    		hits = ESsearcherFilter.tdcpgjcx(filter, from, pagesize, business, "", "");
			    	} 
				} else{//���ط������ݵ�ȫ����¼
					queryCount = ESsearcherFilter.getTdcpgjcxCount(filter);
					if(queryCount > allowCount){//�����ѯ������������������򷵻ش���
						return xmlcreate.createErrorXml(config.getErrorCode07());//"07:��ѯ�����Ϊ"+queryCount+"����������������������С������Χ��"
					} else{
						//��¼����������������ֵ��������
						if(queryCount > maxOrder){
							hits = ESsearcherFilter.tdcpgjcx(filter, 0, queryCount, business, "", "");//ͨ��query��ѯES��
						} else {
							//������ָ����Χ��
							hits = ESsearcherFilter.tdcpgjcx(filter, 0, queryCount, business, sort, sortType);//ͨ��query��ѯES��
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				//"04:��ѯ���ݿ�����쳣������ϵ����Ա��"
				return xmlcreate.createErrorXml(config.getErrorCode04());
			}
			return xmlcreate.createXml(hmdCphm, null, hits);
		} else {//��ѯ������
			try {
				if("0".equals(flag.trim())){//��ѯ�������ݿ���Ͻ��������
					String oraCountSql = CreateSql.getSqlByCon(sdf.format(midDate), jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "1", sort);
					// ES//��ѯ����ʱ��Ϊ�м��ʱ��
					filter = ESutil.getFilterByCon(kssj, sdf.format(midDate), hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, sort);
					//��ѯoracle��ES�����������¼����
					threadsSignal = new CountDownLatch(2);//���������߳���
					ESCountThreadByQuery escount = new ESCountThreadByQuery(threadsSignal, filter);//��ѯ��¼����
					escount.start();
					
					OracleCountThread oraclecount = new OracleCountThread(threadsSignal, oraCountSql);//ִ��oracle�߳�
					oraclecount.start();
					threadsSignal.await();//�ȴ�Oracle��ES���ѯ��ϡ���ֱ���߳���Ϊ�㣩
					
					//��ȡ�������
					int oraCou = oraclecount.count; 
					int esCou = escount.count;
					
					return xmlcreate.createCountXml(oraCou, esCou);
				}
				
				//������ǲ�ѯ���������ҳ��ѯ����
				if(pagesize > 0){
					/**
					 * ��ҳ��ѯ
					 * 	1.���Ȳ�ѯOracle���ݿ���������ļ�¼����queryCount��
					 * 	2.���ݸ�����fromֵ��pagesizeֵ�����from>=queryCount,��ֱ�Ӳ�ѯES�⣬
					 *    ���queryCount>=from+pagesize,��ֻ��ѯOralce���ݿ⣬������Ҫ��ѯ�������ݿ⣻
					 * 	3.�����Ҫ��ѯ�������ݿ⣬Oracle���ݿ��ѯ��ΧΪfrom��queryCount,ES���ѯ��ΧΪ0��pagesize-(queryCount-from)
					 */
					//Oracle����������¼��
					queryCount = OracleSearch.getTDCPGJCXCount(CreateSql.getSqlByCon(sdf.format(midDate), jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "1", sort));
					
					//ES����������¼��
					filter = ESutil.getFilterByCon(kssj, sdf.format(midDate), 
							hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, sort);
					
				    if(queryCount >= from + pagesize){//���ORACLE������ѯ�������������Ѿ��ﵽ�����������������ֻ��ѯORACLE��
				    	listtx = OracleSearch.TDCPGJCX(CreateSql.getSqlByCon(sdf.format(midDate), jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "0", sort), from, pagesize,sort,sortType);
				    	return xmlcreate.createXml(hmdCphm, listtx, null);
				    } else if(from >= queryCount){//��������������ҳ������ORACLE�⣬���ѯES��
				    	if(sort != null && !"".equals(sort.trim()) && sortType != null && !"".equals(sortType.trim())){//���������
				    		int escount = ESsearcherFilter.getTdcpgjcxCount(filter);//��ѯ����ES�����ļ�¼��
				    		if(escount > maxOrder){//��ѯ��¼��������ָ��ֵ����ѯ����
				    			//��¼����������������ֵ
//								return xmlcreate.createErrorXml(config.getErrorCode14()+";�����������������ֵΪ"+config.getMaxOrder());
				    			hits = ESsearcherFilter.tdcpgjcx(filter, from - queryCount, pagesize, businessType,"","");
				    			return xmlcreate.createXml(hmdCphm, null,hits);
				    		} else{
				    			hits = ESsearcherFilter.tdcpgjcx(filter, from - queryCount, pagesize, businessType,sort,sortType);
				    			return xmlcreate.createXml(hmdCphm, null,hits);
				    		}
				    	} 
													
				    	hits = ESsearcherFilter.tdcpgjcx(filter, from - queryCount, pagesize, businessType,sort,sortType);
				    	return xmlcreate.createXml(hmdCphm, null,hits);
					} else{
						//��ѯ������
						threadsSignal = new CountDownLatch(2);//���������߳���
						ESThreadByQuery es = null;
						if(!"".equals(sort.trim())&&!"".equals(sortType)){//���������
				    		Integer escount = ESsearcherFilter.getTdcpgjcxCount(filter);//����������es��¼����
				    		if(escount > maxOrder){
				    			//��¼����������������ֵ
//								return xmlcreate.createErrorXml(config.getErrorCode14()+";�����������������ֵΪ"+config.getMaxOrder());
				    			es = new ESThreadByQuery(threadsSignal, filter, 0, pagesize - (queryCount - from), business, "", "");//ִ��Es�߳�
								es.start();
				    		}else{
				    			es = new ESThreadByQuery(threadsSignal, filter, 0, pagesize - (queryCount - from), business, sort, sortType);//ִ��Es�߳�
								es.start();
				    		}
				    	} else{
				    		es = new ESThreadByQuery(threadsSignal, filter, 0, pagesize - (queryCount - from), business, sort, sortType);//ִ��Es�߳�
							es.start();
				    	}
						
						String sql = CreateSql.getSqlByCon(sdf.format(midDate), jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business,"0", sort);
						OracleThread oracle = new OracleThread(threadsSignal, sql, from, queryCount - from, sort, sortType);//ִ��oracle�߳�
						oracle.start();
						threadsSignal.await();//�ȴ�Oracle��ES���ѯ��ϡ�
						
						return xmlcreate.createXml(hmdCphm, oracle.listtx, es.hits);
					}
				}else{
					/**
					 *�������з��������ļ�¼ 
					 *1.��������ѯ����������������ļ�¼���������¼����������ֵ�������쳣��Ϣ��
					 *2.�ڲ�ѯ�������¼�ۺϺͼ�¼ʱ��ʹ���̲߳���������ͬʱ��ѯ�������ݿ���Ϣ�������Ӧʱ�䣻
					 */
					// ��ѯ��ʼʱ��Ϊ�м�ʱ���
					String oraCountSql = CreateSql.getSqlByCon(sdf.format(midDate), jssj, 
							hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "1", sort);
					// ES//��ѯ����ʱ��Ϊ�м��ʱ��
					filter = ESutil.getFilterByCon(kssj, sdf.format(midDate), 
							hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, sort);
//					query = ESutil.getQueryBuilderByCon(kssj, sdf.format(midDate), 
//							hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business);
					//��ѯoracle��es�����������¼����
					threadsSignal = new CountDownLatch(2);//���������߳���
					ESCountThreadByQuery escount = new ESCountThreadByQuery(threadsSignal, filter);//��ѯ��¼����
					escount.start();
					
					OracleCountThread oraclecount = new OracleCountThread(threadsSignal, oraCountSql);//ִ��oracle�߳�
					oraclecount.start();
					threadsSignal.await();//�ȴ�Oracle��ES���ѯ��ϡ�
					
					//��ȡ���
					int oraCou = oraclecount.count; 
					int esCou  = escount.count ;
					queryCount = oraCou + esCou;
					
					if(queryCount > allowCount){//��ѯ�����������������
						//"07:��ѯ�����Ϊ"+queryCount+"����������������������С������Χ��"
						return xmlcreate.createErrorXml(config.getErrorCode07());
					}else if(queryCount>maxOrder){
						return xmlcreate.createErrorXml(config.getErrorCode14()+";�����������������ֵΪ"+config.getMaxOrder());
					}else{
						String sql = CreateSql.getSqlByCon(sdf.format(midDate), jssj,
								hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "0", sort);
						
						threadsSignal = new CountDownLatch(2);//���������߳���
						ESThreadByQuery es = new ESThreadByQuery(threadsSignal, filter, 0, esCou, business,sort,sortType);//ִ��Es�߳�
						es.start();
						
						OracleThread oracle = new OracleThread(threadsSignal, sql, 0, oraCou,sort,sortType);//ִ��oracle�߳�
						oracle.start();
						threadsSignal.await();//�ȴ�Oracle��ES���ѯ��ϡ�
						
						return xmlcreate.createXml(hmdCphm, oracle.listtx, es.hits);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				//"06:��ѯ�����쳣������ϵ����Ա��"
				return xmlcreate.createErrorXml(config.getErrorCode06());
			}
		}
	}
	
	/**
	 * �켣ͼƬ����
	 * @param xml           XML������
	 * @param ���ؼ�¼ 
	 */
	@SuppressWarnings("unchecked")
	public String tpcx(String xml, String businessType) {
		//������Ϣ��
		Config config = Config.getInstance();
		
		Document document = null;
		Element body = null;
		CountDownLatch threadsSignal;
		//xml��װ
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		
		List<Element> listTpid = null;//ͼƬid����
		List listPic = new ArrayList();//ͼƬ��ַ����
		String tpid = "";
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			body = (Element) root.selectNodes("body").get(0);
			listTpid = body.selectNodes("tpid");
			
			//ͼƬidΪ��
			if(listTpid == null || listTpid.size() == 0){
				//"10:����ͼƬid�޷�������"
				return xmlcreate.createErrorXml(config.getErrorCode10());
			}
		} catch (Exception e) {
			//"09����XML�ļ������쳣
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		
		//ÿ�������߳���
		int threadNum = Integer.parseInt(config.getNumThread().trim());
		try {
			int len = listTpid.size();//ͼƬ��
			for (int j = 0;j < len;j+=threadNum) {
				if(len - j < threadNum){
					threadsSignal = new CountDownLatch(len - j);//����en-beg���߳�,������threadNum��ʱ
				}else{
					threadsSignal = new CountDownLatch(threadNum);//����threadNum���߳�
				}
				
				for(int i = j;i < listTpid.size() && i < j + threadNum;i++){
					tpid = ((Element)listTpid.get(i)).getText();
					if(tpid == null || "".equals(tpid)){
						continue;
					}
					
					PicThread index = new PicThread(threadsSignal, tpid, businessType, listPic);
					index.start();
				}
				threadsSignal.await();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//�ȴ��߳���ִ�����
		return xmlcreate.createPicPath(listPic);
	}
	
	/**
	 * ͨ��ͼƬid��ѯ����
	 * @param kssj
	 * @return
	 */
	@SuppressWarnings("finally")
	public String getHitsByTpid(String xml){
		Config config = Config.getInstance();//������Ϣ��
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		String tpid = null;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//��ȡ���ڵ�
//			Element head = (Element) root.selectNodes("head").get(0);
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			
			tpid = data.element("tpid").getText();//ͼƬid
		} catch (Exception e) {
			//"09:xml�ļ���ʽ�޷����������飡"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES���ݿ����ӳ�,��ȡ��������
		MatchAllQueryBuilder query = matchAllQuery();
		SearchResponse response = null;
		SearchHits hits = null;
		FilterBuilder filter = null;
		try {
			filter = FilterBuilders.boolFilter().must(FilterBuilders.termFilter("tpid1",tpid)).cache(false);
			SearchRequestBuilder searchRequestBuilder = client.prepareSearch("sb").setTypes("sb")
			         .setQuery(QueryBuilders.filteredQuery(query, filter))
					 .addFields(new String[]{"cphm1","jcdid","cplx1","tgsj","cdid","tpid1",
   							 "tpid2","tpid3","tpid4","tpid5","sd","tpzs","cb","scsj"})
   					.setExplain(false);//���Բ�ѯ���ݽ��н���;
			response = searchRequestBuilder.execute().actionGet();
			if(response != null){
				hits = response.getHits();//��ȡ���
			}
		}catch (ReceiveTimeoutTransportException e) {
			e.printStackTrace();
			if (client != null) {
				client.close();
			}
			return xmlcreate.createErrorXml(config.getErrorCode04());
		} finally {
			if (client != null) {
				ecclient.freeConnection("es", client);
			}
			if(hits != null){
				return xmlcreate.createXml("0", null, hits);
			}
			return null;
		}
	}
	/**
	 * ��ѯ������״̬��Ϣ
	 */
	@SuppressWarnings("finally")
	public String searchServeInfo(String xml){
		Config config = Config.getInstance();//������Ϣ��
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		String hosts = null;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//��ȡ���ڵ�
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			
			hosts = data.element("hosts").getText();//ip��ַ
		} catch (Exception e) {
			//"09:xml�ļ���ʽ�޷����������飡"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES���ݿ����ӳ�,��ȡ��������
		QueryBuilder match_all = QueryBuilders.matchAllQuery();
		QueryBuilder query = null;
		SearchHits hits = null;
		try {
			if(StringUtils.isNotEmpty(hosts.trim())){
				query = QueryBuilders.filteredQuery(match_all, FilterBuilders.termsFilter("host", hosts.split(";")));
			}
			//�ۺϲ���������ip�����ѯ����queryTime
			AggregationBuilder aggs = AggregationBuilders.terms("host_group").field("host").size(100).subAggregation(
					AggregationBuilders.max("max_new").field("queryTime"));
			SearchRequestBuilder request = client.prepareSearch("server").setTypes("info")
					.setSearchType(SearchType.COUNT)
					.setQuery(query)
					.addAggregation(aggs).setExplain(false);
			SearchResponse response = request.execute().actionGet();
			if(response != null){
				Terms host_group = response.getAggregations().get("host_group");
				List<FilterBuilder> filterList = new ArrayList<FilterBuilder>();
				for (Terms.Bucket entry : host_group.getBuckets()) {
					Max max_new = entry.getAggregations().get("max_new");
					filterList.add(FilterBuilders.boolFilter().must(FilterBuilders.termFilter("host", entry.getKey()),FilterBuilders.termFilter("queryTime", max_new.getValue())));
				}
				request = client.prepareSearch("server").setTypes("info")
						.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), FilterBuilders.orFilter(filterList.toArray(new FilterBuilder[filterList.size()]))))
						.addSort("host", SortOrder.ASC).setExplain(false);
				response = request.execute().actionGet();
				if(response != null){
					hits = response.getHits();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (client != null) {
				client.close();
			}
			return xmlcreate.createErrorXml(config.getErrorCode04());
		} finally {
			if (client != null) {
				ecclient.freeConnection("es", client);
			}
			if(hits != null){
				return xmlcreate.createServerInfoXml(hits);
			}
			return null;
		}
	}
	/**
	 * ��ѯ���ҹ��
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("finally")
	public String queryZfyc(String xml){
		Config config = Config.getInstance();//������Ϣ��
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		String kssj = null;
		String jssj = null;
		String hphm = null;
		String sort = null;
		String sortType = null;
		int from = 0;
		int size = 200;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//��ȡ���ڵ�
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			Element head = (Element) root.selectNodes("head").get(0);
			
			kssj = data.element("kssj").getTextTrim();//��ʼʱ��
			jssj = data.element("jssj").getTextTrim();//����ʱ��
			hphm = data.element("hphm").getTextTrim();//���ƺ���
			sort = head.element("sort").getTextTrim();
			sortType = head.element("sortType").getTextTrim();
			if(StringUtils.isNotEmpty(head.elementTextTrim("from"))){
				from = Integer.parseInt(head.elementTextTrim("from"));
			}
			if(StringUtils.isNotEmpty(head.elementTextTrim("pagesize"))){
				size = Integer.parseInt(head.elementTextTrim("pagesize"));
			}
		} catch (Exception e) {
			//"09:xml�ļ���ʽ�޷����������飡"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES���ݿ����ӳ�,��ȡ��������
		QueryBuilder query = null;
		SearchHits hits = null;
		SearchResponse response = null;
		List<String> timeList = new ArrayList<String>();
		FilterBuilder filter = null;
		Gson gson = new Gson();
		try {
			List<FilterBuilder> filterList = new ArrayList<FilterBuilder>();
			//ʱ�䷶Χ������
			filterList.add(FilterBuilders.rangeFilter("ts").gte(kssj).lte(jssj));
			//����������
			if(StringUtils.isNotEmpty(hphm)){
				filterList.add(FilterBuilders.termFilter("cphm", hphm));
			}
			//��ѯ��
			query = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), FilterBuilders.boolFilter().must(filterList.toArray(new FilterBuilder[filterList.size()])));
			//������
			SearchRequestBuilder request = client.prepareSearch("daily").setTypes("zfyc")
					.setQuery(query).setExplain(false).setFrom(0).setSize(100)
					.addFields(new String[]{"ts"});
			
			//�õ����
			response = request.execute().actionGet();
			if(response != null){
				hits = response.getHits();
			}
			for (SearchHit hit : hits) {
				SearchHitField field = hit.field("ts");
				timeList.add((String)field.getValue());
			}
			response = null;
			hits = null;
			filterList.clear();
			if(timeList.size() > 0 && StringUtils.isNotEmpty(hphm)){
				for (String str : timeList) {
					filterList.add(FilterBuilders.rangeFilter("tgsj").gte(df.parse(str).getTime()).lte(df.parse(str).getTime()+24*60*60*1000));
				}
				filter = FilterBuilders.boolFilter().must(FilterBuilders.boolFilter().should(filterList.toArray(new FilterBuilder[filterList.size()])),FilterBuilders.termFilter("cphm1", hphm));
			}
			if(filter != null){
				query = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), filter);
				SearchRequestBuilder searchRequestBuilder = client.prepareSearch("sb").setTypes("sb")
						.setQuery(query)
						.addFields(new String[]{"cphm1","jcdid","cplx1","tgsj","cdid","tpid1",
								"tpid2","tpid3","tpid4","tpid5","sd","tpzs","cb","scsj"})
								.setExplain(false).setFrom(from).setSize(size);
				if(StringUtils.isNotEmpty(sort) && StringUtils.isNotEmpty(sortType)){
					searchRequestBuilder.addSort(sort, "DESC".equals(sortType.toUpperCase())?SortOrder.DESC:SortOrder.ASC);
				}
				response = searchRequestBuilder.execute().actionGet();
			}
			if(response != null){
				hits = response.getHits();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (client != null) {
				client.close();
			}
			return xmlcreate.createErrorXml(config.getErrorCode04());
		} finally {
			if (client != null) {
				ecclient.freeConnection("es", client);
			}
			if(hits != null){
				return xmlcreate.createXml("0", null, hits);
			}
			return null;
		}
	}
	/**
	 * ��ѯ�������
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("finally")
	public String queryCcrc(String xml){
		Config config = Config.getInstance();//������Ϣ��
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		String cplx = null;
		String cphm = null;
		String kssj = null;
		String jssj = null;
		String sort = null;
		String sortType = null;
		int from = 0;
		int size = 10;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//��ȡ���ڵ�
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			Element head = (Element) root.selectNodes("head").get(0);
			
			kssj = data.element("kssj").getTextTrim();//��ʼʱ��
			jssj = data.element("jssj").getTextTrim();//����ʱ��
			cplx = data.element("cplx").getTextTrim();//��������
			cphm = data.element("cphm").getTextTrim();//���ƺ���
			sort = head.elementTextTrim("sort");
			sortType = head.elementTextTrim("sortType");
			if(StringUtils.isNotEmpty(head.elementTextTrim("from"))){
				from = Integer.parseInt(head.elementTextTrim("from"));
			}
			if(StringUtils.isNotEmpty(head.elementTextTrim("pagesize"))){
				size = Integer.parseInt(head.elementTextTrim("pagesize"));
			}
		} catch (Exception e) {
			//"09:xml�ļ���ʽ�޷����������飡"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES���ݿ����ӳ�,��ȡ��������
		QueryBuilder query = null;
		SearchHits hits = null;
		SearchResponse response = null;
		try {
			List<FilterBuilder> filterList = new ArrayList<FilterBuilder>();
			if(StringUtils.isNotEmpty(kssj) && StringUtils.isNotEmpty(jssj)){
				filterList.add(FilterBuilders.rangeFilter("tgsj").gte(sdf.parse(kssj).getTime()).lte(sdf.parse(jssj).getTime()));
			}
			if(StringUtils.isNotEmpty(cplx)){
				String[] cplxs = cplx.split(";");
				filterList.add(FilterBuilders.termsFilter("cplx", cplxs));
			}
			if(StringUtils.isNotEmpty(cphm)){
				if(cphm.contains("?") || cphm.contains("*")){
					filterList.add(FilterBuilders.queryFilter(QueryBuilders.wildcardQuery("cphm", cphm)));
				}else{
					String[] cphms = cphm.split(",");
					filterList.add(FilterBuilders.termsFilter("cphm", cphms));
				}
			}
			FilterBuilder filter = FilterBuilders.boolFilter().must(filterList.toArray(new FilterBuilder[filterList.size()]));
			query = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), filter);
			SearchRequestBuilder request = client.prepareSearch("ccrc").setTypes("ccrc")
					.setQuery(query).setExplain(false).setFrom(from).setSize(size);
			if(StringUtils.isNotEmpty(sort) && StringUtils.isNotEmpty(sortType)){
				request.addSort(sort, "DESC".equals(sortType.toUpperCase())?SortOrder.DESC:SortOrder.ASC);
			}
			response = request.execute().actionGet();
			if(response != null){
				hits = response.getHits();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (client != null) {
				client.close();
			}
			return xmlcreate.createErrorXml(config.getErrorCode04());
		} finally {
			if (client != null) {
				ecclient.freeConnection("es", client);
			}
			if(hits != null){
				return xmlcreate.createCcrcXml(hits);
			}
			return null;
		}
	}
	/**
	 * ��ѯҵ����־
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("finally")
	public String queryBusinessLog(String xml){
		Config config = Config.getInstance();//������Ϣ��
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		
		String operator = null;
		String operatorName = null;
		String ip = null;
		String moduleName = null;
		String operateName = null;
		String kssj = null;
		String jssj = null;
		String sort = null;
		String sortType = null;
		int from = 0;
		int size = 10;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//��ȡ���ڵ�
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			Element head = (Element) root.selectNodes("head").get(0);
			
			operator = data.element("operator").getTextTrim();//�������û���
			operatorName = data.element("operatorName").getTextTrim();//����������
			ip = data.element("ip").getTextTrim();//ip
			moduleName = data.element("moduleName").getTextTrim();//ģ����
			operateName = data.element("operateName").getTextTrim();//��������
			kssj = data.element("kssj").getTextTrim();//��ʼʱ��
			jssj = data.element("jssj").getTextTrim();//����ʱ��
			
			sort = head.elementTextTrim("sort");
			sortType = head.elementTextTrim("sortType");
			if(StringUtils.isNotEmpty(head.elementTextTrim("from"))){
				from = Integer.parseInt(head.elementTextTrim("from"));
			}
			if(StringUtils.isNotEmpty(head.elementTextTrim("pagesize"))){
				size = Integer.parseInt(head.elementTextTrim("pagesize"));
			}
		} catch (Exception e) {
			//"09:xml�ļ���ʽ�޷����������飡"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES���ݿ����ӳ�,��ȡ��������
		QueryBuilder query = null;
		SearchHits hits = null;
		SearchResponse response = null;
		try {
			List<FilterBuilder> filterList = new ArrayList<FilterBuilder>();
			//�������û���
			if(StringUtils.isNotEmpty(operator)){
				filterList.add(FilterBuilders.termFilter("operator", operator));
			}
			//����������
			if(StringUtils.isNotEmpty(operatorName)){
				filterList.add(FilterBuilders.termFilter("operatorName", operatorName));
			}
			//ip
			if(StringUtils.isNotEmpty(ip)){
				filterList.add(FilterBuilders.termFilter("ip", ip));
			}
			//ģ����
			if(StringUtils.isNotEmpty(moduleName)){
				filterList.add(FilterBuilders.termFilter("moduleName", moduleName));
			}
			//��������
			if(StringUtils.isNotEmpty(operateName)){
				filterList.add(FilterBuilders.termFilter("operateName", operateName));
			}
			//ʱ��
			if(StringUtils.isNotEmpty(kssj) && StringUtils.isNotEmpty(jssj)){
				filterList.add(FilterBuilders.rangeFilter("operateDate").gte(kssj).lte(jssj));
			}
			FilterBuilder filter = FilterBuilders.boolFilter().must(filterList.toArray(new FilterBuilder[filterList.size()]));
			query = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), filter);
			SearchRequestBuilder request = client.prepareSearch("businesslog").setTypes("businesslog")
					.setQuery(query).setExplain(false).setFrom(from).setSize(size);
			if(StringUtils.isNotEmpty(sort) && StringUtils.isNotEmpty(sortType)){
				request.addSort(sort, "DESC".equals(sortType.toUpperCase())?SortOrder.DESC:SortOrder.ASC);
			}
			response = request.execute().actionGet();
			if(response != null){
				hits = response.getHits();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (client != null) {
				client.close();
			}
			return xmlcreate.createErrorXml(config.getErrorCode04());
		} finally {
			if (client != null) {
				ecclient.freeConnection("es", client);
			}
			if(hits != null){
				return xmlcreate.createBusinessLogXml(hits);
			}
			return null;
		}
	}
	/**
	 * ����id��ѯҵ����־
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("finally")
	public String getBusinessLogById(String xml){
		Config config = Config.getInstance();//������Ϣ��
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		String id = null;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//��ȡ���ڵ�
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			Element head = (Element) root.selectNodes("head").get(0);
			
			id = data.element("id").getTextTrim();//id
		} catch (Exception e) {
			//"09:xml�ļ���ʽ�޷����������飡"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES���ݿ����ӳ�,��ȡ��������
		GetResponse response = null;
		try {
			response = client.prepareGet("businesslog", "businesslog", id).execute().actionGet();
		} catch (Exception e) {
			e.printStackTrace();
			if (client != null) {
				client.close();
			}
			return xmlcreate.createErrorXml(config.getErrorCode04());
		} finally {
			if (client != null) {
				ecclient.freeConnection("es", client);
			}
			if(response != null){
				return xmlcreate.createBusinessLogXml(response.getSource());
			}
			return null;
		}
	}
}