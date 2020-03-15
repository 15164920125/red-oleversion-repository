package cn.ms.sysmain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.function.service.AutoDeploy;

/**
 * 部署服务器工具
 */
public class DeployMain {

	public static void main(String[] args) {
		try {
			AutoDeploy.executeWeiFuWu(msprovider242());
//			AutoDeploy.executeWeiFuWu(mszuul242());
//			AutoDeploy.executeWeiFuWu(msfile242());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Map<String, Object> msprovider242() {
		Map<String, Object> map = new HashMap<>();
		List<String> mingLingList = new ArrayList<String>();
		/**
		 * 每台电脑只需修改这三个参数
		 */
		// 本地maven安装路径
		map.put("mvnPath", "E:/Maven_jishen/apache-maven-3.3.9");
		// 本地pom.xml文件的路径
		map.put("pomPath", "E:/EclipseWorkspace/tfs/Main/ms/ms-provider/pom.xml");
		// jar包本地存放路径
		map.put("benDiWarBao", "E:/EclipseWorkspace/tfs/Main/ms/ms-provider/target/ms-provider/ms-provider.jar");
		
		/**
		 * 以下配置所有电脑通用的，不用修改
		 */
		// 本服务地址
		map.put("benUrl", "http://10.10.4.242:6007/");
		// maven命令
		mingLingList.add("clean");
		mingLingList.add("package");
		map.put("mingLingList", mingLingList);
		// jar包名称
		map.put("jarName", "ms-provider.jar");
		// jar包在服务器上的存放路径
		map.put("serverPath", "/home/ms/server/ms-provider");
		// 文件微服务地址
		map.put("serverUrl", "http://10.10.4.242:6006/");

		List<String> list = new ArrayList<>();
		// 删除文件
		list.add("rm -fr /home/ms/server/ms-provider/ms-provider.jar");
		// 上传文件
		list.add("upload");
		// 重启命令
		list.add("cd /home/ms/server/ms-provider;sh start.sh");
		map.put("list", list);
		return map;
	}
	public static Map<String, Object> mszuul242() {
		Map<String, Object> map = new HashMap<>();
		List<String> mingLingList = new ArrayList<String>();
		/**
		 * 每台电脑只需修改这三个参数
		 */
		// 本地maven安装路径
		map.put("mvnPath", "E:/Maven_jishen/apache-maven-3.3.9");
		// 本地pom.xml文件的路径
		map.put("pomPath", "E:/EclipseWorkspace/tfs/Main/ms/ms-zuul/pom.xml");
		// jar包本地存放路径
		map.put("benDiWarBao", "E:/EclipseWorkspace/tfs/Main/ms/ms-zuul/target/ms-zuul/ms-zuul.jar");
		
		/**
		 * 以下配置所有电脑通用的，不用修改
		 */
		// 本服务地址
		map.put("benUrl", "http://10.10.4.242:6003/");
		// maven命令
		mingLingList.add("clean");
		mingLingList.add("package");
		map.put("mingLingList", mingLingList);
		// jar包名称
		map.put("jarName", "ms-zuul.jar");
		// jar包在服务器上的存放路径
		map.put("serverPath", "/home/ms/server/ms-zuul");
		// 文件微服务地址
		map.put("serverUrl", "http://10.10.4.242:6006/");
		
		List<String> list = new ArrayList<>();
		// 删除文件
		list.add("rm -fr /home/ms/server/ms-zuul/ms-zuul.jar");
		// 上传文件
		list.add("upload");
		// 重启命令
		list.add("cd /home/ms/server/ms-zuul;sh start.sh");
		map.put("list", list);
		return map;
	}
	
	public static Map<String, Object> msfile242() {
		Map<String, Object> map = new HashMap<>();
		List<String> mingLingList = new ArrayList<String>();
		/**
		 * 每台电脑只需修改这三个参数
		 */
		// 本地maven安装路径
		map.put("mvnPath", "E:/Maven_jishen/apache-maven-3.3.9");
		// 本地pom.xml文件的路径
		map.put("pomPath", "E:/EclipseWorkspace/tfs/Main/ms/ms-file/pom.xml");
		// jar包本地存放路径
		map.put("benDiWarBao", "E:/EclipseWorkspace/tfs/Main/ms/ms-file/target/ms-file/ms-file.jar");
		
				
		/**
		 * 以下配置所有电脑通用的，不用修改
		 */
		// 本服务地址
		map.put("benUrl", "http://10.10.4.242:6006/");
		// maven命令
		mingLingList.add("clean");
		mingLingList.add("package");
		map.put("mingLingList", mingLingList);
		// jar包名称
		map.put("jarName", "ms-file.jar");
		// jar包在服务器上的存放路径
		map.put("serverPath", "/home/ms/server/ms-file");
		// 文件微服务地址
		map.put("serverUrl", "http://10.10.4.242:6006/");
		
		List<String> list = new ArrayList<>();
		// 删除文件
		list.add("rm -fr /home/ms/server/ms-file/ms-file.jar");
		// 上传文件
		list.add("upload");
		// 重启命令
		list.add("cd /home/ms/server/ms-file;sh start.sh");
		map.put("list", list);
		return map;
	}
	


}
