/**
 * 
 */
package com.yonyou.trans.auto.processor;

import java.io.File;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.yonyou.trans.auto.model.SheetRowVO;

/**
 * @author Frank
 */
public class TransExcelReadProcessor extends ExcelReadProcessor<SheetRowVO> {

	public TransExcelReadProcessor(File file, boolean processHeader) throws IOException {
		super(file, processHeader);

	}

	@Override
	public SheetRowVO processHSSFRow(HSSFRow hssfRow) {
		SheetRowVO sheetVO = new SheetRowVO();
		HSSFCell filePah = hssfRow.getCell(0);
		HSSFCell fileName = hssfRow.getCell(1);
		HSSFCell resId = hssfRow.getCell(2);
		HSSFCell simpchn = hssfRow.getCell(3);
		HSSFCell english = hssfRow.getCell(4);
		HSSFCell tradchn = hssfRow.getCell(5);
		sheetVO.setRowNum(hssfRow.getRowNum());
		sheetVO.setFilePah(getValue(filePah));
		sheetVO.setFileName(getValue(fileName));
		sheetVO.setResId(getValue(resId));
		sheetVO.setSimpchn(getValue(simpchn));
		sheetVO.setEnglish(getValue(english));
		sheetVO.setTradchn(getValue(tradchn));

		return sheetVO;
	}

}
