package com.dyst.service;

import static org.elasticsearch.index.query.FilterBuilders.scriptFilter;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import com.dyst.elasticsearch.util.ESClientManager;
import com.dyst.utils.Config;
import com.dyst.utils.XmlCreater;

public class AggsService {
	private XmlCreater xmlcreate = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 出行时间统计
	 * @return
	 */
	@SuppressWarnings("finally")
	public String footHold(String xml){
		Config config = Config.getInstance();//配置信息类
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		String cphm = null;
		String kssj = null;
		String jssj = null;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//获取根节点
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			
			cphm = data.element("cphm").getText();//车牌号码
			kssj = data.element("kssj").getText();//起始时间
			jssj = data.element("jssj").getText();//截止时间
		} catch (Exception e) {
			//"09:xml文件格式无法解析，请检查！"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES数据库连接池,获取数据连接
		MatchAllQueryBuilder queryAll = matchAllQuery();
		List<Bucket> bucketList = null;
		long total = 0;
		long sumOther = 0;
		SearchResponse response = null;
		try {
			if(StringUtils.isNotEmpty(cphm) && StringUtils.isNotEmpty(kssj) && StringUtils.isNotEmpty(jssj)){
				FilterBuilder cphmFilter = FilterBuilders.termFilter("cphm1", cphm);
				FilterBuilder timeFilter = FilterBuilders.rangeFilter("tgsj").gte(sdf.parse(kssj).getTime()).lte(sdf.parse(jssj).getTime());
				FilterBuilder filter = FilterBuilders.andFilter(cphmFilter,timeFilter);
				QueryBuilder query = QueryBuilders.filteredQuery(queryAll, filter);
				AggregationBuilder agg = AggregationBuilders.terms("jcd_count").field("jcdid").size(10);//size为最大返回结果数
				response = client.prepareSearch("sb").setTypes("sb").setQuery(query).addAggregation(agg).setSearchType(SearchType.COUNT).execute().actionGet();
				if(response != null){
					total = response.getHits().getTotalHits();
					Terms jcd_count = response.getAggregations().get("jcd_count");
					sumOther = jcd_count.getSumOfOtherDocCounts();
					bucketList = jcd_count.getBuckets();
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
			if(bucketList != null){
				return xmlcreate.createFootHold(bucketList, total-sumOther);
			}
			return null;
		}
		
	}
	/**
	 * 查询初始化车流量图表所需数据
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("finally")
	public String getInitChartData(String xml){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Config config = Config.getInstance();//配置信息类
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		String beginTime = null;
		String endTime = null;
		String jcd = null;
		String intervalStr = null;
		long interval = 0l;
		String max = null;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//获取根节点
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			
			beginTime = data.element("beginTime").getText();//开始时间
			endTime = data.element("endTime").getText();//结束时间
			jcd = data.element("jcd").getText();//监测点
			intervalStr = data.element("interval").getText();//时间间隔
			interval = Integer.parseInt(intervalStr)*60*1000;
			max = sdf.format(new Date((sdf.parse(endTime).getTime() - interval)));
		} catch (Exception e) {
			e.printStackTrace();
			//"09:xml文件格式无法解析，请检查！"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES数据库连接池,获取数据连接
		List<Bucket> bucketList = null;
		long total = 0l;
		
		try {
			//根据时间和监测点对数据进行过滤
			QueryBuilder query = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), 
					FilterBuilders.andFilter(FilterBuilders.rangeFilter("created_time").gte(beginTime).lt(endTime),
							FilterBuilders.termFilter("jcd", jcd)));
			//根据时间间隔对车流量进行统计
			AggregationBuilder agg = AggregationBuilders.terms("lane_group").field("lane").size(10)
					.subAggregation(AggregationBuilders.dateHistogram("time_group").field("created_time").interval(interval).format("yyyy-MM-dd HH:mm:ss").minDocCount(0).extendedBounds(beginTime, max)
							.subAggregation(AggregationBuilders.sum("pass_count").field("count")));
			
			SearchResponse response = client.prepareSearch("lltj").setTypes("sum")
					.setSearchType(SearchType.COUNT)
					.setQuery(query)
					.addAggregation(agg)
					.execute().actionGet();
			Terms lane_group = response.getAggregations().get("lane_group");
			total = response.getHits().getTotalHits();
			bucketList = lane_group.getBuckets();
		} catch (Exception e) {
			e.printStackTrace();
			if (client != null) {
				client.close();
			}
			return xmlcreate.createErrorXml(config.getErrorCode04());
		}finally {
			if (client != null) {
				ecclient.freeConnection("es", client);
			}
			if(bucketList != null){
				return xmlcreate.createInitChartData(bucketList, total);
			}
			return xmlcreate.createErrorXml(config.getErrorCode04());
		}
	}
	
	/**
	 * 统计昼伏夜出
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("finally")
	public String tjZfyc(String xml){
		Config config = Config.getInstance();//配置信息类
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		String kssj = null;
		String jssj = null;
		String cphm = null;
		String cqid = null;
		double zybl = 0.5;
		int size = 10;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//获取根节点
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			Element head = (Element) root.selectNodes("head").get(0);
			
			kssj = data.element("kssj").getTextTrim();//起始时间
			jssj = data.element("jssj").getTextTrim();//结束时间
			cqid = data.element("cqid").getTextTrim();//城区id
			cphm = data.element("cphm").getTextTrim();//车牌号码
			if(StringUtils.isNotEmpty(data.elementTextTrim("zybl"))){
				zybl = Double.parseDouble(data.elementTextTrim("zybl"));
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
		SearchResponse response = null;
		List<Bucket> bucketList = null;
		List<FilterBuilder> filterList = new ArrayList<FilterBuilder>();
		try {
			if(StringUtils.isNotEmpty(kssj) && StringUtils.isNotEmpty(jssj)){
				FilterBuilder filter = FilterBuilders.rangeFilter("ts").gte(kssj).lte(jssj);
				filterList.add(filter);
			}
			if(StringUtils.isNotEmpty(cqid)){
				FilterBuilder filter =FilterBuilders.termFilter("cqid", cqid);
				filterList.add(filter);
			}
			if(StringUtils.isNotEmpty(cphm)){
				if (cphm.contains("*") || cphm.contains("?")) {
					FilterBuilder filter = FilterBuilders.queryFilter(QueryBuilders.wildcardQuery("cphm", cphm));
					filterList.add(filter);
				}else{
					String[] cphms = cphm.split(",");
					FilterBuilder filter = FilterBuilders.termsFilter("cphm", cphms);
					filterList.add(filter);
				}
			}
			FilterBuilder filter = FilterBuilders.boolFilter().must(filterList.toArray(new FilterBuilder[filterList.size()]));
			query = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), filter);
			SumBuilder zfAgg = AggregationBuilders.sum("zf_times").field("zfcs");
			SumBuilder ycAgg = AggregationBuilders.sum("yc_times").field("yccs");
			AggregationBuilder termsAgg = AggregationBuilders.terms("group_by_cphm")
					.field("cphm").size(size).order(Terms.Order.compound(new Terms.Order[]{Terms.Order.aggregation("yc_times",false),Terms.Order.aggregation("zf_times",true)}))
					.subAggregation(zfAgg).subAggregation(ycAgg);
			SearchRequestBuilder request = client.prepareSearch("daily").setTypes("zfyc")
					.setSearchType(SearchType.COUNT)
					.setQuery(query)
					.addAggregation(termsAgg)
					.setExplain(false);
			response = request.execute().actionGet();
			if(response != null){
				Terms terms = response.getAggregations().get("group_by_cphm");
				bucketList = terms.getBuckets();
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
			if(bucketList != null && bucketList.size() > 0){
				return xmlcreate.createZfycTjRes(bucketList, zybl);
			}
			return null;
		}
	}
	
	/**
	 * 统计业务日志
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("finally")
	public String tjBusinessLog(String xml){
		Config config = Config.getInstance();//配置信息类
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		String kssj = null;
		String jssj = null;
		String ip = null;
		String operator = null;
		String operateName = null;
		
		String tjWord = null;
		int size = 200;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//获取根节点
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
			Element head = (Element) root.selectNodes("head").get(0);
			
			kssj = data.element("kssj").getTextTrim();//起始时间
			jssj = data.element("jssj").getTextTrim();//结束时间
			ip = data.element("ip").getTextTrim();//ip
			operator = data.element("operator").getTextTrim();//操作人
			operateName = data.element("operateName").getTextTrim();//操作内容
			
			tjWord = data.element("tjWord").getTextTrim();//统计字段1
			
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
		SearchResponse response = null;
		List<Bucket> bucketList = null;
		List<FilterBuilder> filterList = new ArrayList<FilterBuilder>();
		try {
			//过滤器
			if(StringUtils.isNotEmpty(kssj) && StringUtils.isNotEmpty(jssj)){
				FilterBuilder filter = FilterBuilders.rangeFilter("operateDate").gte(kssj).lte(jssj);
				filterList.add(filter);
			}
			if(StringUtils.isNotEmpty(ip)){
				if (ip.contains("*") || ip.contains("?")) {
					FilterBuilder filter = FilterBuilders.queryFilter(QueryBuilders.wildcardQuery("ip", ip));
					filterList.add(filter);
				}else{
					String[] ips = ip.split(",");
					FilterBuilder filter = FilterBuilders.termsFilter("ip", ips);
					filterList.add(filter);
				}
				FilterBuilder filter =FilterBuilders.queryFilter(QueryBuilders.wildcardQuery("ip", ip));
				filterList.add(filter);
			}
			if(StringUtils.isNotEmpty(operator)){
				if (operator.contains("*") || operator.contains("?")) {
					FilterBuilder filter = FilterBuilders.queryFilter(QueryBuilders.wildcardQuery("operator", operator));
					filterList.add(filter);
				}else{
					String[] operators = operator.split(",");
					FilterBuilder filter = FilterBuilders.termsFilter("operator", operators);
					filterList.add(filter);
				}
			}
			if(StringUtils.isNotEmpty(operateName)){
				if (operator.contains("*") || operator.contains("?")) {
					FilterBuilder filter = FilterBuilders.queryFilter(QueryBuilders.wildcardQuery("operateName", operateName));
					filterList.add(filter);
				}else{
					String[] operateNames = operateName.split(",");
					FilterBuilder filter = FilterBuilders.termsFilter("operateName", operateNames);
					filterList.add(filter);
				}
			}
			FilterBuilder filter = FilterBuilders.boolFilter().must(filterList.toArray(new FilterBuilder[filterList.size()]));
			query = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), filter);
			
			TermsBuilder t = AggregationBuilders.terms("terms_group").field(tjWord).size(size);
			AggregationBuilder termsAgg = AggregationBuilders.terms("module_group").field("moduleName").size(size).subAggregation(t);
			SearchRequestBuilder request = client.prepareSearch("businesslog").setTypes("businesslog")
					.setSearchType(SearchType.COUNT)
					.setQuery(query)
					.addAggregation(termsAgg)
					.setExplain(false);
			response = request.execute().actionGet();
			if(response != null){
				Terms terms = response.getAggregations().get("module_group");
				bucketList = terms.getBuckets();
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
			if(bucketList != null && bucketList.size() > 0){
				return xmlcreate.createBusinessLogTjRes(bucketList);
			}
			return null;
		}
	}
	
	/**
	 * 按监测点分组统计上传超时的数据量，scsj-tgsj > csbz属于上传超时
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("finally")
	public String tjSccsByJcdGroup(String xml){
		Config config = Config.getInstance();//配置信息类
		Document document = null;
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		String kssj = null;//通过起始时间
		String jssj = null;//通过截止时间
		String jcdid = null;//监测点  以半角逗号隔开
		String csbz = null;//超时标志    一个数值     scsj-tgsj > csbz属于上传超时（单位：秒）
		String bcbz = null;//补传标志    0不包括补传数据，1包括补传数据
		
		Date ksDate = null;
		Date jzDate = null;
		String result = "1";
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//获取根节点
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);			
//			Element head = (Element) root.selectNodes("head").get(0);
			
			kssj = data.element("kssj").getTextTrim();//起始时间
			jssj = data.element("jssj").getTextTrim();//结束时间
			//判断时间是否符合规定
			try {
				//判空
				if(kssj == null || "".equals(kssj.trim()) || jssj == null || "".equals(jssj.trim())){
					//15:查询条件不符合
					return xmlcreate.createErrorXml(config.getErrorCode15());
				}
				
				//判时间是否正确及截止时间不能超过当前时间
				ksDate = sdf.parse(kssj);//开始时间
				jzDate = sdf.parse(jssj);//结束时间
				//截止时间不能超出当前时间
				if(jzDate.getTime() > new Date().getTime()){
					jzDate = new Date();
				}
			} catch (ParseException e) {
				//"03:时间格式不合法或者为空，请检查！"
				return xmlcreate.createErrorXml(config.getErrorCode03());
			}
			
			jcdid = data.element("jcdid").getTextTrim();//监测点  以半角逗号隔开
			//判空
			if(jcdid == null || "".equals(jcdid.trim())){
				//15:查询条件不符合
				return xmlcreate.createErrorXml(config.getErrorCode15());
			}
			
			//无需判空，为空时查询总数
			csbz = data.element("csbz").getTextTrim();//超时标志    一个数值     scsj-tgsj > csbz属于上传超时
			
			bcbz = data.element("bcbz").getTextTrim();//补传标志    0不包括补传数据，1包括补传数据
			//判空
			if(bcbz == null || "".equals(bcbz.trim())){
				//15:查询条件不符合
				return xmlcreate.createErrorXml(config.getErrorCode15());
			}
		} catch (Exception e) {
			//"09:xml文件格式无法解析，请检查！"
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES数据库连接池,获取数据连接
		QueryBuilder query = null;
		SearchResponse response = null;
		List<Bucket> bucketList = null;
		List<FilterBuilder> filterList = new ArrayList<FilterBuilder>();
		try {
			//通过时间条件
			if(StringUtils.isNotEmpty(kssj) && StringUtils.isNotEmpty(jssj)){
				FilterBuilder tgsjfilter = FilterBuilders.rangeFilter("tgsj").from(ksDate.getTime()).to(jzDate.getTime()).includeLower(true).includeUpper(true);
				filterList.add(tgsjfilter);
			}
			
			//监测点条件
			if (jcdid != null && !"".equals(jcdid.trim())) {
				String strjcdid[] = jcdid.trim().split(",");//
				FilterBuilder jcdidfilter = null;
				if (strjcdid.length == 1) {//单个监测点
					jcdidfilter = FilterBuilders.termFilter("jcdid", jcdid);
				} else {// 多个监测点
					jcdidfilter = FilterBuilders.termsFilter("jcdid", strjcdid);
				}
				filterList.add(jcdidfilter);
			}
			//是否包含补传数据            0不包括补传数据，1包括补传数据
			if(bcbz != null && "0".equals(bcbz.trim())){//0不包括补传数据
				FilterBuilder bcbzfilter = FilterBuilders.termFilter("bcbz", "0");
				filterList.add(bcbzfilter);
			}
			//超时设置     scsj-tgsj > csbz属于上传超时（单位：秒）
			if(csbz != null && !"".equals(csbz.trim())){
				filterList.add(scriptFilter("doc['scsj'].value - doc['tgsj'].value > param").addParam("param", Integer.parseInt(csbz)*1000));
			}
			
			//生成条件
			FilterBuilder filter = FilterBuilders.boolFilter().must(filterList.toArray(new FilterBuilder[filterList.size()]));
			query = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), filter);
			
			//分组执行
			AggregationBuilder termsAgg = AggregationBuilders.terms("jcdid_group").field("jcdid").size(2000);
			SearchRequestBuilder request = client.prepareSearch("sb").setTypes("sb")
					.setSearchType(SearchType.COUNT)
					.setQuery(query)
					.addAggregation(termsAgg)
					.setExplain(false);
			response = request.execute().actionGet();
			
			//获取返回结果
			if(response != null){
				Terms terms = response.getAggregations().get("jcdid_group");
				bucketList = terms.getBuckets();
			}
		} catch (Exception e) {
			if (client != null) {
				client.close();
			}
			result = "0";
		} finally {
			//释放连接
			if (client != null) {
				ecclient.freeConnection("es", client);
			}
			//封装数据
			if("1".equals(result)){
				return xmlcreate.createSccsByJcdGroupRes(bucketList);
			} else {
				return xmlcreate.createErrorXml(config.getErrorCode06());
			}
		}
	}
}