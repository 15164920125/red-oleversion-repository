package cn.ms;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.github.pagehelper.PageInterceptor;

@SpringBootApplication
@EnableEurekaClient
// 开启事务
@EnableTransactionManagement
// 扫描mapper
@MapperScan({ "cn.ms.*.mapper" })
public class StartMsFile
{
    private static final Logger logger = LoggerFactory.getLogger(StartMsFile.class);

    public static void main(String[] args) throws Exception
    {
        SpringApplication.run(StartMsFile.class, args);
        logger.info("ms-file服务启动成功");
    }

    // 微服务调用微服务-负载均衡
    @LoadBalanced
    @Bean
    RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement()
    {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置文件大小限制 ;
        factory.setMaxFileSize("200MB"); // KB,MB
        // 设置参数-数据总大小
        factory.setMaxRequestSize("200MB");
        return factory.createMultipartConfig();
    }

    // 加载mybatis分页插件
    @Bean
    public PageInterceptor pageHelper()
    {
        return new PageInterceptor();
    }
    //	@Bean
    //	public FileUploadProgressListener  fileUploadProgressListener() {
    //		FileUploadProgressListener factory = new FileUploadProgressListener();
    //		return factory;
    //	}
    //	@Bean
    //	public MultipartResolver  multipartResolver() {
    //		CustomMultipartResolver factory = new CustomMultipartResolver();
    //		return factory;
    //	}
}