package cn.ms.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private StringUtil() {

	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return str != null && str.length() != 0;
	}

	/**
	 * 获取UUID,得到32位的唯一字符串
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 首字母转大写
	 */
	public static String upperCase(String str) {
		char[] chars = str.toCharArray();
		if (chars[0] >= 97 && chars[0] <= 122) {
			chars[0] = (char) (chars[0] - 32);
		}
		return String.valueOf(chars);
	}

	/**
	 * 首字母转小写
	 */
	public static String lowerCase(String str) {
		char[] chars = str.toCharArray();
		if (chars[0] >= 65 && chars[0] <= 90) {
			chars[0] = (char) (chars[0] + 32);
		}
		return String.valueOf(chars);
	}

	/**
	 * 清除带有双引号,单引号字符串
	 * 
	 * @param str
	 */
	public static String strFormat(String str) {
		if (null == str) {
			return "";
		} else if ("".equals(str.trim())) {
			return "";
		}
		String str1 = str.replaceAll(" {2,}", " ");
		String regEx = "[<\"\'>]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str1);
		return m.toString().trim();
	}

	/**
	 * 过滤特殊符号
	 */
	public static String filterScript(String str) {
		Pattern pattern = Pattern.compile("[@#%~!*#$<'>\\\\‘：”“’]");
		Matcher m = pattern.matcher(str);
		return m.replaceAll("").trim();
	}


	/**
	 * 是否有汉字
	 */
	public static boolean isHasHanZi(String str) {
		if(isEmpty(str)){
			return false;
		}
		String regex = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 转码
	 */
	public static String encoding(String str) throws UnsupportedEncodingException {
		if (StringUtil.isEmpty(str)) {
			return null;
		} else {
			// 乱码中没有汉字:如果字符串中没有汉字,则转码
			String regex = "[\\u4e00-\\u9fa5]";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			String str1 = null;
			if (!m.find()) {
				str1 = new String(str.getBytes("ISO-8859-1"), "UTF-8");
			}
			return str1;
		}
	}

	public static String exceptionToString(Exception e) {
		String errorInfo = "";
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			errorInfo = "\r\n" + sw.toString() + "\r\n";
		} finally {
			try {
				if (sw != null) {
					sw.close();
				}
				if (pw != null) {
					pw.close();
				}
			} catch (IOException e1) {
				throw new RuntimeException("流异常！", e1);
			}

		}
		return errorInfo;
	}
}
