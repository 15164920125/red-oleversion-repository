package cn.ms.config;

import java.time.Duration;


import javax.annotation.Resource;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import cn.ms.common.mapper.PzRedisMapper;
import cn.ms.common.schema.PzRedis;

/**
 * springRedis启动类 ,如果用到session共享必须启动此类
 * 设置session过期时间,18000秒,为5小时
 *
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 18000)
public class RedisConfig {
	private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

	private static RedisConfig redisConfig = null;

	public RedisConfig() {
		redisConfig = this;
	}

	@Resource
	private PzRedisMapper pzRedisMapper;

	// redis工厂
	@Bean
	public LettuceConnectionFactory defaultLettuceConnectionFactory() {
		logger.info("加载springRedis配置");
		String ip;
		int port;
		String password;
		int maxActive;
		int maxIdle;
		long maxWait;
		int timeOut;
		try {
			PzRedis pzRedis = redisConfig.pzRedisMapper.selectAll();
			ip = pzRedis.getIp();
			port = pzRedis.getPort();
			password = pzRedis.getPassword();
			maxActive = pzRedis.getMaxActive();
			maxWait = pzRedis.getMaxWait();
			maxIdle = pzRedis.getMaxIdle();
			timeOut = pzRedis.getTimeOut();

			logger.info("redis.ip=" + ip);
			logger.info("redis.port=" + port);
			logger.info("redis.TimeOut=" + timeOut);
			logger.info("redis.password=" + password);

			RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
			redisConfig.setHostName(ip);
			// config.setPassword(RedisPassword.of(password));
			redisConfig.setPort(port);
			redisConfig.setDatabase(0);
			
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			poolConfig.setMaxTotal(maxActive);
			poolConfig.setMaxIdle(maxIdle);
			poolConfig.setMaxWaitMillis(maxWait);
			
			LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
					.commandTimeout(Duration.ofMillis(1000)).poolConfig(poolConfig).build();
			return new LettuceConnectionFactory(redisConfig, clientConfig);
		} catch (Exception e) {
			throw new RuntimeException("查询redis出错", e);
		}

	}

	@Bean
	public RedisTemplate<String, String> defaultRedisTemplate(
			LettuceConnectionFactory defaultLettuceConnectionFactory) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(defaultLettuceConnectionFactory);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

}
