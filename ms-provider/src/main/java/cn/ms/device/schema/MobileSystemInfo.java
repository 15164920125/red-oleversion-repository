package cn.ms.device.schema;

import java.util.Date;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "移动系统信息表", name = "MobileSystemInfo")
public class MobileSystemInfo {

	@Lie(comment = "主键，系统编码", isPrimaryKey = true)
	private String systemCode;

	@Lie(comment = "系统名称")
	private String systemName;
	
	@Lie(comment = "系统图标名称")
	private String systemIconName;
	
	@Lie(comment = "系统图标服务器路径")
	private String systemIconUrl;
	
	@Lie(comment = "系统图标大小")
	private String systemIconSize;
	
	@Lie(comment = "系统图标更换时间")
	private Date systemIconUpdateTime;

	@Lie(comment = "插入时间")
	private Date insertTimeForHis;

	@Lie(comment = "更新时间")
	private Date operateTimeForHis;

}