package cn.ms.device.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ms.device.action.SystemClassifAction;
import cn.ms.device.mapper.ApkInfoMapper;
import cn.ms.device.mapper.MobileSystemInfoMapper;
import cn.ms.device.schema.MobileSystemInfo;
import cn.ms.util.JsonUtil;
import cn.ms.util.StringUtil;

/**
 * 
* <p>time： 2019年5月8日 - 下午3:16:34</p>
*
* @author xu-zheng
* @version $Revision: 1.0 $
 */

@Service("systemClassifService")
public class SystemClassifService
{
    @Autowired
    private  ApkInfoMapper apkInfoMapper;
    
    @Autowired
    private  MobileSystemInfoMapper  mobileSystemInfoMapper;
    
    private Logger logger = LoggerFactory.getLogger(SystemClassifAction.class);

    
    /**
     * 
     * {系统分类查询方法}
     * 
     * @return
     * @author:xu-zheng
     */
    public  Map<String, Object> findSystemClassif(HttpServletRequest request){
        
        try
        {
            int length = 0;// 每页的条数,如果为0就是全查
            int pageNum = 1;// 第几页
            String lengthStr = request.getParameter("length");
            if (StringUtil.isNotEmpty(lengthStr)) {
                length = Integer.valueOf(lengthStr);
            }
            String start = request.getParameter("start");
            if (StringUtil.isNotEmpty(start)) {
                int temp = Integer.valueOf(start);
                pageNum=temp/length+1;
            }
            
            PageHelper.startPage(pageNum, length);
            //查询所有系统
            List<MobileSystemInfo> systemInfos=mobileSystemInfoMapper.selectListByParamExt(MobileSystemInfo.class, null);
            PageInfo<MobileSystemInfo> page = new PageInfo<>(systemInfos);

            System.out.println("总条数：" + page.getTotal());
            System.out.println("总页数：" + page.getPages());

            return JsonUtil.returnDataTable("成功", systemInfos, 200, page.getTotal(), logger);
           // return JsonUtil.returnMsg(JsonUtil.SUCCESS_MESSAGE, systemInfos, JsonUtil.SUCCESS, logger);
        }
        catch (Exception e)
        {
            logger.error("系统分类查询错误！，错误信息：", e);
            return JsonUtil.returnMsg("系统分类查询失败，错误信息：" + e.getMessage(), null, JsonUtil.SUCCESS, logger);
         }
    }
    /**
     * 
     * {系统分类删除方法}
     * 
     * @return
     * @author:xu-zheng
     */
    public  Map<String, Object> del(String systemCode){
        
        Map<String, Object> params = new HashMap<>();
        params.put("systemCode", systemCode);
        boolean validate=this.exitData(params);
        if(!validate){
            return JsonUtil.returnMsg("删除失败,数据不存在或已被删除！", null, JsonUtil.ERROR_CHECK, logger);
        } 
        
        try
        {
            //删除
            int delete=mobileSystemInfoMapper.deleteBySystemCode(systemCode);
            if(delete>0){
                return JsonUtil.returnMsg("删除成功！", null, JsonUtil.SUCCESS, logger);

            }else{
                return JsonUtil.returnMsg("删除失败！", null, JsonUtil.SUCCESS, logger);

            }
        }
        catch (Exception e)
        {
            logger.error("系统分类删除错误！，错误信息：", e);
            return JsonUtil.returnMsg("系统分类删除失败，错误信息：" + e.getMessage(), null, JsonUtil.SUCCESS, logger);
         }
    }
    /**
     * 
     * {系统分类更新方法}
     * 
     * @return
     * @author:xu-zheng
     */
    public  Map<String, Object> update(MultipartHttpServletRequest request){
        String iconUp=request.getParameter("iconUp");
        MobileSystemInfo mobileSystemInfo = new MobileSystemInfo();
        //判断系统图标是否更新过  跟新进入以下方法
        if(!StringUtils.isEmpty(iconUp)){
            mobileSystemInfo=this.upload(request);
        }
        
        mobileSystemInfo.setSystemCode(request.getParameter("systemCode"));
        mobileSystemInfo.setSystemIconName(request.getParameter("systemName"));
        mobileSystemInfo.setOperateTimeForHis(new Date());
        try
        {
            //更新
            int update=mobileSystemInfoMapper.update(mobileSystemInfo, "systemCode");
            if(update>0){
                return JsonUtil.returnMsg("更新成功！", null, JsonUtil.SUCCESS, logger);

            }else{
                return JsonUtil.returnMsg("更新失败！", null, JsonUtil.SUCCESS, logger);

            }
        }
        catch (Exception e)
        {
            logger.error("系统分类更新错误！，错误信息：", e);
            return JsonUtil.returnMsg("系统分类更新失败，错误信息：" + e.getMessage(), null, JsonUtil.SUCCESS, logger);
         }
    }
    /**
     * 
     * {系统分类添加方法}
     * 
     * @return
     * @author:xu-zheng
     */
    public  Map<String, Object> add(MultipartHttpServletRequest request){
        
        MobileSystemInfo mobileSystemInfo=this.upload(request);
        mobileSystemInfo.setInsertTimeForHis(new Date());
        mobileSystemInfo.setSystemCode(request.getParameter("systemCode"));
        mobileSystemInfo.setSystemName(request.getParameter("systemName"));
        mobileSystemInfo.setSystemIconName(request.getParameter("systemName")+"图标");
        mobileSystemInfo.setOperateTimeForHis(new Date());
        Map<String, Object> params = new HashMap<>();
        boolean validate=true;    
        //查询是否有相同的systemCode系统编码
        String systemCode =mobileSystemInfo.getSystemCode();
        params.put("systemCode", systemCode);
        validate=this.exitData(params);
        if(validate){
            return JsonUtil.returnMsg("添加失败,已存在相同名称的系统编码！", null, JsonUtil.ERROR_CHECK, logger);
        }        
        //查询是否存在相同名字的系统
        String systemName =mobileSystemInfo.getSystemName();
        params.clear();
        params.put("systemName", systemName);
        validate=this.exitData(params);
        if(validate){
            return JsonUtil.returnMsg("添加失败,已存在相同名称的系统名称！", null, JsonUtil.ERROR_CHECK, logger);
        } 
        try
        {
            //添加
            int save=mobileSystemInfoMapper.insert(mobileSystemInfo);
            if(save>0){
                return JsonUtil.returnMsg("添加成功！", null, JsonUtil.SUCCESS, logger);

            }else{
                return JsonUtil.returnMsg("添加失败！", null, JsonUtil.ERROR_CHECK, logger);

            }
        }
        catch (Exception e)
        {
            logger.error("系统分类添加错误！，错误信息：", e);
            return JsonUtil.returnMsg("系统分类添加失败，错误信息：" + e.getMessage(), null, JsonUtil.SUCCESS, logger);
         }
    }
    
