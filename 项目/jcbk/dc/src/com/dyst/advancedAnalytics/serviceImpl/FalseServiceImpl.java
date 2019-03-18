package com.dyst.advancedAnalytics.serviceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.advancedAnalytics.dao.FalseDao;
import com.dyst.advancedAnalytics.entities.FalseCphm;
import com.dyst.advancedAnalytics.entities.FalseDelete;
import com.dyst.advancedAnalytics.entities.FalseHphmExcelBean;
import com.dyst.advancedAnalytics.service.FalseService;
import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.excel.ExportExcelUtil;
@Service("falseService")
public class FalseServiceImpl implements FalseService {
	
	@Autowired
	private FalseDao falseDao;
	@Autowired
	private UserService userService;
	/**
	 * 分页查询假牌车辆
	 * @param kssj
	 * @param jssj
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageResult getFalseForPage(String hphm, String kssj, String jssj,
			int pageNo, int pageSize) throws Exception {
		return falseDao.getFalseForPage(hphm, kssj, jssj, pageNo, pageSize);
	}
	
	/**
	 * getList方法
	 */
	@Override
	public List<FalseCphm> getList(String hql, Map<String, Object> params) {
		return falseDao.getList(hql,params);
	}
	
	/**
	 * 导出excel
	 */
	@Override
	public void excelExportForFakeHphm(List<FalseHphmExcelBean> excelBeanList,
			ServletOutputStream outputStream) {
		try {
			ExportExcelUtil.excelExportForFalseHphm(excelBeanList, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id获取假牌车辆
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FalseCphm getFalseById(int id) throws Exception {
		return falseDao.getFalseById(id);
	}
	@Override
	public List findByHphm(String hphm) throws Exception {
		return falseDao.findByHphm(hphm);
	}
	@Override
	public void updateObject(FalseCphm falseCphm) throws Exception {
		falseDao.update(falseCphm);
	}
	@Override
	public String decideIsDelete(int jpid) throws Exception {
		String result = "1";//只标记不删除
		long num = falseDao.getDeleteNum(jpid);
		int setNum = 4;
		//得到设定的规定删除值
		List<Dictionary> list = userService.getDictionarysByTypeCode("DELETE_NUM");
		for (Dictionary dictionary : list) {
			if("假牌".equals(dictionary.getTypeDesc())){
				if(StringUtils.isNotEmpty(dictionary.getTypeSerialNo())){
					setNum = Integer.parseInt(dictionary.getTypeSerialNo());
				}
			}
		}
		if((num+1) == setNum){
			result = "2";//确认是否删除
		}
		return result;
	}
	@Override
	public void deleteCphmAndFlag(int jpid) throws Exception {
		falseDao.deleteCphmAndFlag(jpid);
	}
	@Override
	public List findDeleteData(int jpid) throws Exception {
		return falseDao.findDeleteData(jpid);
	}
	@Override
	public List searchIsMark(int jpid, String pno) throws Exception {
		return falseDao.searchIsMark(jpid, pno);
	}
	@Override
	public void markDelete(Integer jpid, String pno, String pname,
			String reason, String realPlate) throws Exception {
		falseDao.markDelete(jpid, pno, pname, reason, realPlate);
	}
}
