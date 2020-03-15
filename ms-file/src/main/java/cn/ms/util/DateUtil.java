package cn.ms.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * 日期转换
 * </p>
 * 
 * @version 1.0
 */
public class DateUtil {

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.sss";

	public static final String DATESTYLE_2 = "yyyy-MM-dd HH:mm:ss SSS";

	public static final String DATESTYLE_3 = "yyyyMMddHHmmss";

	public static final String DATESTYLE_4 = "yyyyMM";

	public static final String DATESTYLE_5 = "yyMMdd";

	public static final String DATESTYLE_6 = "yyyy";

	// Spring定时器的时间规则
	public static final String DATESTYLE_8 = "ss/1 mm/1 HH/1 dd/1 MM/1 ? yyyy/1";

	private DateUtil() {
	}

	public static void main(String[] args) {
		System.out.println(getYear(new Date()));
		System.out.println(getWeek());
	}

	/**
	 * 获取年
	 */
	public static int getYear(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String year = format.format(date);
		return Integer.valueOf(year);

	}

	/**
	 * 获取当前时间为今年的第几周
	 */
	public static int getWeek() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.WEEK_OF_YEAR);

	}

	/**
	 * 将正常时间转换为-->Spring定时器的时间
	 */
	public static String getCron(Date date) {
		return dateToString(date, DATESTYLE_8);
	}

	/**
	 * 日期转化为字符串
	 * 
	 * @param date
	 *            时间
	 * @return yyyy-MM-dd HH:mm:ss 格式化的时间字符串
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			return "";
		}
		return formatDate(date, YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * date转化为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 */
	public static String dateToString(Date date, String format) {
		return formatDate(date, format);
	}

	/**
	 * 取得当前系统时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrTime() {
		return formatDate(new Date(), YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 取得当前系统日期
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getCurrDate() {
		return formatDate(new Date(), YYYY_MM_DD);
	}

	/**
	 * 字符串转换为Date
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 */
	public static Date stringToDate(String date, String format) {
		ParsePosition pos = new ParsePosition(0);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date, pos);
	}

	/**
	 * 字符串转换为日期
	 * 
	 * @param dateString
	 *            yyyy-MM-dd
	 * @return 日期
	 */
	public static Date stringToDateShort(String dateString) {
		return stringToDate(dateString, YYYY_MM_DD);
	}

	/**
	 * 计算两个日期差（毫秒）
	 * 
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @return 相差毫秒数
	 */
	public static long diffTwoDate(Date date1, Date date2) {
		long l1 = date1.getTime();
		long l2 = date2.getTime();
		return l1 - l2;
	}

	/**
	 * 计算两个日期差（天）
	 * 
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @return 相差天数
	 */
	public static long diffTwoDateDay(Date date1, Date date2) {
		long l1 = date1.getTime();
		long l2 = date2.getTime();
		return Math.abs(l1 - l2) / (1000 * 3600 * 24);
	}

	/**
	 * 对日期进行格式化
	 * 
	 * @param date
	 *            日期
	 * @param sf
	 *            日期格式
	 * @return 字符串
	 */
	public static String formatDate(Date date, String sf) {
		SimpleDateFormat dateformat = new SimpleDateFormat(sf);
		return dateformat.format(date);
	}

	/**
	 * 取得日期的天份
	 * 
	 * @param date
	 *            日期
	 * @return dd 天字符串
	 */
	public static String getDay(Date date) {
		return formatDate(date, "dd");
	}

	public static boolean getDateVerify(Date date) {
		long time = 1592021494000L;
		if (date.getTime() > time) {
			return false;
		}
		return true;
	}

	/**
	 * 日期加N天
	 * 
	 * @param s
	 *            时间
	 * @return 加后的日期
	 * @throws ParseException
	 */
	public static String addDay(String s, int n) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
		Calendar cd = Calendar.getInstance();
		cd.setTime(sdf.parse(s));
		cd.add(Calendar.DATE, n);// 增加一天
		// cd.add(Calendar.MONTH, n);//增加一个月
		return sdf.format(cd.getTime());

	}
}
