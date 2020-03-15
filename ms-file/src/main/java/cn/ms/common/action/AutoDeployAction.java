package cn.ms.common.action;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.function.annotation.ZhuShi;
import cn.function.service.AutoDeploy;
import cn.ms.util.JsonUtil;

@Controller
@RequestMapping("AutoDeployAction")
public class AutoDeployAction {
	private Logger logger = LoggerFactory.getLogger(AutoDeployAction.class);

	@ZhuShi("测试服务是否已启动")
	@RequestMapping("init")
	@ResponseBody
	public Map<String, Object> init() {
		return JsonUtil.returnMsg("连接成功", null, 200, logger);
	}

	/**
	 * 执行linux命令
	 * 
	 * @param data
	 *            当type为2的时候，该字段是数组。传参例子： "aaa,bbb,ccc"
	 * @param type
	 *            1:执行一条命令 2:执行多条命令
	 * @return
	 */
	@ZhuShi("执行linux命令")
	@RequestMapping("executeCommand")
	@ResponseBody
	public Map<String, Object> executeCommand(String data, String type) {
		return AutoDeploy.executeCommand(data, type, logger);
	}

	/**
	 * 执行linux命令
	 * 
	 * @param data
	 *            当type为2的时候，该字段是数组。传参例子： "aaa,bbb,ccc"
	 * @param type
	 *            1:执行一条命令 2:执行多条命令
	 * @return
	 */
	@ZhuShi("执行linux命令")
	@RequestMapping("executeCommandList")
	@ResponseBody
	public Map<String, Object> executeCommandList(String data) {
		return AutoDeploy.executeCommandList(data, logger);
	}

	@ZhuShi("文件上传")
	@RequestMapping("uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletRequest request) {
		return AutoDeploy.uploadFile(request, logger);
	}
	
	@RequestMapping("findFileSize")
	@ResponseBody
	public long findFileSize(String path) {
		File file = new File(path);
		System.out.println("返回参数"+file.length());
		return file.length();
	}

}
