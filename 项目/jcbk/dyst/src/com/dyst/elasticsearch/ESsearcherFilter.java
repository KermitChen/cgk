package com.dyst.elasticsearch;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.util.HashMap;
import java.util.Map;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.ReceiveTimeoutTransportException;
import org.elasticsearch.transport.TransportSerializationException;

import com.dyst.elasticsearch.util.ESClientManager;

/**
 * ͨ��Filter��ʽʵ��ES��ѯ
 * @author Administrator
 */
public class ESsearcherFilter {
	/**
	 * ����Filter�ͷ�ҳ������ѯ�ţӿ�����
	 * @param filter  ������
	 * @param from    ��ʼ��¼
	 * @param to      ��ֹ��¼
	 * @param pagsize ÿҳ��С��
	 * @param sort  �����ֶ�
	 * @param sortType ��������
	 * @return ��ѯ���
	 */
	@SuppressWarnings("finally")
	public static SearchHits tdcpgjcx(FilterBuilder filter, int from , int pagsize, String bussiness, String sort, String sortType) {
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES���ݿ����ӳ�,��ȡ��������
		MatchAllQueryBuilder query = matchAllQuery();
		String selectType = "sb";
		SearchResponse response = null;
		try {
			SearchRequestBuilder searchRequestBuilder = client.prepareSearch("sb").setTypes(selectType)
					 //.setFilter(filter)
			         .setQuery(QueryBuilders.filteredQuery(query, filter))
//					 .setSearchType(SearchType.QUERY_AND_FETCH)
					 .setFrom(from).setSize(pagsize)
					 .addFields(new String[]{"cphm1", "jcdid", "cplx1", "tgsj", "cdid", "tpid1", "tpid2", 
							 "tpid3", "tpid4", "tpid5", "sd", "cdid", "cb", "scsj", "tpzs"})//ָ����ѯ�ֶ�
   					.setExplain(false);//���Բ�ѯ���ݽ��н���;
			if(sort != null && !"".equals(sort.trim()) && sortType != null && !"".equals(sortType.trim())){//��������sql
				searchRequestBuilder.addSort(sort.trim().toLowerCase(), ("DESC".equals(sortType.trim().toUpperCase())?SortOrder.DESC:SortOrder.ASC));//����
			}
			response = searchRequestBuilder.execute().actionGet();
		}catch (ReceiveTimeoutTransportException e) {
			e.printStackTrace();
			if (client != null) {
				client.close();
			}
			return null;
		} finally {
			if (client != null) {
				ecclient.freeConnection("es", client);
			}
			
			if(response != null){
				return response.getHits();
			}
			return null;
		}
	}
	
	/**
	 * ����Query�ͷ�ҳ������ѯ�ţӿ�����
	 * @param filter  ������
	 * @param from    ��ʼ��¼
	 * @param to      ��ֹ��¼
	 * @param pagsize ÿҳ��С��
	 * @return ��ѯ���
	 */
	@SuppressWarnings("finally")
	public static SearchHits tdcpgjcx(QueryBuilder query,int from ,int pagsize,String bussiness) {
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES���ݿ����ӳ�,��ȡ��������
		String selectType = "sb";
		SearchHits hits = null;
		try {
			SearchResponse response = client.prepareSearch("sb").setTypes(selectType)
					 .setQuery(query)//�������
//					 .setSearchType(SearchType.QUERY_AND_FETCH)
					 .setFrom(from).setSize(pagsize)//�ӿ�ʼȡ��ȡ��������
					 .setExplain(false)//���Բ�ѯ���ݽ��н���
   					 .addFields(new String[]{"cphm1","jcdid","cplx1","tgsj","cdid","tpid1",
   							 "tpid2","tpid3","tpid4","tpid5","sd","cdid","cb","scsj"})
//					 .addSort("tgsj", SortOrder.DESC)//����
					 .execute().actionGet();//ִ��
			hits = response.getHits();//��ȡ���
		}catch (ReceiveTimeoutTransportException e) {
			e.printStackTrace();
			if (client != null) {
				client.close();
			}
			return null;
		} finally {
			if (client != null) {
				ecclient.freeConnection("es", client);
			}
			return hits;
		}
	}
	
