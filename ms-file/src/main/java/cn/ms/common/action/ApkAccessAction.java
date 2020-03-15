package cn.ms.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.ms.sys.exception.BusinessException;
import cn.ms.util.JsonUtil;

@Controller
@RequestMapping("/apk")
public class ApkAccessAction  {

    private Logger logger = LoggerFactory.getLogger(ApkAccessAction.class);

    /**
     * 移动设备图片获取
     * @param request
     * @param response
     * @author sundonghao
     */
	@RequestMapping("/downLoadPhoto")
	@ResponseBody
	public void picDownload(HttpServletRequest request, HttpServletResponse response) {
		 String filePath = request.getParameter("filePath");
		 ServletOutputStream out = null;
		 FileInputStream fis=null;
		 File file =null;
		try {
			logger.info("移动设备获取图片请求地址=" + filePath);
			file = new File(filePath);
			fis = new FileInputStream(file);
			
			long length = file.length();
//			response.setContentType("image/jpeg");
			response.setHeader("Content-Type", "image/jpeg");
			response.setContentLength((int)length);
			response.setContentType("image/jpeg"); 
//			response.setContentType("multipart/form-data");
			response.setHeader("Content-Length", String.valueOf(length));
			//附件形式进行处理
//			response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");
			logger.info("移动设备获取图片文件包含:"+length+"字节！");
			byte[] temp = new byte[(int)length];
			fis.read(temp,0,(int)length);
			fis.close();
			
			byte[] data=temp;
			out = response.getOutputStream();
			out.write(data);
			logger.info("响应流结束");
		}catch (FileNotFoundException e){
            throw new BusinessException("找不到文件!",e);
        } catch (Exception e) {
			logger.info("移动设备图片获取失败!"+e);
			throw new BusinessException("文件获取异常!",e);
        }finally{
			try {
				 if(out !=null){
		                try
		                {
		                    out.close();
		                }
		                catch (IOException e)
		                {
		                    e.printStackTrace();
		                }
		            }

		            if(fis !=null){
		                try
		                {
		                    fis.close();
		                }
		                catch (IOException e)
		                {
		                    e.printStackTrace();
		                }
		            }
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException("io异常",e);
			}
			
		}
	}
    
    
    
    
	/**
	 * 
	 * {下载apk}
	 * 
	 * @param request
	 * @param response
	 * @author:xu-zheng
	 */
	@RequestMapping("/downApk")
	public void  downApk(HttpServletRequest request,HttpServletResponse response){
	    String path = request.getParameter("path");

/*	    if(!StringUtils.isEmpty(path)){
	        //linu 和windows 不同需要转换下
	        path.replaceAll("\\\\", File.separator);
	    }	*/    
        logger.info("path:"+path);
	    File file = new File(path);
	    FileInputStream inputStream = null;
	    ServletOutputStream out=null;

	    try
        {
            inputStream=new FileInputStream(file);
            out=response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");
            //如果是本地文件可以直接使用inputStream.available() 获取字节长度
            /*                  
            byte[] b = new byte[inputStream.available()];
            byte[] b = FileUtils.readFileToByteArray(file);*/
            //文件大小
            response.setHeader("Content-Length", inputStream.available() + "");
            logger.info("文件流大小:"+inputStream.available());
            byte[]  bytes= new byte[1024];
            int len;
            while( (len=inputStream.read(bytes))!=-1){
                //写入
                out.write(bytes,0,len);
            }            
        }
        catch (FileNotFoundException e)
        {
            logger.error("找不到文件:"+e.getMessage());
            throw new BusinessException("找不到文件",e);
        }
        catch (IOException e)
        {
            logger.error("io异常:"+e.getMessage());
            throw new BusinessException("io异常",e);
        }finally {
            //先关闭输出流  在关闭 输入
            if(out !=null){
                try
                {
                    out.flush();
                    out.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if(inputStream !=null){
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            logger.error("下载结束");
        }
	    
		
	}
	
	@RequestMapping("/upload")
	@ResponseBody
	public Map<String, Object>  upload(MultipartHttpServletRequest request){
	    Iterator<String> iter = request.getFileNames();
	    Map<String, Object> ret= new HashMap<>();
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
                path="D:"+File.separator+"upload"+File.separator+fileName;
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }
                try
                {
                    ret.put("size",size );
                    ret.put("path",path );
                    ret.put("fileName",fileName );
                    FileUtils.copyInputStreamToFile(mfile.getInputStream(), file);
                    return JsonUtil.returnMsg("文件上传成功：", ret, JsonUtil.SUCCESS, logger);

                }
                catch (IOException e)
                {
                    return JsonUtil.returnMsg("文件上传失败：" + e.getMessage(), null, JsonUtil.ERROR_INEXISTENCE, logger);

                }
             }
           
        }
        return  null;
    }


}
