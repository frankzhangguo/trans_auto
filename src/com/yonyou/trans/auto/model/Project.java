/**
 * 
 */
package com.yonyou.trans.auto.model;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Frank
 */
@XmlRootElement(name = "Project")
public class Project {
	private static Project project = null;

	public static Project getProjectConfig() {
		if (project == null) {
			try {
				project = xmltojava(Project.class, "setting.xml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return project;
	}

	@SuppressWarnings("unchecked")
	private static <T> T xmltojava(Class<T> cls, String file) throws Exception {
		File f = new File(file);
		JAXBContext ctx = JAXBContext.newInstance(cls);
		Unmarshaller us = ctx.createUnmarshaller();
		return (T) us.unmarshal(f);
	}

	private String databaseURL;
	private String databaseUser;
	private String databasePassword;
	private String fromExcelSimp;
	private String toDatabaseDir;
	private String mailReceivers;
	private List<Model> modelsList;

	public Project() {
		super();
	}

	public Project(List<Model> models) {
		super();
		this.modelsList = models;
	}

	/**
	 * @return the databasePassword
	 */
	public String getDatabasePassword() {
		return databasePassword;
	}

	/**
	 * @return the databaseURL
	 */
	public String getDatabaseURL() {
		return databaseURL;
	}

	/**
	 * @return the databaseUser
	 */
	public String getDatabaseUser() {
		return databaseUser;
	}

	/**
	 * @return the fromExcelSimp
	 */
	public String getFromExcelSimp() {
		return fromExcelSimp;
	}

	public String getMailReceivers() {
		return mailReceivers;
	}

	/**
	 * @return the modelsList
	 */
	@XmlElementWrapper(name = "models")
	@XmlElement(name = "Model")
	public List<Model> getModelsList() {
		return modelsList;
	}

	/**
	 * @return the toDatabaseDir
	 */
	public String getToDatabaseDir() {
		return toDatabaseDir;
	}

	/**
	 * @param databasePassword
	 *            the databasePassword to set
	 */
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	/**
	 * @param databaseURL
	 *            the databaseURL to set
	 */
	public void setDatabaseURL(String databaseURL) {
		this.databaseURL = databaseURL;
	}

	/**
	 * @param databaseUser
	 *            the databaseUser to set
	 */
	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	/**
	 * @param fromExcelSimp
	 *            the fromExcelSimp to set
	 */
	public void setFromExcelSimp(String fromExcelSimp) {
		this.fromExcelSimp = fromExcelSimp;
	}

	public void setMailReceivers(String mailReceivers) {
		this.mailReceivers = mailReceivers;
	}

	/**
	 * @param modelsList
	 *            the modelsList to set
	 */
	public void setModelsList(List<Model> modelsList) {
		this.modelsList = modelsList;
	}

	/**
	 * @param toDatabaseDir
	 *            the toDatabaseDir to set
	 */
	public void setToDatabaseDir(String toDatabaseDir) {
		this.toDatabaseDir = toDatabaseDir;
	}
}
