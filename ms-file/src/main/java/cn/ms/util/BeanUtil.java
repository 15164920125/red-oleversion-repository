package cn.ms.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;


//import org.springframework.beans.BeanUtils;



@SuppressWarnings("rawtypes")
public class BeanUtil {
	
	/**
	 * 复制实体类对象
	 * 将orig对象,复制到dest对象
	 * BeanUtils是org.springframework.beans.BeanUtils包里面的,支持date类型
	 *          是org.apache.commons.beanutils.BeanUtils包里面的不支持date类型,String类型转date类型会报错
	 */
	public static Object copyProperties(Object dest,Object orig) {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return dest;
	}
	
	/**
	 * 将map转成实体类对象
	 * @param bean
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object populate(Object bean,Map map) {
		try {
			BeanUtils.populate(bean, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return bean;
	}

}
