package cn.ms.device.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.ms.util.RedisPoolUtils;
import redis.clients.jedis.Jedis;

/**
 * 轮询
 * @author ASUS
 *
 */
@RestController
@RequestMapping("/apkInfoPolling")
public class ApkPolling {
	
	private Logger log = LoggerFactory.getLogger(ApkPolling.class); 
	
	/**
	 * 轮询接口
	 * @return
	 */
//	@RequestMapping("")
//	public Map<String, Object> getPollingInfo(HttpServletRequest request){
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		String key = "";
//		Jedis jedis = RedisPoolUtils.getJedis();
//		try {
//			if(!jedis.exists(key)){
//				log.info("未查询到...为"+key+"信息!");
//				return null;
//			}
//			String json = jedis.get(key);
//			JSONObject resultJson = JSONObject.parseObject(json);
//			resultMap.put(key, resultJson);
//		} catch (Exception e) {
//			log.info("未查询到...为"+key+"信息!!!");
//		}finally {
//			RedisPoolUtils.returnRes(jedis);
//		}
//		return resultMap;
//	}

}
