package cn.ms.device.schema;

import java.util.Date;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;
import lombok.Data;

@Data
@TableName(comment = "移动设备信息表", name = "MobileDeviceInfo")
public class MobileDeviceInfo {

	@Lie(comment = "主键，使用安卓生成唯一id", isPrimaryKey = true)
	private String id;

	@Lie(comment = "移动设备号，MEID码")
	private String meid;
	
	@Lie(comment = "手机号")
	private String phoneNumber;
	
	@Lie(comment = "SIM卡号")
	private String simNo;
	
	@Lie(comment = "设备名称")
	private String deviceName;
	
	@Lie(comment = "设备型号")
	private String deviceCode;

	@Lie(comment = "安卓版本号")
	private String androidVersion;
	
	@Lie(comment = "经度")
	private String longitude;
	
	@Lie(comment = "纬度")
	private String latitude;
	
	@Lie(comment = "详细地址")
	private String detailAddress;
	
	@Lie(comment = "插入时间")
	private Date insertTimeForHis;

	@Lie(comment = "更新时间")
	private Date operateTimeForHis;

}