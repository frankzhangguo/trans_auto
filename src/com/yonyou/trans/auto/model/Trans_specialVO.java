package com.yonyou.trans.auto.model;

import java.io.Serializable;

public class Trans_specialVO implements Serializable {

	private static final long serialVersionUID = 7679621402780861170L;
	String module;
	String location;
	String graphic;
	String file;
	String resid;
	String sheet;
	String source;
	String correct_translation;
	String old_translation;
	String reported_by;

	public String getCorrect_translation() {
		return correct_translation;
	}

	public String getFile() {
		return file;
	}

	public String getGraphic() {
		return graphic;
	}

	public String getLocation() {
		return location;
	}

	public String getModule() {
		return module;
	}

	public String getOld_translation() {
		return old_translation;
	}

	public String getReported_by() {
		return reported_by;
	}

	public String getResid() {
		return resid;
	}

	public String getSheet() {
		return sheet;
	}

	public String getSource() {
		return source;
	}

	public void setCorrect_translation(String correct_translation) {
		this.correct_translation = correct_translation;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setGraphic(String graphic) {
		this.graphic = graphic;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setOld_translation(String old_translation) {
		this.old_translation = old_translation;
	}

	public void setReported_by(String reported_by) {
		this.reported_by = reported_by;
	}

	public void setResid(String resid) {
		this.resid = resid;
	}

	public void setSheet(String sheet) {
		this.sheet = sheet;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
