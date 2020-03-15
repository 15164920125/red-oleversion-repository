package cn.ms.common.schema;

import java.util.Date;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "APK文件信息表", name = "GyApkInfo")
public class GyApkInfo {

	@Lie(comment = "主键", isPrimaryKey = true)
	private String id;

	@Lie(comment = "文件名称")
	private String fileName;
	
	@Lie(comment = "文件路径", length = 1000)
	private String filePath;
	
	@Lie(comment = "版本号",flag=true)
	private Long version;
	
	@Lie(comment = "文件编码")
	private String fileCode;
	
	@Lie(comment = "文件类型")
	private String fileType;

	@Lie(comment = "省机构码数组", length = 4000)
	private String comcodeList;
	
	@Lie(comment = "插入时间")
	private Date insertTimeForHis;

	@Lie(comment = "更新时间")
	private Date operateTimeForHis;

}