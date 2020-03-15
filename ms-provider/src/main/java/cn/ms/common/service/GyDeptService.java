package cn.ms.common.service;

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

import cn.ms.common.action.GyDeptAction;
import cn.ms.common.mapper.GyDeptMapper;
import cn.ms.common.schema.GyDept;
import cn.ms.sys.exception.BusinessException;
import cn.ms.util.HttpUtil;
import cn.ms.util.JsonUtil;
import cn.ms.util.StringUtil;

@Service
@Transactional
public class GyDeptService{

	private Logger logger = LoggerFactory.getLogger(GyDeptAction.class);

	@Autowired
	private GyDeptMapper gyDeptMapper;

	/**
	 * 分页查询接口
	 */

	public Map<String, Object> find1() {
		int pageNum = 1;// 第几页
		int pageSize = 2;// 每页的条数,如果为0就是全查
		
		PageHelper.startPage(pageNum, pageSize);
		List<GyDept> find = gyDeptMapper.selectListByParam(GyDept.class, null);
		PageInfo<GyDept> page = new PageInfo<>(find);
		
		System.out.println("总条数：" + page.getTotal());
		System.out.println("总页数：" + page.getPages());

		return JsonUtil.returnMsg("成功", find, 200, logger);
	}


	public Map<String, Object> update(HttpServletRequest request) {
		GyDept dept = HttpUtil.getParamToVo(request, GyDept.class);
		if(StringUtil.isEmpty(dept.getName())){
			throw new BusinessException("name不能为空");
		}
		try {
//			Jedis jedis = RedisPoolUtils.getJedis();
//			jedis.set("abc", "abc");
//			String string = jedis.get("abc");
//			System.out.println(string);
//			RedisPoolUtils.returnRes(jedis);
			
//			String s=null;
//			s.equals("");
			logger.info("进入update");
			gyDeptMapper.deleteById(dept.getId());
			gyDeptMapper.insert(dept);
//			gyDeptMapper.insertExt(dept);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", dept.getId());
//			param.put("updateDate", dept.getUpdateDate());
//			List<Dept> selectListBytt = gyDeptMapper.selectListBytt(dept.getUpdateDate());
			GyDept select = gyDeptMapper.selectByParam(GyDept.class, param);
			
			param.put("updateDate", "2019-01-21");
			List<GyDept> selectListByS = gyDeptMapper.selectListByParam(GyDept.class, param);
			List<GyDept> selectByPrimaryKey = gyDeptMapper.selectByPrimaryKey(dept.getId(),dept.getName());
//			System.out.println(selectByPrimaryKey);
//			int updateFirst2 = gyDeptMapper.update(dept, "ID");
			int updateFirst = gyDeptMapper.update(dept, "name");
//			int updateFirst1 = gyDeptMapper.updateExt(dept, "name");
//			System.out.println(updateFirst2);
//			System.out.println(updateFirst);
			List<GyDept> selectListByName = gyDeptMapper.selectListByName(dept.getName());
			return JsonUtil.returnMsg("成功", selectListByName, 200, logger);
		} catch (Exception e) {
			throw new BusinessException("报错", e);
		}
	}
}
