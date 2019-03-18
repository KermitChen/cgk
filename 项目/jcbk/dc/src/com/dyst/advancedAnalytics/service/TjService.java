package com.dyst.advancedAnalytics.service;

import java.util.List;

import com.dyst.advancedAnalytics.dto.FootHoldDetail;
import com.dyst.advancedAnalytics.dto.FootHoldResult;

public interface TjService {

	/**
	 * 落脚点分析
	 * @param hphm
	 * @param kssj
	 * @param jssj
	 * @return
	 * @throws Exception
	 */
	public List<FootHoldResult> footHold(String cphm,String kssj,String jssj)throws Exception;
	/**
	 * 计算同一监测点按时段统计次数
	 * @param hphm
	 * @param kssj
	 * @param jssj
	 * @param jcdid
	 * @return
	 * @throws Exception
	 */
	public List<FootHoldDetail> footHoldDetail(String hphm,String kssj,String jssj,String jcdid) throws Exception;
}
