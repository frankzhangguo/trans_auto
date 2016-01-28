/*
 *  ���߼��������ռ�һЩ�����ĺ������ɾ�̬����ɴ���ĸ�����
 */

package com.yonyou.trans.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class Tools {
	public static void copyFile(String resFilePath, String distFolder) throws IOException {
		File resFile = new File(resFilePath);
		File distFile = new File(distFolder);
		if (resFile.isDirectory()) {
			FileUtils.copyDirectoryToDirectory(resFile, distFile);
		} else if (resFile.isFile()) {
			FileUtils.copyFileToDirectory(resFile, distFile, true);
		}
	}

	public static java.util.List<String> find(String path) {
		java.util.List<String> list = new ArrayList<>();
		File file = new File(path);
		File[] files = file.listFiles();

		// ����ļ�����Ϊnull�򷵻�
		if (files == null) {
			return null;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				// �ж��ǲ����ļ��У�������ļ�����������²����ļ�
				java.util.List<String> subList = find(files[i].getAbsolutePath());
				if (subList != null && subList.size() > 0) {
					for (String string : subList) {
						list.add(string);
					}
				}
			} else {
				// ��¼�ļ�·��
				String filePath = files[i].getAbsolutePath().toLowerCase();

				list.add(filePath);
			}
		}
		return list;

	}

	// ��ȡϵͳ��ǰʱ��
	public static String getlocaldatetime() {

		java.util.Calendar c = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return f.format(c.getTime());
	}

	// �ж�һ���ַ����Ƿ������֣����Ƿ����쳣���ж�
	@SuppressWarnings("unused")
	public static boolean isNum(String ch) {
		try {
			double i = Double.parseDouble(ch);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
