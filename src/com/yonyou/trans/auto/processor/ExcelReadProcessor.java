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
	 *            �Ƿ������У� true�Ǵ��� ����û�����У� ȫ������
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
				HSSFRow row = hssfSheet.getRow(i); // ���������
				list.add(processHSSFRow(row));
			}
		}
		return list;
	}


	public String parseExcel(Cell cell) {
		String result = new String();
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:// ��������
			if (org.apache.poi.hssf.usermodel.HSSFDateUtil
					.isCellDateFormatted(cell)) {// �������ڸ�ʽ��ʱ���ʽ
				
				result = parseDate(cell);
			} else if (cell.getCellStyle().getDataFormat() == 58) {  
				result = parseDate(cell);
			} else {
				double value = cell.getNumericCellValue();
				CellStyle style = cell.getCellStyle();
				DecimalFormat format = new DecimalFormat();
				String temp = style.getDataFormatString();
				// ��Ԫ�����óɳ���
				if (temp.equals("General")) {
					format.applyPattern("#");
				}
				result = format.format(value);
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:// String����
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
	 * �������ڸ�ʽ������ͨ��getDataFormat()ֵ���ж�
	yyyy-MM-dd-----	14
	yyyy��m��d��---	31
	yyyy��m��-------	57
	m��d��  ----------	58
	HH:mm-----------	20
	hʱmm��  -------	32
	 */
	private String parseDate(Cell cell){
		String result = null;
		//1���ж��Ƿ�����ֵ��ʽ  
		if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
		    short format = cell.getCellStyle().getDataFormat();  
		    SimpleDateFormat sdf = null;  
		    if(format == 14 || format == 31 || format == 57 || format == 58){  
		        //����  
		        sdf = new SimpleDateFormat("yyyy-MM-dd");  
		    }else if (format == 20 || format == 32) {  
		        //ʱ��  
		        sdf = new SimpleDateFormat("HH:mm");  
		    }
		    double value = cell.getNumericCellValue();  
		    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);  
		    result = sdf.format(date);  
		}  
		return result;
	}
}
