package com.zno.heed.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */

public class DateCalculator {

	public static Date addYearsToDate(int years, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}

	public static long getHours(Date lockdate) {
		Date currdate = new Date();
		long hours = currdate.getTime() - lockdate.getTime();
		return hours / (60 * 60 * 1000);
	}

	public static Boolean checkExpiry(Date expiryDate) {
		return (expiryDate.before(new Date()));
	}

	/**
	 * This method returns the month name and year from the input date. Input =
	 * 2018-05-01 Output = "May 2018"
	 */
	public static String getMonthYear(Date date) {
		SimpleDateFormat targetFormat = new SimpleDateFormat(com.zno.heed.constants.DateFormat.MONTH_YEAR);
		return targetFormat.format(date);
	}

	public static String convertDateToString(String dateFormat, Date date) {
		if (date == null)
			return null;
		SimpleDateFormat targetFormat = new SimpleDateFormat(dateFormat);
		return targetFormat.format(date);
	}

	public static Date convertStringToDate(String dateFormat, String date) {
		if (date == null)
			return null;
		SimpleDateFormat targetFormat = new SimpleDateFormat(dateFormat);
		try {
			return targetFormat.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date getLastDayOfPreviousMonth() throws ParseException {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		DateFormat df = new SimpleDateFormat(com.zno.heed.constants.DateFormat.DATE_MON_YEAR);
		String dateCreated = df.format(c.getTime());
		return df.parse(dateCreated);
	}

	public static Date getLastDayOfPreviousMonth(int month) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -month);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		DateFormat df = new SimpleDateFormat(com.zno.heed.constants.DateFormat.DATE_MON_YEAR);
		String dateCreated = df.format(c.getTime());
		return df.parse(dateCreated);
	}

	public static Date getLastDayOf(String startDate) throws ParseException {
		DateFormat df = new SimpleDateFormat(com.zno.heed.constants.DateFormat.DATE_MON_YEAR);
		Date dateCreated = df.parse("01/" + startDate);
		Calendar c = Calendar.getInstance();
		c.setTime(dateCreated);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		String dateCreatedStr = df.format(c.getTime());
		return df.parse(dateCreatedStr);
	}


	private static final String dateFormatString = "yyyy-MM-dd HH:mm:ss.SSSZ";
	private static final FastDateFormat formatter = FastDateFormat.getInstance(dateFormatString);
	
	public static String getFastDateFormat() {
		return formatter.format(System.currentTimeMillis());
	}
	
	public Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return cal.getTime();
	}

	public int getExpirationInMinutes(int minutes,int hours,int days) {
		return minutes*hours*days;
	}
	
}
