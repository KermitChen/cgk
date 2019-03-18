package com.dyst.BaseDataMsg.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.dao.BkdjDao;
import com.dyst.BaseDataMsg.entities.DicDisType;
import com.dyst.BaseDataMsg.service.BkdjService;
import com.dyst.base.utils.PageResult;
@Service("bkdjService")
public class BkdjServiceImpl implements BkdjService{
	
	@Autowired
	private BkdjDao bkdjDao;
	
	@Override
	public PageResult findByPage(String bkdl,int pageNo,int pageSize) throws Exception {
		return bkdjDao.findByPage(bkdl, pageNo, pageSize);
	}

	@Override
	public void addBkdj(DicDisType type) throws Exception {
		bkdjDao.save(type);
	}

	@Override
	public long decideIsHave(String showOrder, String level,String id,String notId) throws Exception {
		return bkdjDao.decideIsHave(showOrder, level,id,notId);
	}

	@Override
	public void deleteBkdj(String id) throws Exception {
		bkdjDao.deleteBkdj(id);
	}

	@Override
	public DicDisType getBkdjById(String id) throws Exception {
		return bkdjDao.getBkdjById(id);
	}

	@Override
	public void updateBkdj(DicDisType type) throws Exception {
		bkdjDao.update(type);
	}

}
