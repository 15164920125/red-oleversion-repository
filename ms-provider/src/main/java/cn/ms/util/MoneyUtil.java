package cn.ms.util;

import java.text.DecimalFormat;

/**   
* @Title: MoneyFormat.java 
* @Package cn.picc.mcp.util 
* @Description: TODO(阿拉伯数字的金额转换成中国财务大写工具类) 
* @author yqy
* @date 2016年7月31日 下午10:09:44 
* @version V1.0   
*/
public class MoneyUtil {

	private final static String[] STR_NUMBER = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	private final static String[] STR_UNIT = { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟" };// 整数单位
	private final static String[] STR_UNIT2 = { "角", "分", "厘" };// 小数单位

	// 1.金额转换方法convert();
	public static String convert(double m) {
		// 实例化DecimalFormat对象
		DecimalFormat df = new DecimalFormat("#0.###");
		// 格式化double数字
		String strNum = df.format(m);
		// 判断是否包含小数点
		if (strNum.indexOf(".") != -1) {
			String num = strNum.substring(0, strNum.indexOf("."));// 取整数部分
			// 整数部分大于12位不能装换（即单位级别大于千亿）
			if (num.length() > 12) {
				throw new RuntimeException("单位级别大于千亿，不支持转换！");
			}
		}
		String point = null; // 小数点
		if (strNum.indexOf(".") != -1) {
			point = "元";
		} else {
			point = "元整";
		}
		// 转换结果
		String result = getIntger(strNum) + point + getDecimal(strNum);
		if (result.startsWith("元")) {// 判断字符串是否以元为开始-即小于一元
			result = result.substring(1, result.length());// 截取字符串
		}
		return result;
	}

	// 2.转换整数部分
	private static String getIntger(String num) {
		if (num.indexOf(".") != -1) {
			num = num.substring(0, num.indexOf("."));
		}
		num = new StringBuilder(num).reverse().toString();// 翻转字符串
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < num.length(); i++) {
			temp.append(STR_UNIT[i]);
			temp.append(STR_NUMBER[num.charAt(i) - 48]);// num.charAt(i)-48获取数值
			// 或者使用Integer.pa……
		}
		num = temp.reverse().toString();// 翻转字符串
		while (true) {
			// 替换字符串
			num = num.replaceAll("零拾", "零");// 替换字符串的字符
			num = num.replaceAll("零佰", "零");
			num = num.replaceAll("零仟", "零");
			num = num.replaceAll("零万", "万");
			num = num.replaceAll("零亿", "亿");
			num = num.replaceAll("零零", "零");
			num = num.replaceAll("亿万", "亿");
			// 判断字符串中是否包含指定字符
			if (num.indexOf("零零") == -1) {
				break;
			}
		}
		if (num.lastIndexOf("零") == num.length() - 1) {// 如果字符串以零结尾将其出去
			num = num.substring(0, num.length() - 1);
		}
		return num;
	}

	// 3.转换小数部分
	private static String getDecimal(String num) {
		// 判断是否包含小数点
		if (num.indexOf(".") == -1) {
			return "";
		}
		num = num.substring(num.indexOf(".") + 1);
		// 反转字符串
		// num = new StringBuffer(num).reverse().toString();
		num = new StringBuffer(num).toString();
		StringBuffer temp = new StringBuffer();
		// 加入单位
		for (int i = 0; i < num.length(); i++) {
			temp.append(STR_NUMBER[num.charAt(i) - 48]);
			temp.append(STR_UNIT2[i]);
		}
		num = temp.toString();
		while (true) {
			// 替换字符串
			num = num.replaceAll("零角", "零"); // 替换字符串的字符
			num = num.replaceAll("零分", "零"); // 替换字符串的字符
			num = num.replaceAll("零厘", "零"); // 替换字符串的字符
			num = num.replaceAll("零零", "零"); // 替换字符串的字符
			// 判断字符串中是否包含指定字符
			if (num.indexOf("零零") == -1) {
				break;
			}
		}
		// 如果字符串以零结尾将其除去
		if (num.lastIndexOf("零") == num.length() - 1) {
			num = num.substring(0, num.length() - 1);
		}
		return num;
	}


	public static void main(String[] args) {
			String convert = convert(1234567891234.1111);
			System.out.println(convert);
	}
}
