package cn.ms.user.schema;

import java.util.Date;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "角色表")
public class SysRole {
	@Lie(comment = "主键id", isPrimaryKey = true)
	private Long id;

	@Lie(comment = "角色编码", flag = true)
	private String roleCode;
	
	@Lie(comment = "角色名称")
	private String roleName;

	@Lie(comment = "角色描述")
	private String description;
	
	@Lie(comment = "插入时间")
	private Date insertTimeForHis;

	@Lie(comment = "更新时间")
	private Date operateTimeForHis;
}
