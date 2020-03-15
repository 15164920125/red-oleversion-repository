package cn.ms.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	private static String YYYY_MM_DD = "yyyy-MM-dd";
	private static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	private static String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.sss";

	public static void main(String[] args) throws Exception {
		String xml = FileUtils.readFileToString(new File("D:\\loss.txt"));
		// String url =
		// "http://10.10.1.119:9002/mcp/service/receiveNewLossReturn";
		String url =
				// "http://localhost:9002/mcp/service/ReceiveNewTaskServlet";
				// "http://10.10.1.119:9002/mcp/service/ReceiveNewTaskServlet";
				// "http://localhost:9002/mcp/service/ReceiveNewTaskServlet";
				"http://10.10.1.119:9002/mcp/service/receiveNewLossTask";
		// String url =
		// "http://10.10.68.172:5003/piccclaim/MobileDeflossServlet";
		// String url = "http://localhost:9003/mcp/receiveSmartAction";

		// String url = "http://localhost:5006/mcp/reset/resetUserTask";

		// sendDataA(url, xml, log);

		// String url = "http://api01.idataapi.cn:8000/nlp/segment/bitspaceman";
		// String
		// url="https://10.10.1.119:8888/mcpinterf/reset/findUserInfo?userCode=3201000001";
		// String
		// url="http://10.133.240.41:80/mcp/reset/findUserInfo?userCode=3201000001";
		// String url="https://www.baidu.com";
		// String
		// url="http://10.8.20.10:8001/archmanager/databaseStructure/versionManager/list";
		// String url = "http://api.sms.cn/sms/";

		// Map<String, String> contentMap = new HashMap<String, String>();
		// contentMap.put("ac", "send");
		// contentMap.put("uid", "yangrishang11");
		// // contentMap.put("pwd", "03c3c04aba0c01d3041886ac7eae5fa6");
		// contentMap.put("pwd", "03c3c04aba0c01d3041886ac7eae5f");
		// contentMap.put("template", "497165");
		// contentMap.put("mobile", "13260209089");
		//
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("address", "三号楼");
		// map.put("name", "张三");
		// contentMap.put("content", JSON.toJSONString(map));

		String resultq = sendDataA(url, xml, log);
		// String resultq = sendDataB(url, xml, log);
		log.info(XmlUtil.formatXml(resultq));
	}

	/**
	 * 调用接口、发送报文
	 * 
	 * @param url
	 *            接口地址
	 * @param sendStr
	 *            发送报文
	 * @param log
	 *            日志
	 */
	public static String sendDataA(String url, String sendStr, Logger log) throws Exception {
		log.info("接口地址：" + url);
		log.info("发送报文：" + sendStr);

		// 发送的数据字符编码
		String sendEncode = "GBK";
		// 返回的数据字符编码
		String recvEncode = "UTF-8";
		// 超时时间30秒
		int timeout = 30000;

		PostMethod postmethod = null;
		BufferedReader bf = null;
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeout);

			// 网络不通导致的超时,重试次数，默认是3次；当前是禁用掉
			HttpClientParams httpClientParams = new HttpClientParams();
			httpClientParams.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
			httpClient.setParams(httpClientParams);

			// 访问地址
			postmethod = new PostMethod(url);

			// 发送报文
			byte[] by = sendStr.getBytes(sendEncode);
			InputStream in = new ByteArrayInputStream(by);
			RequestEntity re = new InputStreamRequestEntity(in);
			postmethod.setRequestEntity(re);
			httpClient.executeMethod(postmethod);

			// 返回报文
			StringBuffer lines = new StringBuffer();
			String line = "";
			bf = new BufferedReader(new InputStreamReader(postmethod.getResponseBodyAsStream(), recvEncode));
			while ((line = bf.readLine()) != null) {
				lines.append(line);
			}

			log.info("返回报文：" + lines.toString());
			return lines.toString();
		} finally {
			if (postmethod != null) {
				// 释放连接
				postmethod.releaseConnection();
			}
			if (bf != null) {
				bf.close();
			}
		}
	}

	/**
	 * 调用接口、发送报文
	 * 
	 * @param url
	 *            接口地址
	 * @param sendStr
	 *            发送报文
	 * @param log
	 *            日志
	 */
	public static String sendDataB(String url, String sendStr, Logger log) throws Exception {
		log.info("接口地址：" + url);
		log.info("发送报文：" + sendStr);

		// 发送的数据字符编码
		String sendEncode = "GBK";
		// 返回的数据字符编码
		String recvEncode = "GBK";
		// 超时时间30秒
		int timeout = 30000;

		String method = "POST";

		BufferedReader in = null;
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			// 提交方式
			conn.setRequestMethod(method);
			// 设置建立链接超时时间
			conn.setConnectTimeout(timeout);
			// 设置读取,返回的数据超时时间
			conn.setReadTimeout(timeout);
			// 发送数据需要设为true, 默认情况下是false;
			conn.setDoOutput(true);
			// 将对象序列化,比如上传文件.
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
			// 打开连接
			conn.connect();
			// 请求参数
			OutputStream out = conn.getOutputStream();
			out.write(sendStr.getBytes(sendEncode));
			out.flush();
			out.close();

			// 返回数据
			StringBuffer lines = new StringBuffer();
			String line = "";
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), recvEncode));
			while ((line = in.readLine()) != null) {
				lines.append(line);
			}

			log.info("返回报文：" + lines.toString());
			return lines.toString();
		} finally {
			if (in != null) {
				in.close();
			}
			if (conn != null) {
				// 断开连接
				conn.disconnect();
			}
		}
	}

	/**
	 * 模拟浏览器,发送url
	 * 
	 * @param url
	 *            接口地址
	 * @param map
	 *            发送参数
	 * @param log
	 *            日志
	 */
	public static String sendUrl(String url, Map<String, String> paramMap, Logger log) throws Exception {
		// 返回的数据字符编码
		String recvEncode = "UTF-8";
		// 超时时间30秒
		int timeout = 30000;

		PostMethod postmethod = null;
		BufferedReader bf = null;
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeout);

			// 网络不通导致的超时,重试次数，默认是3次；当前是禁用掉
			HttpClientParams httpClientParams = new HttpClientParams();
			httpClientParams.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
			httpClient.setParams(httpClientParams);
			// 访问地址
			postmethod = new PostMethod(url);
			// 在头文件中设置转码
			postmethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + recvEncode);

			log.info("接口地址=" + url);
			// 添加发送参数
			if (paramMap != null) {
				Set<String> keySet = paramMap.keySet();
				for (String key : keySet) {
					String value = paramMap.get(key);
					if (value != null && value.length() != 0) {
						postmethod.addParameter(key, value);
						log.info("发送参数:" + key + "=" + value);
					}
				}

			}

			httpClient.executeMethod(postmethod);

			// 返回报文
			StringBuffer lines = new StringBuffer();
			String line = "";
			bf = new BufferedReader(new InputStreamReader(postmethod.getResponseBodyAsStream(), recvEncode));
			while ((line = bf.readLine()) != null) {
				lines.append(line);
			}
			log.info("返回结果：" + lines.toString());
			return lines.toString();
		} finally {
			if (postmethod != null) {
				// 释放连接
				postmethod.releaseConnection();
			}
			if (bf != null) {
				bf.close();
			}
		}
	}

	// public static String sendHttpsUrl(String url, Map<String, Object> map) {
	// // 返回的数据字符编码
	// String recvEncode = "UTF-8";
	// // 超时时间60秒
	// int timeout = 60000;
	// SSLClient httpClient = null;
	// HttpPost httpPost = null;
	// String result = null;
	// try {
	// httpClient = new SSLClient();
	// httpPost = new HttpPost(url);
	// // httpPost.addHeader("Content-Type", "application/json");
	// // 设置参数
	// List<NameValuePair> list = new ArrayList<NameValuePair>();
	// Set<String> keySet = map.keySet();
	// for (String key : keySet) {
	// list.add(new BasicNameValuePair(key, (String) map.get(key)));
	// }
	// if (list.size() > 0) {
	// UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, recvEncode);
	// httpPost.setEntity(entity);
	// }
	// HttpResponse response = httpClient.execute(httpPost);
	// if (response != null) {
	// HttpEntity resEntity = response.getEntity();
	// if (resEntity != null) {
	// result = EntityUtils.toString(resEntity, recvEncode);
	// }
	// }
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return result;
	// }

	/**
	 * 转发请求头和参数
	 * 
	 * @param serviceUrl
	 *            例子： 其它系统地址http://10.133.240.41:8080/mcp
	 * @param log
	 *            日志
	 */
	public static String transferParams(String serviceUrl, HttpServletRequest request, Logger log) throws Exception {
		// 返回的数据字符编码
		String recvEncode = "UTF-8";
		// 超时时间60秒
		int timeout = 60000;

		PostMethod postmethod = null;
		BufferedReader bf = null;
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeout);

			// 网络不通导致的超时,重试次数，默认是3次；当前是禁用掉
			HttpClientParams httpClientParams = new HttpClientParams();
			httpClientParams.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
			httpClient.setParams(httpClientParams);

			// 访问地址
			String requestUrl = request.getRequestURL().toString();
			String contextPath = request.getContextPath();
			String[] urlArrays = requestUrl.split(contextPath);
			String url = serviceUrl + urlArrays[1];
			postmethod = new PostMethod(url);
			log.info("接口地址=" + url);

			// 添加请求头
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String key = headerNames.nextElement();
				String value = request.getHeader(key);
				if (value != null && value.length() != 0) {
					postmethod.setRequestHeader(key, value);
				}
				log.info("请求头参数 ：" + key + "=" + value);
			}

			// 添加请求参数
			Enumeration<String> paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String key = paramNames.nextElement();
				String value = request.getParameter(key);
				if (value != null && value.length() != 0) {
					postmethod.addParameter(key, value);
				}
				log.info("请求体参数 ：" + key + "=" + value);
			}

			httpClient.executeMethod(postmethod);

			// 返回报文
			StringBuffer lines = new StringBuffer();
			String line = "";
			bf = new BufferedReader(new InputStreamReader(postmethod.getResponseBodyAsStream(), recvEncode));
			while ((line = bf.readLine()) != null) {
				lines.append(line);
			}
			log.info("返回结果：" + lines.toString());

			return lines.toString();
		} finally {
			if (postmethod != null) {
				// 释放连接
				postmethod.releaseConnection();
			}
			if (bf != null) {
				bf.close();
			}
		}
	}

	/**
	 * 转发请求头和流（文件）
	 * 
	 * @param serviceUrl
	 *            例子：其它系统地址 http://10.133.240.41:8080/mcp
	 * @param log
	 *            日志
	 */
	public static String transferStream(String serviceUrl, HttpServletRequest request, Logger log) throws Exception {
		// 返回的数据字符编码
		String recvEncode = "UTF-8";
		// 超时时间60秒
		int timeout = 60000;

		PostMethod postmethod = null;
		BufferedReader bf = null;
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeout);

			// 网络不通导致的超时,重试次数，默认是3次；当前是禁用掉
			HttpClientParams httpClientParams = new HttpClientParams();
			httpClientParams.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
			httpClient.setParams(httpClientParams);

			// 访问地址
			String requestUrl = request.getRequestURL().toString();
			String contextPath = request.getContextPath();
			String[] urlArrays = requestUrl.split(contextPath);
			String url = serviceUrl + urlArrays[1];
			postmethod = new PostMethod(url);
			log.info("接口地址=" + url);

			// 添加请求头
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String key = headerNames.nextElement();
				String value = request.getHeader(key);
				if (value != null && value.length() != 0) {
					postmethod.setRequestHeader(key, value);
				}
				log.info("请求头参数 ：" + key + "=" + value);
			}

			// 添加流文件
			RequestEntity re = new InputStreamRequestEntity(request.getInputStream());
			postmethod.setRequestEntity(re);

			httpClient.executeMethod(postmethod);

			// 返回报文
			StringBuffer lines = new StringBuffer();
			String line = "";
			bf = new BufferedReader(new InputStreamReader(postmethod.getResponseBodyAsStream(), recvEncode));
			while ((line = bf.readLine()) != null) {
				lines.append(line);
			}
			log.info("返回结果：" + lines.toString());

			return lines.toString();
		} finally {
			if (postmethod != null) {
				// 释放连接
				postmethod.releaseConnection();
			}
			if (bf != null) {
				bf.close();
			}
		}
	}

	/**
	 * 获取前端传来的参数,打印日志
	 */
	public static void getParamToLog(HttpServletRequest request, Logger logger) {
		Enumeration<String> paramNames = request.getParameterNames();
		StringBuilder str = new StringBuilder();
		while (paramNames.hasMoreElements()) {
			String key = paramNames.nextElement();
			// if (key.contains("[]")) {
			// String[] valueList = request.getParameterValues(key);
			// key = key.replace("[]", "");
			// str.append(key + "=" + ArrayUtils.toString(valueList)+" ");
			// } else {
			String value = request.getParameter(key);
			str.append(key + "=" + value + "  ");
			// }
		}
		logger.info("前端上传的参数 " + str);
	}

	/**
	 * 获取前端传来的参数,转成map
	 */
	public static Map<String, Object> getParamToMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String key = paramNames.nextElement();
			String value = request.getParameter(key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 获取前端传来的参数,转成实体类
	 * 
	 */
	public static <T> T getParamToVo(HttpServletRequest request, Class<T> clazz) {
		try {
			T t = clazz.newInstance();
			// 获取所有字段
			Field[] fields = clazz.getDeclaredFields();

			for (Field field1 : fields) {
				field1.setAccessible(true);
				String key123 = field1.getName();
				Object value123 = null;
				String parameter1 = request.getParameter(key123);

				// 获取字段类型
				Class<?> type = field1.getType();
				
				if (parameter1 != null && parameter1.length() == 0) {
					if (type.equals(String.class)) {
						field1.set(t, parameter1);
					}
				}
				if (parameter1 != null && parameter1.length() != 0) {
					if (type.equals(String.class)) {
						value123 = parameter1;
					} else if (type.equals(Long.class) || type.equals(long.class)) {
						value123 = Long.valueOf(parameter1);
					} else if (type.equals(Date.class)) {
						String format;
						if (parameter1.length() == 19) {
							format = YYYY_MM_DD_HH_MM_SS;
						} else if (parameter1.length() == 10) {
							format = YYYY_MM_DD;
						} else {
							format = YYYY_MM_DD_HH_MM_SS_SSS;
						}
						value123 = new SimpleDateFormat(format).parse(parameter1);
					} else if (type.equals(Integer.class) || type.equals(int.class)) {
						value123 = Integer.valueOf(parameter1);
					} else if (type.equals(Double.class) || type.equals(double.class)) {
						value123 = Double.valueOf(parameter1);
					} else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
						value123 = Boolean.valueOf(parameter1);
					} else {
						value123 = parameter1;
					}
					field1.set(t, value123);
				}
			}

			return t;
		} catch (Exception e) {
			throw new RuntimeException("getParamToVo方法转换实体类异常", e);
		}
	}

}
