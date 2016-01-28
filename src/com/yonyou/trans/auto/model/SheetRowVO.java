package com.yonyou.trans.auto.model;


import java.io.Serializable;

@SuppressWarnings("serial")
public class SheetRowVO implements Serializable {
	// 文件路径 文件名 资源id simpchn:UTF-8 english:UTF-8 tradchn:UTF-8
	String filePah;
	String fileName;
	String resId;
	String simpchn;
	String english;
	String tradchn;
	String md5;
	Integer rowNum;

	public String getEnglish() {
		return english;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFilePah() {
		return filePah;
	}

	/**
	 * @return md5
	 */
	public String getMd5() {
		return md5;
	}

	public String getResId() {
		return resId;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public String getSimpchn() {
		return simpchn;
	}

	public String getTradchn() {
		return tradchn;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFilePah(String filePah) {
		this.filePah = filePah;
	}

	/**
	 * @param md5 要设置的 md5
	 */
	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public void setSimpchn(String simpchn) {
		this.simpchn = simpchn;
	}

	public void setTradchn(String tradchn) {
		this.tradchn = tradchn;
	}


}
