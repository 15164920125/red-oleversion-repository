package cn.ms.filter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import cn.ms.util.JsonUtil;
import cn.ms.util.StringUtil;

/**
 * 访问过滤器
 */
// @Component
public class PreZuulFilter extends ZuulFilter {

	private final Logger logger = LoggerFactory.getLogger(PreZuulFilter.class);

	@Autowired
	SimpleRouteLocator simpleRouteLocator;
	@Autowired
	private EurekaClient discoveryClient;

	/**
	 * 配置免拦截接口地址
	 */
	private static List<String> uncheckUrls = new ArrayList<>();
	static {
		// uncheckUrls.add("/mcpabc/dept");
		uncheckUrls.add("/ms-provider/sysUserAction/login");
		uncheckUrls.add("/ms-file/apk/downApk");
	}

	/**
	 * 过滤器的逻辑
	 */
	@Override
	public Object run() {
		// 获取当前请求上下文
		RequestContext ctx = RequestContext.getCurrentContext();
		// 取出当前请求
		HttpServletRequest request = ctx.getRequest();
		StringBuffer requestURL = request.getRequestURL();
		String path = request.getServletPath();
		logger.info("");
		logger.info("接口地址={} 访问的方法={}", requestURL, request.getMethod());
		getParam(ctx);
		
		int indexOf = requestURL.indexOf("/zuul");
		if (indexOf != -1) {
			path = requestURL.substring(indexOf);
		}
		Route route = simpleRouteLocator.getMatchingRoute(path);
		if (route == null) {
			JsonUtil.returnMsg(ctx, "找不到该接口对应的微服务,请稍后在试.", logger);
		}


		logger.info("调用微服务" + route.getId());
		InstanceInfo instance = discoveryClient.getNextServerFromEureka(route.getId(), false);
		logger.info("微服务IP:端口=" + instance.getHomePageUrl());

		// 后端接口免拦截效验
		for (String string : uncheckUrls) {
			boolean contains = requestURL.toString().contains(string);
			if (contains) {
				return null;
			}
		}

		String token = request.getHeader("token");
		logger.info("token=" + token);
		if (StringUtil.isEmpty(token)) {
			// web端登录效验
			HttpSession session = request.getSession();
			logger.info("sessionId=" + session.getId());
			Object attribute = session.getAttribute("SysUser");
			if (attribute == null) {
				JsonUtil.returnMsg(ctx, "请重新登录", logger);
			}
		} else {
			// 安卓效验
			if ("android".equals(token)) {
				return null;
			}else{
				JsonUtil.returnMsg(ctx, "身份不明,已被拦截", logger);
			}
		}

//		Jedis jedis = RedisPoolUtils.getJedis();
//		boolean keyExist = jedis.exists(token);
//		if (keyExist) {
//			jedis.del(token);
//			// NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
//			// key过期时间6小时
//			jedis.set(token, "", "NX", "EX", 21600);
//			jedis.close();
//		} else {
//			jedis.close();
//			JsonUtil.returnMsg(ctx, "请重新登录qqqq", logger);
////			JsonUtil.setMsg("请重新登录");
////			throw new RuntimeException("请重新登录");
//		}

		return null;
	}

	public void getParam(RequestContext ctx) {
		HttpServletRequest req = (HttpServletRequest) ctx.getRequest();
		if ("GET".equals(req.getMethod())) {
			StringBuilder params = new StringBuilder("?");
			Enumeration<String> names = req.getParameterNames();
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				params.append(name);
				params.append("=");
				params.append(req.getParameter(name));
				params.append("&");
			}
			logger.info("GET请求参数" + params);
		}

		// 头信息
		// Enumeration<String> headers = req.getHeaderNames();
		// while (headers.hasMoreElements()) {
		// String name = (String) headers.nextElement();
		// String value = req.getHeader(name);
		// logger.info("请求头" + name + "=" + value);
		// }
		// if (!ctx.isChunkedRequestBody()) {
		// ServletInputStream inp = null;
		// try {
		// inp = ctx.getRequest().getInputStream();
		// String body = null;
		// if (inp != null) {
		// body = IOUtils.toString(inp);
		// logger.info("请求体 " + body);
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
	}

	/**
	 * 过滤器类型选择： pre 为路由前 route 为路由过程中 post 为路由过程后 error 为出现错误的时候 同时也支持static
	 * ，返回静态的响应，详情见StaticResponseFilter的实现
	 * 以上类型在会创建或添加或运行在FilterProcessor.runFilters(type)
	 */
	@Override
	public String filterType() {
		return "pre"; // ZuulFilter源码中注释"pre"为在路由前过滤
	}

	/**
	 * 用来过滤器排序执行的
	 * 
	 * @return 排序的序号
	 */
	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * 是否通过这个过滤器，默认为true，改成false则不启用
	 */
	@Override
	public boolean shouldFilter() {
		return true; // 返回true表示执行这个过滤器
	}

}