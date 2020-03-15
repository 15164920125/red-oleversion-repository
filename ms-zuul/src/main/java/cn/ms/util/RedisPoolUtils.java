package cn.ms.util;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.ms.common.mapper.PzRedisMapper;
import cn.ms.common.schema.PzRedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisPoolUtils {
	private static Logger log = LoggerFactory.getLogger(RedisPoolUtils.class);
	private static JedisPool redisPool;
	private static RedisPoolUtils redirsPoolUtils = null;

	private RedisPoolUtils() {
		redirsPoolUtils = this;
	}

	@Resource
	private PzRedisMapper pzRedisMapper;

	public static void createJedisPool() {
		String ip;
		int port;
		String password;
		int maxActive;
		int maxIdle;
		long maxWait;
		int timeOut;
		PzRedis pzRedis = null;
		try {
			pzRedis = redirsPoolUtils.pzRedisMapper.selectAll();
			ip = pzRedis.getIp();
			port = pzRedis.getPort();
			password = pzRedis.getPassword();
			maxActive = pzRedis.getMaxActive();
			maxWait = pzRedis.getMaxWait();
			maxIdle = pzRedis.getMaxIdle();
			timeOut = pzRedis.getTimeOut();

			log.info("redis.ip=" + ip);
			log.info("redis.port=" + port);
			log.info("redis.TimeOut=" + timeOut);
			log.info("redis.password=" + password);

			// 建立连接池配置参数
			JedisPoolConfig config = new JedisPoolConfig();
			// 设置最大连接数
			config.setMaxTotal(maxActive);
			// 设置连接redis超时时间，单位是毫秒
			config.setMaxWaitMillis(maxWait);
			;
			// 设置空间连接
			config.setMaxIdle(maxIdle);
			// 创建连接池
			redisPool = new JedisPool(config, ip, port, timeOut);
		} catch (Exception e) {
			log.error("查询redis出错", e);
			JsonUtil.setMsg("查询redis出错");
			throw new RuntimeException("查询redis出错");
		}
	}

	/**
	 * 在多线程环境同步初始化
	 */
	private static synchronized void poolInit() {
		if (redisPool == null) {
			createJedisPool();
			System.out.println("初始化redis连接池");
		}
	}

	/**
	 * 获取一个jedis 对象
	 */
	public static Jedis getJedis() {
		if (redisPool == null) {
			poolInit();
		}
		return redisPool.getResource();
	}

	/**
	 * 归还一个连接
	 */
	public static void returnRes(Jedis jedis) {
		jedis.close();
	}

	public static void main(String[] args) {

		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 1000; i++) {
					Jedis jedis = RedisPoolUtils.getJedis();
					jedis.set("123456", "你好");

					// JedisPoolUtils.returnRes(jedis);
					System.out.println(i);
				}

			}
		}).start();

		// new Thread(new Runnable() {
		// public void run() {
		// for (int i = 0; i < 1000; i++) {
		// Jedis jedis = JedisPoolUtils.getJedis();
		// jedis.set("123456", "你好");
		// JedisPoolUtils.returnRes(jedis);
		// System.out.println(i);
		// }
		//
		// }
		// }).start();

	}
}
