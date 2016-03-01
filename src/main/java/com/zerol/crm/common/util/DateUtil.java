package com.zerol.crm.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtil {

	static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static final String DEFAULT_TIMEZONE = "Aisa/Shanghai";
	static final TimeZone timezone = TimeZone.getTimeZone(DEFAULT_TIMEZONE);

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public static final String FORMAT_DATE_YYYYMMDD = "yyyyMMdd";
	public static final String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String FORMAT_DATE_TIMESTAMP = "yyyy-MM-dd HH24:mm:ss";
	public static final String FORMAT_STANDARD_TIME_HH24MMSS = "HH24:mm:ss";

	public static final String FORMAT_DATE_YYYYMMDD_WITH_SPLASH = "yyyy/MM/dd";
	public static final String FORMAT_DATE_DDMMYYYY_WITH_SPLASH = "dd/MM/yyyy";

	public static final String MYSQL_DATE_TIME_FORMAT = "%Y-%m-%d %H:%i:%s";
	public static final String MYSQL_DATE_FORMAT = "%Y-%m-%d";

	static {
		TimeZone.setDefault(timezone);
	}

	/**
	 * Date string format validate
	 * 
	 * @param format
	 * @param value
	 * @return
	 */
	public static boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			formatter.setLenient(false); //make it strict
			date = formatter.parse(value);
		} catch (ParseException ex) {
			return false;
		}
		return date != null;
	}

	public static String getLastDayOfMonth(String yyyyAndMM) {
		if (!isValidFormat("yyyy-MM", yyyyAndMM)) {
			logger.error("Invaild parameter[yyyy-MM] : ", yyyyAndMM);
			return null;
		}
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Integer.parseInt(yyyyAndMM.substring(0, 4)), Integer.parseInt(yyyyAndMM.substring(5, 7)) - 1, 1);
		return yyyyAndMM + "-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	static String[] str = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	public static String getMonthName(String month){
		int n = Integer.parseInt(month);
		if(n>12 || n<1){
		    throw new RuntimeException("Month num exceed range [1~12] : " + month);
		}
		return str[n];
	}

	
	public static Date strToJavaDate(String format , String value)throws ParseException{
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			formatter.setLenient(false); //make it strict
			date = formatter.parse(value);
		} catch (ParseException e) {
			throw new ParseException("Failed to parse the "+value+" to Date by format["+format+"].", 1);
		}
		return date;
	}

	public static java.sql.Date strToSqlDate(String format , String value)throws ParseException{
		Date utilDate = strToJavaDate(format, value);
		return convertJavaDateToSqlDate(utilDate);
	}

	public static Date convertSqlDateToJavaDate(java.sql.Date sqlDate)throws ParseException{
		Date javaDate = null;
		if (sqlDate != null) {
			javaDate = new Date(sqlDate.getTime());
		}
		return javaDate;
	}

	public static java.sql.Date convertJavaDateToSqlDate(Date javaDate)throws ParseException{
		java.sql.Date sqlDate = null;
		if (javaDate != null) {
			sqlDate = new java.sql.Date(javaDate.getTime());
		}
		return sqlDate;
	}

	public static String dateToStr(String format, Date date){
		String str = null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setLenient(false); //make it strict
		str = formatter.format(date);
		return str;
	}

	/**
	 * Get current date, format yyyy/MM/dd.
	 *
	 * @return
	 */
	public static String getCurrentDate() {
		return formatDate(getCurrentTimestamp());
	}

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(getDate().getTime());
	}

	public static Date getDate() {
		TimeZone tz = TimeZone.getTimeZone(DEFAULT_TIMEZONE);
		Calendar calendar = Calendar.getInstance(tz);
		return calendar.getTime();
	}

	public static String formatDate(Date iDate) {
		return formatDate(iDate, FORMAT_DATE_YYYY_MM_DD);
	}

	public static String formatDate(Date iDate, String sDatePattern) {
		if (iDate == null) {
			return null;
		}
		SimpleDateFormat oFormat = new SimpleDateFormat(sDatePattern);
		oFormat.setLenient(false);
		return oFormat.format(iDate);
	}
}
