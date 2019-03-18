package com.dyst.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
import com.dyst.oracle.JcdOracle;

/**
 * ͼƬ����ʵ���ࡣ�µķ�ʽ
 * @author Administrator
 *
 */
public class CopyPicFromServer {
	/**
	 * ͼƬ������ʽ���������ƴ洢
	 * @param pic  ͼƬid
	 * @param flag ��־λ
	 * @return �������ͼƬ���ӵ�ַ
	 */
	public static String copyPicReturnPathOfHk(String pic) {
		Config config = Config.getInstance();
		String logFolder = config.getLogFolder();//��־�ļ��洢·��
		String base64 = "";
		
		if (pic != null && !"".equals(pic)) {
			//��ȡͼƬ����id
			String tpfwid = pic.substring(pic.lastIndexOf("_") + 1, pic.length());
			
			//-------------------
			//ͼƬ����ڻ���ľ���·��
			String httpStr = JcdOracle.getTpcfljById(tpfwid);
			if(httpStr != null && !"".equals(httpStr)) {
				httpStr = httpStr + pic.substring(0, pic.lastIndexOf("_"));//Զ��ͼƬĿ¼
				try {
					base64 = getBase64FromUrl(httpStr);
				} catch (Exception e) {
					StringUtil.writerTXT(logFolder, "Copy picture failure��" + httpStr);
					e.printStackTrace();
				}
			}
		}
		return base64;//ͼƬǰ׺+ͼƬID
	}
	
	/**
	   * ��http��ַת��base64
	 * 
	 * @param path
	   *            �ļ�����λ��
	 * @param url
	   *            �ļ�url��ַ
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
			// ��ȡͼƬ��
			URL url = new URL(urlStr);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(10 * 1000);
			con.setReadTimeout(10 * 1000);
			
			//��ȡ��
			inputStream = con.getInputStream();
			if (inputStream != null) {
				//��ȡ��
				while ((len = inputStream.read(data)) != -1) {
					out.write(data, 0, len);
				}
				
				// ת��base64
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