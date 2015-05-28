package com.hardrock.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {
	private final static int[] monthDays = new int[]{
//			31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
			0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334
	};
	
	/**
	 * "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
	 * @param dateTime
	 * @return
	 */
	public static Date parseMongoDateTime(String dateTime){
		int year = dateTime.charAt(0) * 1000 + dateTime.charAt(1) * 100 + dateTime.charAt(2) * 10 + dateTime.charAt(3) - 53328;
		int month = dateTime.charAt(5) * 10 + dateTime.charAt(6) - 528;
		int day = dateTime.charAt(8) * 10 + dateTime.charAt(9) - 528;
		int hour = dateTime.charAt(11) * 10 + dateTime.charAt(12) - 528;
		int minute = dateTime.charAt(14) * 10 + dateTime.charAt(15) - 528;
		int second = dateTime.charAt(17) * 10 + dateTime.charAt(18) - 528;
		int millSec = 0;
		if(dateTime.length() == 24){
			millSec = dateTime.charAt(20) * 100 + dateTime.charAt(21) * 10 + dateTime.charAt(22) - 5328;
		}
		else{
			millSec = dateTime.charAt(20) * 100 - 4800;
		}
		//cal the days from 1900 to year
		//((year % 4 == 0) ? 1 : 0) 这里要month>=3才会有
		int days = (year - 1970) * 365 + (year - 1970 + 1)/4 + monthDays[month-1] + ((year % 4 == 0) && month >= 3 ? 1 : 0) + (day - 1);
		long timeMills = DateUtils.MILLIS_PER_DAY * days + (hour - 8) * DateUtils.MILLIS_PER_HOUR + minute * DateUtils.MILLIS_PER_MINUTE + second * DateUtils.MILLIS_PER_SECOND + millSec;
		return new Date(timeMills);
	}
	
	public static void main(String[] args) throws InterruptedException, ParseException {
		System.out.println(DateUtil.parseMongoDateTime("1970-01-27T06:44:00.0Z"));
		System.out.println(DateUtil.parseMongoDateTime("2014-03-27T06:44:00.000Z"));
		System.out.println(DateUtil.parseMongoDateTime("2013-08-27T06:44:00.000Z"));
		System.out.println(DateUtil.parseMongoDateTime("2013-01-08T14:04:45.968Z"));
		System.out.println(DateUtil.parseMongoDateTime("2015-02-28T14:46:45.968Z"));
		System.out.println(DateUtil.parseMongoDateTime("2008-02-29T16:00:00.000Z"));
		System.out.println(DateUtil.parseMongoDateTime("2008-02-28T16:00:00.000Z"));
		System.out.println(DateUtil.parseMongoDateTime("2008-04-04T16:00:00.0Z"));
	}
}