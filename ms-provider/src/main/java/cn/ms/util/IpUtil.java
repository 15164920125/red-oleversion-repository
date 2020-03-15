package cn.ms.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpUtil {
	public static void main(String[] args) {
		
	}

	/**
	 * 获取本机IP
	 */
	public static String getLocalHostIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException("获取ip地址异常", e);
		}
	}

	/**
	 * 如果用了docker容器,用此方法获取本机IP
	 *
	 */
	public static List<String> getLinuxLocalIp() {
		List<String> list = new ArrayList<String>();
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				String name = intf.getName();
				if (!name.contains("docker") && !name.contains("lo")) {
					for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
						InetAddress inetAddress = enumIpAddr.nextElement();
						if (!inetAddress.isLoopbackAddress()) {
							String ipaddress = inetAddress.getHostAddress();
							if (!ipaddress.contains("::") && !ipaddress.contains("0:0:")
									&& !ipaddress.contains("fe80")) {
								list.add(ipaddress);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("获取ip地址异常", e);
		}
		return list;
	}

}
