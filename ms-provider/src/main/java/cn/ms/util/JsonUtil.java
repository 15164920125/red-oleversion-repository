package cn.ms.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {

	// json返回状态码
	public final static Integer SUCCESS = 200; // 正确返回
	public final static String SUCCESS_MESSAGE = "操作成功"; // 正确返回提示
	public final static Integer ERROR = 500; // 服务器内部错误
	public final static Integer ERROR_PARAM = 400; // 参数错误
	public final static Integer ERROR_AUTH = 401; // 权限错误
	public final static Integer ERROR_INEXISTENCE = 404; // 请求资源不存在
	public final static Integer ERROR_TYPE = 415; // 请求类型错误
	public final static Integer ERROR_CHECK = 422; // 校验错误

	private final static Map<Integer, String> STATE_NUMBER = new HashMap<Integer, String>();

	static {
		STATE_NUMBER.put(SUCCESS, "成功");
		STATE_NUMBER.put(ERROR, "服务器内部错误");
		STATE_NUMBER.put(ERROR_PARAM, "参数错误");
		STATE_NUMBER.put(ERROR_AUTH, "权限错误");
		STATE_NUMBER.put(ERROR_INEXISTENCE, "请求资源不存在");
		STATE_NUMBER.put(ERROR_TYPE, "请求类型错误");
		STATE_NUMBER.put(ERROR_CHECK, "校验错误");
	}

	public static final String getMsg(Integer state) {
		return STATE_NUMBER.get(state);
	}

	/**
	 * web端,查询表格专用
	 */
	public static Map<String, Object> returnDataTable(String msg, Object data, int status, long total, Logger log) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", status);
		if (data != null) {
			result.put("data", data);
		} else {
			result.put("data", Collections.emptyList());
		}
		if (status != 200) {
			total=0;
			result.put("error", msg);
		}
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);

		log.info("返回给前端的数据：" + JSON.toJSONString(result, SerializerFeature.WriteMapNullValue,
				SerializerFeature.QuoteFieldNames, SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteDateUseDateFormat));
		return result;
	}

	/**
	 * 给返回前端数据
	 */
	public static Map<String, Object> returnMsg(String msg, Object data, int status, Logger log) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", status);
		if (data != null) {
			result.put("data", data);
		} else {
			result.put("data", Collections.emptyList());
		}
		result.put("error", msg);

		log.info("返回给前端的数据：" + JSON.toJSONString(result, SerializerFeature.WriteMapNullValue,
				SerializerFeature.QuoteFieldNames, SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteDateUseDateFormat));
		return result;
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
