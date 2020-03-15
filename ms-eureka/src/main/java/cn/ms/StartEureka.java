package cn.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class StartEureka {
	public static void main(String[] args) {
		SpringApplication.run(StartEureka.class, args);
		System.out.println("eureka启动成功");
	}
}