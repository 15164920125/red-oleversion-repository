package cn.ms.sysmain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.function.annotation.TableName;
import cn.function.service.DataBase;
import cn.function.util.VoConvertUtil;
import cn.ms.common.schema.GyApkInfo;
import cn.ms.common.schema.PzKaiGuan;
import cn.ms.common.schema.PzRedis;

/**
 * 建表
 */
public class DBMain {
	/**
	 * 一个实体类，带来的便利功能。
	 * 
	 * @value 1.自动生成action、service、mapper层代码
	 * @value 2.自动建表
	 * @value 3.智能检测字段信息,并自动执行新增字段、删除字段、修改字段长度、修改字段类型sql
	 * @value 4.sql脚本可以直接导入PowerDesigner
	 * @value 教程：https://blog.csdn.net/tuohuang0303/article/details/80921167
	 * @value 5.后期新增、删除字段的时候,增、改、查sql不用修改
	 * @value 6.生成带注释的json报文,供前端人员使用
	 * @value 7.移动查勘专属功能:如果为总库的表，实体类中为@tableName(name="mcpdb.表名")。
	 *        即可实现动态sql、mapper层代码、sql脚本都会有mcpdb的前缀
	 */
	public static void main(final String... args) throws Exception {
		mysql();
		DataBase.filePath = "E:/dataBase";
		DataBase.voCommentFlag = false;
		DataBase.weiFlag = true;

		// false为不连数据库
		DataBase.moShiFlag = true;

		addVo();
		zhuShi();

	}

	// 实体类
	public static void addVo() {
		List<Class<?>> list = DataBase.classList;
//		list.add(PzRedis.class);
		list.add(GyApkInfo.class);

		// 获取包下的所有实体类(只获取有@tableName注解的类)
		// list.addAll(getClassList("cn.ms.user.schema"));

	}

	// 生成带注释的json报文,供前端人员使用
	public static void zhuShi() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("kaiGuan", VoConvertUtil.voToMap(new PzKaiGuan()));
		map.put("redis", VoConvertUtil.voToList(new PzRedis()));
		System.out.println(JSON.toJSONString(map, SerializerFeature.PrettyFormat));
	}

	public static void mysql() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://10.10.4.63:3306/ms_kaifa?characterEncoding=UTF8";
		String userName = "root";
		String password = "Ytx_!rb123";
		DataBase.setDB(driver, url, userName, password);
	}

	public static List<Class<?>> getClassList(String packageName) {
		List<Class<?>> list = new ArrayList<>();
		List<Class<?>> classList = DataBase.getClassList(packageName);
		for (Class<?> class1 : classList) {
			TableName annotation = class1.getAnnotation(TableName.class);
			if (annotation != null) {
				list.add(class1);
			}
		}
		return list;
	}
}
