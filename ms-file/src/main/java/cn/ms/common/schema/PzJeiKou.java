package cn.ms.common.schema;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "配置-接口表", name = "Pz_JeiKou")
public class PzJeiKou {
	@Lie(comment = "主键", isPrimaryKey = true)
	private String id;

	@Lie(comment = "接口名称")
	private String name;

	@Lie(comment = "接口编码", flag = true)
	private String key;

	@Lie(comment = "接口地址")
	private String value;

	@Lie(comment = "方法名称(一般webService接口会有此字段)")
	private String method;

	@Lie(comment = "超时时间")
	private long timeout;

	@Lie(comment = "是否有效")
	private int status;
}
