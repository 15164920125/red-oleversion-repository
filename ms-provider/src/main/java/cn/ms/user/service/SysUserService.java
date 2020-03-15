package cn.ms.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ms.sys.exception.BusinessException;
import cn.ms.user.action.SysUserAction;
import cn.ms.user.mapper.SysQuanXianMapper;
import cn.ms.user.mapper.SysRoleQuanXianMapper;
import cn.ms.user.mapper.SysUserLogMapper;
import cn.ms.user.mapper.SysUserMapper;
import cn.ms.user.schema.SysQuanXian;
import cn.ms.user.schema.SysRoleQuanXian;
import cn.ms.user.schema.SysUser;
import cn.ms.user.schema.SysUserLog;
import cn.ms.util.HttpUtil;
import cn.ms.util.JsonUtil;
import cn.ms.util.RedisPoolUtils;
import cn.ms.util.StringUtil;

@Service
@Transactional
public class SysUserService {

	private Logger logger = LoggerFactory.getLogger(SysUserAction.class);

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysRoleQuanXianMapper sysRoleQuanXianMapper;
	@Autowired
	private SysUserLogMapper sysUserLogMapper;
	@Autowired
	private SysQuanXianMapper sysQuanXianMapper;
	
	public Map<String, Object> insert(HttpServletRequest request) {
		try {
			SysUser sysUser = HttpUtil.getParamToVo(request, SysUser.class);
			sysUser.setToken(StringUtil.getUuid());
			SysUser user = sysUserMapper.selectByUserNo(sysUser.getUserNo());
			if (user != null) {
				return JsonUtil.returnMsg("该账号已存在,请修改.", null, 500, logger);
			}
			sysUserMapper.insert(sysUser);

			return JsonUtil.returnMsg("成功", null, 200, logger);
		} catch (Exception e) {
			throw new BusinessException("失败", e);
		}
	}

	public Map<String, Object> delete(HttpServletRequest request) {
		try {
			String userNo = request.getParameter("userNo");
			if (StringUtil.isEmpty(userNo)) {
				throw new BusinessException("id不能为空");
			}

			sysUserMapper.deleteByUserNo(userNo);
			return JsonUtil.returnMsg("成功", null, 200, logger);
		} catch (Exception e) {
			throw new BusinessException("失败", e);
		}
	}

	public Map<String, Object> update(HttpServletRequest request) {
		try {
			SysUser sysUser = HttpUtil.getParamToVo(request, SysUser.class);
			sysUser.setOperateTimeForHis(new Date());
			sysUserMapper.update(sysUser, "userNo");

			return JsonUtil.returnMsg("成功", null, 200, logger);
		} catch (Exception e) {
			throw new BusinessException("失败", e);
		}
	}

	/**
	 * 分页查询接口
	 */
	public Map<String, Object> select(HttpServletRequest request) {
		try {
			int length = 0;// 每页的条数,如果为0就是全查
			int pageNum = 1;// 第几页
			String lengthStr = request.getParameter("length");
			if (StringUtil.isNotEmpty(lengthStr)) {
				length = Integer.valueOf(lengthStr);
			}
			String start = request.getParameter("start");
			if (StringUtil.isNotEmpty(start)) {
				int temp = Integer.valueOf(start);
				pageNum = temp / length + 1;
			}

			Map<String, Object[]> param = new HashMap<>();
			param.put("userNo", new Object[] { request.getParameter("userNo") });
			param.put("phoneNum", new Object[] { request.getParameter("phoneNum") });
			param.put("email", new Object[] { request.getParameter("email") });
			param.put("roleCode", new Object[] { request.getParameter("roleCode") });
			PageHelper.startPage(pageNum, length);
			List<SysUser> list = sysUserMapper.selectListByParamExt(SysUser.class, param);
			PageInfo<SysUser> page = new PageInfo<>(list);

			System.out.println("总条数：" + page.getTotal());
			System.out.println("总页数：" + page.getPages());

			return JsonUtil.returnDataTable("成功", list, 200, page.getTotal(), logger);
		} catch (Exception e) {
			throw new BusinessException("查询失败", e);
		}
	}

	/**
	 * 登录接口
	 */
	public Map<String, Object> login(HttpServletRequest request) {
		String userNo = request.getParameter("userNo");
		String pwd = request.getParameter("pwd");
		if (StringUtil.isEmpty(userNo)) {
			throw new BusinessException("账号不能为空");
		} else if (StringUtil.isEmpty(pwd)) {
			throw new BusinessException("密码不能为空");
		}
		try {
			SysUser sysUser = sysUserMapper.selectByUserNoAndPwd(userNo, pwd);
			if (sysUser == null) {
				return JsonUtil.returnMsg("账号或密码有误", null, 500, logger);
			}
			sysUser.setPwd(null);

			//权限
			List<SysRoleQuanXian> sysRoleQuanXianList = sysRoleQuanXianMapper.selectListByRoleCode(sysUser.getRoleCode());
			for (SysRoleQuanXian sysRoleQuanXian : sysRoleQuanXianList) {
				SysQuanXian selectById = sysQuanXianMapper.selectByQuanXianCode(sysRoleQuanXian.getQuanXianCode());
				sysRoleQuanXian.setQuanXianName(selectById.getQuanXianName());
			}
			
			//登录记录
			SysUserLog sysUserLog = new SysUserLog();
			sysUserLog.setUserNo(userNo);
			SysUserLog selectByUserNo = sysUserLogMapper.selectByUserNoAndOperationCode(userNo, "dengLu");
			if (selectByUserNo == null) {
				sysUserLog.setUserName(sysUser.getUserName());
				sysUserLog.setOperationCode("dengLu");
				sysUserLog.setOperationName("登录成功");
				sysUserLogMapper.insert(sysUserLog);
			} else {
				sysUserLog.setOperateTimeForHis(new Date());
				sysUserLogMapper.update(sysUserLog, "id");
			}

			Map<String, Object> map = new HashMap<>();
			map.put("SysUser", sysUser);
			map.put("SysRoleQuanXian", sysRoleQuanXianList);

			// token放到redis
			// Jedis jedis = RedisPoolUtils.getJedis();
			// boolean keyExist = jedis.exists(sysUser.getToken());
			// if (keyExist) {
			// jedis.del(sysUser.getToken());
			// }
			// // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
			// //key过期时间6小时
			// jedis.set(sysUser.getToken(), "", "NX", "EX", 21600);
			// jedis.close();
			return JsonUtil.returnMsg("成功", map, 200, logger);
		} catch (Exception e) {
			RedisPoolUtils.redisPool = null;
			throw new BusinessException("登录异常", e);
		}
	}

	/**
	 * 退出接口
	 */
	public Map<String, Object> logout(HttpServletRequest request) {
		String userNo = request.getParameter("userNo");
		return JsonUtil.returnMsg(userNo + " 退出系统成功", null, 200, logger);
	}
}
