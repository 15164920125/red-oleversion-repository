package cn.ms.device.schema;

import java.util.Date;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "移动设备子信息表", name = "MobileDeviceChildInfo")
public class MobileDeviceChildInfo {

	@Lie(comment = "主键", isPrimaryKey = true)
	private String id;

	@Lie(comment = "移动设备信息表id")
	private String mainId;
	
	@Lie(comment = "子信息类型：APK_YDCK.移动查勘 APK_YDXP.移动协赔 APK_IM.IM APK_XLC.修理厂 APK_YDSP.移动视频 APK_YDMH.移动门户")
	private String infoType;
	
	@Lie(comment = "下载版本")
	private String downVersion;
	
	@Lie(comment = "安装版本")
	private String installVersion;
	
	@Lie(comment = "插入时间")
	private Date insertTimeForHis;

	@Lie(comment = "更新时间")
	private Date operateTimeForHis;

}