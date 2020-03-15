package cn.ms.common.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import cn.ms.common.service.PzRedisService;

@RestController
@RequestMapping("/pzRedisAction")
public class PzRedisAction {

	@Autowired
	private PzRedisService pzRedisService;

	/**
	 * 查询接口
	 */
	@RequestMapping("")
	public Map<String, Object> find(HttpServletRequest request) {
		return pzRedisService.select(request);
	}

}
