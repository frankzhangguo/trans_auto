package com.yonyou.trans.auto.engine;

import com.yonyou.trans.auto.model.Trans_fileVO;
import com.yonyou.trans.auto.processor.WordExcelReadProcessor;

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