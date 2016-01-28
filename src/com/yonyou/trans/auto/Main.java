package com.yonyou.trans.auto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.yonyou.trans.auto.model.Model;
import com.yonyou.trans.auto.model.Project;
import com.yonyou.trans.tools.FileUtil;
import com.yonyou.trans.tools.MailUtil;

/**
 * @author Frank
 * 
 */
public class Main {

	/**
	 * 翻译主入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Translation translation = new Translation();
		Project project = Project.getProjectConfig();
		try {
			// 1:将翻译好的EXCEL表读入系统
			{
				// 1.1 读入EXCEL
				translation.readNewTrans(project);
				// 1.2 完成后，删除本地的上传翻译的文件
				FileUtil.deleteDirectory(project.getToDatabaseDir(), false);
			}
			// 2：翻译
			List<Model> models = project.getModelsList();
			for (Model model : models) {
				// 2.1 删除本地的等待翻译的文件
				FileUtil.deleteDirectory(model.getLocalTranPath(), false);
				// 2.2 将远程的文件复制到本地
				FileUtil.copyDirectory(model.getRemoteFromPath(), model.getLocalTranPath());
				// 2.3 翻译本地文件
				translation.doTrans(model);
				// 2.4 将本地文件的文件复制到远程
				FileUtil.copyDirectory(model.getLocalTranPath(), model.getRemoteToPath());
				// 2.5 备份本地文件
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				String toPath = model.getLocalBackupPath() + "/" + sf.format(c.getTime()) + "/";
				FileUtil.copyDirectory(model.getLocalTranPath(), toPath);
			}
			// 3:将待翻译的写入EXCEL表,并发送邮件
			{
				// 3.1:删除原来的
				FileUtil.deleteDirectory(project.getFromExcelSimp(), false);
				// 3.2:写入新的数据,并发送邮件
				WriteEntranslated2Excel entranslated2Excel = new WriteEntranslated2Excel(project);
				entranslated2Excel.write2Excel();
			}

		} catch (Exception e) {
			MailUtil mailUtil = new MailUtil();
			mailUtil.send(e.toString());
			e.printStackTrace();
		}
	}
}
