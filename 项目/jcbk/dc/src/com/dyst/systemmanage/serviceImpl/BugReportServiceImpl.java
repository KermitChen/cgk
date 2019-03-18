package com.dyst.systemmanage.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.dao.BugReportDao;
import com.dyst.systemmanage.entities.BugReport;
import com.dyst.systemmanage.service.BugReportService;

@Service("bugReportService")
public class BugReportServiceImpl implements BugReportService{

	@Autowired
	private BugReportDao bugReportDao;
	
	@Override
	public PageResult searchBugReportByPage(String beginTime, String endTime,
			String isDeal, int pageNo, int pageSize) throws Exception {
		return bugReportDao.searchBugReportByPage(beginTime, endTime, isDeal, pageNo, pageSize);
	}

	@Override
	public void addBugReport(BugReport report) throws Exception {
		bugReportDao.save(report);
	}

	@Override
	public void updateBugReport(BugReport report) throws Exception {
		bugReportDao.update(report);
	}

	@Override
	public void deleteBugReport(BugReport report) throws Exception {
		bugReportDao.delete(report);
	}

	@Override
	public BugReport getById(int id) throws Exception {
		return (BugReport) bugReportDao.getObjectById(BugReport.class, id);
	}

}
