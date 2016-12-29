package com.yonyou.trans.auto;

import java.io.File;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.yonyou.trans.auto.model.Trans_simpchnVO;

public class WordExcelReadProcessor extends ExcelReadProcessor<Trans_simpchnVO> {

	public WordExcelReadProcessor(File file, boolean processHeader) throws IOException {
		super(file, processHeader);
	}

	@Override
	public Trans_simpchnVO processHSSFRow(HSSFRow hssfRow) {

		Trans_simpchnVO sheetVO = new Trans_simpchnVO();
		HSSFCell simpchn = hssfRow.getCell(3);
		HSSFCell english = hssfRow.getCell(5);
		sheetVO.setSimpchn(getValue(simpchn));
		sheetVO.setEnglish(getValue(english));

		return sheetVO;

	}
}
