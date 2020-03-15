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
import cn.ms.user.action.SysQuanXianAction;
import cn.ms.user.mapper.SysQuanXianMapper;
import cn.ms.user.schema.SysQuanXian;
import cn.ms.util.HttpUtil;
import cn.ms.util.JsonUtil;
import cn.ms.util.StringUtil;

@Service
@Transactional
public class SysQuanXianService {

	private Logger logger = LoggerFactory.getLogger(SysQuanXianAction.class);

	@Autowired
	private SysQuanXianMapper sysQuanXianMapper;

	public Map<String, Object> insert(HttpServletRequest request) {
		try {
			SysQuanXian sysRole = HttpUtil.getParamToVo(request, SysQuanXian.class);
			sysQuanXianMapper.insert(sysRole);

			return JsonUtil.returnMsg("成功", null, 200, logger);
		} catch (Exception e) {
			throw new BusinessException("失败", e);
		}
	}
	
	public Map<String, Object> update(HttpServletRequest request) {
		try {
			SysQuanXian sysRole = HttpUtil.getParamToVo(request, SysQuanXian.class);
			sysRole.setOperateTimeForHis(new Date());
			sysQuanXianMapper.update(sysRole, "quanXianCode");
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
				pageNum=temp/length+1;
			}

			Map<String, Object[]> param = new HashMap<>();
			PageHelper.startPage(pageNum, length);
			List<SysQuanXian> list = sysQuanXianMapper.selectListByParamExt(SysQuanXian.class, param);
			PageInfo<SysQuanXian> page = new PageInfo<>(list);

			System.out.println("总条数：" + page.getTotal());
			System.out.println("总页数：" + page.getPages());

			return JsonUtil.returnDataTable("成功", list, 200, page.getTotal(), logger);
		} catch (Exception e) {
			throw new BusinessException("查询失败", e);
		}
	}

}
