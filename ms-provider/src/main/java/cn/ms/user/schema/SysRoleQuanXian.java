package cn.ms.user.schema;

import java.util.Date;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "角色权限关联表")
public class SysRoleQuanXian {

	@Lie(comment = "主键id", isPrimaryKey = true)
	private Long id;

	@Lie(comment = "角色编码", flag = true)
	private String roleCode;

	@Lie(comment = "权限编码")
	private String quanXianCode;

	@Lie(comment = "权限名称", isExclude = true)
	private String quanXianName;

	@Lie(comment = "插入时间")
	private Date insertTimeForHis;

	@Lie(comment = "更新时间")
	private Date operateTimeForHis;

}
