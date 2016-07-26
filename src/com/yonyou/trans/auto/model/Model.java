/**
 * 
 */
package com.yonyou.trans.auto.model;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Frank
 */
public class Model {
	String name;
	String remoteFromPath;
	String remoteToPath;
	String localTranPath;
	String localBackupPath;
	String weekday;

	/**
	 * 
	 */
	public Model() {
		super();
	}

	/**
	 * @return the localBackupPath
	 */
	public String getLocalBackupPath() {
		return localBackupPath;
	}

	/**
	 * @return the localTranPath
	 */
	public String getLocalTranPath() {
		return localTranPath;
	}

	@XmlAttribute(required = true)
	public String getName() {
		return name;
	}

	/**
	 * @return the remoteFromPath
	 */
	public String getRemoteFromPath() {
		return remoteFromPath;
	}

	/**
	 * @return the remoteToPath
	 */
	public String getRemoteToPath() {
		return remoteToPath;
	}

	public String getWeekday() {
		return weekday;
	}

	/**
	 * @param localBackupPath
	 *            the localBackupPath to set
	 */
	public void setLocalBackupPath(String localBackupPath) {
		this.localBackupPath = localBackupPath;
	}

	/**
	 * @param localTranPath
	 *            the localTranPath to set
	 */
	public void setLocalTranPath(String localTranPath) {
		this.localTranPath = localTranPath;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param remoteFromPath
	 *            the remoteFromPath to set
	 */
	public void setRemoteFromPath(String remoteFromPath) {
		this.remoteFromPath = remoteFromPath;
	}

	/**
	 * @param remoteToPath
	 *            the remoteToPath to set
	 */
	public void setRemoteToPath(String remoteToPath) {
		this.remoteToPath = remoteToPath;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
}
