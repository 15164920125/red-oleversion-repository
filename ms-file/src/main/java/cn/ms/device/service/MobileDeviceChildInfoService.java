package cn.ms.device.service;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.ms.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.ms.sys.exception.BusinessException;
import cn.ms.device.schema.MobileDeviceChildInfo;
import cn.ms.device.action.MobileDeviceChildInfoAction;
import cn.ms.device.mapper.MobileDeviceChildInfoMapper;

@Service
@Transactional
public class MobileDeviceChildInfoService{

	private Logger logger = LoggerFactory.getLogger(MobileDeviceChildInfoAction.class);

	@Autowired
	private MobileDeviceChildInfoMapper mobileDeviceChildInfoMapper;

	/**
	 * 查询接口
	 */
	public Map<String, Object> select(HttpServletRequest request) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", request.getParameter("id"));
			MobileDeviceChildInfo select = mobileDeviceChildInfoMapper.selectByParam(MobileDeviceChildInfo.class, param);
			return JsonUtil.returnMsg("成功", select, 200, logger);
		} catch (Exception e) {
			throw new BusinessException("查询失败", e);
		}
	}
}
