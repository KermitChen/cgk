package com.ht.utils;

import org.apache.commons.codec.binary.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片工具类
 * @author 
 *
 */
public class ImageUtils {
	/**
	 * 将http地址转成base64
	 * 
	 * @param url
	 *            图片url地址
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
			con.setConnectTimeout(10000);
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