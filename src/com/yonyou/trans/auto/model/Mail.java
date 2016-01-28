package com.yonyou.trans.auto.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Mail属性实体
 * 
 * @author frank
 * 
 */
@SuppressWarnings("serial")
public class Mail implements Serializable {

	public static String getMessage(Trans_simpchnVO[] sheetVOs) {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		sb.append("恭喜您！\r\n 自动化翻译软件" + sf.format(c.getTime()) + "翻译完毕，");

		if (sheetVOs == null || sheetVOs.length == 0) {
			sb.append("没有待翻译的内容!");
		} else {
			sb.append("待翻译清单如下：");
			sb.append("\r\n");
			for (Trans_simpchnVO trans_simpchnVO : sheetVOs) {
				sb.append("      " + trans_simpchnVO.getSimpchn());
				sb.append("\r\n");
			}
		}
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("================END==================");
		return sb.toString();
	}

	public static String getSubject() {
		return "待翻译清单";
	}

}