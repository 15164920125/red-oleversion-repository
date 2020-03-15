package cn.ms.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

public class FileUtil {

	/**
	 * 创建文件
	 * 
	 * @param path
	 *            文件路径
	 */
	public static File createFile(String path) throws IOException {
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.mkdirs();
		}
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		return file;
	}

	/**
	 * 读取文件
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String path) throws IOException {
		return FileUtils.readFileToString(new File(path));
	}

	/**
	 * 查找该目录下的所有文件夹
	 * 
	 * @param path
	 *            文件路径
	 * @return 返回List
	 */
	public static List<File> findFolder(String path) {
		List<File> list = new ArrayList<File>();
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		File[] files = file.listFiles();
		for (File fi : files) {
			if (fi.isDirectory()) {
				list.add(fi);
			}
		}
		return list;
	}

	/**
	 * 查找该目录下的所有文件
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 */
	public static List<File> findFile(String path) {
		List<File> list = new ArrayList<File>();
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		File[] files = file.listFiles();
		for (File fi : files) {
			if (fi.isFile()) {
				list.add(fi);
			}
		}
		return list;
	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 *            文件名
	 * @param filePath
	 *            文件路径
	 */
	public static void downloadExt(HttpServletRequest request, HttpServletResponse response, String fileName,
			String filePath) throws Exception {
		File file = new File(filePath);
		if (file.mkdirs()) {
			throw new RuntimeException("filePath不能为文件夹,应该为文件所在路径");
		}

		ServletOutputStream out = null;
		try {
			// 清空response
			response.reset();
			// 设置输出的格式
			response.setContentType("application/x-download");// 设置为下载application/x-download
			response.addHeader("content-type ", "application/x-msdownload");
			response.setContentType("application/octet-stream");

			// 解决IE和其它浏览器乱码问题
			String agent = request.getHeader("USER-AGENT");
			// ie
			if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident")) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else
			// 火狐,chrome等
			if (null != agent && -1 != agent.indexOf("Mozilla")) {
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			}
			// 设定输出文件头
			response.setHeader("content-disposition", "attachment; filename=" + fileName);

			out = response.getOutputStream();
			byte[] by = FileUtils.readFileToByteArray(file);
			// 文件写入输出流
			out.write(by, 0, by.length);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 浏览器访问图片
	 * 
	 * @param imgFilePath
	 *            图片文件路径
	 */
	public static void readPhoto(HttpServletResponse response, String imgFilePath) throws IOException {
		ServletOutputStream out = response.getOutputStream();
		try {
			response.setHeader("Content-Type", "image/jpeg");
			File file = new File(imgFilePath);
			byte[] by = FileUtils.readFileToByteArray(file);
			out.write(by, 0, by.length);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
