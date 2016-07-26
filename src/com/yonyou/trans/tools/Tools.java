/*
 *  ���߼��������ռ�һЩ�����ĺ������ɾ�̬����ɴ���ĸ�����
 */

package com.yonyou.trans.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

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

	public static int getCurrentWeekday() {
		Calendar now = Calendar.getInstance();
		// һ�ܵ�һ���Ƿ�Ϊ������
		boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
		// ��ȡ�ܼ�
		int weekDay = now.get(Calendar.DAY_OF_WEEK);
		// ��һ�ܵ�һ��Ϊ�����죬��-1
		if (isFirstSunday) {
			weekDay = weekDay - 1;
			if (weekDay == 0) {
				weekDay = 7;
			}
		}
		return weekDay;
	}

	/**
	 * �õ�һ���ַ����ĳ���,��ʾ�ĳ���,һ�����ֻ��պ��ĳ���Ϊ1,Ӣ���ַ�����Ϊ0
	 * 
	 * @param String
	 *            s ��Ҫ�õ����ȵ��ַ���
	 * @return int �õ����ַ�������
	 */
	public static Integer getLength(String s) {
		Integer valueLength = 0;

		if (s == null) {
			return valueLength;
		}
		String chinese = "[\u4e00-\u9fa5]";
		// ��ȡ�ֶ�ֵ�ĳ��ȣ�����������ַ�����ÿ�������ַ�����Ϊ2������Ϊ1
		for (int i = 0; i < s.length(); i++) {
			// ��ȡһ���ַ�
			String temp = s.substring(i, i + 1);
			// �ж��Ƿ�Ϊ�����ַ�
			if (temp.matches(chinese)) {
				// �����ַ�����Ϊ1
				valueLength += 1;
			} else {
				// �����ַ�����Ϊ0
				valueLength += 0;
			}
		}
		// ��λȡ��
		return valueLength;
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
