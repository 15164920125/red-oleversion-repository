package cn.ms.common.schema;

import cn.function.annotation.Lie;
import cn.function.annotation.TableName;

@TableName(comment = "配置redis表",name="Pz_Redis")
public class PzRedis {
	
	@Lie(comment = "主键",isPrimaryKey=true,flag=true)
	private Long id;
	
	@Lie(comment = "ip地址")
	private String ip;

	@Lie(comment = "端口")
	private int port;

	@Lie(comment = "密码")
	private String password;

	@Lie(comment = "设置最大连接数")
	private int maxActive;

	@Lie(comment = "设置最大空闲连接")
	private int maxIdle;

	@Lie(comment = "连接redis超时时间，单位是毫秒")
	private long maxWait;

	@Lie(comment = "等待redis返回结果超时时间，单位是毫秒")
	private int timeOut;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public long getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	

}
