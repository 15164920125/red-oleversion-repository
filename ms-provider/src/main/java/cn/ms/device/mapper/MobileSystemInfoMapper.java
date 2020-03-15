package cn.ms.device.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import cn.function.mapper.SqlMapper;
import cn.function.service.SqlFactory;
import cn.ms.device.schema.MobileSystemInfo;

public interface MobileSystemInfoMapper extends SqlMapper<MobileSystemInfo> {

	/**
	 * 自定义条件查询
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectByParam", type = SqlFactory.class)
	MobileSystemInfo selectByParam(Class<MobileSystemInfo> class1, Map<String, Object> param);

	/**
	 * 自定义条件查询-返回List
	 *
	 * @param class1
	 *            表名
	 * @param param
	 *            参数
	 */
	@SelectProvider(method = "selectListByParam", type = SqlFactory.class)
	List<MobileSystemInfo> selectListByParam(Class<MobileSystemInfo> class1, Map<String, Object> param);

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
    List<MobileSystemInfo> selectListByParamExt(Class<MobileSystemInfo> class1, Map<String, Object[]> param);
	
	
	
	/**
	 * 根据主键，系统编码删除
	 */
	@Delete(value = { "<script>", "DELETE FROM MobileSystemInfo WHERE systemCode=#{param1}", "</script>" })
	int deleteBySystemCode(String systemCode);

	/**
	 * 根据主键，系统编码查询-返回单个
	 */
	@Select(value = { "<script>", "SELECT * FROM MobileSystemInfo WHERE systemCode=#{param1}", "</script>" })
	MobileSystemInfo selectBySystemCode(String systemCode);

}
