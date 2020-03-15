package cn.ms.device.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.ms.device.action.ApkInfoAction;
import cn.ms.device.mapper.ApkChildInfoMapper;
import cn.ms.device.mapper.ApkInfoMapper;
import cn.ms.device.mapper.MobileSystemInfoMapper;
import cn.ms.device.schema.ApkChildInfo;
import cn.ms.device.schema.ApkInfo;
import cn.ms.device.schema.MobileSystemInfo;
import cn.ms.sys.exception.BusinessException;
import cn.ms.util.HttpUtil;
import cn.ms.util.JsonUtil;
import cn.ms.util.RedisPoolUtils;
import redis.clients.jedis.Jedis;

@Service
@Transactional
public class ApkInfoService{

	private Logger log = LoggerFactory.getLogger(ApkInfoAction.class);

	@Autowired
	private ApkInfoMapper apkInfoMapper;
	
	@Autowired
	private ApkChildInfoMapper apkChildInfoMapper;
	
	@Autowired
	private MobileSystemInfoMapper mobileSystemInfoMapper;

	/**
	 * 查询接口
	 */
	public Map<String, Object> selectApkList(HttpServletRequest request) {
		try {
			String SysRoleQuanXian = request.getParameter("SysRoleQuanXian");
			JSONArray JurisdictionArray = JSONArray.parseArray(SysRoleQuanXian);
			if(JurisdictionArray==null){
				log.info("权限集合为空，返回空！");
				throw new BusinessException("权限集合为空，返回空！");
			}
			//未发布
			List<ApkInfo> saveList = new ArrayList<ApkInfo>();
			for(int i=0; i<JurisdictionArray.size(); i++){
				JSONObject obj = (JSONObject) JurisdictionArray.get(i);
				String quanXianCode = obj.getString("quanXianCode");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("sysCode", quanXianCode);
				param.put("publishFlag", "1");
				List<ApkInfo> apkList = apkInfoMapper.selectListByParam(ApkInfo.class, param);
				if(apkList!=null && apkList.size()>0){
					saveList.addAll(apkList);
				}
				Collections.sort(saveList, new ApkComparator());
			}
			//生产版本
			List<ApkInfo> publishList = apkInfoMapper.selectLatestPublish();
			//历史版本
			List<ApkInfo> historyList = apkInfoMapper.selectHistoryPublish();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("saveList", saveList);
			resultMap.put("publishList", publishList);
			resultMap.put("historyList", historyList);
			return JsonUtil.returnMsg("成功", resultMap, 200, log);
		} catch (Exception e) {
			throw new BusinessException("查询失败", e);
		}
	}
	
