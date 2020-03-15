package cn.ms.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import cn.ms.user.service.SysUserService;

import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sysUserAction")
public class SysUserAction {

	@Autowired
	private SysUserService sysUserService;

	@RequestMapping("/insert")
	public Map<String, Object> insert(HttpServletRequest request) {
		return sysUserService.insert(request);
	}

	@RequestMapping("/delete")
	public Map<String, Object> delete(HttpServletRequest request) {
		return sysUserService.delete(request);
	}

	@RequestMapping("/update")
	public Map<String, Object> update(HttpServletRequest request) {
		return sysUserService.update(request);
	}

	@RequestMapping("/select")
	public Map<String, Object> select(HttpServletRequest request) {
		return sysUserService.select(request);
	}

	/**
	 * 登录接口
	 */
	@RequestMapping("/login")
	public Map<String, Object> login(HttpServletRequest request) {
		return sysUserService.login(request);
	}

	/**
	 * 退出接口
	 */
	@RequestMapping("/logout")
	public Map<String, Object> logout(HttpServletRequest request) {
		return sysUserService.logout(request);
	}
}
