package cn.ms.sys;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * fastjson-消息转换器
 *
 */
@Configuration
public class SpringMessageConverter {

	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		// 解决IE调后台接口提示下载的问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(new MediaType("text", "json", Charset.forName("UTF-8")));
		fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);

		// 2:添加fastJson的配置信息;
		fastJsonHttpMessageConverter.setFastJsonConfig(fastjsonConfig());
		return new HttpMessageConverters(fastJsonHttpMessageConverter);

	}

	/**
	 * fastjson的配置
	 */
	public FastJsonConfig fastjsonConfig() {
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue, // 输出空置字段
				SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
				SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
				SerializerFeature.WriteDateUseDateFormat);// date格式化
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//将为null的字段设置成空字符串
//		ValueFilter valueFilter = new ValueFilter() {
//			public Object process(Object o, String s, Object o1) {
//				if (null == o1) {
//					o1 = "";
//				}
//				return o1;
//			}
//		};
//		fastJsonConfig.setSerializeFilters(valueFilter);
		fastJsonConfig.setCharset(Charset.forName("utf-8"));

		// 解决Long转json精度丢失的问题
		SerializeConfig serializeConfig = SerializeConfig.globalInstance;
		serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
		serializeConfig.put(Long.class, ToStringSerializer.instance);
		serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
		fastJsonConfig.setSerializeConfig(serializeConfig);
		return fastJsonConfig;
	}
}