	/**
	 * apk版本保存及发布
	 */
	public Map<String, Object> addApkVersion(HttpServletRequest request){
		try {
			String path = "";
			ApkInfo oldApkInfo = null;
			ApkInfo paramToVo = HttpUtil.getParamToVo(request, ApkInfo.class);
			List<ApkChildInfo> returnChildList = new ArrayList<ApkChildInfo>();
			if(StringUtils.isNotEmpty(paramToVo.getId())){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("id", paramToVo.getId());
				oldApkInfo = apkInfoMapper.selectByParam(ApkInfo.class, param);
				if(oldApkInfo == null){
					log.error("未找到id为" + paramToVo.getId() + "的apk信息，请新增对应版本！");
					throw new BusinessException("未找到id为" + paramToVo.getId() + "的apk信息，请新增对应版本！");
				}
				if("2".equals(oldApkInfo.getPublishFlag())){
					log.error("该版本已经发布，不允许修改！");
					throw new BusinessException("该版本已经发布，不允许修改！");
				}
				Map<String, Object> childParam = new HashMap<String, Object>();
				childParam.put("mainId", paramToVo.getId());
				List<ApkChildInfo> childOldList = apkChildInfoMapper.selectListByParam(ApkChildInfo.class, childParam);
				if(childOldList!=null && childOldList.size()>0){
					for(ApkChildInfo child : childOldList){
						File imgFile = new File(child.getImgUrl());
						if (imgFile.exists()) {
							imgFile.delete();
						}
						apkChildInfoMapper.deleteById(child.getId());
					}
				}
			}
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					String key = iter.next().toString();
					// 一次遍历所有文件
					MultipartFile mfile = multiRequest.getFile(key);
					if (mfile != null) {
						String fileName = mfile.getOriginalFilename();
						//删除更新文件
						if(StringUtils.isNotEmpty(paramToVo.getId())){
							if(fileName.endsWith(".apk")){
								File apkFile = new File(oldApkInfo.getApkUrl());
								if (apkFile.exists()) {
									apkFile.delete();
								}
								paramToVo.setUploadTime(new Date());
							}
						}
						path = "/home" + "/" + paramToVo.getSysCode() + "/" + paramToVo.getApkVersion() + "/" + fileName;
						File file = new File(path);
						if (file.exists()) {
							file.delete();
						}
						FileUtils.copyInputStreamToFile(mfile.getInputStream(), file);
						log.info("文件上传成功=" + path);
						BigDecimal fileSize = BigDecimal.valueOf(mfile.getSize());
						fileSize = fileSize.divide(new BigDecimal(1024L * 1024L), 2, BigDecimal.ROUND_HALF_UP);
						log.info("文件大小=" + fileSize.toString()+"M");
						if(!fileName.endsWith(".apk")){
							ApkChildInfo child = new ApkChildInfo();
							child.setId(UUID.randomUUID().toString().replace("-", ""));
							child.setMainId(paramToVo.getId());
							child.setImgName(fileName);
							child.setImgSize(fileSize.toString()+"M");
							child.setImgUrl(path);
							apkChildInfoMapper.insert(child);
							returnChildList.add(child);
						}
						if(fileName.endsWith(".apk")){
							paramToVo.setApkUrl(path);
						}
					}
				}
			}
			if(StringUtils.isNotEmpty(paramToVo.getId())){
				BeanUtils.copyProperties(paramToVo, oldApkInfo);
				oldApkInfo.setOperateTimeForHis(new Date());
				apkInfoMapper.update(oldApkInfo, "id");
			}else{
				paramToVo.setId(UUID.randomUUID().toString().replace("-", ""));
				apkInfoMapper.insert(paramToVo);
			}
			if("2".equals(paramToVo.getPublishFlag())){
				List<ApkInfo> newList = apkInfoMapper.selectLatestPublish();
				for(ApkInfo apk : newList){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("mainId", apk.getId());
					List<ApkChildInfo> apkChildList = apkChildInfoMapper.selectListByParam(ApkChildInfo.class, param);
					apk.setChildList(apkChildList);
					Map<String, Object> param1 = new HashMap<String, Object>();
					param1.put("systemCode", apk.getSysCode());
					MobileSystemInfo sysInfo = mobileSystemInfoMapper.selectByParam(MobileSystemInfo.class, param1);
					if(sysInfo!=null){
						apk.setIconUrl(sysInfo.getSystemIconUrl());
					}
				}
				Jedis jedis = RedisPoolUtils.getJedis();
				jedis.set("newestApkInfo", JSON.toJSONString(newList, SerializerFeature.WriteMapNullValue,
						SerializerFeature.QuoteFieldNames, SerializerFeature.WriteNullStringAsEmpty,
						SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteDateUseDateFormat));
				RedisPoolUtils.returnRes(jedis);
			}
			paramToVo.setChildList(returnChildList);
			return JsonUtil.returnMsg("成功", paramToVo, 200, log);
		} catch (Exception e) {
			log.error("更新/新增APK版本信息异常：" + e.getMessage(), e);
			throw new BusinessException("更新/新增APK版本信息异常：" + e.getMessage(), e);
		}
	}
	
	/**
	 * 新apk初始化
	 */
	public Map<String, Object> initNewApkInfo(HttpServletRequest request){
		//查询所有系统
        List<MobileSystemInfo> systemInfos=mobileSystemInfoMapper.selectListByParam(MobileSystemInfo.class, new HashMap<>());
		return JsonUtil.returnMsg("成功", systemInfos, 200, log);
	}
	
	/**
	 * 已保存apk初始化
	 */
	public Map<String, Object> initOldApkInfo(HttpServletRequest request){
		String id = request.getParameter("id");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", request.getParameter("id"));
		ApkInfo apkInfo = apkInfoMapper.selectByParam(ApkInfo.class, param);
		if(apkInfo==null){
			log.error("未找到id为" + id + "的apk信息，请新增对应版本！");
			throw new BusinessException("未找到id为" + id + "的apk信息，请新增对应版本！");
		}
		if("2".equals(apkInfo.getPublishFlag())){
			log.error("该版本已经发布，不允许修改！");
			throw new BusinessException("该版本已经发布，不允许修改！");
		}
		Map<String, Object> param1 = new HashMap<String, Object>();
		param1.put("mainId", id);
		List<ApkChildInfo> childList = apkChildInfoMapper.selectListByParam(ApkChildInfo.class, param1);
		apkInfo.setChildList(childList);
		List<MobileSystemInfo> systemInfos=mobileSystemInfoMapper.selectListByParam(MobileSystemInfo.class, new HashMap<>());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("apkInfo", apkInfo);
		result.put("systemList", systemInfos);
		return JsonUtil.returnMsg("成功", result, 200, log);
	}
	
	/**
	 * 删除保存的apk信息
	 */
	public Map<String, Object> deleteNoPublishInfo(HttpServletRequest request){
		String id = request.getParameter("id");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", request.getParameter("id"));
		ApkInfo apkInfo = apkInfoMapper.selectByParam(ApkInfo.class, param);
		if(apkInfo==null){
			log.error("未找到id为" + id + "的apk信息，请新增对应版本！");
			throw new BusinessException("未找到id为" + id + "的apk信息，请新增对应版本！");
		}
		if("2".equals(apkInfo.getPublishFlag())){
			log.error("该版本已经发布，不允许修改！");
			throw new BusinessException("该版本已经发布，不允许修改！");
		}
		File apkFile = new File(apkInfo.getApkUrl());
		if (apkFile.exists()) {
			apkFile.delete();
		}
		Map<String, Object> param1 = new HashMap<String, Object>();
		param1.put("mainId", id);
		List<ApkChildInfo> childList = apkChildInfoMapper.selectListByParam(ApkChildInfo.class, param1);
		if(childList!=null && childList.size()>0){
			for(ApkChildInfo child : childList){
				File childFile = new File(child.getImgUrl());
				if (childFile.exists()) {
					childFile.delete();
				}
				apkChildInfoMapper.deleteById(child.getId());
			}
		}
		apkInfoMapper.deleteById(id);
		return JsonUtil.returnMsg("成功", null, 200, log);
	}
	
	/**
	 * ApkInfo排序
	 */
	class ApkComparator implements Comparator<ApkInfo>{
		@Override
		public int compare(ApkInfo o1, ApkInfo o2) {
			if (o1 == null) {
				return -1;
			}
			if (o2 == null) {
				return -1;
			}
			return o1.getOperateTimeForHis().compareTo(o2.getOperateTimeForHis());
		}
		
	}
}
