package com.dyst.webservice;

import com.dyst.service.AggsService;
import com.dyst.service.ClientServiceQuery;
import com.dyst.service.Ddpzfx;
import com.dyst.service.FrequentlyAppear;
import com.dyst.service.SbUpdate;
import com.dyst.service.TjService;
import com.dyst.utils.Config;
import com.dyst.utils.XmlCreater;

public class InAccessServiceImpl implements IInAccessService {
	private XmlCreater xmlcreate = null;
	private ClientServiceQuery clientService = null;
	private SbUpdate sbUpdate = null;
	private TjService tjService = null;
	private FrequentlyAppear frequentlyAppear = null;
	private Ddpzfx ddpz = null;
	private AggsService aggsService = null;

	/**
	 * �����켣��ѯ
	 * 
	 * @param systemType
	 *            ϵͳ����
	 * @param businessType
	 *            ҵ������ 01:��ʶ���ѯ 02:ͼƬ��ַ��ѯ ��03:δʶ���ѯ��04:����ģ����ѯ
	 * @param sn
	 *            У����
	 * @param xml
	 *            ����XML����
	 * 
	 * @return XML�ļ��ַ���
	 */
	@SuppressWarnings("finally")
	public String executes(String systemType, String businessType, String sn,
			String data, String xml) {
		Config config = Config.getInstance();// ������Ϣ��
		// xml��װ��
		if (xmlcreate == null) {
			xmlcreate = new XmlCreater();
		}
		if (businessType != null) {
			businessType = businessType.trim();
		}

		if (sn != null && "hello,world".equals(sn.trim())) {// �Ϸ���У��
			// ����filter��ʽʵ��
			if (clientService == null) {
				clientService = new ClientServiceQuery();
			}
			/**
			 * 01:��ʶ��켣��ѯ 03:δʶ������ѯ 04:���ƺ�ģ����ѯ 05:����ǰ׺��ѯ 11:������ʶ���δʶ��
			 */
			if ("01".equals(businessType) || "04".equals(businessType)
					|| "03".equals(businessType) || "05".equals(businessType) 
					|| "11".equals(businessType)) {
				String xmlstr = "";
				try {
					xmlstr = clientService.gjcx(xml, businessType, data);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("02".equals(businessType) || "07".equals(businessType)) {// ͼƬ·����ѯ
				String xmlstr = "";
				try {
					xmlstr = clientService.tpcx(xml, businessType);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("06".equals(businessType)) {// ʶ���¼����
				String xmlstr = "";
				try {
					if(sbUpdate == null){
						sbUpdate = new SbUpdate();
					}
					xmlstr = sbUpdate.updateSb(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("08".equals(businessType)) {// ͳ�Ʋ�ѯ
				String xmlstr = "";
				try {
					if(tjService == null){
						tjService = new TjService();
					}
					xmlstr = tjService.tjcx(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("09".equals(businessType)) {// Ƶ�����ֵ����
				String xmlstr = "";
				try {
					if(frequentlyAppear == null){
						frequentlyAppear = new FrequentlyAppear();
					}
					xmlstr = frequentlyAppear.frequentlyApp(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("10".equals(businessType)) {// �����ײ�ȶԷ���
				String xmlstr = "";
				try {
					if(ddpz == null){
						ddpz = new Ddpzfx();
					}
					xmlstr = ddpz.pzfx(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("12".equals(businessType)){//���������ͳ���ϴ���ʱ����������scsj-tgsj > num�����ϴ���ʱ����λ���룩
				String xmlstr = "";
				try {
					if(aggsService == null){
						aggsService = new AggsService();
					}
					xmlstr = aggsService.tjSccsByJcdGroup(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if("100".equals(businessType)){//����ͼƬid��ѯ������¼
				String xmlstr = "";
				try {
					xmlstr = clientService.getHitsByTpid(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("101".equals(businessType)){//����ʱ�����
				String xmlstr = "";
				try {
					if(aggsService == null){
						aggsService = new AggsService();
					}
					xmlstr = aggsService.footHold(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("102".equals(businessType)){//������ͼ���ʼ��
				String xmlstr = "";
				try {
					if(aggsService == null){
						aggsService = new AggsService();
					}
					xmlstr = aggsService.getInitChartData(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("103".equals(businessType)){//��ѯ��������Ϣ
				String xmlstr = "";
				try {
					xmlstr = clientService.searchServeInfo(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("104".equals(businessType)){//��ѯ���ҹ�����
				String xmlstr = "";
				try {
					if(aggsService == null){
						aggsService = new AggsService();
					}
					xmlstr = aggsService.tjZfyc(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("105".equals(businessType)){//��ѯ������ǽ��
				String xmlstr = "";
				try {
					xmlstr = clientService.queryCcrc(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("106".equals(businessType)){//��ѯҵ����־
				String xmlstr = "";
				try {
					xmlstr = clientService.queryBusinessLog(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("107".equals(businessType)){//��ѯ����ҵ����־
				String xmlstr = "";
				try {
					xmlstr = clientService.getBusinessLogById(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("108".equals(businessType)){//ͳ��ҵ����־
				String xmlstr = "";
				try {
					if(aggsService == null){
						aggsService = new AggsService();
					}
					xmlstr = aggsService.tjBusinessLog(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("109".equals(businessType)){//��ѯ���ҹ��
				String xmlstr = "";
				try {
					xmlstr = clientService.queryZfyc(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else {
				// �޸����Ͳ�ѯҵ�񷽷�"02:����ҵ�����ʹ��벻����"
				return xmlcreate.createErrorXml(config.getErrorCode02());
			}
		} else {
			// ���õ�λ���Ϸ�������Error��XML����"01:���õ�λ���Ϸ������ѯ�Ƿ�����������Ȩ�ޣ�"
			return xmlcreate.createErrorXml(config.getErrorCode01());
		}
	}
}