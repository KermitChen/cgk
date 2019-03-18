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
	 * �����ֶ�ֵͳ��
	 * 
	 * @param filed
	 *            ��ѯ�ֶ�
	 * @param value
	 *            ��ѯֵ
	 * @throws ParseException
	 */
	@SuppressWarnings("finally")
	public static TermsFacet facetByCon(BoolQueryBuilder query, String groupName) {
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");// ES���ݿ����ӳ�,��ȡ��������
		TermsFacet termsFacet = null;
		try {
			FacetBuilder facet = FacetBuilders.termsFacet("tj").field(groupName).size(10000);
			SearchResponse response = client.prepareSearch("sb").setQuery(query)
				.addFacet(facet).execute().actionGet();
			termsFacet = (TermsFacet) response.getFacets().facetsAsMap().get("tj");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if (client != null) {// �ر�����
				ecclient.freeConnection("es", client);
			}
			return termsFacet;
		}
	}
}
