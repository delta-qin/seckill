package com.deltaqin.seckill.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author deltaqin
 * @date 2021/6/13 下午9:58
 */
public class JodaTimeUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static DateTime strToJodaTime(String time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT);
        return dateTimeFormatter.parseDateTime(time);
    }

    public static Date strToDate(String time) {
        DateTime dateTime = strToJodaTime(time);
        return dateTime.toDate();
    }

    public static Date strToDate(String time, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = dateTimeFormatter.parseDateTime(time);
        return dateTime.toDate();
    }

    public static String dateToStr(Date time, String format) {
        if (time == null) {
            // 至少不要返回空指针异常
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(time);
        return dateTime.toString(format);
    }

    public static String dateToStr(Date time) {
        return dateToStr(time, DATE_FORMAT);
    }

    public static void main(String[] args) {
        System.out.println(JodaTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(JodaTimeUtil.strToDate("2021-06-13 22:10:45","yyyy-MM-dd HH:mm:ss"));
    }
}
