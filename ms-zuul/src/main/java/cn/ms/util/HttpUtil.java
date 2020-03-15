package cn.ms.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

public class HttpUtil {

	/**
	 * 获取前端传来的参数,打印日志
	 */
	public static void getParamToLog(HttpServletRequest request, Logger logger) {
		Enumeration<String> paramNames = request.getParameterNames();
		StringBuilder str = new StringBuilder();
		while (paramNames.hasMoreElements()) {
			String key = paramNames.nextElement();
			String value = request.getParameter(key);
			str.append(key + "=" + value + "  ");
		}
		logger.info("前端上传的参数 " + str);
	}

}
