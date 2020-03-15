package cn.ms.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;

@SuppressWarnings("unchecked")
public class XmlUtil {

	public static void main(String[] args) throws Exception {

//		System.out.println(ObjectToXML(new Dept()));
//		System.out.println(beanToXml(new Dept()));
	}

	/**
	 * 读取xml文件
	 */
	public static Document getDom(String path) {
		try {
			SAXReader sax = new SAXReader();
			Document dom = sax.read(path);
			return dom;
		} catch (Exception e) {
			throw new RuntimeException("读取xml文件出现异常,文件路径：" + path, e);
		}
	}

	/**
	 * xml转Document元素
	 */
	public static Document xmlToDoc(String xml) throws DocumentException {
		Document document = DocumentHelper.parseText(xml);
		return document;
	}

	/**
	 * xml转Document元素
	 */
	public Document xmlToEle(String xml) throws DocumentException {
		Document document = DocumentHelper.parseText(xml);
		return document;
	}

	/**
	 * @param doc
	 * @param xpath
	 *            例子：取该节点的值 "//prplOFRriskFactorsInfoList"
	 * @return
	 */
	public static String getXmlByDoc(Document doc, String xpath) {
		Element e = (Element) doc.selectSingleNode(xpath);
		if (e == null) {
			return "";
		} else {
			return e.asXML();
		}
	}

	/**
	 * xml转对象
	 */

	public static <T> T xmlToBean(String xmlStr, Class<T> clazz) {
		XStream x = new XStream();
		// 通过注解方式的，一定要有这句话
		x.processAnnotations(clazz);
		// 实体类没有xml里面的某个字段，可以忽略。
		x.ignoreUnknownElements();
		// 将String类型时间转成Date类型时间（如果实体类中不是Date类型，则不转换）
		x.registerConverter(new DateConverter("yyyy-MM-dd HH:mm:ss", null, TimeZone.getTimeZone("GMT+8")));

		return (T) x.fromXML(xmlStr);
	}

	/**
	 * 对象转xml
	 */
	public static String beanToXml(Object bean) {
		XStream x = new XStream();
		// 通过注解方式的，一定要有这句话
		x.processAnnotations(bean.getClass());

//		x.registerConverter(new MoreDataResultNullConverter());
		// 将Date类型时间转成String类型
		x.registerConverter(new DateConverter("yyyy-MM-dd HH:mm:ss", null, TimeZone.getTimeZone("GMT+8")));

		return x.toXML(bean);
	}


	/**
	 * 将List<\Element>转成List<实体类>
	 * 
	 * @param elements
	 *            List<\Element>集合
	 * @param name
	 *            实体类的别名（如果不用，可以写成null）
	 * @param clazz
	 *            实体类.class
	 * @return 返回List<实体类>集合
	 */
	public static <T> List<T> eleToList(List<Element> elements, String name, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		XStream x = new XStream();
		for (Element element : elements) {
			if (!(name == null || name.trim() == "")) {
				x.alias(name, clazz);// 给实体类起别名
			}
			String xml = element.asXML();
			list.add((T) x.fromXML(xml));
		}
		return list;
	}

	/**
	 * 将xml转成List<实体类>
	 * 
	 * @param elements
	 *            List<\Element>集合
	 * 
	 * @param clazz
	 *            实体类.class
	 * 
	 * @return 返回List<实体类>集合
	 */
	public static <T> List<T> xmlToList(String xml, Class<T> clazz) throws Exception {
		List<T> list = new ArrayList<T>();
		Document document = DocumentHelper.parseText(xml);
		Element rootElement = document.getRootElement();
		List<Element> elements = rootElement.elements();
		for (Element element : elements) {
			T t = xmlToBean(element.asXML(), clazz);
			list.add(t);
		}
		return list;
	}

