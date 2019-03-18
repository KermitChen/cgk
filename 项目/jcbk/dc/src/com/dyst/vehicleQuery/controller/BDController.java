package com.dyst.vehicleQuery.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.vehicleQuery.service.BDService;

@Controller
@RequestMapping("/bdController")
public class BDController {

	@Resource
	private BDService bdService;
	/**
	 * 根据监测点编号翻译监测点
	 * @param response
	 * @param jcdid
	 */
	@RequestMapping("/tranJcdId")
	public void tranJcdId(HttpServletResponse response,String jcdid){
		response.setContentType("application/json");
		PrintWriter out = null;
		StringBuffer sb = null;
		try {
			sb = new StringBuffer();
			Map<String, String> jcdMap = bdService.getJcdMap();//得到监测点集合
			if(StringUtils.isNotEmpty(jcdid)){
				String[] jcdids = jcdid.split(",");//拆分监测点
				if(jcdids != null && jcdids.length > 0){
					for (int i = 0; i < jcdids.length; i++) {
						sb.append(jcdMap.get(jcdids[i])).append(",");
					}
				}
				if(sb.length() > 1){
					sb.deleteCharAt(sb.lastIndexOf(","));//移除最后面的逗号
				}
				out = response.getWriter();
				out.write(JSON.toJSONString(sb.toString()));
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
		}
	}
	@RequestMapping("/tranCplx")
	public void tranCplx(HttpServletResponse response,String cplx){
		response.setContentType("application/json");
		PrintWriter out = null;
		try {
			Map<String, String> cplxMap = bdService.getCplxMap();
			if(StringUtils.isNotEmpty(cplx)){
				out = response.getWriter();
				out.write(JSON.toJSONString(cplxMap.get(cplx)));
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
		}
	}
	@RequestMapping("/zoomImage")
	public String zoomImage(String tpUrl,Map<String, Object> map){
		String page = "/clcx/image";
		try {
			map.put("tpUrl", tpUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
}
