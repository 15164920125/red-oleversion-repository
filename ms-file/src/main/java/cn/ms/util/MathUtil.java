package cn.ms.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {
	/**
	 * 加法
	 */
	public static String add(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).toString();
	}
	
	/**
	 * 减法
	 */
	public static String sub(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).toString();
	}

	/**
	 * 给val设置默认值
	 */
	public static Double safeDouble(Double val, Double defaultVal) {
		if (null == val) {
			return defaultVal;
		} else {
			return val;
		}
	}

	/**
	 * value保留两位小数（RoundingMode.HALF_UP 四舍五入的原则）
	 */
	public static String scale(String value) {
		return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toString();
	}

	public static String scale(double val) {
		return new BigDecimal(String.valueOf(val)).setScale(2, RoundingMode.HALF_UP).toString();
	}

	/**
	 * 乘法
	 * @param scale 几位小数
	 * @return （四舍五入的原则）
	 */
	public static String multiply(String first, String second,int scale) {
		return new BigDecimal(first).multiply(new BigDecimal(second)).setScale(scale, RoundingMode.HALF_UP).toString();
	}

	public static String multiply(double first, double second) {
		return new BigDecimal(String.valueOf(first)).multiply(new BigDecimal(String.valueOf(second)))
				.setScale(2, RoundingMode.HALF_UP).toString();

	}

	/**
	 * 乘法
	 * first*second*third 结果：保留两位小数（四舍五入的原则）
	 */
	public static String multiply(double first, double second, double third) {
		return new BigDecimal(String.valueOf(first)).multiply(new BigDecimal(String.valueOf(second)))
				.multiply(new BigDecimal(String.valueOf(third))).setScale(2, RoundingMode.HALF_UP).toString();
	}

	/**
	 * 除法
	 * @param scale   几位小数
	 * @return（四舍五入的原则）
	 */
	public static String divide(String first, String second,int scale) {
		return new BigDecimal(first).divide(new BigDecimal(second), scale, RoundingMode.HALF_UP).toString();
	}

}
