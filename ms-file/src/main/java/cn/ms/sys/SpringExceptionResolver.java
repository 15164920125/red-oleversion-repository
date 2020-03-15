package cn.ms.sys;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.ms.sys.exception.BusinessException;
import cn.ms.sys.exception.FastJsonJsonViewExt;

/**
 * 全局异常处理器
 *
 */
@Configuration
public class SpringExceptionResolver implements HandlerExceptionResolver {
	private static final Logger logger = LoggerFactory.getLogger(SpringExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		String msg = null;
		ModelAndView modelAndView = new ModelAndView();
		try {
			HandlerMethod hm = (HandlerMethod) handler;
			Object bean = hm.getBean();
			Logger log = LoggerFactory.getLogger(bean.getClass());

			if (ex instanceof BusinessException) {
				BusinessException be = (BusinessException) ex;
				msg = be.getMsg();
				Exception exception = be.getException();
				if (exception == null) {
					log.error(msg);
				} else {
					log.error(msg, exception);
				}
			} else {
				msg = "繁忙的系统暂时需要停下歇歇,请您稍后再试";
				log.error(msg, ex);
			}

			modelAndView.addObject("status", 500);
			modelAndView.addObject("error", msg);
			modelAndView.addObject("data", Collections.emptyList());
			modelAndView.addObject("recordsFiltered", 0);// 前端dataTable分页框架，此字段为0,表示没有分页
			log.info("返回给前端的数据=" + JSON.toJSONString(modelAndView.getModel()));
		} catch (Exception e) {
			msg = "繁忙的系统暂时需要停下歇歇,请您稍后再试";
			modelAndView.addObject("status", 500);
			modelAndView.addObject("error", msg);
			modelAndView.addObject("data", Collections.emptyList());
			modelAndView.addObject("recordsFiltered", 0);// 前端dataTable分页框架，此字段为0,表示没有分页
			logger.info("",e);
			logger.info("返回给前端的数据=" + JSON.toJSONString(modelAndView.getModel()));
		}
		// MappingJackson2JsonView view = new MappingJackson2JsonView();
		FastJsonJsonViewExt view = new FastJsonJsonViewExt();
		modelAndView.setView(view);
		return modelAndView;
	}

}
