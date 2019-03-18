package com.dyst.webservice;

import com.dyst.service.ClientServiceQuery;
import com.dyst.utils.Config;
import com.dyst.utils.XmlCreater;

public class InAccessServiceImpl implements IInAccessService {
	private XmlCreater xmlcreate = null;
	private ClientServiceQuery clientService = null;

	/**
	 * 车辆轨迹查询
	 * 
	 * @param systemType
	 *            系统类型
	 * @param businessType
	 *            业务类型 01:已识别查询 02:图片地址查询 ；03:未识别查询；04:车辆模糊查询
	 * @param sn
	 *            校验码
	 * @param xml
	 *            请求XML报文
	 * 
	 * @return XML文件字符串
	 */
	@SuppressWarnings("finally")
	public String executes(String systemType, String businessType, String sn, String data, String xml) {
		Config config = Config.getInstance();// 配置信息类
		// xml封装类
		if (xmlcreate == null) {
			xmlcreate = new XmlCreater();
		}

		if (sn != null && "hello,world".equals(sn.trim())) {// 合法性校验
			// 采用filter方式实现
			if (clientService == null) {
				clientService = new ClientServiceQuery();
			}
			
			/**
			 * 02 过车图片   07违法图片
			 */
			if ("02".equals(businessType) || "07".equals(businessType)) {// 图片路径查询
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
				// 无该类型查询业务方法"02:调用业务类型代码不存在"
				return xmlcreate.createErrorXml(config.getErrorCode02());
			}
		} else {
			// 调用单位不合法，生成Error的XML报文"01:调用单位不合法，请查询是否输错或者有无权限！"
			return xmlcreate.createErrorXml(config.getErrorCode01());
		}
	}
}