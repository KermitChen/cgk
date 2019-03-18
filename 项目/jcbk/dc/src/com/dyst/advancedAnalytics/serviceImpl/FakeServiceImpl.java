package com.dyst.advancedAnalytics.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.directwebremoting.impl.ExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.advancedAnalytics.dao.FakeDao;
import com.dyst.advancedAnalytics.entities.FakeDelete;
import com.dyst.advancedAnalytics.entities.FakeHphmExcelBean;
import com.dyst.advancedAnalytics.entities.FakePlate;
import com.dyst.advancedAnalytics.service.FakeService;
import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.excel.ExportExcelUtil;
@Service("fakeService")
public class FakeServiceImpl implements FakeService{

	@Autowired
	private FakeDao fakeDao;
	@Autowired
	private UserService userService;
	/**
	 * 分页查询套牌车辆信息
	 * @param jcdid	监测点id
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageResult getFakeForPage(String hphm,String jcdid, String kssj, String jssj,
			int pageNo, int pageSize) throws Exception {
		return fakeDao.getFakeForPage(hphm,jcdid, kssj, jssj, pageNo, pageSize);
	}
	
	/**
	 * 假牌导出excel
	 */
	@Override
	public void excelExportForFakeHphm(List<FakeHphmExcelBean> excelBeanList, ServletOutputStream outputStream) {
		try {
			ExportExcelUtil.excelExportForFakeHphm(excelBeanList, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询假牌列表
	 */
	@Override
	public List<FakePlate> getList(String hql,Map<String, Object> params) {
		return fakeDao.getList(hql,params);
	}

	/**
	 * 根据id查询套牌车辆
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FakePlate getFakeById(int id) throws Exception {
		return fakeDao.getFakeById(id);
	}
	@Override
	public List findByHphm(String hphm) throws Exception {
		return fakeDao.findByHphm(hphm);
	}
	@Override
	public String decideIsDelete(int tpid)
			throws Exception {
		String result = "1";//只标记不删除
		long num = fakeDao.getDeleteNum(tpid);
		int setNum = 4;
		//得到设定的规定删除值
		List<Dictionary> list = userService.getDictionarysByTypeCode("DELETE_NUM");
		for (Dictionary dictionary : list) {
			if("套牌".equals(dictionary.getTypeDesc())){
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
	public void deleteCphmAndFlag(int tpid) throws Exception {
		fakeDao.deleteCphmAndFlag(tpid);
	}
	@Override
	public List findDeleteData(int tpid) throws Exception {
		return fakeDao.findDeleteData(tpid);
	}
	@Override
	public void markData(Integer tpid, String pno, String pname, String reason,
			String realPlate) throws Exception {
		fakeDao.save(new FakeDelete(tpid, pno, pname, new Date(), reason, realPlate));
	}
}
