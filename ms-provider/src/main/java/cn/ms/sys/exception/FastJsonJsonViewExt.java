package cn.ms.sys.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

public class FastJsonJsonViewExt extends FastJsonJsonView {
	// 解决IE浏览器调后端接口提示下载的问题
	public static final String DEFAULT_CONTENT_TYPE = "text/json";

	public FastJsonJsonViewExt() {
		setContentType(DEFAULT_CONTENT_TYPE);
		setExposePathVariables(false);
	}

}
