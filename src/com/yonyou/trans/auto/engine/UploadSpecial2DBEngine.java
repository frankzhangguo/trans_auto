/**
 * 
 */
package com.yonyou.trans.auto.engine;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.yonyou.trans.auto.model.Trans_fileVO;
import com.yonyou.trans.auto.model.Trans_specialVO;
import com.yonyou.trans.auto.processor.ExcelReadProcessor;

/**
 * @author Frank
 * 
 */
public class UploadSpecial2DBEngine extends AbstractEngine {

	/**
	 * @param fileVO
	 */
	public UploadSpecial2DBEngine(Trans_fileVO fileVO) {
		super(fileVO);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yonyou.trans.auto.AbstractEngine#start()
	 */
	@Override
	public boolean start() throws Exception {
		ExcelReadProcessor<Trans_specialVO> excelReadProcessor = new ExcelReadProcessor<Trans_specialVO>(getFileVO().getFile(), false) {

			@Override
			public Trans_specialVO processHSSFRow(HSSFRow hssfRow) {
				Trans_specialVO sheetVO = new Trans_specialVO();
				HSSFCell module = hssfRow.getCell(0);
				HSSFCell location = hssfRow.getCell(1);
				HSSFCell graphic = hssfRow.getCell(2);
				HSSFCell file = hssfRow.getCell(3);
				HSSFCell resid = hssfRow.getCell(4);
				HSSFCell sheet = hssfRow.getCell(5);
				HSSFCell source = hssfRow.getCell(6);
				HSSFCell correct_translation = hssfRow.getCell(7);
				HSSFCell old_translation = hssfRow.getCell(8);
				HSSFCell reported_by = hssfRow.getCell(9);

				sheetVO.setModule(getValue(module));
				sheetVO.setLocation(getValue(location));
				sheetVO.setGraphic(getValue(graphic));
				sheetVO.setFile(getValue(file));
				sheetVO.setResid(getValue(resid));
				sheetVO.setSheet(getValue(sheet));
				sheetVO.setSource(getValue(source));
				sheetVO.setCorrect_translation(getValue(correct_translation));
				sheetVO.setOld_translation(getValue(old_translation));
				sheetVO.setReported_by(getValue(reported_by));

				return sheetVO;
			}
		};

		getSqlHelper().insertSpecialTrans(excelReadProcessor.getWorkBookMap());
		return true;
	}

}
