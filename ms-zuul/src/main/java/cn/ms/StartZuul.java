package cn.ms;

import java.io.File;
import java.io.IOException;

import javax.servlet.MultipartConfigElement;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import cn.ms.filter.PostZuulFilter;
import cn.ms.filter.PreZuulFilter;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
// 扫描mapper
@MapperScan({ "cn.ms.*.mapper" })
public class StartZuul {
	private static final Logger logger = LoggerFactory.getLogger(StartZuul.class);

	public static void main(String[] args) {
		SpringApplication.run(StartZuul.class, args);
		logger.info("ms-zuul启动成功");
	}

	/**
	 * 前后端分离,跨域设置
	 */
	@Bean
	public CorsFilter corsFilter() {
		logger.info("加载corsFilter过滤器");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*"); // 允许任何域名使用
		config.addAllowedHeader("*"); // 允许任何头
		config.addAllowedMethod("*"); // 允许任何方法（post、get等）
		// 保证ajax请求的所有接口sessionId一致：ajax需要配置xhrFields: {withCredentials: true},
		config.setAllowCredentials(true);
		config.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", config); // 对所有接口配置跨域设置
		return new CorsFilter(source);
	}

	@Bean
	public PreZuulFilter accessFilter() {
		logger.info("加载PreZuulFilter过滤器");
		return new PreZuulFilter();
	}

	@Bean
	public PostZuulFilter postZuulFilter() {
		logger.info("加载PostZuulFilter过滤器");
		return new PostZuulFilter();
	}

	// 微服务调用微服务-负载均衡
	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 设置文件大小限制 ;
		factory.setMaxFileSize("200MB"); // KB,MB
		// 设置参数-数据总大小
		factory.setMaxRequestSize("1000MB");
		return factory.createMultipartConfig();
	}

	// 添加https
	@Value("${https.port}")
	private Integer port;
	@Value("${https.ssl.key-store-password}")
	private String key_store_password;
	@Value("${https.ssl.key-store}")
	private String key_store;

	@Bean
	public ServletWebServerFactory servletContainer() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
		try {
			String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
			String filePpath = null;
			File file = null;
			if (path.lastIndexOf(".jar") != -1) {
				file = new File(path);
				filePpath = file.getParent() + "/config/" + key_store;
			} else {
				file = new ClassPathResource(key_store).getFile();
				filePpath =file.getAbsolutePath();
			}
			logger.info("读取https证书" + filePpath);
			protocol.setKeystoreFile(filePpath);
			protocol.setKeystorePass(key_store_password);
			protocol.setSSLEnabled(true);
			connector.setSecure(true);
			connector.setScheme("https");
			connector.setPort(port);
		} catch (IOException ex) {
			throw new IllegalStateException("读取https证书失败", ex);
		}
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addAdditionalTomcatConnectors(connector);
		return tomcat;
	}
}