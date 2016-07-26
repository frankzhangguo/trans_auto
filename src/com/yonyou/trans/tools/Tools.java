/*
 *  工具集，用来收集一些公共的函数做成静态，完成代码的复用性
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

		// 如果文件数组为null则返回
		if (files == null) {
			return null;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				// 判断是不是文件夹，如果是文件夹则继续向下查找文件
				java.util.List<String> subList = find(files[i].getAbsolutePath());
				if (subList != null && subList.size() > 0) {
					for (String string : subList) {
						list.add(string);
					}
				}
			} else {
				// 记录文件路径
				String filePath = files[i].getAbsolutePath().toLowerCase();

				list.add(filePath);
			}
		}
		return list;

	}

	public static int getCurrentWeekday() {
		Calendar now = Calendar.getInstance();
		// 一周第一天是否为星期天
		boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
		// 获取周几
		int weekDay = now.get(Calendar.DAY_OF_WEEK);
		// 若一周第一天为星期天，则-1
		if (isFirstSunday) {
			weekDay = weekDay - 1;
			if (weekDay == 0) {
				weekDay = 7;
			}
		}
		return weekDay;
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0
	 * 
	 * @param String
	 *            s 需要得到长度的字符串
	 * @return int 得到的字符串长度
	 */
	public static Integer getLength(String s) {
		Integer valueLength = 0;

		if (s == null) {
			return valueLength;
		}
		String chinese = "[\u4e00-\u9fa5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < s.length(); i++) {
			// 获取一个字符
			String temp = s.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为1
				valueLength += 1;
			} else {
				// 其他字符长度为0
				valueLength += 0;
			}
		}
		// 进位取整
		return valueLength;
	}

	// 获取系统当前时间
	public static String getlocaldatetime() {

		java.util.Calendar c = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return f.format(c.getTime());
	}

	// 判断一个字符串是否是数字，用是否抛异常来判断
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
