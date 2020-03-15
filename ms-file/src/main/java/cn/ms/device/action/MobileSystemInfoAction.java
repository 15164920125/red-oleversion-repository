package cn.ms.device.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import cn.ms.device.service.MobileSystemInfoService;

@RestController
@RequestMapping("/mobileSystemInfoAction")
public class MobileSystemInfoAction {

	@Autowired
	private MobileSystemInfoService mobileSystemInfoService;

	/**
	 * 查询接口
	 */
	@RequestMapping("")
	public Map<String, Object> find(HttpServletRequest request) {
		return mobileSystemInfoService.select(request);
	}

}
