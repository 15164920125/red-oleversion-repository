package cn.ms.util;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertiesUtil {
	private Properties properties;

	/**
	 * @param fileName文件名 例如:"redis.properties"
	 */
	public PropertiesUtil(String fileName) throws Exception {
		this.properties = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileName);
			properties.load(in);
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * @param key文件中的key名称
	 * @return
	 */
	public String getProperty(String key) {
		String value = properties.getProperty(key, "");
		return value;
	}

	/**
	 * 获取properties文件的内容
	 * 
	 * @param fileName文件名,例如abc
	 * 
	 * @param key文件中的key名称
	 */
	public static String getKey(String fileName, String key) {
		ResourceBundle rb = ResourceBundle.getBundle(fileName);
		String value = rb.getString(key);
		return value;
	}

	/**
	 * 获取properties文件的内容
	 * 
	 * @param filepath文件路径,例如/home/weblogic/faceConstraintKg.properties
	 * @param key文件中的key名称
	 */
	public static String getKeyFirst(String filePath, String key) throws Exception {
		FileInputStream in = null;
		try {
			in = new FileInputStream(filePath);
			ResourceBundle rb = new PropertyResourceBundle(in);
			String value = rb.getString(key);
			return value;
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

}
