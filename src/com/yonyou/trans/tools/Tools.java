/*
 *  工具集，用来收集一些公共的函数做成静态，完成代码的复用性
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
