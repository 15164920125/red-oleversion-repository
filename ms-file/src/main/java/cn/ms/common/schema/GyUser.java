package cn.ms.common.schema;

import java.io.Serializable;
import java.util.Date;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "用户表", name = "GY_User")
public class GyUser implements Serializable {

	@Lie(comment = "序列化", isExclude = true)
	private static final long serialVersionUID = 6969430321722269129L;

	@Lie(comment = "主键id", isPrimaryKey = true)
	private Long id;

	@Lie(comment = "帐号", flag = true)
	private String account;

	@Lie(comment = "密码")
	private String pwd;

	@Lie(comment = "姓名")
	private String name;

	@Lie(comment = "姓别")
	private String sex;

	@Lie(comment = "机构名")
	private String comname;

	@Lie(comment = "机构码")
	private String comcode;

	@Lie(comment = "权限")
	private String quanXian;

	@Lie(comment = "是否有效")
	private int status;

	@Lie(comment = "插入时间")
	private Date insertDate;

	@Lie(comment = "更新时间")
	private Date updateDate;

}