package cn.ms.device.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import cn.function.mapper.SqlMapper;
import cn.function.service.SqlFactory;
import cn.ms.device.schema.MobileDeviceInfo;

public interface MobileDeviceInfoMapper extends SqlMapper<MobileDeviceInfo> {

	/**
	 * 自定义条件查询
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectByParam", type = SqlFactory.class)
	MobileDeviceInfo selectByParam(Class<MobileDeviceInfo> class1, Map<String, Object> param);

	/**
	 * 自定义条件查询-返回List
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectListByParam", type = SqlFactory.class)
	List<MobileDeviceInfo> selectListByParam(Class<MobileDeviceInfo> class1, Map<String, Object> param);

	/**
	 * 根据主键，使用安卓生成唯一id删除
	 */
	@Delete(value = { "<script>", "DELETE FROM MobileDeviceInfo WHERE id=#{param1}", "</script>" })
	int deleteById(String id);

	/**
	 * 根据主键，使用安卓生成唯一id查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM MobileDeviceInfo WHERE id=#{param1}", "</script>" })
	MobileDeviceInfo selectById(String id);

}
