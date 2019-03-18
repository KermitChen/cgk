package com.dyst.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
import com.dyst.oracle.JcdOracle;

/**
 * 图片解析实现类。新的方式
 * @author Administrator
 *
 */
public class CopyPicFromServer {
	/**
	 * 图片解析方式——海康云存储
	 * @param pic  图片id
	 * @param flag 标志位
	 * @return 解析后的图片链接地址
	 */
	public static String copyPicReturnPathOfHk(String pic) {
		Config config = Config.getInstance();
		String logFolder = config.getLogFolder();//日志文件存储路径
		String base64 = "";
		
		if (pic != null && !"".equals(pic)) {
			//获取图片服务id
			String tpfwid = pic.substring(pic.lastIndexOf("_") + 1, pic.length());
			
			//-------------------
			//图片存放在缓存的绝对路径
			String httpStr = JcdOracle.getTpcfljById(tpfwid);
			if(httpStr != null && !"".equals(httpStr)) {
				httpStr = httpStr + pic.substring(0, pic.lastIndexOf("_"));//远程图片目录
				try {
					base64 = getBase64FromUrl(httpStr);
				} catch (Exception e) {
					StringUtil.writerTXT(logFolder, "Copy picture failure：" + httpStr);
					e.printStackTrace();
				}
			}
		}
		return base64;//图片前缀+图片ID
	}
	
	/**
	   * 将http地址转成base64
	 * 
	 * @param path
	   *            文件保存位置
	 * @param url
	   *            文件url地址
	 * @throws IOException
	 */
	public static String getBase64FromUrl(String urlStr) throws Exception {
		HttpURLConnection con = null;
		String base64 = null;
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		InputStream inputStream = null;
		try {
			// 读取图片流
			URL url = new URL(urlStr);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(10 * 1000);
			con.setReadTimeout(10 * 1000);
			
			//获取流
			inputStream = con.getInputStream();
			if (inputStream != null) {
				//获取流
				while ((len = inputStream.read(data)) != -1) {
					out.write(data, 0, len);
				}
				
				// 转成base64
				base64 = Base64.encodeBase64String(out.toByteArray());
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(out != null) {
				out.close();
			}
			if(inputStream != null) {
				inputStream.close();
			}
			if(con != null) {
				con.disconnect();
			}
		}
		return base64;
	}
}