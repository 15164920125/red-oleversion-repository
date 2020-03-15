package cn.ms.device.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.function.annotation.Lie;
import cn.ms.device.service.ApkMobileInteractionService;

/**
 * 
* <p>time： 2019年5月7日 - 下午2:48:07</p>
*
* @author xu-zheng
* @version $Revision: 1.0 $
 */
@RestController
@RequestMapping("/apkMobileInteractionAction")
public class ApkMobileInteractionAction
{
    
    @Autowired
    private  ApkMobileInteractionService apkMobileInteractionService;
      
    /**
     * 
     * {移动端下载回写}
     * 
     * @param request
     * @return
     * @author:xu-zheng
     */
    @RequestMapping("/mobileDownInfo")
    @ResponseBody
    public Map<String, Object> mobileDownInfo(HttpServletRequest request){
        
        String mainId = request.getParameter("id");//安卓生成唯一id 移动设备信息表id
        
        String downVersion =request.getParameter("downVersion");//下载版本
        
        String installVersion =request.getParameter("installVersion");//安装版本
        
        String infoType =request.getParameter("infoType");//子信息类型：APK_YDCK.移动查勘 APK_YDXP.移动协赔 APK_IM.IM APK_XLC.修理厂 APK_YDSP.移动视频 APK_YDMH.移动门户
        
        return apkMobileInteractionService.mobileDownInfo(mainId, downVersion, installVersion, infoType);

    }
    
    /**
     * 
     * {移动设备获取APK}
     * 
     * @param request
     * @return
     * @author:xu-zheng
     */
    @RequestMapping("/findApkInfo")
    @ResponseBody
    public Map<String, Object> findApkInfo(HttpServletRequest request){
        
        String systemCode =request.getParameter("systemCode");//对应系统
        
        return  apkMobileInteractionService.findApkInfo(systemCode);
    }

}
