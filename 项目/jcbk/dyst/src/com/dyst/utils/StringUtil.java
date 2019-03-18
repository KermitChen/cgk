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
	// ת���ַ���Ϊʮ�����Ʊ���

	private static String hexString="0123456789ABCDEF"; 
	public static String toHexString(String str) {
		// ����Ĭ�ϱ����ȡ�ֽ�����
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// ���ֽ�������ÿ���ֽڲ���2λ16��������
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();

	}
	
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// ��ÿ2λ16����������װ��һ���ֽ�
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}
 
	/**
	 * д��־�ļ������ƶ�Ŀ¼��Щ��־��Ϣ����־��Ϣ������.log��ʽ��ţ�һ��һ���ļ�
	 * @param filePath  ��־�ļ�����ļ���
	 * @param conent    д������
	 */
	public static void writerTXT(String filePath, String conent) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FileWriter fileWriter = null;
		BufferedWriter bw = null;
		try {
			//�����ļ���
			File fileFolder = new File(filePath);
			if (!fileFolder.getParentFile().exists()) {
				fileFolder.getParentFile().mkdirs();
			}
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
			
			//���ļ�
			File file = new File(fileFolder + File.separator + sdf.format(new Date()) + ".log");// ��־�ļ�
			fileWriter = new FileWriter(file, true);
			bw = new BufferedWriter(fileWriter);
			
			//д��
			bw.newLine();
			bw.write("д��ʱ�䣺"+sdf1.format(new Date())+"����־���ݣ�"+conent);
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
	 * д�ļ�������д pathDir:�ļ����·�� fileName:�ļ����� content:������ changeName:����һ���׸��ļ�����
	 */
	@SuppressWarnings("finally")
	public static Boolean writeFile(String pathDir, String fileName, String content, boolean changeName, boolean addTime) {
		boolean writeSuccess = true;
		OutputStreamWriter out = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuilder sb = new StringBuilder();
		try {
			// �ж�·���Ƿ���ڣ������ڣ��򴴽�
			File fileFolder = new File(pathDir);
			if (!fileFolder.getParentFile().exists()) {
				fileFolder.getParentFile().mkdirs();
			}
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}

			// д�ļ�
			out = new OutputStreamWriter(new BufferedOutputStream(
					new FileOutputStream(pathDir + File.separator + fileName,
							true)), "UTF-8");
			out.write("\n");// ����
			// ��ʱ���
			if (addTime) {
				sb.append(dateFormat.format(new Date())).append(":");
			}
			sb.append(content);//����
			out.write(sb.toString());
			out.flush();
			out.close();
			out = null;

			// ����һ���ף������
			if (changeName) {
				File file = new File(pathDir + File.separator + fileName);
				if ((file.length() / 1048576.0) > 50.0) {// �������50�ף������
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