package cn.ms.common.schema;

import java.util.Date;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "部门表", name = "GY_Dept")
public class GyDept {

	@Lie(comment = "主键", isPrimaryKey = true)
	private String id;
	
	@Lie(comment = "名字", flag = true)
	private String name;

	@Lie(comment = "姓别")
	private Long sex;

	@Lie(comment = "内容", length = 1301)
	private String loc;
	@Lie(comment = "内容1qweqeqw1", length = 101)
	private String loc123;

	@Lie(comment = "插入时间")
	private Date insertDate;

	@Lie(comment = "更新时间")
	private Date updateDate;

}
