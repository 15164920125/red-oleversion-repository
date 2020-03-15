package cn.ms.device.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import cn.function.mapper.SqlMapper;
import cn.function.service.SqlFactory;
import cn.ms.device.schema.ApkInfo;

public interface ApkInfoMapper extends SqlMapper<ApkInfo> {

	/**
	 * 自定义条件查询
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectByParam", type = SqlFactory.class)
	ApkInfo selectByParam(Class<ApkInfo> class1, Map<String, Object> param);

	/**
	 * 自定义条件查询-返回List
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectListByParam", type = SqlFactory.class)
	List<ApkInfo> selectListByParam(Class<ApkInfo> class1, Map<String, Object> param);

	/**
	 * 根据主键删除
	 */
	@Delete(value = { "<script>", "DELETE FROM ApkInfo WHERE id=#{param1}", "</script>" })
	int deleteById(String id);

	/**
	 * 根据主键查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM ApkInfo WHERE id=#{param1}", "</script>" })
	ApkInfo selectById(String id);
	
	/**
	 * 查询最新发布的apk信息
	 */
	List<ApkInfo> selectLatest();

}
