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
import cn.ms.device.schema.ApkChildInfo;
import cn.ms.device.action.ApkChildInfoAction;
import cn.ms.device.mapper.ApkChildInfoMapper;

@Service
@Transactional
public class ApkChildInfoService{

	private Logger logger = LoggerFactory.getLogger(ApkChildInfoAction.class);

	@Autowired
	private ApkChildInfoMapper apkChildInfoMapper;

	/**
	 * 查询接口
	 */
	public Map<String, Object> select(HttpServletRequest request) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", request.getParameter("id"));
			ApkChildInfo select = apkChildInfoMapper.selectByParam(ApkChildInfo.class, param);
			return JsonUtil.returnMsg("成功", select, 200, logger);
		} catch (Exception e) {
			throw new BusinessException("查询失败", e);
		}
	}
}
