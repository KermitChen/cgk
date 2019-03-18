package com.dyst.serverMonitor.service;

import java.util.List;

import javax.servlet.ServletOutputStream;

import com.dyst.serverMonitor.entities.ExcelBeanForJcdStatus;

public interface ServerManagerService {
	/**
	 * 查询服务器信息
	 * @param hosts
	 * @return
	 * @throws Exception
	 */
	public List queryServerInfo(String hosts)throws Exception;

	/**
	 * 设备状态导出excel
	 * @param excelBeanList
	 * @param outputStream
	 */
	public void excelExportForDeviceStatus(
			List<ExcelBeanForJcdStatus> excelBeanList,
			ServletOutputStream outputStream);
}
