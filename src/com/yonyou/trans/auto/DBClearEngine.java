/**
 * 
 */
package com.yonyou.trans.auto;

import com.yonyou.trans.auto.model.Trans_fileVO;

/**
 * @author Frank
 * 
 */
public class DBClearEngine extends AbstractEngine {

	/**
	 * @param fileVO
	 */
	public DBClearEngine(Trans_fileVO fileVO) {
		super(fileVO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yonyou.trans.auto.AbstractEngine#start()
	 */
	@Override
	public boolean start() throws Exception {

		getSqlHelper().clearDB();
		getSqlHelper().close();
		return true;
	}

}
