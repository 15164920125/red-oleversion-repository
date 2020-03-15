package cn.ms.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import cn.ms.user.service.SysQuanXianService;

import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sysQuanXianAction")
public class SysQuanXianAction {

	@Autowired
	private SysQuanXianService sysQuanXianService;

	@RequestMapping("/insert")
	public Map<String, Object> insert(HttpServletRequest request) {
		return sysQuanXianService.insert(request);
	}
	
	@RequestMapping("/update")
	public Map<String, Object> update(HttpServletRequest request) {
		return sysQuanXianService.update(request);
	}

	/**
	 * 查询接口
	 */
	@RequestMapping("select")
	public Map<String, Object> select(HttpServletRequest request) {
		return sysQuanXianService.select(request);
	}

}
