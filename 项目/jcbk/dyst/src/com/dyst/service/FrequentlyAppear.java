package com.dyst.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;

import com.dyst.elasticsearch.util.ESClientManager;
import com.dyst.entites.*;
import com.dyst.utils.Config;
import com.dyst.utils.XmlCreater;

public class FrequentlyAppear {
	// ����Ƶ�����ֵ����
	public String frequentlyApp(String requestXml) {
		XmlCreater xml = new XmlCreater();
		Config config = Config.getInstance();// ������Ϣ��
		Document document = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// ��������
		String cphm = null;// ���ƺ���
		Date startTime = null;// ��ʼʱ��
		Date endTime = null;// ��ֹʱ��
		int maxReturnValue = 20;// Ĭ�Ϸ��ص�����¼����Ĭ��Ϊ20��
		// ����XML�ļ�����ȡ����
		try {
			document = (Document) DocumentHelper.parseText(requestXml);
			Element root = document.getRootElement();// ��ȡ���ڵ�
			Element head = (Element) root.selectNodes("head").get(0);
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);

			startTime = sdf.parse(data.element("kssj").getText().trim());// ��ʼʱ��
			endTime = sdf.parse(data.element("jssj").getText().trim());// ��ֹʱ��
			cphm = data.element("hphm").getText().trim();// ���ƺ���
			maxReturnValue = Integer.parseInt(head.element("maxReturnCount").getText().trim());
		} catch (Exception e) {
			// "09:xml�ļ���ʽ�޷����������飡"
			return xml.createErrorXml(config.getErrorCode09());
		}
		// ��ȡes���ݿ�����
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");// ES���ݿ����ӳ�,��ȡ��������

