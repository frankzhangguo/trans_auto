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
			// 1:将翻译好的EXCEL表读入系统
			{
				// 1.1 读入EXCEL
				translation.readNewWords(project);
				// 1.2 完成后，删除本地的上传翻译的文件
				FileUtil.deleteDirectory(project.getToDatabaseDir(), false);
			}
		} catch (Exception e) {
			MailUtil mailUtil = new MailUtil();
			mailUtil.send(e.getMessage());
		}
	}

}
