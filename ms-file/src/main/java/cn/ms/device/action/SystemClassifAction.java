package cn.ms.device.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import cn.ms.device.schema.MobileSystemInfo;
import cn.ms.device.service.SystemClassifService;
import cn.ms.util.JsonUtil;

/**
 * 
* <p>time： 2019年5月8日 - 下午3:07:12</p>
*  系统分类
* @author xu-zheng
* @version $Revision: 1.0 $
 */
@Controller
@RequestMapping("/systemClassifAction")
public class SystemClassifAction
{
    @Autowired
    private  SystemClassifService  systemClassifService;
    
    /**
     * 
     * {系统分类：查询方法}
     * 
     * @param request
     * @return
     * @author:xu-zheng
     */
    @RequestMapping("/findSystemClassif")
    @ResponseBody
    public  Map<String, Object> findSystemClassif(HttpServletRequest request){
        
        
        
        return systemClassifService.findSystemClassif( request);
    }
    /**
     * 
     * {系统分类：增加方法}
     * 
     * @param request
     * @return
     * @author:xu-zheng
     */
    @RequestMapping("/addSystemClassif")
    @ResponseBody
    public  Map<String, Object> addSystemClassif(MultipartHttpServletRequest request){
        
        return systemClassifService.add(request);
    }
    
    /**
     * 
     * {系统分类：更新方法}
     * 
     * @param request
     * @return
     * @author:xu-zheng
     */
    @RequestMapping("/upSystemClassif")
    @ResponseBody
    public  Map<String, Object> upSystemClassif(HttpServletRequest request){
     
        return systemClassifService.update(request);
    }
    /**
     * 
     * {系统分类：删除方法}
     * 
     * @param request
     * @return
     * @author:xu-zheng
     */
    @RequestMapping("/delSystemClassif")
    @ResponseBody
    public  Map<String, Object> delSystemClassif(HttpServletRequest request){
        
        String systemCode=request.getParameter("systemCode");
        
        
        return systemClassifService.del(systemCode);
    }


}
