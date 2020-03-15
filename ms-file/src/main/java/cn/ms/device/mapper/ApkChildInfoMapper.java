package cn.ms.device.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import cn.function.mapper.SqlMapper;
import cn.function.service.SqlFactory;
import cn.ms.device.schema.ApkChildInfo;

public interface ApkChildInfoMapper extends SqlMapper<ApkChildInfo> {

	/**
	 * 自定义条件查询
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectByParam", type = SqlFactory.class)
	ApkChildInfo selectByParam(Class<ApkChildInfo> class1, Map<String, Object> param);

	/**
	 * 自定义条件查询-返回List
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectListByParam", type = SqlFactory.class)
	List<ApkChildInfo> selectListByParam(Class<ApkChildInfo> class1, Map<String, Object> param);

	/**
	 * 根据主键删除
	 */
	@Delete(value = { "<script>", "DELETE FROM ApkChildInfo WHERE id=#{param1}", "</script>" })
	int deleteById(String id);

	/**
	 * 根据主键查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM ApkChildInfo WHERE id=#{param1}", "</script>" })
	ApkChildInfo selectById(String id);

}
