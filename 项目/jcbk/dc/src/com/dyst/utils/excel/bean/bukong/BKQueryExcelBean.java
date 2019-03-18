package com.dyst.utils.excel.bean.bukong;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.dispatched.entities.Dispatched;

public class BKQueryExcelBean {

	
	private List<BkItemBean> bklist;
	
	public BKQueryExcelBean() {
	}
	
	public BKQueryExcelBean(List<Dictionary> dicList, List<Map> bklbList,
			List<Dispatched> bkList) {
		bklist = new ArrayList<BkItemBean>();
		for(Dispatched d:bkList){
			BkItemBean bkItemBean = new BkItemBean();
			bkItemBean.setCphm(d.getHphm());
			//布控类别
			for(Map m:bklbList){
				if(m.get("ID").equals(d.getBklb())){
					bkItemBean.setBklb(m.get("NAME").toString());
				}
			}
			//布控申请人
			bkItemBean.setBksqr(d.getBkr());
			//布控申请时间
			bkItemBean.setSqsj(d.getBksj().toString());
			//布控起始时间
			bkItemBean.setQssj(d.getBkqssj().toString());
			//布控截止时间
			bkItemBean.setJzsj(d.getBkjzsj().toString());
			//是否直接布控
			if(d.getZjbk().equals("1")){
				bkItemBean.setZjbk("是");
			}else if(d.getZjbk().equals("0")){
				bkItemBean.setZjbk("否");
			}
			for(Dictionary dic:dicList){
				String typeCode = dic.getTypeCode();
				String typeSerialNo = dic.getTypeSerialNo();
				//车辆种类
				if(typeCode.equals("HPZL")){
					if(typeSerialNo.equals(d.getHpzl())){
						bkItemBean.setHpzl(dic.getTypeDesc());
					}
				}
				//布控大类
				if(typeCode.equals("BKDL")){
					if(typeSerialNo.equals(d.getBkdl())){
						bkItemBean.setBkdl(dic.getTypeDesc());
					}
				}
				//布控性质
				if(typeCode.equals("BKXZ")){
					if(typeSerialNo.equals(d.getBkxz())){
						bkItemBean.setBkxz(dic.getTypeDesc());
					}
				}
				//布控业务状态
				if(typeCode.equals("BKYWZT")){
					if(typeSerialNo.equals(d.getYwzt())){
						bkItemBean.setBkzt(dic.getTypeDesc());
					}
				}
			}
			bklist.add(bkItemBean);
		}
	}

	public List<BkItemBean> getBklist() {
		return bklist;
	}

	public void setBklist(List<BkItemBean> bklist) {
		this.bklist = bklist;
	}

}
