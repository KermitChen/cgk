package com.dyst.advancedAnalytics.service;


import java.util.List;

import javax.servlet.ServletOutputStream;

import com.dyst.advancedAnalytics.dto.RequestParameters;
import com.dyst.advancedAnalytics.entities.ExcelBeanForDwpz;
import com.dyst.advancedAnalytics.entities.ResFootHold;
import com.dyst.base.utils.PageResult;

public interface GjfxService {
	/**
	 * 想kafka发送请求并得到查询标志
	 * @param parms
	 * @return
	 */
	public void requestKafka(RequestParameters params) throws Exception;
	/**
	 * 伴随分析
	 * @param params	请求参数
	 * @param pageResult
	 * @return
	 */
	public PageResult bsAnalysis(RequestParameters params) throws Exception;
	/**
	 * 查询伴随车辆轨迹
	 * @param hphm
	 * @param resFlag
	 * @return
	 * @throws Exception
	 */
	public List getBsPath(String hphm,String resFlag,String txyz)throws Exception;
	/**
	 * 多维碰撞分析
	 * @param parms
	 * @param pageResult
	 * @return
	 */
	public PageResult dwpzAnalysis(RequestParameters params,int pageNo,int pageSize) throws Exception;
	/**
	 * 初次入城车辆查询
	 * @param parms
	 * @param pageResult
	 * @return
	 */
	public PageResult ccrcAnalysis(RequestParameters params,int pageNo,int pageSize) throws Exception;
	/**
	 * 频繁过车分析
	 * @param parms
	 * @param pageResult
	 * @return
	 */
	public PageResult pfgcAnalysis(RequestParameters params, int pageNo, int pageSize) throws Exception;
	/**
	 * 昼伏夜出分析
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageResult zfycAnalysis(RequestParameters params,int pageNo,int pageSize) throws Exception;
	/**
	 * 查询布控车辆
	 * @param cphm
	 * @return
	 * @throws Exception
	 */
	public List getBkCphm(String cphm)throws Exception;
	/**
	 * 导出多维碰撞结果到Excel
	 */
	public void excelExportForDwpz(ExcelBeanForDwpz excelBean,
			ServletOutputStream outputStream);
}
