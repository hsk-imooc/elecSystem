package com.rupeng.elec.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class POIUtils {

	public static void exportExecl(List<String[]> data, OutputStream out) throws IOException {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();

		int rowIndex = 0;

		if (data != null) {
			/**
			 * 获得data中的表头数据
			 */
			String[] titleData = data.get(0);
			int length = titleData.length;
			/**
			 * 设置样式
			 */
			HSSFCellStyle titleCellStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			titleCellStyle.setFont(font);

			/**
			 * 创建表头行
			 */
			HSSFRow titleRow = sheet.createRow(rowIndex);
			rowIndex++;
			for (int i = 0; i < length; i++) {
				sheet.setColumnWidth((short) i, (short) 5000);// 设置列宽

				HSSFCell titleCell = titleRow.createCell((short) i);
				titleCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				titleCell.setCellStyle(titleCellStyle);
				titleCell.setCellValue(titleData[i]);
			}

			/**
			 * 装填数据
			 */
			for (int i = 1; i < data.size(); i++) {
				HSSFRow row = sheet.createRow(rowIndex);// 创建一行
				rowIndex++;
				String[] rowData = data.get(i);// 该行数据
				for (int j = 0; j < rowData.length; j++) {
					HSSFCell cell = row.createCell((short) j);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(rowData[j]);
				}
			}

			workbook.write(out);
		}

	}
}
