package cn.ms.user.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import cn.function.mapper.SqlMapper;
import cn.function.service.SqlFactory;
import cn.ms.user.schema.SysUserLog;

public interface SysUserLogMapper extends SqlMapper<SysUserLog> {

	/**
	 * 自定义条件查询
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectByParam", type = SqlFactory.class)
	SysUserLog selectByParam(Class<SysUserLog> class1, Map<String, Object> param);

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
	List<SysUserLog> selectListByParamExt(Class<SysUserLog> class1, Map<String, Object[]> param);


	/**
	 * 根据主键删除
	 */
	@Delete(value = { "<script>", "DELETE FROM SysUserLog WHERE id=#{param1}", "</script>" })
	int deleteById(Long id);

	/**
	 * 根据主键查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM SysUserLog WHERE id=#{param1}", "</script>" })
	SysUserLog selectById(Long id);

	/**
	 * 根据帐号删除
	 */
	@Delete(value = { "<script>", "DELETE FROM SysUserLog WHERE userNo=#{param1}", "</script>" })
	int deleteByUserNo(String userNo);

	/**
	 * 根据帐号查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM SysUserLog WHERE userNo=#{param1}", "</script>" })
	SysUserLog selectByUserNo(String userNo);

	/**
	 * 根据帐号查询-返回List
	 */
	@Select(value = { "<script>", "SELECT * FROM SysUserLog WHERE userNo=#{param1}", "</script>" })
	List<SysUserLog> selectListByUserNo(String userNo);
	
	@Select(value = { "<script>", "SELECT * FROM SysUserLog WHERE userNo=#{param1} and operationCode=#{param2}", "</script>" })
	SysUserLog selectByUserNoAndOperationCode(String userNo,String operationCode);

}
