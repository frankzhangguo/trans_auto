/**
 * 
 */
package com.yonyou.trans.auto.model;

import java.io.Serializable;

/**
 * @author Frank
 * 
 */
@SuppressWarnings("serial")
public class Trans_simpchnVO implements Serializable {
	private String filemd5;
	private String sheetname;
	private String simpchn;
	private String english;

	public String getEnglish() {
		return english;
	}

	public String getFilemd5() {
		return filemd5;
	}

	public String getSheetname() {
		return sheetname;
	}

	public String getSimpchn() {
		return simpchn;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}

	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}

	public void setSimpchn(String simpchn) {
		this.simpchn = simpchn;
	}
}
