package com.dyst.utils;

public class PkiUtil {
	/**
	 * 产生认证原文
	 */
	public static String generateRandomNum() {
		String num = "1234567890abcdefghijklmnopqrstopqrstuvwxyz";
		int size = 6;
		char[] charArray = num.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			sb.append(charArray[((int) (Math.random() * 10000) % charArray.length)]);
		}
		return sb.toString();
	}
}
