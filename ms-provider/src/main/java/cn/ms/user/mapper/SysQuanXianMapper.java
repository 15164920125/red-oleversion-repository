package cn.ms.user.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import cn.function.mapper.SqlMapper;
import cn.function.service.SqlFactory;
import cn.ms.user.schema.SysQuanXian;

public interface SysQuanXianMapper extends SqlMapper<SysQuanXian> {

	/**
	 * 自定义条件查询
	 *
	 * @param class1
	 *            实体类
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectByParam", type = SqlFactory.class)
	SysQuanXian selectByParam(Class<SysQuanXian> class1, Map<String, Object> param);

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
	List<SysQuanXian> selectListByParamExt(Class<SysQuanXian> class1, Map<String, Object[]> param);

	/**
	 * 根据主键id删除
	 */
	@Delete(value = { "<script>", "DELETE FROM SysQuanXian WHERE id=#{param1}", "</script>" })
	int deleteById(Long id);

	/**
	 * 根据主键id查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM SysQuanXian WHERE id=#{param1}", "</script>" })
	SysQuanXian selectById(Long id);

	/**
	 * 根据权限编码删除
	 */
	@Delete(value = { "<script>", "DELETE FROM SysQuanXian WHERE quanXianCode=#{param1}", "</script>" })
	int deleteByQuanXianCode(String quanXianCode);

	/**
	 * 根据权限编码查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM SysQuanXian WHERE quanXianCode=#{param1}", "</script>" })
	SysQuanXian selectByQuanXianCode(String quanXianCode);

	/**
	 * 根据权限编码查询-返回List
	 */
	@Select(value = { "<script>", "SELECT * FROM SysQuanXian WHERE quanXianCode=#{param1}", "</script>" })
	List<SysQuanXian> selectListByQuanXianCode(String quanXianCode);

}
