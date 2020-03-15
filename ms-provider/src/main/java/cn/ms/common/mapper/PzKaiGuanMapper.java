package cn.ms.common.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import cn.function.mapper.SqlMapper;
import cn.function.service.SqlFactory;
import cn.ms.common.schema.PzKaiGuan;

public interface PzKaiGuanMapper extends SqlMapper<PzKaiGuan> {

	/**
	 * 自定义条件查询
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectByParam", type = SqlFactory.class)
	PzKaiGuan selectByParam(Class<PzKaiGuan> class1, Map<String, Object> param);

	/**
	 * 自定义条件查询-返回List
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectListByParam", type = SqlFactory.class)
	List<PzKaiGuan> selectListByParam(Class<PzKaiGuan> class1, Map<String, Object> param);

	/**
	 * 根据主键删除
	 */
	@Delete(value = { "<script>", "DELETE FROM Pz_KaiGuan WHERE id=#{param1}", "</script>" })
	int deleteById(String id);

	/**
	 * 根据主键查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM Pz_KaiGuan WHERE id=#{param1}", "</script>" })
	PzKaiGuan selectById(String id);

	/**
	 * 根据开关编码删除
	 */
	@Delete(value = { "<script>", "DELETE FROM Pz_KaiGuan WHERE keyCode=#{param1}", "</script>" })
	int deleteByKeyCode(String keyCode);

	/**
	 * 根据开关编码查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM Pz_KaiGuan WHERE keyCode=#{param1}", "</script>" })
	PzKaiGuan selectByKeyCode(String keyCode);

	/**
	 * 根据开关编码查询-返回List
	 */
	@Select(value = { "<script>", "SELECT * FROM Pz_KaiGuan WHERE keyCode=#{param1}", "</script>" })
	List<PzKaiGuan> selectListByKeyCode(String keyCode);

}
