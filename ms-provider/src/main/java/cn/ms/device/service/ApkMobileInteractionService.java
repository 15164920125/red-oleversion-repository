package cn.ms.device.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ms.device.action.ApkMobileInteractionAction;
import cn.ms.device.mapper.ApkInfoMapper;
import cn.ms.device.mapper.MobileDeviceChildInfoMapper;
import cn.ms.device.mapper.MobileDeviceInfoMapper;
import cn.ms.device.schema.ApkInfo;
import cn.ms.device.schema.MobileDeviceChildInfo;
import cn.ms.device.schema.MobileDeviceInfo;
import cn.ms.util.JsonUtil;

@Transactional
@Service("apkMobileInteractionService")
public class ApkMobileInteractionService
{
    @Autowired
    private MobileDeviceChildInfoMapper mobileDeviceChildInfoMapper;
    
    @Autowired
    private MobileDeviceInfoMapper mobileDeviceInfoMapper;
    
    @Autowired
    private ApkInfoMapper  apkInfoMapper;
    
    private Logger logger = LoggerFactory.getLogger(ApkMobileInteractionAction.class);

    
    /**
     * 
     * {移动设备获取APK}
     * 
     * @param sysCode 系统编码
     * @return
     * @author:xu-zheng
     */
    public Map<String, Object> findApkInfo(String  systemCode){
        try
        {
            //查询最新的版本集合
            List<ApkInfo> apks = apkInfoMapper.selectLatest();
            if(apks.isEmpty()){
                return JsonUtil.returnMsg("未能获取到相关apk信息", null, JsonUtil.ERROR_INEXISTENCE, logger);

            }
            ApkInfo apkInfo = null;
            //与syscode 进行匹配 返回需要的对象
            for (ApkInfo apk : apks)
            {
                if (systemCode.equals(apk.getSysCode()))
                {
                    apkInfo = apk;
                    break;
                }
            } 
            //判断是否可以获取到需要的apk
            if(apkInfo !=null){               
                return JsonUtil.returnMsg(JsonUtil.SUCCESS_MESSAGE, apkInfo, JsonUtil.SUCCESS, logger);

            }else{                
                return JsonUtil.returnMsg("未能获取到相关apk信息", null, JsonUtil.ERROR_INEXISTENCE, logger);
            }
        }
        catch (Exception e)
        {
            logger.error("获取apk接口错误！，错误信息：", e);
            return JsonUtil.returnMsg("移动端下载回写失败，错误信息：" + e.getMessage(), null, JsonUtil.SUCCESS, logger);

        }
     }
    
    
    
    
    /**
     * 
     * {移动端下载回写}
     * 
     * @param mainId  安卓唯一id号
     * @param downVersion  下载版本
     * @param installVersion   安装版本
     * @param infoType  系统类型
     * @return
     * @author:xu-zheng
     */
    public Map<String, Object> mobileDownInfo(String mainId,String downVersion, String installVersion, String infoType){
        try
        {
            //先根据安卓唯一id 查询移动设备信息表
            MobileDeviceInfo deviceInfo=mobileDeviceInfoMapper.selectById(mainId);
            if(deviceInfo ==null){
                logger.error("根据安卓唯一id 未能获取移动设备信息!");
                return JsonUtil.returnMsg("根据安卓唯一id 未能获取移动设备信息!", null, JsonUtil.SUCCESS, logger);              
            }
            
            Map<String, Object> params = new HashMap<>();
            params.put("mainId", mainId);
            params.put("infoType", infoType);
            //根据条件查询是否有符合条件的数据 (每个 mainid ,infotype 应只用一个对应的数据)
            List<MobileDeviceChildInfo> childs = mobileDeviceChildInfoMapper
                    .selectListByParam(MobileDeviceChildInfo.class, params);
            MobileDeviceChildInfo childInfo = new MobileDeviceChildInfo();
            //为空则增加  否则更新
            if (childs.isEmpty())
            {
                childInfo.setMainId(mainId);
                childInfo.setInfoType(infoType);
                childInfo.setInsertTimeForHis(new Date());
                childInfo.setOperateTimeForHis(new Date());
                childInfo.setDownVersion(downVersion);
                childInfo.setInstallVersion(installVersion);
                //新增
                mobileDeviceChildInfoMapper.insertExt(childInfo);
            }
            else
            {
                childInfo = childs.get(0);
                childInfo.setOperateTimeForHis(new Date());
                childInfo.setDownVersion(downVersion);
                childInfo.setInstallVersion(installVersion);
                //更新
                mobileDeviceChildInfoMapper.update(childInfo, "id");
            }
            return JsonUtil.returnMsg(JsonUtil.SUCCESS_MESSAGE, null, JsonUtil.SUCCESS, logger);

        }
        catch (Exception e)
        {
            logger.error("移动端下载回写失败，错误信息：", e);
            return JsonUtil.returnMsg("移动端下载回写失败，错误信息：" + e.getMessage(), null, JsonUtil.ERROR, logger);
        }
    }

}
