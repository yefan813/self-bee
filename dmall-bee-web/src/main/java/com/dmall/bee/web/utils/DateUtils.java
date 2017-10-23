package com.dmall.bee.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private final static Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

	public static long getServerTime() {
		return System.currentTimeMillis();
	}

	public static Timestamp getCurrentTime() {
		Date date = new Date();
		return new Timestamp(date.getTime());
	}

	/**
	 * 格式化日期,默认返回yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 格式化日期,默认返回yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateformat(Date date) {
		return format(date, "yyyy-MM-dd");
	}


	/**
	 * 格式化显示当前日期
	 * 
	 * @param format
	 * @return
	 */
	public static String format(String format) {
		return format(new Date(), format);
	}

	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			LOGGER.error("日期格式化失败.{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 时间格式化， 传入毫秒
	 * 
	 * @param time
	 * @return
	 */
	public static String dateFormat(long time) {
		return format(new Date(time), "yyyy-MM-dd HH:mm:ss");
	}
	
	
	/**
	 * 得到时间年月字符串， 传入毫秒
	 * 
	 * @param time
	 * @return
	 */
	public static String monthFormat(long time) {
		return format(new Date(time), "yyyyMM");
	}
	/**
	 * 得到时间年月日字符串， 传入毫秒
	 * 
	 * @param time
	 * @return
	 */
	public static String dayStrFormat(long time) {
		return format(new Date(time), "yyyyMMdd");
	}
}
