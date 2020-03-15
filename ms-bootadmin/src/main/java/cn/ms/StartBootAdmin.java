package cn.ms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAutoConfiguration
@EnableEurekaClient
@EnableAdminServer
public class StartBootAdmin implements WebMvcConfigurer{
	private static final Logger logger = LoggerFactory.getLogger(StartBootAdmin.class);
	
    public static void main(String[] args) {
        SpringApplication.run(StartBootAdmin.class, args);
        logger.info("bootAdmin启动成功");
    }
    
    /**
	 * favorPathExtension:true表示支持后缀匹配，
	 * 属性ignoreAcceptHeader默认为fasle，表示accept-header匹配，defaultContentType开启默认匹配。
	 * 例如：请求aaa.xx，若设置<entry key="xx" value="application/xml"/> 也能匹配以xml返回。
	 * 根据以上条件进行一一匹配最终，得到相关并符合的策略初始化ContentNegotiationManagerc
	 * 
	 * 解决报错：org.springframework.web.HttpMediaTypeNotAcceptableException: Could
	 * not find acceptable representation
	 */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer arg0) {
		logger.info("favorPathExtension设置为false");
		arg0.favorPathExtension(false);
	}
    
}