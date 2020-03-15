package cn.ms.common.schema;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "配置-开关表", name = "Pz_KaiGuan")
public class PzKaiGuan {
	@Lie(comment = "主键", isPrimaryKey = true)
	private String id;
	
	@Lie(comment = "开关名称")
	private String name;
	
	@Lie(comment = "开关编码", flag = true)
	private String key;

	@Lie(comment = "开关状态:0关、1开")
	private int flag;
	
	@Lie(comment = "扩展字段")
	private String value;
	
	@Lie(comment = "是否有效")
	private int status;
}
