package com.villege.dade.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static String getDateStr(Date aDate, String datePattern) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	public static String getDateStr(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			String datePattern = "yyyy-MM-dd";
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	public static String getDateTimeStr(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			String datePattern = "yyyy-MM-dd HH:mm:ss";
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	public static Date getDate(String dateStr) throws ParseException {
		String[] f = { "yyyy-MM-dd" };
		return DateUtils.parseDate(dateStr, f);
	}

	public static Date getDateTime(String dateTimeStr) throws ParseException {
		String[] f = { "yyyy-MM-dd HH:mm:ss" };
		return DateUtils.parseDate(dateTimeStr, f);
	}

	public static Date getDateTime(String dateStr, String format)
			throws ParseException {
		String[] f = { format };
		return DateUtils.parseDate(dateStr, f);
	}

	public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		XMLGregorianCalendar gc = null;
		try {
			gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (DatatypeConfigurationException e) {
			Log log = LogFactory.getLog(DateUtil.class);
			log
					.debug(
							"There are some error for convert java.util.Date to XMLGregorianCalendar",
							e);
		}

		return gc;
	}

	/**
	 * 判断某一时间是否在一个范围内（time大于等于begin，小于end）
	 * 
	 * @param time
	 *            要判断的时间
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 在指定的范围返回true，否则返回false
	 */
	public static boolean isTimeInRange(long time, long begin, long end) {
		if (time >= begin && time < end) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断某一时间是否在一个范围内（time大于等于begin，小于end）
	 * 
	 * @param time
	 *            要判断的时间
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 在指定的范围返回true，否则返回false
	 */
	public static boolean isTimeInRange(Date time, Date begin, Date end) {
		if (time.after(begin) && time.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断某一时间的时分秒部分是否在指定范围段内
	 * 
	 * @param time
	 *            要判断的时间
	 * @param dateRangs
	 *            范围数组，格式为HH:mm:ss-HH:mm:ss
	 * @return 在指定的范围段返回true，否则返回
	 */
	public static boolean isTimePartInRange(Date time, String[] dateRangs) {
		for (String datestr : dateRangs) {
			String[] str = datestr.split("-");
			if (isTimePartInRange(time, str[0], str[1])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断某一时间的时分秒部分是否在一个范围内
	 * 
	 * @param time
	 *            要判断的时间
	 * @param begin
	 *            开始时间，格式为HH:mm:ss
	 * @param end
	 *            结束时间，格式为HH:mm:ss
	 * @return 在指定的范围返回true，否则返回false
	 */
	public static boolean isTimePartInRange(Date time, String begin, String end) {
		String[] starts = begin.split(":");
		String[] stops = end.split(":");

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(time);
		cal1.set(Calendar.HOUR_OF_DAY, Integer.valueOf(starts[0]));
		cal1.set(Calendar.MINUTE, Integer.valueOf(starts[1]));
		cal1.set(Calendar.SECOND, Integer.valueOf(starts[2]));
		cal1.set(Calendar.MILLISECOND, 0);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(time);
		cal2.set(Calendar.HOUR_OF_DAY, Integer.valueOf(stops[0]));
		cal2.set(Calendar.MINUTE, Integer.valueOf(stops[1]));
		cal2.set(Calendar.SECOND, Integer.valueOf(stops[2]));
		cal2.set(Calendar.MILLISECOND, 0);

		if (time.after(cal1.getTime()) && time.before(cal2.getTime())) {
			return true;
		} else {
			return false;
		}
	}
}
