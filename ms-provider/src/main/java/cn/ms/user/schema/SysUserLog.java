package cn.ms.user.schema;

import java.util.Date;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "用户操作记录表")
public class SysUserLog {
	@Lie(comment = "主键", isPrimaryKey = true)
	private Long id;
	
	@Lie(comment = "帐号", flag = true)
	private String userNo;
	
	@Lie(comment = "姓名")
	private String userName;
	
	@Lie(comment = "操作名称")
	private String operationName;
	
	@Lie(comment = "操作代码")
	private String operationCode;
	
	
	@Lie(comment = "插入时间")
	private Date insertTimeForHis;

	@Lie(comment = "更新时间")
	private Date operateTimeForHis;
}
