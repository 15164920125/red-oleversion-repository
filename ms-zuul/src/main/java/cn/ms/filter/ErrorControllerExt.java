package cn.ms.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ms.util.JsonUtil;

@RestController
public class ErrorControllerExt implements ErrorController{
	private Logger logger = LoggerFactory.getLogger(ErrorControllerExt.class);
	/**
	 * 出异常后进入该方法，交由下面的方法处理
	 */
	@Override
	public String getErrorPath() {
		return "/error";
	}

    @RequestMapping("/error")
    public Object error(HttpServletRequest request,HttpServletResponse response){
        Integer status = (Integer)request.getAttribute("javax.servlet.error.status_code");
        logger.info("进入ErrorControllerExt过虑器");
		if(status==404){
			JsonUtil.setMsg("500");
			response.setStatus(200);
			return JsonUtil.returnMsg("访问的微服务不存在,可能微服务名称写错了或者微服务已宕机", null, 500, logger);
		}else{
			response.setStatus(200);
			if(JsonUtil.getMsg()!=null){
				return JsonUtil.returnMsg(JsonUtil.getMsg(), null, 500, logger);
			}
			JsonUtil.setMsg("500");
			return JsonUtil.returnMsg("访问的微服务繁忙,无法连接,请稍后在试", null, 500, logger);
		}

    }
}
