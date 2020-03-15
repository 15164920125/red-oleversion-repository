package cn.ms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {


	/**
	 * 创建EXcel
	 * 
	 * @param list<T>
	 *            T为实体类
	 * @param sheetName
	 *            工作薄名称
	 * @param fieldNames
	 *            工作薄中第一行（字段名称） String[] fieldNames = { "序号", "名称", "数量"};
	 * @param excludeField
	 *            实体类中需要排除的字段,例如"code,name,level" 如果没有,请传空null
	 * @param filePath
	 *            文件路径
	 */
	public static <T> void createExcel(List<T> list, String sheetName, String[] fieldNames, String excludeField,
			String filePath) throws Exception {

		// 创建工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建表
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 创建第一行（字段名称）
		HSSFRow queryFieldRow = sheet.createRow(0);
		for (int i = 0; i < fieldNames.length; i++) {
			queryFieldRow.createCell(i).setCellValue(fieldNames[i]);
		}

		// 写入数据
		int i = 1;
		for (T t : list) {
			// 创建行
			HSSFRow dataRow = sheet.createRow(i++);
			// 获取所有字段
			Field[] fields = t.getClass().getDeclaredFields();
			int j = 0;
			for (Field field : fields) {
				field.setAccessible(true);
				if (excludeField != null && excludeField.trim().length() != 0) {
					if (excludeField.contains(field.getName())) {
						continue;
					}
				}
				Object object = field.get(t);
				if (object != null && !"".equals(object.toString().trim())) {
					if (object instanceof Date) {
						String value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(object);
						dataRow.createCell(j).setCellValue(value);
					} else {
						String value = String.valueOf(object);
						dataRow.createCell(j).setCellValue(value);
					}
				}
				j = j + 1;
			}
		}

		// 创建文件
		OutputStream out = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}

			File parentPath = new File(file.getParent());
			if (!parentPath.exists()) {
				parentPath.mkdirs();
			}

			out = new FileOutputStream(filePath);
			workbook.write(out);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
			if (out != null) {
				out.close();
			}
		}

	}

}
