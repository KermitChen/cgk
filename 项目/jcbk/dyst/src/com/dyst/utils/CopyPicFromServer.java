package com.dyst.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import org.apache.commons.lang.StringUtils;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.codehaus.xfire.client.Client;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;

/**
 * ͼƬ����ʵ���ࡣ�µķ�ʽ
 * @author Administrator
 *
 */
public class CopyPicFromServer {
	public static void main(String[] args) throws Exception {
		Config config = Config.getInstance();
		
		//ͼƬid
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject picJson = new JSONObject();
		picJson.put("imgid", "20190313152344520251005311N_100");
		//token
		JSONObject obj = new JSONObject();
		obj.put("username", config.getUsername());
		obj.put("token", config.getToken());
		obj.put("where", list);
		
		//����
		JSONObject jsonObject = HttpReqJsonUtils.http().post(config.getKjcPic() + "/showImgloca", JSONObject.toJSONString(obj));
		
		//�������
		if(jsonObject != null) {
			if(StringUtils.isNotBlank(jsonObject.getString("code")) && "200".equals(jsonObject.getString("code"))) {//���óɹ�
				//��ȡ����
				if(StringUtils.isNotBlank(jsonObject.getString("data"))) {
					//ת��������
					JSONArray dataArr = jsonObject.parseArray(jsonObject.getString("data"));
					if(dataArr != null && dataArr.size() > 0) {
						//��ȡ��һ��Ԫ��
						JSONObject json = dataArr.getJSONObject(0);
						if(json != null && StringUtils.isNotBlank(json.getString("imgurl"))) {//ͼƬ��ַ����Ϊ��
							
						}
					}
				}
			}
		}
	}
	
	/**
	 * ͼƬ������ʽ��������洢
	 * @param pic  ͼƬid
	 * @param flag ��־λ
	 * @return �������ͼƬ���ӵ�ַ
	 */
	public static String copyPicReturnPath(String tpid, String flag) {
		Config config = Config.getInstance();
		String logFolder = config.getLogFolder();//��־�ļ��洢·��
		String picUrl = "";
		
		//ͼƬid����Ϊ��
		if (StringUtils.isNotBlank(tpid)) {
			if("02".equals(flag)){
				try {
					//ͼƬid
					List<JSONObject> list = new ArrayList<JSONObject>();
					JSONObject picJson = new JSONObject();
					picJson.put("imgid", tpid);
					//token
					JSONObject obj = new JSONObject();
					obj.put("username", config.getUsername());
					obj.put("token", config.getToken());
					obj.put("where", list);
					
					//����
					JSONObject jsonObject = HttpReqJsonUtils.http().post(config.getKjcPic() + "/showImgloca", JSONObject.toJSONString(obj));
					
					//�������
					if(jsonObject != null) {
						if(StringUtils.isNotBlank(jsonObject.getString("code")) && "200".equals(jsonObject.getString("code"))) {//���óɹ�
							//��ȡ����
							if(StringUtils.isNotBlank(jsonObject.getString("data"))) {
								//ת��������
								JSONArray dataArr = jsonObject.parseArray(jsonObject.getString("data"));
								if(dataArr != null && dataArr.size() > 0) {
									//��ȡ��һ��Ԫ��
									JSONObject json = dataArr.getJSONObject(0);
									if(json != null && StringUtils.isNotBlank(json.getString("imgurl"))) {//ͼƬ��ַ����Ϊ��
										picUrl = json.getString("imgurl");
									}
								}
							}
						}
					}
				} catch (Exception e) {
					StringUtil.writerTXT(logFolder, "�����ֽӿڻ�ȡ����ͼƬʧ�ܣ�" + tpid);
					e.printStackTrace();
				}
			} else if("07".equals(flag)){//������ǵ�ȡ����ͼƬ����Υ������ͼƬת��

			}
		}
		
		//���ص�ַ
		return picUrl;
	}
	
