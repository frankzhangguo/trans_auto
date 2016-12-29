/**
 * 
 */
package com.yonyou.trans.auto.main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.yonyou.trans.auto.engine.AbstractEngine;
import com.yonyou.trans.auto.engine.DBClearEngine;

/**
 * @author Frank
 * 
 */
public class ScheduledService {

	/**
	 * 获取指定时间对应的毫秒数
	 * 
	 * @param time
	 *            "HH:mm:ss"
	 * @return
	 */
	private static long getTimeMillis(String time) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
			return curDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static boolean isWeekend() {
		boolean weekend = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			weekend = true;
		}
		return weekend;
	}

	public static void main(String[] args) {

		try {
			AbstractEngine engine = new DBClearEngine(null);
			engine.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**
		 * try { new
		 * NewTransWatcherService(Paths.get(Project.getProjectConfig().
		 * getToDatabaseDir())).handleEvents(); } catch (InterruptedException |
		 * IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// if (!isWeekend()) {
				boolean isSingleProcess = false;
				Main.start(isSingleProcess);
				// }
			}
		};
		// 下午2点翻译
		long oneDay = 24 * 60 * 60 * 1000;
		long initDelay = getTimeMillis("14:00:00") - System.currentTimeMillis();
		initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable, initDelay, oneDay, TimeUnit.MILLISECONDS);
	}
}
