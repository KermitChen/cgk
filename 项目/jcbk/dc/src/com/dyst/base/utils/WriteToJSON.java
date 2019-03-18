package com.dyst.base.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * 把 任何可以的格式的数据写成JSON格式返回到前端页面
 * @author liuqiang
 *
 */
public class WriteToJSON {

	public static void writeJson(Object object,HttpServletResponse resp){
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			resp.setContentType("text/html;charset=utf-8");
			resp.getWriter().write(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
