package cn.ms.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import cn.ms.user.service.SysRoleService;

import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sysRoleAction")
public class SysRoleAction {

	@Autowired
	private SysRoleService sysRoleService;

	@RequestMapping("/insert")
	public Map<String, Object> insert(HttpServletRequest request) {
		return sysRoleService.insert(request);
	}

	@RequestMapping("/delete")
	public Map<String, Object> delete(HttpServletRequest request) {
		return sysRoleService.delete(request);
	}

	@RequestMapping("/update")
	public Map<String, Object> update(HttpServletRequest request) {
		return sysRoleService.update(request);
	}

	@RequestMapping("/select")
	public Map<String, Object> select(HttpServletRequest request) {
		return sysRoleService.select(request);
	}

}
