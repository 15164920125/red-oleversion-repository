package cn.ms.device.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import cn.ms.device.service.ApkInfoService;

@RestController
@RequestMapping("/apkInfoAction")
public class ApkInfoAction {

	@Autowired
	private ApkInfoService apkInfoService;

	/**
	 * 查询接口
	 */
	@RequestMapping("/selectApkList")
	public Map<String, Object> selectApkList(HttpServletRequest request) {
		return apkInfoService.selectApkList(request);
	}
	
	/**
	 * apk版本保存及发布
	 * @param request
	 * @return
	 */
	@RequestMapping("/addApkVersion")
	public Map<String, Object> addApkVersion(HttpServletRequest request){
		return apkInfoService.addApkVersion(request);
	}
	
	/**
	 * 新apk初始化
	 */
	@RequestMapping("/initNewApkInfo")
	public Map<String, Object> initNewApkInfo(HttpServletRequest request){
		return apkInfoService.initNewApkInfo(request);
	}
	
	/**
	 * 已保存apk初始化
	 */
	@RequestMapping("/initOldApkInfo")
	public Map<String, Object> initOldApkInfo(HttpServletRequest request){
		return apkInfoService.initOldApkInfo(request);
	}
	
	/**
	 * 删除未发布版本
	 */
	@RequestMapping("/deleteNoPublishInfo")
	public Map<String, Object> deleteNoPublishInfo(HttpServletRequest request){
		return apkInfoService.deleteNoPublishInfo(request);
	}

}
