package cn.ms.util;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.netflix.zuul.context.RequestContext;

public class JsonUtil {
	private static final ThreadLocal<String> DBUSER_CONTEXT = new ThreadLocal<String>();
	
	public static String getMsg() {
		return DBUSER_CONTEXT.get();
	}

	public static void setMsg(String msg) {
		DBUSER_CONTEXT.set(msg);
	}

	public static void removeMsg() {
		DBUSER_CONTEXT.remove();
	}
	

	/**
	 * 给返回前端数据
	 */
	public static Map<String, Object> returnMsg(String error, Object data, int status, Logger log) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", status);
		if (data != null) {
			result.put("data", data);
		} else {
			result.put("data", Collections.emptyList());
		}
		result.put("error", error);

		log.info("返回的数据：" + JSON.toJSONString(result, SerializerFeature.WriteMapNullValue,
				SerializerFeature.QuoteFieldNames, SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteDateUseDateFormat));
		return result;
	}

	public static void returnMsg(RequestContext ctx, String error, Logger log) {
		ctx.setSendZuulResponse(false);
		ctx.setResponseStatusCode(200);
		HttpServletResponse res = ctx.getResponse();
		res.setContentType("text/json;charset=UTF-8");
		PrintWriter writer = null;
		try {
			setMsg("500");
			JSONObject map = new JSONObject();
			map.put("status", 500);
			map.put("data", Collections.emptyList());
			map.put("error", error);
			log.info("返回的数据：" + JSON.toJSONString(map));
			
			writer = res.getWriter();
			writer.write(map.toJSONString());
		} catch (Exception e) {
			log.info("错误", e);
		} finally {
			writer.close();
		}
	}

	/**
	 * 将Json字符串转成Map<\String,List<实体类>> </span>
	 * 
	 * @param json
	 *            json字符串
	 * @param clazz
	 *            实体类.class
	 */
	public static <T> Map<String, List<T>> jsonToMap(String json, Class<T> clazz) {
		Map<String, List<T>> map = new HashMap<String, List<T>>();
		JSONObject object = JSON.parseObject(json);
		for (String key : object.keySet()) {
			String value = object.getString(key);
			List<T> list = JSON.parseArray(value, clazz);
			map.put(key, list);
		}
		return map;
	}

}
