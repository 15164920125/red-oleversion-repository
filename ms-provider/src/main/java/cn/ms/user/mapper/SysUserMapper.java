package cn.ms.user.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import cn.function.mapper.SqlMapper;
import cn.function.service.SqlFactory;
import cn.ms.user.schema.SysUser;

public interface SysUserMapper extends SqlMapper<SysUser> {

	/**
	 * 自定义条件查询
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectByParam", type = SqlFactory.class)
	SysUser selectByParam(Class<SysUser> class1, Map<String, Object> param);

	/**
	 * 自定义条件查询-返回List
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectListByParamExt", type = SqlFactory.class)
	List<SysUser> selectListByParamExt(Class<SysUser> class1, Map<String, Object[]> param);

	/**
	 * 根据主键删除
	 */
	@Delete(value = { "<script>", "DELETE FROM SysUser WHERE id=#{param1}", "</script>" })
	int deleteById(Long id);

	/**
	 * 根据主键查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM SysUser WHERE id=#{param1}", "</script>" })
	SysUser selectById(Long id);

	/**
	 * 根据帐号删除
	 */
	@Delete(value = { "<script>", "DELETE FROM SysUser WHERE userNo=#{param1}", "</script>" })
	int deleteByUserNo(String userNo);

	/**
	 * 根据帐号查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM SysUser WHERE userNo=#{param1}", "</script>" })
	SysUser selectByUserNo(String userNo);

	/**
	 * 根据帐号查询-返回List
	 */
	@Select(value = { "<script>", "SELECT * FROM SysUser WHERE userNo=#{param1}", "</script>" })
	List<SysUser> selectListByUserNo(String userNo);

	/**
	 * 根据token删除
	 */
	@Delete(value = { "<script>", "DELETE FROM SysUser WHERE token=#{param1}", "</script>" })
	int deleteByToken(String token);

	/**
	 * 根据token查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM SysUser WHERE token=#{param1}", "</script>" })
	SysUser selectByToken(String token);

	/**
	 * 根据token查询-返回List
	 */
	@Select(value = { "<script>", "SELECT * FROM SysUser WHERE token=#{param1}", "</script>" })
	List<SysUser> selectListByToken(String token);
	
	@Select(value = { "<script>", "SELECT * FROM SysUser WHERE userNo=#{param1} and pwd=#{param2}", "</script>" })
	SysUser selectByUserNoAndPwd(String userNo,String pwd);

}