	/**
	 * ͼƬ������ʽ���������ƴ洢
	 * @param pic  ͼƬid
	 * @param flag ��־λ
	 * @return �������ͼƬ���ӵ�ַ
	 */
	public static String copyPicReturnPathOfHk(String pic, String flag) {
		Config config = Config.getInstance();
		String picURL = config.getPicURL();//ͼƬ����ǰ׺
		String hcUrl = config.getCacheUrl();//����·��
		String logFolder = config.getLogFolder();//��־�ļ��洢·��
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		
		String tplj = "";//ͼƬ�м�·��
		if (pic != null && !"".equals(pic)) {
			//������
			String ymd = df.format(new Date());
			int filePath1 = Integer.parseInt(ymd.substring(0, 4));//��
			int filePath2 =Integer.parseInt(ymd.substring(4, 6));//��
			int filePath3 = Integer.parseInt(ymd.substring(6, 8));//��
			tplj = File.separator + filePath1 + File.separator + filePath2 + File.separator + filePath3 + File.separator;
			
			//-------------------
			//ͼƬ����ڻ���ľ���·��
			String hcPath = hcUrl + tplj + changeSpecialSign(pic) + ".jpg";//���ʻ����·��
			if("02".equals(flag)){//��������ڣ�����Զ���л�ȡ����ŵ�������
				if (exists(hcPath)) {//������ڻ����У�ֱ�ӷ���·��
					
				} else {//��ȡ��Ƶר���ӿڻ�ȡͼƬbase64����
					try {
						//������װ
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("tpid", pic);
						
						//����
						JSONObject jsonResult = HttpReqJsonUtils.http().post(config.getHkPic() + "/hkpic/getPicOfBase64ById", JSONObject.toJSONString(params));
						if(jsonResult != null) {
							//����״̬�ǳɹ�
							String ret = jsonResult.getString("ret");
							if(StringUtils.isNotBlank(ret) && SystemStatusCode.CALL_SUCCESS.equals(ret)) {
								//���ݲ���Ϊ��
								String base64 = jsonResult.getString("data");
								
								//����ͼƬ
								transImgBase64StrToImg(base64, hcPath);
							}
						}
					} catch (Exception e) {
						StringUtil.writerTXT(logFolder, "�Ӻ����ӿڻ�ȡ����ͼƬʧ�ܣ�" + pic);
						e.printStackTrace();
					}
				}
			} else if("07".equals(flag)){//������ǵ�ȡ����ͼƬ����Υ������ͼƬת��

			}
		}
		
		return picURL + tplj + changeSpecialSign(pic) + ".jpg";//ͼƬǰ׺+ͼƬID
	}
	
