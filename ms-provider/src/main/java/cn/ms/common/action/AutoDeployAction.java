package cn.ms.common.action;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ms.util.JsonUtil;

@Controller
@RequestMapping("AutoDeployAction")
public class AutoDeployAction {
	private Logger logger = LoggerFactory.getLogger(AutoDeployAction.class);

	@RequestMapping("init")
	@ResponseBody
	public Map<String, Object> init() {
		return JsonUtil.returnMsg("连接成功", null, 200, logger);
	}

}
