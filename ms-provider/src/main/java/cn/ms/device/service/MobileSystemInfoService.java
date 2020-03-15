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
import cn.ms.device.schema.MobileSystemInfo;
import cn.ms.device.action.MobileSystemInfoAction;
import cn.ms.device.mapper.MobileSystemInfoMapper;

@Service
@Transactional
public class MobileSystemInfoService{

	private Logger logger = LoggerFactory.getLogger(MobileSystemInfoAction.class);

	@Autowired
	private MobileSystemInfoMapper mobileSystemInfoMapper;

	/**
	 * 查询接口
	 */
	public Map<String, Object> select(HttpServletRequest request) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("systemCode", request.getParameter("systemCode"));
			MobileSystemInfo select = mobileSystemInfoMapper.selectByParam(MobileSystemInfo.class, param);
			return JsonUtil.returnMsg("成功", select, 200, logger);
		} catch (Exception e) {
			throw new BusinessException("查询失败", e);
		}
	}
}
