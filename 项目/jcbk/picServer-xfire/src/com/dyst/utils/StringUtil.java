package com.dyst.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StringUtil {
	// 转化字符串为十六进制编码

	private static String hexString="0123456789ABCDEF"; 
	public static String toHexString(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();

	}
	
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}
 
	/**
	 * 写日志文件，在制定目录下些日志信息，日志信息以日期.log形式存放，一天一个文件
	 * @param filePath  日志文件存放文件夹
	 * @param conent    写入内容
	 */
	public static void writerTXT(String filePath, String conent) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FileWriter fileWriter = null;
		BufferedWriter bw = null;
		try {
			//创建文件夹
			File fileFolder = new File(filePath);
			if (!fileFolder.getParentFile().exists()) {
				fileFolder.getParentFile().mkdirs();
			}
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
			
			//打开文件
			File file = new File(fileFolder + File.separator + sdf.format(new Date()) + ".log");// 日志文件
			fileWriter = new FileWriter(file, true);
			bw = new BufferedWriter(fileWriter);
			
			//写入
			bw.newLine();
			bw.write("写入时间："+sdf1.format(new Date())+"，日志内容："+conent);
			fileWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fileWriter != null){
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 写文件，换行写 pathDir:文件存放路径 fileName:文件名字 content:行内容 changeName:大于一百兆改文件名字
	 */
	@SuppressWarnings("finally")
	public static Boolean writeFile(String pathDir, String fileName, String content, boolean changeName, boolean addTime) {
		boolean writeSuccess = true;
		OutputStreamWriter out = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuilder sb = new StringBuilder();
		try {
			// 判断路径是否存在，不存在，则创建
			File fileFolder = new File(pathDir);
			if (!fileFolder.getParentFile().exists()) {
				fileFolder.getParentFile().mkdirs();
			}
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}

			// 写文件
			out = new OutputStreamWriter(new BufferedOutputStream(
					new FileOutputStream(pathDir + File.separator + fileName,
							true)), "UTF-8");
			out.write("\n");// 换行
			// 加时间戳
			if (addTime) {
				sb.append(dateFormat.format(new Date())).append(":");
			}
			sb.append(content);//内容
			out.write(sb.toString());
			out.flush();
			out.close();
			out = null;

			// 大于一百兆，则改名
			if (changeName) {
				File file = new File(pathDir + File.separator + fileName);
				if ((file.length() / 1048576.0) > 50.0) {// 如果大于50兆，则改名
					file.renameTo(new File(pathDir + File.separator + fileName + dateFormat.format(new Date())));
				}
			}
		} catch (Exception e) {
			writeSuccess = false;
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return writeSuccess;
		}
	}
}