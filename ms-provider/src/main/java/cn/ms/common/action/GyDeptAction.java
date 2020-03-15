package cn.ms.common.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ms.common.service.GyDeptService;

@RestController
@RequestMapping("/gyDeptAction")
public class GyDeptAction {

	@Autowired
	private GyDeptService gyDeptService;

	@RequestMapping("")
	Map<String, Object> update(HttpServletRequest request) {
		return gyDeptService.update(request);
	}



	@RequestMapping("/find1")
	public Map<String, Object> find1(String id,String name) {
		return gyDeptService.find1();
	}
}
