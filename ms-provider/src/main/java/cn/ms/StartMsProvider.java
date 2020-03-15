package cn.ms;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.github.pagehelper.PageInterceptor;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})//不连数据库
// 接口文档主页 http://localhost:6080/swagger-ui.html
//@EnableSwagger2
@SpringBootApplication
@EnableEurekaClient
// 开启事务
@EnableTransactionManagement
// 扫描mapper
@MapperScan({ "cn.ms.*.mapper" })
public class StartMsProvider {
	private static final Logger logger = LoggerFactory.getLogger(StartMsProvider.class);
	public static void main(String[] args) throws Exception {
		SpringApplication.run(StartMsProvider.class, args);
		logger.info("ms-provider服务启动成功");
	}
	
	// 微服务调用微服务-负载均衡
	@LoadBalanced 
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	// 加载mybatis分页插件
	@Bean
	public PageInterceptor pageHelper() {
		return new PageInterceptor();
	}

}