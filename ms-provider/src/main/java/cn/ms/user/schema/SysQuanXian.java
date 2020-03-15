package cn.ms.user.schema;

import java.util.Date;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "权限表")
public class SysQuanXian {
	@Lie(comment = "主键id", isPrimaryKey = true)
	private Long id;

	@Lie(comment = "权限编码",flag=true)
	private String quanXianCode;
	
	@Lie(comment = "权限名称")
	private String quanXianName;
	
	@Lie(comment = "权限描述")
	private String description;
	
	@Lie(comment = "权限类型编码")
	private String typeCode;
	
	@Lie(comment = "权限类型名称")
	private String typeName;
	
	@Lie(comment = "插入时间")
	private Date insertTimeForHis;

	@Lie(comment = "更新时间")
	private Date operateTimeForHis;
}
