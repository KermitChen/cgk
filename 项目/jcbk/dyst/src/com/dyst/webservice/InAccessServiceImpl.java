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
	public String executes(String systemType, String businessType, String sn,
			String data, String xml) {
		Config config = Config.getInstance();// 配置信息类
		// xml封装类
		if (xmlcreate == null) {
			xmlcreate = new XmlCreater();
		}
		if (businessType != null) {
			businessType = businessType.trim();
		}

		if (sn != null && "hello,world".equals(sn.trim())) {// 合法性校验
			// 采用filter方式实现
			if (clientService == null) {
				clientService = new ClientServiceQuery();
			}
			/**
			 * 01:已识别轨迹查询 03:未识别车辆查询 04:车牌号模糊查询 05:车牌前缀查询 11:不分已识别和未识别
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
			} else if ("02".equals(businessType) || "07".equals(businessType)) {// 图片路径查询
				String xmlstr = "";
				try {
					xmlstr = clientService.tpcx(xml, businessType);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("06".equals(businessType)) {// 识别记录更新
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
			} else if ("08".equals(businessType)) {// 统计查询
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
			} else if ("09".equals(businessType)) {// 频繁出现点分析
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
			} else if ("10".equals(businessType)) {// 多点碰撞比对分析
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
			} else if ("12".equals(businessType)){//按监测点分组统计上传超时的数据量，scsj-tgsj > num属于上传超时（单位：秒）
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
			} else if("100".equals(businessType)){//根据图片id查询过车记录
				String xmlstr = "";
				try {
					xmlstr = clientService.getHitsByTpid(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("101".equals(businessType)){//出行时间分析
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
			} else if ("102".equals(businessType)){//车流量图表初始化
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
			} else if ("103".equals(businessType)){//查询服务器信息
				String xmlstr = "";
				try {
					xmlstr = clientService.searchServeInfo(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("104".equals(businessType)){//查询昼伏夜出结果
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
			} else if ("105".equals(businessType)){//查询初次入城结果
				String xmlstr = "";
				try {
					xmlstr = clientService.queryCcrc(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("106".equals(businessType)){//查询业务日志
				String xmlstr = "";
				try {
					xmlstr = clientService.queryBusinessLog(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("107".equals(businessType)){//查询单条业务日志
				String xmlstr = "";
				try {
					xmlstr = clientService.getBusinessLogById(xml);
				} catch (Exception e) {
					xmlstr = xmlcreate.createErrorXml(config.getErrorCode06());
					e.printStackTrace();
				} finally {
					return xmlstr;
				}
			} else if ("108".equals(businessType)){//统计业务日志
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
			} else if ("109".equals(businessType)){//查询昼伏夜出
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
				// 无该类型查询业务方法"02:调用业务类型代码不存在"
				return xmlcreate.createErrorXml(config.getErrorCode02());
			}
		} else {
			// 调用单位不合法，生成Error的XML报文"01:调用单位不合法，请查询是否输错或者有无权限！"
			return xmlcreate.createErrorXml(config.getErrorCode01());
		}
	}
}