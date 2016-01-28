package com.yonyou.trans.auto.model;

import java.io.Serializable;
import java.util.List;


/**
 * trans_sheets  µÃÂ¿‡ Fri Jan 15 09:36:15 CST 2016 Frank
 */
@SuppressWarnings("serial")
public class Trans_sheetsVO implements Serializable {
	private String filemd5;
	private String sheetname;

	private List<SheetRowVO> rowVOs;

	public String getFilemd5() {
		return filemd5;
	}

	public List<SheetRowVO> getRowVOs() {
		return rowVOs;
	}

	public String getSheetname() {
		return sheetname;
	}

	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}

	public void setRowVOs(List<SheetRowVO> rowVOs) {
		this.rowVOs = rowVOs;
	}

	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}

}
