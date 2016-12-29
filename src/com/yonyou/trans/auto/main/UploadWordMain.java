/**
 * 
 */
package com.yonyou.trans.auto.main;

import com.yonyou.trans.auto.Translation;
import com.yonyou.trans.auto.model.Project;
import com.yonyou.trans.tools.FileUtil;
import com.yonyou.trans.tools.MailUtil;

/**
 * @author Frank
 *
 */
public class UploadWordMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		start();

	}

	public static void start() {
		// start translation
		Translation translation = new Translation();
		Project project = Project.getProjectConfig();
		try {
			// 1:������õ�EXCEL�����ϵͳ
			{
				// 1.1 ����EXCEL
				translation.readNewWords(project);
				// 1.2 ��ɺ�ɾ�����ص��ϴ�������ļ�
				FileUtil.deleteDirectory(project.getToDatabaseDir(), false);
			}
		} catch (Exception e) {
			MailUtil mailUtil = new MailUtil();
			mailUtil.send(e.getMessage());
		}
	}

}
