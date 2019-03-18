package com.dyst.utils.excel.bean.bukong;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.entities.Withdraw;

public class CKExcelBean {

	private List<CKItemBean> list;

	public CKExcelBean() {
	}

	/**
	 * 初始化 撤控 list
	 */
	public CKExcelBean(List<Dictionary> dicList,List<Withdraw> cklist) {
		list = new ArrayList<CKItemBean>();
		String pattern = "yyyy-MM-dd HH:mm:ss";
		for(Withdraw w:cklist){
			Dispatched dispatched = w.getDispatched();
			CKItemBean ckItemBean = new CKItemBean();
			ckItemBean.setHphm(dispatched.getHphm());//号牌号码
			ckItemBean.setCxsqr(w.getCxsqr());//撤销申请人
			ckItemBean.setCxsj(DateFormatUtils.format(w.getCxsqsj(), pattern));//撤销时间
			if(w.getZjck().equals("1")){//直接撤控
				ckItemBean.setZjck("是");
			}else if(w.getZjck().equals("1")){
				ckItemBean.setZjck("否");
			}
			for(Dictionary dic:dicList){
				String typeCode = dic.getTypeCode();
				String typeSerialNo = dic.getTypeSerialNo();
				//车辆种类
				if(typeCode.equals("HPZL")){
					if(typeSerialNo.equals(dispatched.getHpzl())){
						ckItemBean.setHpzl(dic.getTypeDesc());
					}
				}
				//布控性质
				if(typeCode.equals("BKXZ")){
					if(typeSerialNo.equals(dispatched.getBkxz())){
						ckItemBean.setBkxz(dic.getTypeDesc());
					}
				}
				//撤销状态
				if(typeCode.equals("CKYWZT")){
					if(typeSerialNo.equals(w.getYwzt())){
						ckItemBean.setCxzt(dic.getTypeDesc());
					}
				}
			}
			list.add(ckItemBean);
		}
	}

	public List<CKItemBean> getList() {
		return list;
	}

	public void setList(List<CKItemBean> list) {
		this.list = list;
	}
	
}
