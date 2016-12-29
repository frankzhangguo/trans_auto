package com.yonyou.trans.auto.model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * trans_file  µÃÂ¿‡ Fri Jan 15 09:32:53 CST 2016 Frank
 */

@SuppressWarnings("serial")
public class Trans_fileVO implements Serializable {
	private String filemd5;
	private String filename;
	private Date lastdate;
	private String runid;
	private Model model;
	private File file;

	private String path;

	public File getFile() {
		return file;
	}

	public String getFilemd5() {
		return filemd5;
	}

	public String getFilename() {
		return filename;
	}

	public Date getLastdate() {
		return lastdate;
	}

	public Model getModel() {
		return model;
	}

	public String getPath() {
		return path;
	}

	public String getRunid() {
		return runid;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setRunid(String runid) {
		this.runid = runid;
	}

}
