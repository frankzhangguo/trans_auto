/**
 * 
 */
package com.yonyou.trans.auto.processor;

import java.io.File;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.yonyou.trans.auto.model.Trans_simpchnVO;

/**
 * @author Frank
 * 
 */
public class SimpchnExcelReadProcessor extends ExcelReadProcessor<Trans_simpchnVO> {

	public SimpchnExcelReadProcessor(File file, boolean processHeader) throws IOException {
		super(file, processHeader);
	}

	@Override
	public Trans_simpchnVO processHSSFRow(HSSFRow hssfRow) {

		Trans_simpchnVO sheetVO = new Trans_simpchnVO();
		HSSFCell simpchn = hssfRow.getCell(0);
		HSSFCell english = hssfRow.getCell(1);
		sheetVO.setSimpchn(getValue(simpchn));
		sheetVO.setEnglish(getValue(english));

		return sheetVO;

	}
}
