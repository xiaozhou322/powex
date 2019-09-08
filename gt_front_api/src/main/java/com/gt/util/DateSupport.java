package com.gt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DateSupport {
	
	private static final Logger logger = LoggerFactory.getLogger("logger");

	/**
	 * 将字符日期按照指定的格式转换为日期型
	 * 
	 * @param date
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return
	 * @author tangshengyu
	 * @version falvm
	 * @date Dec 9, 2009
	 * @return Date
	 */
	public static Date strForDate(String date, String format) {
		Date dateTime = null;
		if (date == null || "".equals(date)) {
			return dateTime;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			dateTime = formatter.parse(date);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return dateTime;

	}

	/**
	 * 将日期转为指定格式的字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return
	 * @author tangshengyu
	 * @version falvm
	 * @date Dec 9, 2009
	 * @return String
	 */
	public static String dateForString(Date date, String format) {
		String dateTime = "";
		if (date == null) {
			return dateTime;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		dateTime = formatter.format(date);
		return dateTime;
	}
	
	public static String datePlusDay(String strDate,int plus){
		Date date = strForDate(strDate, "yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int D = calendar.get(Calendar.DAY_OF_MONTH);
		date.setDate(D+plus);
		return dateForString(date, "yyyy-MM-dd");
	}
	
	public static Calendar getDateCalendar(String strDate){
		Date date = strForDate(strDate, "yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	/**
	 * 获取月的最大值
	 * 
	 * @param strDate
	 * @return
	 * @author tangshengyu
	 * @version panyu
	 * @date	Jul 16, 2010 
	 * @return  int
	 */
	public static int getDateMonthLastDay(String strDate){
		Calendar calendar = getDateCalendar(strDate);
		return calendar.getActualMaximum(Calendar.MONTH);
	}

}
