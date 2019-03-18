package com.dyst.BaseDataMsg.serviceImpl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.dao.DictionaryDao;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.service.DictionaryService;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService{
	
	//注入持久层dao
		@Autowired
		private DictionaryDao dictionaryDao;

		public void addDictionary(Dictionary d){
			dictionaryDao.save(d);
		}

		public void updateDictionary(Dictionary d) {
			dictionaryDao.update(d);
		}

		public void deleteDictionary(Dictionary d) {
			dictionaryDao.delete(d);
		}

		public List<Object> findObjects(String hql, List<Object> parameters) {
			return dictionaryDao.findObjects(hql, parameters);
		}

		public List<Dictionary> findObjects(QueryHelper queryHelper) {
			return dictionaryDao.findObjects(queryHelper);
		}

		public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize) {
			return dictionaryDao.getPageResult(queryHelper, pageNo, pageSize);
		}

		public Dictionary findDictionayById(Serializable id) {
			return dictionaryDao.findObjectById(id);
		}
		/**
		 * 根据ID逻辑删除基础数据条目
		 */
		public void deleteDictionary(Serializable id) {
			dictionaryDao.delete(id);
		}

		@Override
		public Map<String, String> findMapObjects(String hql,
				List<Object> params) {
			return dictionaryDao.findMapObjects(hql,params);
		}
		
		@Override
		public Map<String, String> findMapObject(String hql,
				Map<String, Object> params) {
			return dictionaryDao.findMapObject(hql, params);
		}

		@Override
		public List<Dictionary> findDicListByTypeCodeIds(String hql,
				Map<String, Object> params) {
			return dictionaryDao.findDicListByTypeCodeIds(hql,params);
		}

		
}
