package com.yonyou.trans.auto;

import com.yonyou.trans.auto.model.Trans_fileVO;

public class WordExcel2DBEngine extends AbstractEngine {

	public WordExcel2DBEngine(Trans_fileVO fileVO) {
		super(fileVO);
	}

	@Override
	public boolean start() throws Exception {
		WordExcelReadProcessor excelReadProcessor = new WordExcelReadProcessor(getFileVO().getFile(), false);
		getSqlHelper().insertNewTrans(excelReadProcessor.getWorkBookMap());
		return true;
	}
}