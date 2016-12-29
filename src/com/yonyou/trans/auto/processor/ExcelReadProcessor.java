package com.yonyou.trans.auto.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

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
	public ExcelReadProcessor(File file, boolean processHeader)
			throws IOException {
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


	public String parseExcel(Cell cell) {
		String result = new String();
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
			if (org.apache.poi.hssf.usermodel.HSSFDateUtil
					.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
				
				result = parseDate(cell);
			} else if (cell.getCellStyle().getDataFormat() == 58) {  
				result = parseDate(cell);
			} else {
				double value = cell.getNumericCellValue();
				CellStyle style = cell.getCellStyle();
				DecimalFormat format = new DecimalFormat();
				String temp = style.getDataFormatString();
				// 单元格设置成常规
				if (temp.equals("General")) {
					format.applyPattern("#");
				}
				result = format.format(value);
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:// String类型
			result = cell.getRichStringCellValue().toString();
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			result = "";
		default:
			result = "";
			break;
		}
		return result;
	}
	
	/*
	 * 所有日期格式都可以通过getDataFormat()值来判断
	yyyy-MM-dd-----	14
	yyyy年m月d日---	31
	yyyy年m月-------	57
	m月d日  ----------	58
	HH:mm-----------	20
	h时mm分  -------	32
	 */
	private String parseDate(Cell cell){
		String result = null;
		//1、判断是否是数值格式  
		if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
		    short format = cell.getCellStyle().getDataFormat();  
		    SimpleDateFormat sdf = null;  
		    if(format == 14 || format == 31 || format == 57 || format == 58){  
		        //日期  
		        sdf = new SimpleDateFormat("yyyy-MM-dd");  
		    }else if (format == 20 || format == 32) {  
		        //时间  
		        sdf = new SimpleDateFormat("HH:mm");  
		    }
		    double value = cell.getNumericCellValue();  
		    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);  
		    result = sdf.format(date);  
		}  
		return result;
	}
}