		try {
			// ����query��ѯ����
			BoolQueryBuilder query = boolQuery();
			// ���ƺ���
			if (cphm != null && !"".equals(cphm.trim())) {
				query.must(termsQuery("cphm1", cphm));
			}
			// query.must(termQuery("jcdid", "20504604"));
			// ͨ��ʱ��
			if (startTime != null && endTime != null) {
				query.must(rangeQuery("tgsj").from(startTime.getTime()).to(endTime.getTime()).includeLower(true).includeUpper(true));
			}

			/*
			 * ��ѯES���ݿ��ʶ�����ݣ�ֻ����"cphm1","jcdid","cplx1","tgsj","tpid1"�ֶΣ�
			 * Ĭ��ֻ����10�����ݣ�����.setSize(9999)�ĳ�9999����Ϊ���ķ��ؼ�¼��
			 */
			SearchResponse response = client.prepareSearch("sb").setTypes("sb").setQuery(query).setFrom(0).setSize(9999).addFields(
							new String[] { "cphm1", "jcdid", "cplx1", "tgsj", "tpid1" }).setExplain(false).execute().actionGet();
			SearchHits hits = response.getHits();
			
			// ��������
			Map<String, String> map;// = new LinkedHashMap<String, String>();
			List<Sbfx> listresult = new ArrayList<Sbfx>();// �������������н����
			Sbfx sb = null;
			for (final SearchHit hit : response.getHits()) {
				final Iterator<SearchHitField> iterator = hit.iterator();
				map = new LinkedHashMap<String, String>();
				while (iterator.hasNext()) {
					final SearchHitField hitfield = iterator.next();
					map.put(hitfield.getName(), hitfield.getValue().toString());
				}
				sb = new Sbfx();
				sb.setCphm1(map.get("cphm1"));
				sb.setCplx1(map.get("Cplx1"));
				sb.setJcdid(map.get("jcdid"));
				sb.setTgsj(new Date(Long.parseLong(map.get("tgsj"))));
				sb.setTpid1(map.get("tpid1"));
				listresult.add(sb);
			}
			// ʱ���1����ʼֵ��0��ʼ��
			ArrayList<int[]> listTime1 = szTime(0, 15);
			// ʱ���2����ʼֵ��00:05�ֿ�ʼ��
			ArrayList<int[]> listTime2 = szTime(5, 15);

			List<PfTemp> listA = new ArrayList<PfTemp>();
			PfTemp temp = null;
			// �������A
			for (Sbfx s : listresult) {
				temp = new PfTemp();
				temp.setJcdid(s.getJcdid());// ���ֵص�
				temp.setIndex1(indexTime(s.getTgsj(), listTime1));
				temp.setIndex2(indexTime(s.getTgsj(), listTime2));
				temp.setTpid(s.getTpid1());// ͼƬid
				// System.out.println(s.getCphm1());
				listA.add(temp);
			}
			// ���ռ���id����
			Collections.sort(listA);//
			/*
			 * ���ɼ���B1��B2,B1��Ӧindex1��B2��ӦIndex2 ��A�����ʱ���1�͵ص���ͬ�����ݽ��з��飬��ͳ�Ƹ�������Ϊ
			 * B1{�ص㣬ʱ���1�����ִ���} ��A�����ʱ���2�͵ص���ͬ�����ݽ��з��飬��ͳ�Ƹ�������Ϊ B2{�ص㣬ʱ���2�����ִ���}
			 */
			List<PfTemp> listB1 = new ArrayList<PfTemp>();
			List<PfTemp> listB2 = new ArrayList<PfTemp>();

			// ��������B1
			for (int i = 0; i < listA.size(); i++) {
				PfTemp tmp = listA.get(i);
				if (!existSB(tmp.getJcdid(), tmp.getIndex1(), listB1, "1")) {
					listB1.add(generateSB(tmp.getJcdid(), tmp.getIndex1(), listA, "1"));
				}
				if (!existSB(tmp.getJcdid(), tmp.getIndex2(), listB2, "2")) {
					listB2.add(generateSB(tmp.getJcdid(), tmp.getIndex2(), listA, "2"));
				}
			}
			// ��B1��B2���ݽ��й鲢���鲢�������ص��ʱ��������ͬ�ģ�����
			// ������ӣ����鲢���д�뵽C������B1��B2������ֱ��д��C
			List<SbC> listC = new ArrayList<SbC>();
			for (int i = 0; i < listB1.size(); i++) {
				PfTemp tmp = listB1.get(i);
				SbC c = new SbC();
				c.setJcdid(tmp.getJcdid());// �ص�
				c.setCount(tmp.getCount());// �ȰѼ���B1��count��ӵ�c��
				HashSet<String> set = new HashSet<String>();
				set.addAll(tmp.getSetTpid());

				boolean flag = false;// ���ڿ�����ʾ��ʱ�����Ϣ��
				for (int j = 0; j < listB2.size(); j++) {
					PfTemp tmpB = listB2.get(j);
					if (tmp.getJcdid().equals(tmpB.getJcdid())
							&& tmp.getIndex1() == tmpB.getIndex2()) {
						// c.setCount((int)Math.ceil((double)(c.getCount()+tmpB.getCount())/2d));
						flag = true;// ��־���ڼ���1��2�Ľ���
						// c.setCount(c.getCount()+tmpB.getCount());
						set.addAll(tmpB.getSetTpid());
						listB2.remove(j);// ��B1�鲢��ɾ������Ԫ��
						break;
					}
				}
				// ʱ�������
				if (flag) {
					c.setDescription(getDecsByIndex(tmp.getIndex1(), 15, 0));//
				} else {
					c.setDescription(getDecsByIndex2(tmp.getIndex1(), 15, 0));//
				}
				c.setCount(set.size());//
				c.setSetTpid(set);
				listC.add(c);
			}

			Collections.sort(listC);//

			return xml.createFrequently(listC, Long.valueOf(hits.getTotalHits()).intValue(), maxReturnValue);
		} catch (Exception e) {
			e.printStackTrace();
			return xml.createErrorXml("Ƶ�����ֵ��������");
		} finally {
			if (ecclient != null) {
				ecclient.freeConnection("es", client);
			}
		}
	}

	/**
	 * �����������ʱ���������Ϣ ���磺ʱ���Ϊ1��ʱ���[0:00 0:15]->[0:00 0:20]
	 * 
	 * @param index
	 * @return
	 */
	public String getDecsByIndex(int index, int step, int start) {
		String desc =
		"[" + +(index * step - start) / 60 + ":" + (index * step - start) % 60
				+ "  " + ((index * step - start) + 20) / 60 + ":"
				+ ((index * step - start) + 20) % 60 + "]";
		return desc;
	}

	/**
	 * �����������ʱ���������Ϣ
	 * 
	 * @param index
	 * @return
	 */
	public String getDecsByIndex2(int index, int step, int start) {
		String desc = "[" + (index * step + start) / 60 + ":"
				+ (index * step + start) % 60 + "  "
				+ ((index * step) + step + start) / 60 + ":"
				+ ((index * step) + step + start) % 60 + "]";
		return desc;
	}

	/**
	 * ��������B,����jcdid����ţ����Ҽ����д��ڸ�ָ��jacketed�������ͬ�ģ���count��1
	 * 
	 * @param jcdid
	 * @param index
	 * @param list
	 * @param flag
	 * @return
	 */
	public PfTemp generateSB(String jcdid, int index, List<PfTemp> list,
			String flag) {
		PfTemp p = new PfTemp();
		int count = 0;
		if ("1".equals(flag)) {
			p.setJcdid(jcdid);
			p.setIndex1(index);
			Set<String> tpidSet = new HashSet<String>();// (new
														// Set()).add(p.getTpid());
			// tpidSet.add(p.getTpid());
			for (PfTemp pt : list) {
				if (jcdid.equals(pt.getJcdid()) && index == pt.getIndex1()) {
					count++;
					tpidSet.add(pt.getTpid());
				}
			}
			p.setCount(count);
			// ͼƬid��set���ϡ�ȥ��
			p.setSetTpid(tpidSet);
		} else if ("2".equals(flag)) {
			p.setJcdid(jcdid);
			p.setIndex2(index);
			Set<String> tpidSet = new HashSet<String>();// (new
														// Set()).add(p.getTpid());
			// tpidSet.add(p.getTpid());
			for (PfTemp pt : list) {
				if (jcdid.equals(pt.getJcdid()) && index == pt.getIndex2()) {
					count++;
					tpidSet.add(pt.getTpid());
				}
			}
			p.setCount(count);
			p.setSetTpid(tpidSet);
		}
		return p;
	}

	/**
	 * �жϸ����ļ���id������Ƿ��ڼ����д���
	 * 
	 * @param jcdid
	 * @param index
	 * @param list
	 * @param flag
	 *            �ж���index1����index2
	 * @return
	 */
	public boolean existSB(String jcdid, int index, List<PfTemp> list,
			String flag) {
		if ("1".equals(flag)) {
			for (PfTemp p : list) {
				if (p.getJcdid().equals(jcdid) && p.getIndex1() == index) {
					return true;
				}
			}
		} else if ("2".equals(flag)) {
			for (PfTemp p : list) {
				if (p.getJcdid().equals(jcdid) && p.getIndex2() == index) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * �����ڵ� Сʱ*60+���� �������ֵ�ڼ����е�����ֵ
	 * 
	 * @param d
	 *            ����
	 * @param list
	 *            �������鼯�ϣ�int[2] ÿ��ʵ�������ʼֵ�ͽ���ֵ��Χ
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int indexTime(Date d, ArrayList<int[]> list) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(d.getTime());
		int time = d.getHours() * 60 + d.getMinutes();
		for (int i = 0; i < list.size(); i++) {
			int begin = list.get(i)[0];
			int end = list.get(i)[1];
			if (time >= begin && time < end) {
				return i;
			}
		}
		return list.size() - 1;
	}

	/**
	 * ���ݸ�����ʼֵ������һ����stepΪ���������顣
	 * 
	 * @param d
	 *            ��ʼֵ
	 * @param step
	 *            ����
	 * @return
	 */
	public ArrayList<int[]> szTime(int d, int step) {
		ArrayList<int[]> list = new ArrayList<int[]>();
		int[] time = null;
		while (d < 24 * 60) {
			// �����2λ������ڵ���24������ת����ʼ��
			if ((d + step) / 60 >= 24) {
				time = new int[] { d, (d + step) % 60 };
			} else {
				time = new int[] { d, d + step };
			}
			d += step;
			list.add(time);
		}
		return list;
	}
}
