package cn.ms.device.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.ms.device.action.MobileDeviceInfoAction;
import cn.ms.device.mapper.ApkChildInfoMapper;
import cn.ms.device.mapper.ApkInfoMapper;
import cn.ms.device.mapper.MobileDeviceInfoMapper;
import cn.ms.device.mapper.MobileSystemInfoMapper;
import cn.ms.device.schema.ApkChildInfo;
import cn.ms.device.schema.ApkInfo;
import cn.ms.device.schema.MobileDeviceInfo;
import cn.ms.device.schema.MobileSystemInfo;
import cn.ms.sys.exception.BusinessException;
import cn.ms.util.FileUtil;
import cn.ms.util.JsonUtil;

@Service
@Transactional
public class MobileDeviceInfoService{

	private Logger logger = LoggerFactory.getLogger(MobileDeviceInfoAction.class);

	@Autowired
	private MobileDeviceInfoMapper mobileDeviceInfoMapper;
	
	@Autowired
	private ApkInfoMapper apkInfoMapper;
	
	@Autowired
	private ApkChildInfoMapper apkChildInfoMapper;

	@Autowired
	private MobileSystemInfoMapper mobileSystemInfoMapper;
	
	/**
	 * 查询接口
	 */
	public Map<String, Object> select(HttpServletRequest request) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", request.getParameter("id"));
			MobileDeviceInfo select = mobileDeviceInfoMapper.selectByParam(MobileDeviceInfo.class, param);
			return JsonUtil.returnMsg("成功", select, 200, logger);
		} catch (Exception e) {
			throw new BusinessException("查询失败", e);
		}
	}
	
	/**
	 * 移动设备初始化
	 */
	public Map<String, Object> initMobileDevice(HttpServletRequest request){
		try {
			String data = request.getParameter("data");
			JSONObject jsonObj = JSONObject.parseObject(data);
			MobileDeviceInfo deviceInfo = new MobileDeviceInfo();
			BeanUtils.copyProperties(deviceInfo, jsonObj);
			String id = deviceInfo.getId();//安卓生成唯一id
			Map<String, Object> param2 = new HashMap<String, Object>();
			param2.put("id", id);
			MobileDeviceInfo oldDevice = mobileDeviceInfoMapper.selectByParam(MobileDeviceInfo.class, param2);
			if(oldDevice==null){
				mobileDeviceInfoMapper.insert(deviceInfo);
			}else{
				mobileDeviceInfoMapper.update(deviceInfo, "id");
			}
			List<ApkInfo> apkList = apkInfoMapper.selectLatest();
			for(ApkInfo apk : apkList){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("mainId", apk.getId());
				List<ApkChildInfo> apkChildList = apkChildInfoMapper.selectListByParam(ApkChildInfo.class, param);
				apk.setChildList(apkChildList);
				Map<String, Object> param1 = new HashMap<String, Object>();
				param1.put("systemCode", apk.getSysCode());
				MobileSystemInfo sysInfo = mobileSystemInfoMapper.selectByParam(MobileSystemInfo.class, param1);
				if(sysInfo!=null){
					apk.setIconUrl(sysInfo.getSystemIconUrl());
				}
			}
			return JsonUtil.returnMsg(JsonUtil.SUCCESS_MESSAGE, apkList, JsonUtil.SUCCESS, logger);
		} catch (Exception e) {
			logger.error("移动设备初始化失败，错误信息：", e);
			return JsonUtil.returnMsg("移动设备初始化失败，错误信息：" + e.getMessage(), null, JsonUtil.ERROR, logger);
		}
	}
}
