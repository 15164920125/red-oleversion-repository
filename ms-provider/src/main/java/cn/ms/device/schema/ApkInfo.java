package cn.ms.device.schema;

import java.util.Date;
import java.util.List;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "Apk信息表", name = "ApkInfo")
public class ApkInfo {

	@Lie(comment = "主键", isPrimaryKey = true)
	private String id;

	@Lie(comment = "对应系统")
	private String sysCode;
	
	@Lie(comment = "包名")
	private String apkName;
	
	@Lie(comment = "服务器路径")
	private String apkUrl;
	
	@Lie(comment = "版本号")
	private String apkVersion;
	
	@Lie(comment = "版本名")
	private String apkVersionName;

	@Lie(comment = "apk大小")
	private String apkSize;
	
	@Lie(comment = "上传时间")
	private Date uploadTime;
	
	@Lie(comment = "应用介绍")
	private String apkDetail;
	
	@Lie(comment = "更新内容")
	private String updateDetail;

	@Lie(comment = "发布标识")
	private String publishFlag;
	
	@Lie(comment = "操作人代码")
	private String userNo;
	
	@Lie(comment = "操作人名称")
	private String userName;
	
	@Lie(comment = "插入时间")
	private Date insertTimeForHis;

	@Lie(comment = "更新时间")
	private Date operateTimeForHis;
	
	@Lie(comment = "子表集合", isExclude = true)
	private List<ApkChildInfo> childList;
	
	@Lie(comment = "APK图标", isExclude = true)
	private String iconUrl;

}