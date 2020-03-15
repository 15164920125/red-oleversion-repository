package cn.ms.common.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import cn.function.mapper.SqlMapper;
import cn.function.service.SqlFactory;
import cn.ms.common.schema.GyDept;

public interface GyDeptMapper extends SqlMapper<GyDept> {

	/**
	 * 自定义条件查询
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectByParam", type = SqlFactory.class)
	GyDept selectByParam(Class<GyDept> class1, Map<String, Object> param);

	/**
	 * 自定义条件查询-返回List
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectListByParam", type = SqlFactory.class)
	List<GyDept> selectListByParam(Class<GyDept> class1, Map<String, Object> param);

	/**
	 * 根据主键删除
	 */
	@Delete(value = { "<script>", "DELETE FROM GyDept WHERE id=#{param1}", "</script>" })
	int deleteById(String id);

	/**
	 * 根据主键查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM GyDept WHERE id=#{param1}", "</script>" })
	GyDept selectById(String id);

	/**
	 * 根据名字删除
	 */
	@Delete(value = { "<script>", "DELETE FROM GyDept WHERE name=#{param1}", "</script>" })
	int deleteByName(String name);

	/**
	 * 根据名字查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM GyDept WHERE name=#{param1}", "</script>" })
	GyDept selectByName(String name);

	/**
	 * 根据名字查询-返回List
	 */
	@Select(value = { "<script>", "SELECT * FROM GyDept WHERE name=#{param1}", "</script>" })
	List<GyDept> selectListByName(String name);
	
	List<GyDept> selectByPrimaryKey(String id,String name);
}
