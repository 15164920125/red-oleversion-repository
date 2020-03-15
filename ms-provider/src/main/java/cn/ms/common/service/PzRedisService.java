package cn.ms.common.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ms.common.action.PzRedisAction;
import cn.ms.common.mapper.PzRedisMapper;
import cn.ms.common.schema.PzRedis;
import cn.ms.sys.exception.BusinessException;
import cn.ms.util.JsonUtil;

@Service
@Transactional
public class PzRedisService{

	private Logger logger = LoggerFactory.getLogger(PzRedisAction.class);

	@Autowired
	private PzRedisMapper pzRedisMapper;

	/**
	 * 查询接口
	 */
	public Map<String, Object> select(HttpServletRequest request) {
		try {
			PzRedis pzRedis = pzRedisMapper.selectByParam(PzRedis.class, null);
			return JsonUtil.returnMsg("成功", pzRedis, 200, logger);
		} catch (Exception e) {
			throw new BusinessException("查询失败", e);
		}
	}
}
