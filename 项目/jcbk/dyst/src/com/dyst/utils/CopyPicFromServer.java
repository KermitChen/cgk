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
 * 图片解析实现类。新的方式
 * @author Administrator
 *
 */
public class CopyPicFromServer {
	public static void main(String[] args) throws Exception {
		Config config = Config.getInstance();
		
		//图片id
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject picJson = new JSONObject();
		picJson.put("imgid", "20190313152344520251005311N_100");
		//token
		JSONObject obj = new JSONObject();
		obj.put("username", config.getUsername());
		obj.put("token", config.getToken());
		obj.put("where", list);
		
		//调用
		JSONObject jsonObject = HttpReqJsonUtils.http().post(config.getKjcPic() + "/showImgloca", JSONObject.toJSONString(obj));
		
		//解析结果
		if(jsonObject != null) {
			if(StringUtils.isNotBlank(jsonObject.getString("code")) && "200".equals(jsonObject.getString("code"))) {//调用成功
				//获取数据
				if(StringUtils.isNotBlank(jsonObject.getString("data"))) {
					//转换成数组
					JSONArray dataArr = jsonObject.parseArray(jsonObject.getString("data"));
					if(dataArr != null && dataArr.size() > 0) {
						//获取第一个元素
						JSONObject json = dataArr.getJSONObject(0);
						if(json != null && StringUtils.isNotBlank(json.getString("imgurl"))) {//图片地址不能为空
							
						}
					}
				}
			}
		}
	}
	
	/**
	 * 图片解析方式——中央存储
	 * @param pic  图片id
	 * @param flag 标志位
	 * @return 解析后的图片链接地址
	 */
	public static String copyPicReturnPath(String tpid, String flag) {
		Config config = Config.getInstance();
		String logFolder = config.getLogFolder();//日志文件存储路径
		String picUrl = "";
		
		//图片id不能为空
		if (StringUtils.isNotBlank(tpid)) {
			if("02".equals(flag)){
				try {
					//图片id
					List<JSONObject> list = new ArrayList<JSONObject>();
					JSONObject picJson = new JSONObject();
					picJson.put("imgid", tpid);
					//token
					JSONObject obj = new JSONObject();
					obj.put("username", config.getUsername());
					obj.put("token", config.getToken());
					obj.put("where", list);
					
					//调用
					JSONObject jsonObject = HttpReqJsonUtils.http().post(config.getKjcPic() + "/showImgloca", JSONObject.toJSONString(obj));
					
					//解析结果
					if(jsonObject != null) {
						if(StringUtils.isNotBlank(jsonObject.getString("code")) && "200".equals(jsonObject.getString("code"))) {//调用成功
							//获取数据
							if(StringUtils.isNotBlank(jsonObject.getString("data"))) {
								//转换成数组
								JSONArray dataArr = jsonObject.parseArray(jsonObject.getString("data"));
								if(dataArr != null && dataArr.size() > 0) {
									//获取第一个元素
									JSONObject json = dataArr.getJSONObject(0);
									if(json != null && StringUtils.isNotBlank(json.getString("imgurl"))) {//图片地址不能为空
										picUrl = json.getString("imgurl");
									}
								}
							}
						}
					}
				} catch (Exception e) {
					StringUtil.writerTXT(logFolder, "从新乐接口获取过车图片失败：" + tpid);
					e.printStackTrace();
				}
			} else if("07".equals(flag)){//如果不是调取过车图片，则违法过车图片转移

			}
		}
		
		//返回地址
		return picUrl;
	}
	
	/**
	 * 图片解析方式——海康云存储
	 * @param pic  图片id
	 * @param flag 标志位
	 * @return 解析后的图片链接地址
	 */
	public static String copyPicReturnPathOfHk(String pic, String flag) {
		Config config = Config.getInstance();
		String picURL = config.getPicURL();//图片调用前缀
		String hcUrl = config.getCacheUrl();//缓存路径
		String logFolder = config.getLogFolder();//日志文件存储路径
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		
		String tplj = "";//图片中间路径
		if (pic != null && !"".equals(pic)) {
			//年月日
			String ymd = df.format(new Date());
			int filePath1 = Integer.parseInt(ymd.substring(0, 4));//年
			int filePath2 =Integer.parseInt(ymd.substring(4, 6));//月
			int filePath3 = Integer.parseInt(ymd.substring(6, 8));//日
			tplj = File.separator + filePath1 + File.separator + filePath2 + File.separator + filePath3 + File.separator;
			
			//-------------------
			//图片存放在缓存的绝对路径
			String hcPath = hcUrl + tplj + changeSpecialSign(pic) + ".jpg";//访问缓存的路径
			if("02".equals(flag)){//如果不存在，则重远程中获取并存放到缓存中
				if (exists(hcPath)) {//如果存在缓存中，直接返回路径
					
				} else {//调取视频专网接口获取图片base64编码
					try {
						//参数封装
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("tpid", pic);
						
						//调用
						JSONObject jsonResult = HttpReqJsonUtils.http().post(config.getHkPic() + "/hkpic/getPicOfBase64ById", JSONObject.toJSONString(params));
						if(jsonResult != null) {
							//返回状态是成功
							String ret = jsonResult.getString("ret");
							if(StringUtils.isNotBlank(ret) && SystemStatusCode.CALL_SUCCESS.equals(ret)) {
								//数据不能为空
								String base64 = jsonResult.getString("data");
								
								//缓存图片
								transImgBase64StrToImg(base64, hcPath);
							}
						}
					} catch (Exception e) {
						StringUtil.writerTXT(logFolder, "从海康接口获取过车图片失败：" + pic);
						e.printStackTrace();
					}
				}
			} else if("07".equals(flag)){//如果不是调取过车图片，则违法过车图片转移

			}
		}
		
		return picURL + tplj + changeSpecialSign(pic) + ".jpg";//图片前缀+图片ID
	}
	