    /**
     * 
     * 查询是否存在符合条件的数据   }
     * 
     * @return  没有对应的接口则返回false
     * @author:xu-zheng
     */
    public  boolean  exitData(Map<String, Object> param){
        
        MobileSystemInfo info =mobileSystemInfoMapper.selectByParam(MobileSystemInfo.class, param);
        
        //如果结果为null 返回false
        if(info==null){
            return false;
        } 
        return true;
       
    }
    
    /**
     * 
     * {上传系统图片}
     * 
     * @param request
     * @return
     * @author:xu-zheng
     */
    public MobileSystemInfo  upload(MultipartHttpServletRequest request){
        MobileSystemInfo systemInfo = new MobileSystemInfo();
        Iterator<String> iter = request.getFileNames();
         while (iter.hasNext()) {
            String key = iter.next().toString();
            // 一次遍历所有文件
            MultipartFile mfile = request.getFile(key);
            if (mfile != null) {
                String fileName = mfile.getOriginalFilename();
                //文件大小
                long size=mfile.getSize();
                String path = File.separator+"home" + File.separator +"mobliesystemIcon"+File.separator+fileName;
                //本地测试
               // path="D:"+File.separator+"upload"+File.separator+fileName;
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }
                try
                {
  
                    FileUtils.copyInputStreamToFile(mfile.getInputStream(), file);
                    systemInfo.setSystemIconUrl(path);
                    systemInfo.setSystemIconSize(String.valueOf(size));
                    systemInfo.setSystemIconUpdateTime(new Date());
                    
                }
                catch (IOException e)
                {
                    logger.error("上传系统分类照片错误！，错误信息：", e);
                    return  null;
                }
             }
           
        }
        return  systemInfo;
    }

}