	/**
	 * ͨ�����е�ֵ����ѯ ����,filter��ʽ
	 */
	public static Integer getTdcpgjcxCount(FilterBuilder filter) throws Exception {
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES���ݿ����ӳ�,��ȡ��������
		try {
			MatchAllQueryBuilder query = matchAllQuery();
			CountResponse response = client.prepareCount("sb").setTypes("sb")
						.setQuery(QueryBuilders.filteredQuery(query,filter)).execute().actionGet();
			return ((Long)response.getCount()).intValue();
		} catch (ReceiveTimeoutTransportException e) {
			e.printStackTrace();
			if (client != null) {
				client.close();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ES���ݿ��ѯ�쳣");
		} finally {
			if (client != null) {
		    	ecclient.freeConnection("es", client);
			}
		}
	}
	/**
	 * ͨ�����е�ֵ����ѯ���� ,query��ʽ
	 */
	public static Integer getTdcpgjcxCount(QueryBuilder query,String bussiness) throws Exception {
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES���ݿ����ӳ�,��ȡ��������
		String selectType = "sb";
		try {
			//prepareSearch  ����                       setTypes  ��������
			CountResponse response = client.prepareCount("sb").setTypes(selectType)
					 .setQuery(query)//�������
//					 .setSearchType(SearchType.COUNT)//��������
//					 .setExplain(false)//�Ƿ�������ݣ����������ؼ���
				     .execute().actionGet();//ִ��
			return ((Long)response.getCount()).intValue();
		}catch (ReceiveTimeoutTransportException e) {
			e.printStackTrace();
			if (client != null) {
				client.close();
			}
			return 0;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ES���ݿ��ѯ�쳣");
		} 
		finally {
			if (client != null) {
		    	ecclient.freeConnection("es", client);
			}
		}
	}
	
	/**
	 * ���£ţ�ʶ���¼
	 * tpid1 ͼƬid
	 * cphm1 �޸ĺ�ĳ��ƺ���
	 * cplx1 �޸ĺ�ĳ�������
	 */
	@SuppressWarnings("finally")
	public static String updateEsSb(String tpid1,String cphm1,String cplx1) throws Exception {
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES���ݿ����ӳ�,��ȡ��������
		String result = "1";
		try {
			//���ƺ��뼰ͼƬid����Ϊ��
			if(tpid1 != null && !"".equals(tpid1.trim()) 
					&& cphm1 != null && !"".equals(cphm1.trim())){
				//�����������null������Ϊ���ַ���
				if(cplx1 == null){
					cplx1 = "";
				}
				
				//����ʶ���¼id
				UpdateRequestBuilder update = client.prepareUpdate("sb", "sb", tpid1.split("_")[0]);
				Map<String,Object> map = new HashMap<String, Object>();
				//ָ���޸��ֶ�ֵ�������ֶβ�����
				map.put("cphm1", cphm1);//�޸ĳ��ƺ���
				map.put("cplx1", cplx1);//�޸ĳ�����ɫ
				map.put("qpsfwc", "1");//�ĳ���ʶ��
				update.setDoc(map);
				
				@SuppressWarnings("unused")
				UpdateResponse res = update.execute().actionGet();
			} else{
				result = "0";
			}
		} catch (ReceiveTimeoutTransportException e) {
			result = "0";
			if (client != null) {
		    	ecclient.freeConnection("es", client);
			}
			e.printStackTrace();
			throw new Exception("ES���ݿ�����쳣");
		} catch (TransportSerializationException e) {
			e.printStackTrace();
			throw new Exception("û���ҵ�Ҫ�޸ĵ����ݼ�¼");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ES���ݿ�����쳣");
		} finally {
			if (client != null) {
		    	ecclient.freeConnection("es", client);
			}
			return result;
		}
	}
}
