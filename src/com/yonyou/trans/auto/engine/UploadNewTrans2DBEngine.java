package com.yonyou.trans.auto.engine;

import com.yonyou.trans.auto.model.Trans_fileVO;
import com.yonyou.trans.auto.processor.SimpchnExcelReadProcessor;

public class UploadNewTrans2DBEngine extends AbstractEngine {

	public UploadNewTrans2DBEngine(Trans_fileVO fileVO) {
		super(fileVO);
	}

	@Override
	public boolean start() throws Exception {
		SimpchnExcelReadProcessor excelReadProcessor = new SimpchnExcelReadProcessor(getFileVO().getFile(), false);
		getSqlHelper().insertNewTrans(excelReadProcessor.getWorkBookMap());
		return true;
	}
}
