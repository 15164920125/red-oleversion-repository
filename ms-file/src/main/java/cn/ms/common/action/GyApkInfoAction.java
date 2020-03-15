package cn.ms.common.action;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.ms.common.schema.GyApkInfo;
import cn.ms.sys.exception.BusinessException;
import cn.ms.util.HttpUtil;
import cn.ms.util.JsonUtil;

@RestController
@RequestMapping("/gyApkInfoAction")
public class GyApkInfoAction {
	private static final Logger log = LoggerFactory.getLogger(GyApkInfoAction.class);



	@RequestMapping(value = "/save")
	public Map<String, Object> save(HttpServletRequest request) {
		try {
			GyApkInfo paramToVo = HttpUtil.getParamToVo(request, GyApkInfo.class);
			// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 检查form中是否有enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
//				CommonsMultipartFile file123 = (CommonsMultipartFile) request;
//				
//				System.out.println(file123.getOriginalFilename());
				
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					String key = iter.next().toString();
					// 一次遍历所有文件
					MultipartFile mfile = multiRequest.getFile(key);
					if (mfile != null) {
						String fileName = mfile.getOriginalFilename();
						
						log.info("文件大小=" + mfile.getSize());
						String path = "/home" + "/" +fileName;
						File file = new File(path);
						if (file.exists()) {
							file.delete();
						}
						FileUtils.copyInputStreamToFile(mfile.getInputStream(), file);
						log.info("文件上传成功=" + path);
					}

				}

			}
			

			return JsonUtil.returnMsg("保存成功", null, 200, log);
		} catch (Exception e) {
			String errorMsg = "保存失败";
			throw new BusinessException(errorMsg,e);
		}
	}

}