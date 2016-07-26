package com.yonyou.trans.auto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.yonyou.trans.db.MD5Utl;

/**
 * @author Frank
 * @param <T>
 */
public abstract class ExcelReadProcessor<T> {
	private HSSFWorkbook workbook = null;
	private boolean processHeader = false;
	private InputStream inp = null;

	/**
	 * @param fileName
	 * @param processHeader
	 *            是否处理首行， true是处理， 就是没有首行， 全是数据
	 * @throws IOException
	 */
	public ExcelReadProcessor(File file, boolean processHeader) throws IOException {
		this.inp = new FileInputStream(file);
		this.workbook = new HSSFWorkbook(inp);
		this.processHeader = processHeader;
	}

	public String getValue(HSSFCell cell) {
		if (cell == null) {
			return "";
		}

		cell.setCellType(Cell.CELL_TYPE_STRING);
		return MD5Utl.removeSpace(String.valueOf(cell.getStringCellValue()));
	}

	public String getValue(HSSFRow hssfRow, int cell) {
		return getValue(hssfRow.getCell(cell));

	}

	/**
	 * @return
	 * @throws IOException
	 */
	public HashMap<String, List<T>> getWorkBookMap() throws IOException {
		HashMap<String, List<T>> map = new HashMap<String, List<T>>();
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = workbook.getSheetAt(numSheet);
			map.put(hssfSheet.getSheetName(), processHSSFSheet(hssfSheet));
		}
		if (workbook != null) {
			workbook.close();
		}
		if (inp != null) {
			inp.close();
		}
		return map;
	}

	/**
	 * @param row
	 * @return
	 */
	public abstract T processHSSFRow(HSSFRow hssfRow);

	/**
	 * @param hssfSheet
	 * @return
	 */
	public List<T> processHSSFSheet(HSSFSheet hssfSheet) {
		if (hssfSheet == null) {
			return null;
		}
		int lastRowNum = hssfSheet.getLastRowNum();
		List<T> list = new ArrayList<T>();
		if (lastRowNum > 0) {
			for (int i = processHeader ? 0 : 1; i <= lastRowNum; i++) {
				HSSFRow row = hssfSheet.getRow(i); // 获得行数据
				list.add(processHSSFRow(row));
			}
		}
		return list;
	}
}
