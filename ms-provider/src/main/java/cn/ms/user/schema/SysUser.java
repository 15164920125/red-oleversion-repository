package cn.ms.user.schema;

import java.io.Serializable;
import java.util.Date;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "用户表")
public class SysUser implements Serializable {

	@Lie(comment = "序列化",isExclude=true)
	private static final long serialVersionUID = -7651034549907385802L;

	@Lie(comment = "主键", isPrimaryKey = true)
	private Long id;

	@Lie(comment = "帐号", flag = true)
	private String userNo;

	@Lie(comment = "密码")
	private String pwd;

	@Lie(comment = "token", flag = true)
	private String token;

	@Lie(comment = "姓名")
	private String userName;

	@Lie(comment = "手机号", length = 15)
	private String phoneNum;

	@Lie(comment = "邮箱")
	private String email;

	@Lie(comment = "省级机构名")
	private String comname;

	@Lie(comment = "省级机构码")
	private String comcode;

	@Lie(comment = "账号状态:1启用 0禁用")
	private String status;
	
	@Lie(comment = "角色编码", flag = true)
	private String roleCode;
	
	@Lie(comment = "角色名称")
	private String roleName;
	
	@Lie(comment = "插入时间")
	private Date insertTimeForHis;

	@Lie(comment = "更新时间")
	private Date operateTimeForHis;

}