	/**
	 * 将List<实体类>集合放到Element元素中
	 * 
	 * @param element
	 *            Element元素
	 * @param list
	 *            List<实体类>集合
	 * @param name
	 *            实体类的别名（如果不用，可以写成null）
	 */
	public static <T> void listToEle(Element element, List<T> list, String name) throws Exception {
		XStream x = new XStream();
		for (T t : list) {
			if (!(name == null || name.trim() == "")) {
				x.alias(name, t.getClass());// 给实体类起别名
			}
			String textXml = x.toXML(t);
			Document doc = DocumentHelper.parseText(textXml);
			Element root = doc.getRootElement();
			element.add(root);
		}
	}

	/**
	 * 格式化XML
	 * 
	 * @param str
	 * @return
	 */
	public static String formatXml(String xml) {
		StringWriter writer = null;
		XMLWriter xmlWriter = null;
		try {
			Document document = DocumentHelper.parseText(xml);
			// 格式化输出格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			writer = new StringWriter();
			// 格式化输出流
			xmlWriter = new XMLWriter(writer, format);
			// 将document写入到输出流
			xmlWriter.write(document);
		} catch (Exception e) {
			throw new RuntimeException("格式化XML数据出现异常", e);
		} finally {
			try {
				if (xmlWriter != null) {
					xmlWriter.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("格式化XML时,IO流关闭异常!", e);
			}
		}
		return writer.toString();
	}

	/**
	 * 压缩XML
	 */
	public static String compressXml(String xml) {
		StringWriter writer = null;
		XMLWriter xmlWriter = null;
		try {
			Document document = DocumentHelper.parseText(xml);
			// 格式化输出格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			// 设置XML编码方式,即是用指定的编码方式保存XML文档到字符串(String),这里也可以指定为GBK或是ISO8859-1
			// outputFormat.setSuppressDeclaration(true); //是否生成xml头
			// format.setEncoding("UTF-8");
			// format.setIndent(" "); //以四个空格方式实现缩进
			// format.setIndent(true); //设置是否缩进
			format.setNewlines(false); // 设置是否换行
			writer = new StringWriter();
			// 格式化输出流
			xmlWriter = new XMLWriter(writer, format);
			// 将document写入到输出流
			xmlWriter.write(document);
		} catch (Exception e) {
			throw new RuntimeException("压缩XML数据出现异常", e);
		} finally {
			try {
				if (xmlWriter != null) {
					xmlWriter.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("压缩XML时,IO流关闭异常!", e);
			}
		}
		return writer.toString();
	}

	/**
	 * @param xml
	 * 
	 * @param list
	 *            存放为集合的节点名称。告诉工具,当前节点为集合。例如List<String> list=new ArrayList<>();
	 *            list.add("registVoList"); list.add("bpmMainVoList");
	 */
	public static JSONObject xmlToJson(String xml, List<String> list) throws DocumentException {
		Document parseText = DocumentHelper.parseText(xml);
		return eleToJson(parseText.getRootElement(), list);
	}

	/**
	 * @param e
	 * 
	 * @param list
	 *            存放为集合的节点名称。告诉工具,当前节点为集合。例如List<String> list=new ArrayList<>();
	 *            list.add("registVoList"); list.add("bpmMainVoList");
	 */
	public static JSONObject eleToJson(Element e, List<String> isArr) {
		JSONObject obj = new JSONObject();
		if (e != null) {
			List<Element> list = e.elements();
			for (Element el : list) {
				if (isArr.contains(el.getName())) {
					JSONArray arr = new JSONArray();
					List<Element> arrList = el.elements();
					if (arrList != null && !arrList.isEmpty()) {
						for (Element e2 : arrList) {
							if (e2 != null) {
								JSONObject obj3 = eleToJson(e2, isArr);
								arr.add(obj3);
							}
						}
					}
					obj.put(el.getName(), arr);
				} else if (el.isTextOnly()) {
					obj.put(el.getName(), el.getTextTrim());
				} else {
					JSONObject o = eleToJson(el, isArr);
					obj.put(el.getName(), o);
				}
			}
		}

		return obj;
	}

}
