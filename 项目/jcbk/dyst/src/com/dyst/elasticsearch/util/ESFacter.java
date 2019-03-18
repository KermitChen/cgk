package com.dyst.elasticsearch.util;

import java.text.ParseException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.facet.FacetBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.terms.TermsFacet;

@SuppressWarnings("deprecation")
public class ESFacter {
	/**
	 * 按照字段值统计
	 * 
	 * @param filed
	 *            查询字段
	 * @param value
	 *            查询值
	 * @throws ParseException
	 */
	@SuppressWarnings("finally")
	public static TermsFacet facetByCon(BoolQueryBuilder query, String groupName) {
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");// ES数据库连接池,获取数据连接
		TermsFacet termsFacet = null;
		try {
			FacetBuilder facet = FacetBuilders.termsFacet("tj").field(groupName).size(10000);
			SearchResponse response = client.prepareSearch("sb").setQuery(query)
				.addFacet(facet).execute().actionGet();
			termsFacet = (TermsFacet) response.getFacets().facetsAsMap().get("tj");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if (client != null) {// 关闭连接
				ecclient.freeConnection("es", client);
			}
			return termsFacet;
		}
	}
}
