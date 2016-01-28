package com.yonyou.trans.auto.model;

import java.io.Serializable;

/**
 * trans_words  µÃÂ¿‡ Fri Jan 15 09:36:25 CST 2016 Frank
 */
@SuppressWarnings("serial")
public class Trans_wordsVO implements Serializable {
	private String md5;
	private String simpchn;
	private String cTranslation;
	private Boolean checked;

	public Boolean getChecked() {
		return checked;
	}

	public String getCTranslation() {
		return cTranslation;
	}

	public String getMd5() {
		return md5;
	}

	public String getSimpchn() {
		return simpchn;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public void setCTranslation(String cTranslation) {
		this.cTranslation = cTranslation;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public void setSimpchn(String simpchn) {
		this.simpchn = simpchn;
	}
}
