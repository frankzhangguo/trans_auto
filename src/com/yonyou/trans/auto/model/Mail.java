package com.yonyou.trans.auto.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Mail����ʵ��
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
		sb.append("��ϲ����\r\n �Զ����������" + sf.format(c.getTime()) + "������ϣ�");

		if (sheetVOs == null || sheetVOs.length == 0) {
			sb.append("û�д����������!");
		} else {
			sb.append("�������嵥���£�");
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
		return "�������嵥";
	}

}