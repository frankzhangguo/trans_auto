package com.yonyou.trans.auto;

import com.yonyou.trans.auto.model.Trans_fileVO;
import com.yonyou.trans.db.DBHelper;

public abstract class AbstractEngine {
	private final Trans_fileVO fileVO;
	private DBHelper sqlHelper = null;

	public AbstractEngine(Trans_fileVO fileVO) {
		this.fileVO = fileVO;
	}

	public Trans_fileVO getFileVO() {
		return fileVO;
	}

	/**
	 * @return sqlHelper
	 */
	public DBHelper getSqlHelper() {
		if (sqlHelper == null) {
			sqlHelper = new DBHelper();
		}
		return sqlHelper;
	}

	abstract public boolean start() throws Exception;
}
