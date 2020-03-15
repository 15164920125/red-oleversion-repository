package cn.ms.device.schema;

import java.util.Date;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "Apk子信息表", name = "ApkChildInfo")
public class ApkChildInfo {

	@Lie(comment = "主键", isPrimaryKey = true)
	private String id;

	@Lie(comment = "主表id")
	private String mainId;
	
	@Lie(comment = "截图名")
	private String imgName;
	
	@Lie(comment = "服务器路径")
	private String imgUrl;

	@Lie(comment = "截图大小")
	private String imgSize;
	
	@Lie(comment = "上传时间")
	private Date uploadTime;
	
	@Lie(comment = "插入时间")
	private Date insertTimeForHis;

	@Lie(comment = "更新时间")
	private Date operateTimeForHis;

}