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
	private CreateSql sqlInfo = null;//oracle库sql语句生成类
	
	/**
	 * 轨迹查询方法，按照业务类型区分
	 * @param xml           XML请求报文
	 * @param businessType  查询业务类型
	 * @param flag          1:返回记录，0:返回记录总数，  返回识别记录还是记录总数
	 * @param 返回记录总数还是识别记录
	 */
	public String gjcx(String xml, String businessType, String flag){
		Config config = Config.getInstance();//配置信息类
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		
		//条件变量
		String cphid = null;//号牌号码
		String hpzl = null;//号牌种类
		String cplx = null;//车牌类型
		String jcdid = null;//监测点ID
		String gcxh = null;//过车序号（图片id）
		String kssj = null;//开始时间
		String jssj = null;//截止时间
		String cd = null;//车道
		String cb = null;//车标
		String sd = null;//速度
		String hmdCphm = null;//红名单车牌号码
		String strPage = null;//页面显示记录数
		String strFrom = null;//起始记录数
		String business = null;//业务查询类型
		String sort = "";//排序字段
		String sortType = "" ;//排序字段排序顺序，降序或升序
		
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//获取根节点
			Element head = (Element) root.selectNodes("head").get(0);
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);
			
			strPage = head.element("pagesize").getText();//页面显示记录数
			strFrom = head.element("from").getText();//起始记录数
			
			kssj = data.element("kssj").getText();//起始时间
			jssj = data.element("jssj").getText();//截止时间
			hpzl = data.element("hpzl").getText();//号牌种类
			cphid = data.element("hphm").getText();//号牌号码
			cplx = data.element("cplx").getText();//车牌类型
			gcxh = data.element("tpid").getText();//过车序号
			jcdid = data.element("jcdid").getText();//监测点ID
			cd = data.element("cd").getText();//车道
			cb = data.element("cb").getText();//车标
			sd = data.element("sd").getText();//速度
			hmdCphm = data.element("hmdCphm").getText();//红名单车牌号码
			business = businessType;//业务查询类型
			try {
				sort = head.element("sort").getText();//排序字段
				sortType = head.element("sortType").getText();//排序字段
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			//"09:xml文件格式无法解析，请检查！"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		
		//判断给定时间，查询Oracle数据库还是ES库
		Date midDate = null;
		Date ksDate = null;
		Date jzDate = null;
		try {
			ksDate = sdf.parse(kssj);//开始时间
			jzDate = sdf.parse(jssj);//结束时间
			//截止时间不能超出当前时间
			if(jzDate.getTime() > new Date().getTime()){
				jzDate = new Date();
			}
			
			//Oracle库和ES库查询分界点,,,参数可配置
			midDate = df.parse(InterUtil.getTime(Integer.parseInt(config.getBeforeDate())));
		} catch (ParseException e) {
			//"03:时间格式不合法或者为空，请检查！"
			return  xmlcreate.createErrorXml(config.getErrorCode03());
		}
		
		//----分页参数处理-----
		int pagesize = 0;//每页显示的数据
		int from = 0;//从第几条开始取
		int allowCount = Integer.parseInt(config.getMaxCount());//最大允许返回记录总数
		int pageCount = Integer.parseInt(config.getPageCount());;//分页查询最大允许返回的记录数
		int maxOrder = Integer.parseInt(config.getMaxOrder());//最大允许排序总记录数
		
		if(strPage != null && !"".equals(strPage)){
			try {
				pagesize = Integer.parseInt(strPage);
				from = Integer.parseInt(strFrom);
				if(pagesize > 0 && pagesize > pageCount){//检查分页记录数
					//"05:分页查询记录数超过最大允许值！"
					return xmlcreate.createErrorXml(config.getErrorCode05());
				} else if(pagesize < 0 || from < 0) {
					//"11:分页参数不合法！"
					return xmlcreate.createErrorXml(config.getErrorCode11());
				}
				//按照分页查询，记录数超过设置值时，不再返回，因为ES做深层的分页时，
				//由于查询机制问题，容易导致内存溢出，库崩溃
				if((pagesize+from) > maxOrder){
					//分页条数超过限定值
					return xmlcreate.createErrorXml(config.getErrorCode05());
				}
			} catch (Exception e) {
				//08:解析分页参数出现异常
				return  xmlcreate.createErrorXml(config.getErrorCode08());
			}
		}
		
		SearchHits hits = null;
		FilterBuilder filter = null;
		CountDownLatch threadsSignal;
		int queryCount = 0;//满足查询条件总数
		List<Sbnew> listtx = new ArrayList<Sbnew>();//存放查询结果集
		
		//oracle库sql语句生成类
		if(sqlInfo == null){
			sqlInfo = new CreateSql();
		}
		
		if(ksDate.after(midDate) || ksDate.equals(midDate)){//如果开始时间大于当前时间减去n天前的时间，则只查询oracle库
			try {
				//生成查记录sql
				String sql0 = CreateSql.getSqlByCon(kssj, jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "0", sort);
				//生成查总数sql
				String sql1 = CreateSql.getSqlByCon(kssj, jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "1", sort);
				
				//查询符合结果的总数
				if("0".equals(flag.trim())){
					//执行查询
					queryCount = OracleSearch.getTDCPGJCXCount(sql1);
					//创建xml，返回结果
					return xmlcreate.createCountXml(queryCount, 0);
				}
				
				//如果不是查询总数，则分页查询数据
				if(pagesize > 0){//分页查询数据
					//执行查询指定条数记录
					listtx = OracleSearch.TDCPGJCX(sql0, from, pagesize, sort, sortType);
				} else {//返回符合查询条件的所有数据
					//查询总数，以便判断总数是否超出最大返回数，如果不超出，则返回记录，否则返回错误
					queryCount = OracleSearch.getTDCPGJCXCount(sql1);
					
					//如果查询结果集超过给定数，则返回错误。
					if(queryCount > allowCount){
						return xmlcreate.createErrorXml(config.getErrorCode07());//超出允许条数，请缩小检索范围！
					} else{
						//获取所有记录
						listtx = OracleSearch.TDCPGJCX(sql0, 0, queryCount, sort, sortType);	
					}
				}
			} catch (SQLException e) {
				//"04:查询数据库出现异常，请联系管理员！"
				return xmlcreate.createErrorXml(config.getErrorCode04());
			}
			
			//返回结果
			return xmlcreate.createXml(hmdCphm, listtx, null);
		} else if(jzDate.before(midDate)){//如果截止时间小于当前时间减去N天前的时间，则只查询ES库
			//生成FilterBuilder filter
			filter = ESutil.getFilterByCon(kssj, jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, sort);//准备查询条件
			try {
				//查询符合结果的总数
				if(flag != null && "0".equals(flag.trim())){
					queryCount = ESsearcherFilter.getTdcpgjcxCount(filter);
					return xmlcreate.createCountXml(0, queryCount);
				}
				
				//如果不是查询总数，则分页查询数据
				if(pagesize > 0){
					if(sort != null && !"".equals(sort.trim()) && sortType != null && !"".equals(sortType.trim())){//如果有排序
			    		queryCount = ESsearcherFilter.getTdcpgjcxCount(filter);
			    		if(queryCount > maxOrder){
			    			//记录总数超过允许排序值，则不排序
			    			hits = ESsearcherFilter.tdcpgjcx(filter, from, pagesize, business, "", "");
			    		} else{
			    			//排序，在指定范围内
			    			hits = ESsearcherFilter.tdcpgjcx(filter, from, pagesize, business, sort, sortType);
			    		}
			    	} else{
			    		hits = ESsearcherFilter.tdcpgjcx(filter, from, pagesize, business, "", "");
			    	} 
				} else{//返回符合数据的全部记录
					queryCount = ESsearcherFilter.getTdcpgjcxCount(filter);
					if(queryCount > allowCount){//如果查询结果集超过给定数，则返回错误。
						return xmlcreate.createErrorXml(config.getErrorCode07());//"07:查询结果集为"+queryCount+"条，超出允许条数，请缩小检索范围！"
					} else{
						//记录总数超过允许排序值，则不排序
						if(queryCount > maxOrder){
							hits = ESsearcherFilter.tdcpgjcx(filter, 0, queryCount, business, "", "");//通过query查询ES库
						} else {
							//排序，在指定范围内
							hits = ESsearcherFilter.tdcpgjcx(filter, 0, queryCount, business, sort, sortType);//通过query查询ES库
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				//"04:查询数据库出现异常，请联系管理员！"
				return xmlcreate.createErrorXml(config.getErrorCode04());
			}
			return xmlcreate.createXml(hmdCphm, null, hits);
		} else {//查询两个库
			try {
				if("0".equals(flag.trim())){//查询两个数据库符合结果的总数
					String oraCountSql = CreateSql.getSqlByCon(sdf.format(midDate), jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "1", sort);
					// ES//查询结束时间为中间点时间
					filter = ESutil.getFilterByCon(kssj, sdf.format(midDate), hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, sort);
					//查询oracle和ES库符合条件记录总数
					threadsSignal = new CountDownLatch(2);//创建两个线程组
					ESCountThreadByQuery escount = new ESCountThreadByQuery(threadsSignal, filter);//查询记录总数
					escount.start();
					
					OracleCountThread oraclecount = new OracleCountThread(threadsSignal, oraCountSql);//执行oracle线程
					oraclecount.start();
					threadsSignal.await();//等待Oracle和ES库查询完毕。（直到线程数为零）
					
					//获取结果总数
					int oraCou = oraclecount.count; 
					int esCou = escount.count;
					
					return xmlcreate.createCountXml(oraCou, esCou);
				}
				
				//如果不是查询总数，则分页查询数据
				if(pagesize > 0){
					/**
					 * 分页查询
					 * 	1.首先查询Oracle数据库符合条件的记录总数queryCount；
					 * 	2.根据给定的from值和pagesize值，如果from>=queryCount,则直接查询ES库，
					 *    如果queryCount>=from+pagesize,则只查询Oralce数据库，否则需要查询两个数据库；
					 * 	3.如果需要查询两个数据库，Oracle数据库查询范围为from至queryCount,ES库查询范围为0至pagesize-(queryCount-from)
					 */
					//Oracle符合条件记录数
					queryCount = OracleSearch.getTDCPGJCXCount(CreateSql.getSqlByCon(sdf.format(midDate), jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "1", sort));
					
					//ES符合条件记录数
					filter = ESutil.getFilterByCon(kssj, sdf.format(midDate), 
							hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, sort);
					
				    if(queryCount >= from + pagesize){//如果ORACLE库所查询到的数据总数已经达到所请求的数据量，则只查询ORACLE库
				    	listtx = OracleSearch.TDCPGJCX(CreateSql.getSqlByCon(sdf.format(midDate), jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "0", sort), from, pagesize,sort,sortType);
				    	return xmlcreate.createXml(hmdCphm, listtx, null);
				    } else if(from >= queryCount){//如果所请求的数据页数不在ORACLE库，则查询ES库
				    	if(sort != null && !"".equals(sort.trim()) && sortType != null && !"".equals(sortType.trim())){//如果有排序
				    		int escount = ESsearcherFilter.getTdcpgjcxCount(filter);//查询满足ES条件的记录数
				    		if(escount > maxOrder){//查询记录总数大于指定值，查询不做
				    			//记录总数超过允许排序值
//								return xmlcreate.createErrorXml(config.getErrorCode14()+";排序数据量最大允许值为"+config.getMaxOrder());
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
						//查询两个库
						threadsSignal = new CountDownLatch(2);//创建两个线程组
						ESThreadByQuery es = null;
						if(!"".equals(sort.trim())&&!"".equals(sortType)){//如果有排序
				    		Integer escount = ESsearcherFilter.getTdcpgjcxCount(filter);//符合条件的es记录总数
				    		if(escount > maxOrder){
				    			//记录总数超过允许排序值
//								return xmlcreate.createErrorXml(config.getErrorCode14()+";排序数据量最大允许值为"+config.getMaxOrder());
				    			es = new ESThreadByQuery(threadsSignal, filter, 0, pagesize - (queryCount - from), business, "", "");//执行Es线程
								es.start();
				    		}else{
				    			es = new ESThreadByQuery(threadsSignal, filter, 0, pagesize - (queryCount - from), business, sort, sortType);//执行Es线程
								es.start();
				    		}
				    	} else{
				    		es = new ESThreadByQuery(threadsSignal, filter, 0, pagesize - (queryCount - from), business, sort, sortType);//执行Es线程
							es.start();
				    	}
						
						String sql = CreateSql.getSqlByCon(sdf.format(midDate), jssj, hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business,"0", sort);
						OracleThread oracle = new OracleThread(threadsSignal, sql, from, queryCount - from, sort, sortType);//执行oracle线程
						oracle.start();
						threadsSignal.await();//等待Oracle和ES库查询完毕。
						
						return xmlcreate.createXml(hmdCphm, oracle.listtx, es.hits);
					}
				}else{
					/**
					 *返回所有符合条件的记录 
					 *1.按条件查询出两个库符合条件的记录数，如果记录数超过给定值，返回异常信息；
					 *2.在查询两个库记录综合和记录时，使用线程并发技术，同时查询两个数据库信息，提高响应时间；
					 */
					// 查询起始时间为中间时间点
					String oraCountSql = CreateSql.getSqlByCon(sdf.format(midDate), jssj, 
							hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "1", sort);
					// ES//查询结束时间为中间点时间
					filter = ESutil.getFilterByCon(kssj, sdf.format(midDate), 
							hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, sort);
//					query = ESutil.getQueryBuilderByCon(kssj, sdf.format(midDate), 
//							hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business);
					//查询oracle和es库符合条件记录总数
					threadsSignal = new CountDownLatch(2);//创建两个线程组
					ESCountThreadByQuery escount = new ESCountThreadByQuery(threadsSignal, filter);//查询记录总数
					escount.start();
					
					OracleCountThread oraclecount = new OracleCountThread(threadsSignal, oraCountSql);//执行oracle线程
					oraclecount.start();
					threadsSignal.await();//等待Oracle和ES库查询完毕。
					
					//获取结果
					int oraCou = oraclecount.count; 
					int esCou  = escount.count ;
					queryCount = oraCou + esCou;
					
					if(queryCount > allowCount){//查询结果集超过给定数。
						//"07:查询结果集为"+queryCount+"条，超出允许条数，请缩小检索范围！"
						return xmlcreate.createErrorXml(config.getErrorCode07());
					}else if(queryCount>maxOrder){
						return xmlcreate.createErrorXml(config.getErrorCode14()+";排序数据量最大允许值为"+config.getMaxOrder());
					}else{
						String sql = CreateSql.getSqlByCon(sdf.format(midDate), jssj,
								hpzl, cphid, cplx, gcxh, jcdid, cd, cb, sd, hmdCphm, business, "0", sort);
						
						threadsSignal = new CountDownLatch(2);//创建两个线程组
						ESThreadByQuery es = new ESThreadByQuery(threadsSignal, filter, 0, esCou, business,sort,sortType);//执行Es线程
						es.start();
						
						OracleThread oracle = new OracleThread(threadsSignal, sql, 0, oraCou,sort,sortType);//执行oracle线程
						oracle.start();
						threadsSignal.await();//等待Oracle和ES库查询完毕。
						
						return xmlcreate.createXml(hmdCphm, oracle.listtx, es.hits);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				//"06:查询出现异常，请联系管理员！"
				return xmlcreate.createErrorXml(config.getErrorCode06());
			}
		}
	}
	
	/**
	 * 轨迹图片方法
	 * @param xml           XML请求报文
	 * @param 返回记录 
	 */
	@SuppressWarnings("unchecked")
	public String tpcx(String xml, String businessType) {
		//配置信息类
		Config config = Config.getInstance();
		
		Document document = null;
		Element body = null;
		CountDownLatch threadsSignal;
		//xml封装
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		
		List<Element> listTpid = null;//图片id集合
		List listPic = new ArrayList();//图片地址集合
		String tpid = "";
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			body = (Element) root.selectNodes("body").get(0);
			listTpid = body.selectNodes("tpid");
			
			//图片id为空
			if(listTpid == null || listTpid.size() == 0){
				//"10:给定图片id无法解析！"
				return xmlcreate.createErrorXml(config.getErrorCode10());
			}
		} catch (Exception e) {
			//"09解析XML文件出现异常
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		
		//每次启动线程数
		int threadNum = Integer.parseInt(config.getNumThread().trim());
		try {
			int len = listTpid.size();//图片数
			for (int j = 0;j < len;j+=threadNum) {
				if(len - j < threadNum){
					threadsSignal = new CountDownLatch(len - j);//创建en-beg个线程,当不足threadNum个时
				}else{
					threadsSignal = new CountDownLatch(threadNum);//创建threadNum个线程
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
		
		//等待线程组执行完成
		return xmlcreate.createPicPath(listPic);
	}
	
	/**
	 * 通过图片id查询数据
	 * @param kssj
	 * @return
	 */
	@SuppressWarnings("finally")
	public String getHitsByTpid(String xml){
		Config config = Config.getInstance();//配置信息类
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		String tpid = null;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//获取根节点
//			Element head = (Element) root.selectNodes("head").get(0);
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			
			tpid = data.element("tpid").getText();//图片id
		} catch (Exception e) {
			//"09:xml文件格式无法解析，请检查！"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES数据库连接池,获取数据连接
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
   					.setExplain(false);//不对查询数据进行解析;
			response = searchRequestBuilder.execute().actionGet();
			if(response != null){
				hits = response.getHits();//获取结果
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
	 * 查询服务器状态信息
	 */
	@SuppressWarnings("finally")
	public String searchServeInfo(String xml){
		Config config = Config.getInstance();//配置信息类
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		String hosts = null;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//获取根节点
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			
			hosts = data.element("hosts").getText();//ip地址
		} catch (Exception e) {
			//"09:xml文件格式无法解析，请检查！"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES数据库连接池,获取数据连接
		QueryBuilder match_all = QueryBuilders.matchAllQuery();
		QueryBuilder query = null;
		SearchHits hits = null;
		try {
			if(StringUtils.isNotEmpty(hosts.trim())){
				query = QueryBuilders.filteredQuery(match_all, FilterBuilders.termsFilter("host", hosts.split(";")));
			}
			//聚合操作，根据ip分组查询最大的queryTime
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
	 * 查询昼伏夜出
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("finally")
	public String queryZfyc(String xml){
		Config config = Config.getInstance();//配置信息类
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
			Element root = document.getRootElement();//获取根节点
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			Element head = (Element) root.selectNodes("head").get(0);
			
			kssj = data.element("kssj").getTextTrim();//起始时间
			jssj = data.element("jssj").getTextTrim();//结束时间
			hphm = data.element("hphm").getTextTrim();//车牌号码
			sort = head.element("sort").getTextTrim();
			sortType = head.element("sortType").getTextTrim();
			if(StringUtils.isNotEmpty(head.elementTextTrim("from"))){
				from = Integer.parseInt(head.elementTextTrim("from"));
			}
			if(StringUtils.isNotEmpty(head.elementTextTrim("pagesize"))){
				size = Integer.parseInt(head.elementTextTrim("pagesize"));
			}
		} catch (Exception e) {
			//"09:xml文件格式无法解析，请检查！"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES数据库连接池,获取数据连接
		QueryBuilder query = null;
		SearchHits hits = null;
		SearchResponse response = null;
		List<String> timeList = new ArrayList<String>();
		FilterBuilder filter = null;
		Gson gson = new Gson();
		try {
			List<FilterBuilder> filterList = new ArrayList<FilterBuilder>();
			//时间范围过滤器
			filterList.add(FilterBuilders.rangeFilter("ts").gte(kssj).lte(jssj));
			//城区过滤器
			if(StringUtils.isNotEmpty(hphm)){
				filterList.add(FilterBuilders.termFilter("cphm", hphm));
			}
			//查询体
			query = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), FilterBuilders.boolFilter().must(filterList.toArray(new FilterBuilder[filterList.size()])));
			//请求体
			SearchRequestBuilder request = client.prepareSearch("daily").setTypes("zfyc")
					.setQuery(query).setExplain(false).setFrom(0).setSize(100)
					.addFields(new String[]{"ts"});
			
			//得到结果
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
	 * 查询初次入城
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("finally")
	public String queryCcrc(String xml){
		Config config = Config.getInstance();//配置信息类
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
			Element root = document.getRootElement();//获取根节点
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			Element head = (Element) root.selectNodes("head").get(0);
			
			kssj = data.element("kssj").getTextTrim();//起始时间
			jssj = data.element("jssj").getTextTrim();//结束时间
			cplx = data.element("cplx").getTextTrim();//车牌类型
			cphm = data.element("cphm").getTextTrim();//车牌号码
			sort = head.elementTextTrim("sort");
			sortType = head.elementTextTrim("sortType");
			if(StringUtils.isNotEmpty(head.elementTextTrim("from"))){
				from = Integer.parseInt(head.elementTextTrim("from"));
			}
			if(StringUtils.isNotEmpty(head.elementTextTrim("pagesize"))){
				size = Integer.parseInt(head.elementTextTrim("pagesize"));
			}
		} catch (Exception e) {
			//"09:xml文件格式无法解析，请检查！"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES数据库连接池,获取数据连接
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
	 * 查询业务日志
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("finally")
	public String queryBusinessLog(String xml){
		Config config = Config.getInstance();//配置信息类
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
			Element root = document.getRootElement();//获取根节点
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			Element head = (Element) root.selectNodes("head").get(0);
			
			operator = data.element("operator").getTextTrim();//操作者用户名
			operatorName = data.element("operatorName").getTextTrim();//操作者姓名
			ip = data.element("ip").getTextTrim();//ip
			moduleName = data.element("moduleName").getTextTrim();//模块名
			operateName = data.element("operateName").getTextTrim();//操作内容
			kssj = data.element("kssj").getTextTrim();//起始时间
			jssj = data.element("jssj").getTextTrim();//结束时间
			
			sort = head.elementTextTrim("sort");
			sortType = head.elementTextTrim("sortType");
			if(StringUtils.isNotEmpty(head.elementTextTrim("from"))){
				from = Integer.parseInt(head.elementTextTrim("from"));
			}
			if(StringUtils.isNotEmpty(head.elementTextTrim("pagesize"))){
				size = Integer.parseInt(head.elementTextTrim("pagesize"));
			}
		} catch (Exception e) {
			//"09:xml文件格式无法解析，请检查！"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES数据库连接池,获取数据连接
		QueryBuilder query = null;
		SearchHits hits = null;
		SearchResponse response = null;
		try {
			List<FilterBuilder> filterList = new ArrayList<FilterBuilder>();
			//操作者用户名
			if(StringUtils.isNotEmpty(operator)){
				filterList.add(FilterBuilders.termFilter("operator", operator));
			}
			//操作者姓名
			if(StringUtils.isNotEmpty(operatorName)){
				filterList.add(FilterBuilders.termFilter("operatorName", operatorName));
			}
			//ip
			if(StringUtils.isNotEmpty(ip)){
				filterList.add(FilterBuilders.termFilter("ip", ip));
			}
			//模块名
			if(StringUtils.isNotEmpty(moduleName)){
				filterList.add(FilterBuilders.termFilter("moduleName", moduleName));
			}
			//操作内容
			if(StringUtils.isNotEmpty(operateName)){
				filterList.add(FilterBuilders.termFilter("operateName", operateName));
			}
			//时间
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
	 * 根据id查询业务日志
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("finally")
	public String getBusinessLogById(String xml){
		Config config = Config.getInstance();//配置信息类
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		String id = null;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//获取根节点
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			Element head = (Element) root.selectNodes("head").get(0);
			
			id = data.element("id").getTextTrim();//id
		} catch (Exception e) {
			//"09:xml文件格式无法解析，请检查！"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES数据库连接池,获取数据连接
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