	/**
	 * 转换特殊字符
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
	 * 判断图片是否存在
	 * @param pName
	 * @return
	 */
	public static boolean exists(String pName) {
		return new File(pName).exists();
	}
	
	/**
	 * 查看某一个图片连接是否存在
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
	 * 将图片复制到缓存路径下
	 * @param src
	 *            原路径（缓存路径）
	 * @param dest
	 *            目的路径（文件存放目录）
	 * @throws Exception 
	 */
	public static void copyImage(String src, String dest) throws Exception {
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		File srcDir = new File(src);
		File destDir = new File(dest);
		try {
			if(!destDir.getParentFile().exists()){//如果文件夹路径不存在，创建文件夹
				destDir.getParentFile().mkdirs();
			}
			
			//读取图片流
			input = new BufferedInputStream(new FileInputStream(srcDir));
			byte[] data = new byte[input.available()];
			input.read(data);
			
			//写流
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
	* 将http地址形势的文件下载并保存到本地  
	*   
	* @param path  
	*            文件保存位置  
	* @param url  
	*            文件url地址  
	* @throws IOException  
	*/  
	public static void downloadFile(String path, String urlStr) throws Exception {  
		URL url = null;
		HttpURLConnection con = null;
		BufferedImage input = null;
		BufferedOutputStream output = null;
		
		//如果文件夹路径不存在，创建文件夹
		File pathDir = new File(path);
		if(!pathDir.getParentFile().exists()){
			pathDir.getParentFile().mkdirs();
		}
		
		try {
			//读取图片流
			url = new URL(urlStr);
			con = (HttpURLConnection)url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(10*1000);
			input = ImageIO.read(con.getInputStream());
			
			//写图片
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
	 * @Description: 将base64编码字符串转换为图片
	 * @Author:
	 * @CreateTime:
	 * @param imgStr
	 *            base64编码字符串
	 * @param path
	 *            图片路径-具体到文件
	 * @return
	 * @throws Exception 
	 */
	public static boolean transImgBase64StrToImg(String imgStr, String path) throws Exception {
		// 参数不能为空
		if (StringUtils.isBlank(imgStr) || StringUtils.isBlank(path)) {
			return false;
		}

		// 判断路径是否存在，不存在，则创建
		File fileFolder = new File(path);
		if (!fileFolder.getParentFile().exists()) {
			fileFolder.getParentFile().mkdirs();
		}

		OutputStream out = null;
		boolean succFlag = false;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			// 写文件
			out = new FileOutputStream(path);
			out.write(b);
			out.flush();

			// 保存成功
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
	 * 停车场图片
	 * @param tpid
	 * @return
	 */
	public static String picCall(String tpid, String flag){
//		String xtFlag = Config.getInstance().getSysFlag();
		Config config = Config.getInstance();
		String picURL = config.getPicURL();//图片调用前缀
		String wftpURL = config.getWftpURL();//违法图片访问url
		String hcUrl = config.getCacheUrl();//缓存路径
		String wftpUrl = config.getGcscpicUrl();
		String logFolder = config.getLogFolder();//日志文件存储路径
		String serverIp = Config.getInstance().getPicCall(); 
		
		//如果包含_，则取前面部分
		String oldTpid = tpid;
		if(tpid.indexOf("_") != -1){
			tpid = tpid.substring(0, tpid.lastIndexOf("_"));//监测点id	
		}
    	
		//构造   /年/月/日/      路径
		int year = Integer.parseInt(tpid.substring(0, 4));//年
		int month =Integer.parseInt(tpid.substring(4, 6));//月
		int day = Integer.parseInt(tpid.substring(6, 8));//日
		String jcdid = tpid.substring(16, 24);//监测点id
		 
		//-----图片id长度为27位--,,,,,,由于有几个点图片id出现异常的处理方法
		if(tpid.length() == 27){
			jcdid = tpid.substring(17, 25);//监测点id	
		}
		
		String year_month_day = File.separator + year + File.separator + month + File.separator + day + File.separator;//生成    /年/月/日/   路径字符串
		if (tpid != null && !"".equals(tpid)) {
			String hcPath = hcUrl + year_month_day + oldTpid + ".jpg";//访问缓存的路径
			if("02".equals(flag)){//如果不存在，则重远程中获取并存放到缓存中
				if (exists(hcPath)) {//如果存在缓存中，直接返回路径
					
				} else {
					Client client = null;
					try {
						client = new Client(new URL(serverIp));
						Object[] s = client.invoke("PicCall2", new String[] {tpid});
						String picUrl = (String)s[0];
						if(picUrl != null && !"".equals(picUrl) && InterUtil.exists(picUrl)){//如果图片存在，则复制到缓存
							//复制到缓存
							downloadFile(hcPath, picUrl);
						}
					} catch (Exception e) {
						StringUtil.writerTXT(logFolder, "从停车场接口获取过车图片失败：" + oldTpid);
						e.printStackTrace();
					} finally {
						//关闭
						if(client != null) {
							client.close();
						}
					}
				}
			}else if("07".equals(flag)){//如果不是调取过车图片，则违法过车图片转移
				String wfUrl = wftpUrl + year_month_day + jcdid + File.separator + oldTpid + ".jpg";//图片存放目录
				if(exists(hcPath) && !exists(wfUrl)){//如果缓存中有图片，并且违法中不存在
					try {
						copyImage(hcPath, wfUrl);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return wftpURL + year_month_day + jcdid + File.separator + oldTpid + ".jpg";
			}
		}
		return picURL + year_month_day + oldTpid + ".jpg";//图片前缀+图片ID
	}
}