	/**
	 * ת�������ַ�
	 * @return
	 */
	private static String changeSpecialSign(String tpid) {
		String[] specialSignArr = new String[]{"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};
		for(int i=0;i < specialSignArr.length;i++){
			while(tpid.indexOf(specialSignArr[i]) != -1){
				tpid = tpid.replace(specialSignArr[i], "-");
			}
		}
		return tpid;
	}

	/**
	 * �ж�ͼƬ�Ƿ����
	 * @param pName
	 * @return
	 */
	public static boolean exists(String pName) {
		return new File(pName).exists();
	}
	
	/**
	 * �鿴ĳһ��ͼƬ�����Ƿ����
	 * @param pName
	 * @return
	 */
	public static boolean httpExists(String pName){
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(pName).openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * ��ͼƬ���Ƶ�����·����
	 * @param src
	 *            ԭ·��������·����
	 * @param dest
	 *            Ŀ��·�����ļ����Ŀ¼��
	 * @throws Exception 
	 */
	public static void copyImage(String src, String dest) throws Exception {
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		File srcDir = new File(src);
		File destDir = new File(dest);
		try {
			if(!destDir.getParentFile().exists()){//����ļ���·�������ڣ������ļ���
				destDir.getParentFile().mkdirs();
			}
			
			//��ȡͼƬ��
			input = new BufferedInputStream(new FileInputStream(srcDir));
			byte[] data = new byte[input.available()];
			input.read(data);
			
			//д��
			output = new BufferedOutputStream(new FileOutputStream(destDir));
			output.write(data);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (input != null) {
					input.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**  
	* ��http��ַ���Ƶ��ļ����ز����浽����  
	*   
	* @param path  
	*            �ļ�����λ��  
	* @param url  
	*            �ļ�url��ַ  
	* @throws IOException  
	*/  
	public static void downloadFile(String path, String urlStr) throws Exception {  
		URL url = null;
		HttpURLConnection con = null;
		BufferedImage input = null;
		BufferedOutputStream output = null;
		
		//����ļ���·�������ڣ������ļ���
		File pathDir = new File(path);
		if(!pathDir.getParentFile().exists()){
			pathDir.getParentFile().mkdirs();
		}
		
		try {
			//��ȡͼƬ��
			url = new URL(urlStr);
			con = (HttpURLConnection)url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(10*1000);
			input = ImageIO.read(con.getInputStream());
			
			//дͼƬ
			output = new BufferedOutputStream(new FileOutputStream(pathDir));
			ImageIO.write(input, "jpg", output);
		} catch (Exception e) {
			throw e;
		} finally {
			if(output != null) {
				output.flush();
				output.close();
			}
			
			if(con != null) {
				con.disconnect();
			}
		}
	}
	
	/**
	 * @Description: ��base64�����ַ���ת��ΪͼƬ
	 * @Author:
	 * @CreateTime:
	 * @param imgStr
	 *            base64�����ַ���
	 * @param path
	 *            ͼƬ·��-���嵽�ļ�
	 * @return
	 * @throws Exception 
	 */
	public static boolean transImgBase64StrToImg(String imgStr, String path) throws Exception {
		// ��������Ϊ��
		if (StringUtils.isBlank(imgStr) || StringUtils.isBlank(path)) {
			return false;
		}

		// �ж�·���Ƿ���ڣ������ڣ��򴴽�
		File fileFolder = new File(path);
		if (!fileFolder.getParentFile().exists()) {
			fileFolder.getParentFile().mkdirs();
		}

		OutputStream out = null;
		boolean succFlag = false;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			// ����
			byte[] b = decoder.decodeBuffer(imgStr);
			// ��������
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			// д�ļ�
			out = new FileOutputStream(path);
			out.write(b);
			out.flush();

			// ����ɹ�
			succFlag = true;
		} catch (Exception e) {
			throw e;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return succFlag;
	}
	
	/**
	 * ͣ����ͼƬ
	 * @param tpid
	 * @return
	 */
	public static String picCall(String tpid, String flag){
//		String xtFlag = Config.getInstance().getSysFlag();
		Config config = Config.getInstance();
		String picURL = config.getPicURL();//ͼƬ����ǰ׺
		String wftpURL = config.getWftpURL();//Υ��ͼƬ����url
		String hcUrl = config.getCacheUrl();//����·��
		String wftpUrl = config.getGcscpicUrl();
		String logFolder = config.getLogFolder();//��־�ļ��洢·��
		String serverIp = Config.getInstance().getPicCall(); 
		
		//�������_����ȡǰ�沿��
		String oldTpid = tpid;
		if(tpid.indexOf("_") != -1){
			tpid = tpid.substring(0, tpid.lastIndexOf("_"));//����id	
		}
    	
		//����   /��/��/��/      ·��
		int year = Integer.parseInt(tpid.substring(0, 4));//��
		int month =Integer.parseInt(tpid.substring(4, 6));//��
		int day = Integer.parseInt(tpid.substring(6, 8));//��
		String jcdid = tpid.substring(16, 24);//����id
		 
		//-----ͼƬid����Ϊ27λ--,,,,,,�����м�����ͼƬid�����쳣�Ĵ�����
		if(tpid.length() == 27){
			jcdid = tpid.substring(17, 25);//����id	
		}
		
		String year_month_day = File.separator + year + File.separator + month + File.separator + day + File.separator;//����    /��/��/��/   ·���ַ���
		if (tpid != null && !"".equals(tpid)) {
			String hcPath = hcUrl + year_month_day + oldTpid + ".jpg";//���ʻ����·��
			if("02".equals(flag)){//��������ڣ�����Զ���л�ȡ����ŵ�������
				if (exists(hcPath)) {//������ڻ����У�ֱ�ӷ���·��
					
				} else {
					Client client = null;
					try {
						client = new Client(new URL(serverIp));
						Object[] s = client.invoke("PicCall2", new String[] {tpid});
						String picUrl = (String)s[0];
						if(picUrl != null && !"".equals(picUrl) && InterUtil.exists(picUrl)){//���ͼƬ���ڣ����Ƶ�����
							//���Ƶ�����
							downloadFile(hcPath, picUrl);
						}
					} catch (Exception e) {
						StringUtil.writerTXT(logFolder, "��ͣ�����ӿڻ�ȡ����ͼƬʧ�ܣ�" + oldTpid);
						e.printStackTrace();
					} finally {
						//�ر�
						if(client != null) {
							client.close();
						}
					}
				}
			}else if("07".equals(flag)){//������ǵ�ȡ����ͼƬ����Υ������ͼƬת��
				String wfUrl = wftpUrl + year_month_day + jcdid + File.separator + oldTpid + ".jpg";//ͼƬ���Ŀ¼
				if(exists(hcPath) && !exists(wfUrl)){//�����������ͼƬ������Υ���в�����
					try {
						copyImage(hcPath, wfUrl);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return wftpURL + year_month_day + jcdid + File.separator + oldTpid + ".jpg";
			}
		}
		return picURL + year_month_day + oldTpid + ".jpg";//ͼƬǰ׺+ͼƬID
	}
}
