package cn.ms.user.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import cn.function.mapper.SqlMapper;
import cn.function.service.SqlFactory;
import cn.ms.user.schema.SysRoleQuanXian;

public interface SysRoleQuanXianMapper extends SqlMapper<SysRoleQuanXian> {

	/**
	 * 自定义条件查询
	 *
	 * @param class1
	 *            实体类
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectByParam", type = SqlFactory.class)
	SysRoleQuanXian selectByParam(Class<SysRoleQuanXian> class1, Map<String, Object> param);

	/**
	 * 自定义条件查询-返回List
	 *
	 * @param class1
	 *            实体类
	 * @param param
	 *            参数：调用样例,根据时间排序,并且大于参数时间
	 *@since Map<String, Object[]> param1 = new HashMap<String, Object[]>();
	 *@since param1.put("updateDate",new Object[]{"2019-01-21",SqlVo.daYuDengYu,SqlVo.desc});
	 */
	@SelectProvider(method = "selectListByParamExt", type = SqlFactory.class)
	List<SysRoleQuanXian> selectListByParamExt(Class<SysRoleQuanXian> class1, Map<String, Object[]> param);

	/**
	 * 根据主键id删除
	 */
	@Delete(value = { "<script>", "DELETE FROM SysRoleQuanXian WHERE id=#{param1}", "</script>" })
	int deleteById(Long id);

	/**
	 * 根据主键id查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM SysRoleQuanXian WHERE id=#{param1}", "</script>" })
	SysRoleQuanXian selectById(Long id);

	/**
	 * 根据角色编码删除
	 */
	@Delete(value = { "<script>", "DELETE FROM SysRoleQuanXian WHERE roleCode=#{param1}", "</script>" })
	int deleteByRoleCode(String roleCode);

	/**
	 * 根据角色编码查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM SysRoleQuanXian WHERE roleCode=#{param1}", "</script>" })
	SysRoleQuanXian selectByRoleCode(String roleCode);

	/**
	 * 根据角色编码查询-返回List
	 */
	@Select(value = { "<script>", "SELECT * FROM SysRoleQuanXian WHERE roleCode=#{param1}", "</script>" })
	List<SysRoleQuanXian> selectListByRoleCode(String roleCode);

}
