package cn.ms.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import cn.ms.util.JsonUtil;

public class PostZuulFilter extends ZuulFilter {
	private Logger logger = LoggerFactory.getLogger(PostZuulFilter.class);

	@Override
	public Object run() {
		try {
			if ("500".equals(JsonUtil.getMsg())) {
				JsonUtil.removeMsg();
				return null;
			}

			RequestContext ctx = RequestContext.getCurrentContext();
			HttpServletRequest request = ctx.getRequest();
			String path = request.getServletPath();
			if ("/error".equals(path)) {
				JsonUtil.removeMsg();
				return null;
			}

			// 区分正常数据和文件下载
			String body = "文件流";
			Object zuulResponse = RequestContext.getCurrentContext().get("zuulResponse");
			if (zuulResponse != null) {
				@SuppressWarnings("resource")
				RibbonHttpResponse resp = (RibbonHttpResponse) zuulResponse;
				MediaType contentType = resp.getHeaders().getContentType();
				if (contentType != null) {
					logger.info("contentType=" + contentType.toString());
					if (contentType.toString().contains("json")) {
						body = IOUtils.toString(resp.getBody(), "UTF-8");
						ctx.setResponseBody(body);
					}
				}
			}

			// 登录接口放session
			if ("/ms-provider/sysUserAction/login".equals(path)) {
				JSONObject jsonObject = JSON.parseObject(body);
				if (jsonObject.getIntValue("status") == 200) {
					JSONObject object = jsonObject.getJSONObject("data");
					JSONObject sysUser = object.getJSONObject("SysUser");
					request.getSession().setAttribute("SysUser", sysUser);
					logger.info("登录成功sessionId=" + request.getSession().getId());
				}
			}

			// 退出系统
			if ("/ms-provider/sysUserAction/logout".equals(path)) {
				HttpSession session = request.getSession();
				Object attribute = session.getAttribute("SysUser");
				if (attribute != null) {
					session.removeAttribute("SysUser");
				}
			}
			logger.info("返回的数据=" + body);
		} catch (Exception e) {
			logger.info("错误信息", e);
		}
		JsonUtil.removeMsg();
		return null;
	}

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "post";
	}
}
