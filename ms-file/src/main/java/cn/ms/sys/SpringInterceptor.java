package cn.ms.sys;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSON;

import cn.ms.util.HttpUtil;
import cn.ms.util.JsonUtil;

/**
 * 拦截器
 */
@ControllerAdvice
public class SpringInterceptor implements HandlerInterceptor, ResponseBodyAdvice<Object> {
	private Logger log = LoggerFactory.getLogger(SpringInterceptor.class);
	/**
	 * 配置免拦截接口地址
	 */
	private static List<String> uncheckUrls = new ArrayList<>();
	static {
		// 排除swagger相关接口
		uncheckUrls.add("/swagger-ui.html");
		uncheckUrls.add("/webjars");
		uncheckUrls.add("/csrf");
		uncheckUrls.add("/swagger-resources");
		// 排除心跳相关接口
		uncheckUrls.add("/actuator");
		// 排除静态资源
		uncheckUrls.add("/static");
		
		uncheckUrls.add("/domain");
	}

	/**
	 * 对请求进行拦截处理
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestUrl = request.getRequestURI();
		log.info("接口地址=" + requestUrl);

		// 接口免拦截效验
		for (String string : uncheckUrls) {
			boolean contains = requestUrl.contains(string);
			if (contains) {
				return true;
			}
		}
		// 打印前端传来参数
		try {
			HandlerMethod hm = (HandlerMethod) handler;
			Object bean = hm.getBean();
			Logger logger = LoggerFactory.getLogger(bean.getClass());
			HttpUtil.getParamToLog(request, logger);
		} catch (Exception e) {
			log.info("接口地址不存在,请检查是否写错了!" + e);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			Map<String, Object> returnMsg = JsonUtil.returnMsg("接口地址不存在,请检查是否写错了!", null, 500, log);
			writer.write(JSON.toJSONString(returnMsg));
			return false;
		}
		return true;

	}

	/**
	 * 对使用@ResponseBody注解的接口,接口返回的数据进行处理
	 */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
		HttpServletRequest servletRequest = servletServerHttpRequest.getServletRequest();
		String requestUrl = servletRequest.getRequestURI();
		if("/error".equals(requestUrl)){
//			log.info("接口地址=" + requestUrl);
			log.info(JSON.toJSONString("返回的数据"+body));
		}
		return body;

	}

	// TODO 处理器执行后被调用
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// String requestUrl = request.getRequestURI();
		// if (!uncheckUrls.contains(requestUrl)) {
		// DBContext.removeDbUser();
		// }
	}

	// TODO全部执行完成后调用
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}
}
