package cn.ms.user.service;

import java.util.ArrayList;
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
import cn.ms.user.action.SysRoleAction;
import cn.ms.user.mapper.SysQuanXianMapper;
import cn.ms.user.mapper.SysRoleMapper;
import cn.ms.user.mapper.SysRoleQuanXianMapper;
import cn.ms.user.mapper.SysUserMapper;
import cn.ms.user.schema.SysQuanXian;
import cn.ms.user.schema.SysRole;
import cn.ms.user.schema.SysRoleQuanXian;
import cn.ms.user.schema.SysUser;
import cn.ms.util.HttpUtil;
import cn.ms.util.JsonUtil;
import cn.ms.util.StringUtil;

@Service
@Transactional
public class SysRoleService {

	private Logger logger = LoggerFactory.getLogger(SysRoleAction.class);

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysRoleQuanXianMapper sysRoleQuanXianMapper;

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysQuanXianMapper sysQuanXianMapper;

	public Map<String, Object> insert(HttpServletRequest request) {
		try {
			SysRole sysRole = HttpUtil.getParamToVo(request, SysRole.class);
			SysRole selectByRoleCode = sysRoleMapper.selectByRoleCode(sysRole.getRoleCode());
			if (selectByRoleCode != null) {
				return JsonUtil.returnMsg("该角色已存在,请修改.", null, 500, logger);
			}
			sysRole.setRoleCode(StringUtil.getUuid());
			sysRoleMapper.insert(sysRole);
			
			String parameter = request.getParameter("quanXianCode");
			String[] list = parameter.split(",");
			for (String quanXianCode : list) {
				if (StringUtil.isNotEmpty(quanXianCode)) {
					SysRoleQuanXian sysRoleQuanXian = new SysRoleQuanXian();
					sysRoleQuanXian.setQuanXianCode(quanXianCode);
					sysRoleQuanXian.setRoleCode(sysRole.getRoleCode());
					sysRoleQuanXianMapper.insert(sysRoleQuanXian);
				}
			}

			return JsonUtil.returnMsg("成功", null, 200, logger);
		} catch (Exception e) {
			throw new BusinessException("失败", e);
		}
	}

	public Map<String, Object> delete(HttpServletRequest request) {
		try {
			String roleCode = request.getParameter("roleCode");
			if (StringUtil.isEmpty(roleCode)) {
				throw new BusinessException("roleCode不能为空");
			}

			sysRoleMapper.deleteByRoleCode(roleCode);
			sysRoleQuanXianMapper.deleteByRoleCode(roleCode);

			// 删除用户表的角色信息
			Map<String, Object[]> param = new HashMap<>();
			param.put("roleCode", new Object[] { roleCode });
			List<SysUser> list = sysUserMapper.selectListByParamExt(SysUser.class, param);
			for (SysUser sysUser123 : list) {
				SysUser sysUser = new SysUser();
				sysUser.setUserNo(sysUser123.getUserNo());
				sysUser.setRoleCode("");
				sysUser.setRoleName("");
				sysUser.setOperateTimeForHis(new Date());
				sysUserMapper.update(sysUser, "userNo");
			}

			return JsonUtil.returnMsg("成功", null, 200, logger);
		} catch (Exception e) {
			throw new BusinessException("失败", e);
		}
	}

	public Map<String, Object> update(HttpServletRequest request) {
		try {
			SysRole sysRole = HttpUtil.getParamToVo(request, SysRole.class);
			sysRole.setOperateTimeForHis(new Date());
			sysRoleMapper.update(sysRole, "roleCode");

			SysUser sysUser = new SysUser();
			sysUser.setRoleCode(sysRole.getRoleCode());
			sysUser.setRoleName(sysRole.getRoleName());
			sysUser.setOperateTimeForHis(new Date());
			sysUserMapper.update(sysUser, "roleCode");

			sysRoleQuanXianMapper.deleteByRoleCode(sysRole.getRoleCode());
			String parameter = request.getParameter("quanXianCode");
			String[] list = parameter.split(",");
			for (String quanXianCode : list) {
				if (StringUtil.isNotEmpty(quanXianCode)) {
					SysRoleQuanXian sysRoleQuanXian = new SysRoleQuanXian();
					sysRoleQuanXian.setQuanXianCode(quanXianCode);
					sysRoleQuanXian.setRoleCode(sysRole.getRoleCode());
					sysRoleQuanXianMapper.insert(sysRoleQuanXian);
				}
			}

			return JsonUtil.returnMsg("成功", null, 200, logger);
		} catch (Exception e) {
			throw new BusinessException("失败", e);
		}
	}

	/**
	 * 分页查询接口
	 */
	public Map<String, Object> select(HttpServletRequest request) {
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

		try {

			PageHelper.startPage(pageNum, length);
			List<SysRole> list = sysRoleMapper.selectListByParamExt(SysRole.class, null);
			PageInfo<SysRole> page = new PageInfo<>(list);

			List<Map<String, Object>> resultList = new ArrayList<>();
			for (SysRole sysRole : list) {
				Map<String, Object> map = new HashMap<>();
				map.put("roleCode", sysRole.getRoleCode());
				map.put("roleName", sysRole.getRoleName());
				map.put("description", sysRole.getDescription());

				List<SysRoleQuanXian> selectByRoleCodeList = sysRoleQuanXianMapper
						.selectListByRoleCode(sysRole.getRoleCode());
				for (SysRoleQuanXian sysRoleQuanXian : selectByRoleCodeList) {
					SysQuanXian selectById = sysQuanXianMapper.selectByQuanXianCode(sysRoleQuanXian.getQuanXianCode());
					sysRoleQuanXian.setQuanXianName(selectById.getQuanXianName());
				}
				map.put("SysRoleQuanXian", selectByRoleCodeList);

				resultList.add(map);
			}

			System.out.println("总条数：" + page.getTotal());
			System.out.println("总页数：" + page.getPages());

			return JsonUtil.returnDataTable("成功", resultList, 200, page.getTotal(), logger);
		} catch (Exception e) {
			throw new BusinessException("查询失败", e);
		}
	}
}
