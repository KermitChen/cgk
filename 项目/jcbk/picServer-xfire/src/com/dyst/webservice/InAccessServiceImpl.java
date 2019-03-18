package com.dyst.webservice;

import com.dyst.service.ClientServiceQuery;
import com.dyst.utils.Config;
import com.dyst.utils.XmlCreater;

public class InAccessServiceImpl implements IInAccessService {
	private XmlCreater xmlcreate = null;
	private ClientServiceQuery clientService = null;

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
	public String executes(String systemType, String businessType, String sn, String data, String xml) {
		Config config = Config.getInstance();// ������Ϣ��
		// xml��װ��
		if (xmlcreate == null) {
			xmlcreate = new XmlCreater();
		}

		if (sn != null && "hello,world".equals(sn.trim())) {// �Ϸ���У��
			// ����filter��ʽʵ��
			if (clientService == null) {
				clientService = new ClientServiceQuery();
			}
			
			/**
			 * 02 ����ͼƬ   07Υ��ͼƬ
			 */
			if ("02".equals(businessType) || "07".equals(businessType)) {// ͼƬ·����ѯ
				String xmlstr = "";
				try {
					xmlstr = clientService.tpcx(xml, businessType);
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