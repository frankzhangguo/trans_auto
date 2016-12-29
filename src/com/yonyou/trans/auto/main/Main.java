package com.yonyou.trans.auto.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.yonyou.trans.auto.Translation;
import com.yonyou.trans.auto.WriteEntranslated2Excel;
import com.yonyou.trans.auto.model.MailContents;
import com.yonyou.trans.auto.model.Model;
import com.yonyou.trans.auto.model.Project;
import com.yonyou.trans.tools.FileUtil;
import com.yonyou.trans.tools.MailUtil;
import com.yonyou.trans.tools.Tools;

/**
 * @author Frank
 * 
 */
public class Main {
	/**
	 * ���������
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		start(true);
	}

	/**
	 * ֻҪ��һ��ģ����Ҫ���룬 ����Ҫ����
	 * 
	 * @return
	 */
	public static boolean need2Work() {
		Project project = Project.getProjectConfig();
		List<Model> models = project.getModelsList();
		for (Model model : models) {// ֻҪ��һ��ģ����Ҫ���룬 ����Ҫ����
			if (workday(model)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 
	 * @param isSingleProcess
	 */
	public static void start(boolean isSingleProcess) {

		if (!isSingleProcess) {
			// ���������Ҫ���룬 �ͷ��ذ�
			if (!need2Work()) {
				return;
			}
		}

		// clear the contents for comments
		MailContents.clearContents();

		// start translation
		Translation translation = new Translation();
		Project project = Project.getProjectConfig();
		try {
			// 1:������õ�EXCEL�����ϵͳ
			{
				// 1.1 ����EXCEL
				translation.readNewTrans(project);
				// 1.2 ��ɺ�ɾ�����ص��ϴ�������ļ�
				FileUtil.deleteDirectory(project.getToDatabaseDir(), false);
			}
			// 2������
			List<Model> models = project.getModelsList();
			for (Model model : models) {
				if (!isSingleProcess) {
					if (!workday(model)) {
						MailContents
								.addComments("ģ�����ƣ�[" + model.getName() + "] ��" + Tools.getCurrentWeekday() + "������");
						continue;
					}
				}

				MailContents.addComments("ģ�����ƣ�" + model.getName());

				// 2.1 ɾ�����صĵȴ�������ļ�
				FileUtil.deleteDirectory(model.getLocalTranPath(), false);
				// 2.2 ��Զ�̵��ļ����Ƶ�����
				FileUtil.copyDirectory(model.getRemoteFromPath(), model.getLocalTranPath());
				// 2.3 ���뱾���ļ�
				translation.doTrans(model);
				// 2.4 �������ļ����ļ����Ƶ�Զ��
				FileUtil.copyDirectory(model.getLocalTranPath(), model.getRemoteToPath());
				// 2.5 ���ݱ����ļ�
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				String toPath = model.getLocalBackupPath() + "/" + sf.format(c.getTime()) + "/";
				FileUtil.copyDirectory(model.getLocalTranPath(), toPath);
			}
			// 3:���������д��EXCEL��,�������ʼ�

			if (true) {
				// 3.1:ɾ��ԭ����
				FileUtil.deleteDirectory(project.getFromExcelSimp(), false);
				// 3.2:д���µ�����,�������ʼ�
				WriteEntranslated2Excel entranslated2Excel = new WriteEntranslated2Excel(project);
				entranslated2Excel.write2Excel();
			}

		} catch (Exception e) {
			MailUtil mailUtil = new MailUtil();
			mailUtil.send(e.getMessage());
		}

	}

	/**
	 * 
	 * @param model
	 * @return �Ƿ��ģ����Ҫ����
	 */
	public static boolean workday(Model model) {
		String weekday = model.getWeekday();
		if (weekday != null) {
			String[] weekdays = weekday.split(",");
			for (String workday : weekdays) {
				Integer week = new Integer(workday);
				if (Tools.getCurrentWeekday() == week.intValue()) {
					return true;
				}
			}
		}
		return false;
	}
}
