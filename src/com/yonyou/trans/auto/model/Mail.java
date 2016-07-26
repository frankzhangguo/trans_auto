package com.yonyou.trans.auto.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
		sb.append("\r\n");
		sb.append(MailContents.getContents());
		sb.append("\r\n");

		if (sheetVOs == null || sheetVOs.length == 0) {
			sb.append("没有新增待翻译的内容!");
		} else {

			HashMap<String, List<Trans_simpchnVO>> hashMap = new HashMap<String, List<Trans_simpchnVO>>();

			for (Trans_simpchnVO simpchnVO : sheetVOs) {
				List<Trans_simpchnVO> list = hashMap.get(simpchnVO.getSheetname());
				if (list == null) {
					list = new ArrayList<Trans_simpchnVO>();
				}
				list.add(simpchnVO);
				hashMap.put(simpchnVO.getSheetname(), list);
			}

			Iterator<String> iterator = hashMap.keySet().iterator();
			if (iterator.hasNext()) {
				String model = iterator.next();
				sb.append("\r\n");
				sb.append("待翻译清单如下：(" + model + ")");
				sb.append("\r\n");

				List<Trans_simpchnVO> list = hashMap.get(model);
				for (Trans_simpchnVO trans_simpchnVO : list) {
					sb.append("      " + trans_simpchnVO.getSimpchn());
					sb.append("\r\n");
				}
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