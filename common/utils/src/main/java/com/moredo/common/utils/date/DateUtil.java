package com.moredo.common.utils.date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by loop on 14-1-9.
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DD_MM_YYYY_HH_MM = "dd-MM-yyyy HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    private static final Map<Integer, String> DAY_OF_WEEK = new HashMap<Integer, String>();

    static {
        DAY_OF_WEEK.put(1, "monday");
        DAY_OF_WEEK.put(2, "tuesday");
        DAY_OF_WEEK.put(3, "wednesday");
        DAY_OF_WEEK.put(4, "thursday");
        DAY_OF_WEEK.put(5, "friday");
        DAY_OF_WEEK.put(6, "saturday");
        DAY_OF_WEEK.put(7, "sunday");
    }

    /**
     * 返回前缀加英文的星期几
     * 如：prefix : ip. ; dayOfWeek : 1 =  ip.monday
     *
     * @param dayOfWeek
     * @param prefix
     * @return
     */
    public static String getDayOfWeekString(int dayOfWeek, String prefix) {
        if (StringUtils.isNotBlank(prefix)) {
            return prefix + DAY_OF_WEEK.get(dayOfWeek);
        } else {
            return DAY_OF_WEEK.get(dayOfWeek);
        }
    }

    public static String midnight(String pattern) {
        return DateTime.now().withTimeAtStartOfDay().toString(pattern);
    }

    public static Date stringToDate(String pattern, String date) {
        if (StringUtils.isEmpty(date)) return null;
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        DateTime dateTime = DateTime.parse(date, format);
        return dateTime.toDate();
    }

    public static int getDayOfWeek(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getDayOfWeek();
    }

    public static String dateToString(String pattern, Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(pattern);
    }

    public static Date plusDaysWithTimeAtEndOfDay(int days) {
        return DateTime.now().withTimeAtStartOfDay().plusDays(days + 1).plusMillis(-1).toDate();
    }

    public static Date minusDaysWithTimeAtEndOfDa(int days) {
        return DateTime.now().withTimeAtStartOfDay().minusDays(days).toDate();
    }

    public static Date minusDays(int days, Date date) {
        return new DateTime(date).minusDays(days).toDate();
    }

    public static Date plusDaysOfDate(Date date) {
        return new DateTime(date).withTimeAtStartOfDay().plusDays(1).toDate();
    }

    public static Date plusDaysOfDateAndDays(Date date, int days) {
        return new DateTime(date).withTimeAtStartOfDay().plusDays(days).toDate();
    }

    public static Date withTimeAtEndOfDay(Date date) {
        return new DateTime(date).withTimeAtStartOfDay().plusDays(1).plusMillis(-1).toDate();
    }

    public static Date plusDaysOfDateAndMin(Date date, int min) {
        return new DateTime(date).withTimeAtStartOfDay().plusMinutes(min).toDate();
    }

    public static Date plusDaysOfDateAndSec(Date date, int second) {
        return new DateTime(date).withTimeAtStartOfDay().plusSeconds(second).toDate();
    }

    public static Date withTimeAtStartOfDay(Date date) {
        return new DateTime(date).withTimeAtStartOfDay().toDate();
    }

    public static Date withTimeAtMinute(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.withSecondOfMinute(0).withMillisOfSecond(0).toDate();
    }

    public static Date plusYears(int year) {
        DateTime dateTime = DateTime.now().withTimeAtStartOfDay();
        return dateTime.plusYears(year).toDate();
    }

    public static Date plusMouths(int mouth) {
        DateTime dateTime = DateTime.now().withTimeAtStartOfDay();
        return dateTime.plusMonths(mouth).toDate();
    }

    public static Date plusMouths(int mouth, Date date) {
        DateTime dateTime = new DateTime(date).withTimeAtStartOfDay();
        return dateTime.plusMonths(mouth).toDate();
    }

    public static Date plusDays(int days, Date data) {
        DateTime dateTime = new DateTime(data);
        return dateTime.plusDays(days).toDate();
    }

    public static Date plusHours(int hours, Date data) {
        DateTime dateTime = new DateTime(data);
        return dateTime.plusHours(hours).toDate();
    }

    public static Date plusMinutes(int minutes, Date data) {
        DateTime dateTime = new DateTime(data);
        return dateTime.plusMinutes(minutes).toDate();
    }

    public static boolean isAfterOrEquals(Date date1, Date date2) {
        return date1.compareTo(date2) >= 0;
    }

    public static boolean isAfter(Date date1, Date date2) {
        return date1.compareTo(date2) > 0;
    }

    public static boolean startOfDayIsAfter(Date date1, Date date2) {
        return withTimeAtStartOfDay(date1).compareTo(withTimeAtStartOfDay(date2)) > 0;
    }

    public static boolean endOfDayIsAfter(Date date1, Date date2) {
        return withTimeAtEndOfDay(date1).compareTo(withTimeAtEndOfDay(date2)) > 0;
    }

    public static boolean isBetween(Date date, Date startDate, Date endDate) {
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
    }

    public static boolean isEquals(Date date1, Date date2) {
        return date1.compareTo(date2) == 0;
    }

    public static boolean startOfDayIsNotEquals(Date date1, Date date2) {
        return withTimeAtStartOfDay(date1).compareTo(withTimeAtStartOfDay(date2)) != 0;
    }

    public static boolean endOfDayIsNotEquals(Date date1, Date date2) {
        return withTimeAtEndOfDay(date1).compareTo(withTimeAtEndOfDay(date2)) != 0;
    }

    public static boolean minuteIsNotEquals(Date date1, Date date2) {
        return withTimeAtMinute(date1).compareTo(withTimeAtMinute(date2)) != 0;
    }

    public static int countDifferDays(Date endTime, Date startTime) {
        long end = endTime.getTime();
        long start = startTime.getTime();

        if (end < start){
            return (int) ((start - end) / (24 * 3600 * 1000)) + 1;
        }
        return (int) ((end - start) / (24 * 3600 * 1000)) + 1;
    }
    public static int subDifferDays(Date endTime, Date startTime) {
        long end = endTime.getTime();
        long start = startTime.getTime();
        return (int) ((end - start) / (24 * 3600 * 1000)) + 1;
    }

    /**
     * 计算两个时间相关多少小时
     * @param endTime 结束日期
     * @param startTime 开始日期
     * @return
     */
    public static double countDifferHours(Date endTime, Date startTime) {
        return (endTime.getTime() - startTime.getTime()) / (3600 * 1000);
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) return "";
        if (pattern == null) pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return (sdf.format(date));
    }

    public static Date stringToStartDateTime(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) return null;
        DateTimeFormatter format = DateTimeFormat.forPattern(YYYY_MM_DD_HH_MM_SS);
        DateTimeFormatter formatDate = DateTimeFormat.forPattern(YYYY_MM_DD);
        dateStr = dateStr + " 00:00:00";
        DateTime dateTime = DateTime.parse(dateStr, format);
        return dateTime.toDate();
    }

    public static Date stringToEndDateTime(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) return null;
        DateTimeFormatter format = DateTimeFormat.forPattern(YYYY_MM_DD_HH_MM_SS);
        DateTimeFormatter formatDate = DateTimeFormat.forPattern(YYYY_MM_DD);
        dateStr = dateStr + " 00:00:00";
        DateTime dateTime = DateTime.parse(dateStr, format);
        return dateTime.toDate();
    }

    /**
     * @param dateStr
     * @return
     * @author ZaoSheng
     */
    public static Date stringToEndDate(String dateStr) {
        return stringToEndDate(dateStr, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @param dateStr
     * @param format
     * @return
     * @author ZaoSheng
     */
    public static Date stringToEndDate(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr)) return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        try {
            return dateFormat.parse(dateStr.trim());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * @param dateStr
     * @param format
     * @return
     * @author ZaoSheng
     */
    public static Date toEndDate(String dateStr, String format) throws ParseException {
        if (StringUtils.isEmpty(dateStr)) return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.parse(dateStr.trim());

    }

    /**
     * 默认格式化yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        if (date == null) return "";
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return (sdf.format(date));
    }

    //获取当前年份
    public static int getCurrentYear(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getYear();
    }

    //获取当前月份
    public static int getCurrentMonth(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getMonthOfYear();
    }

    public static int getCurrentDayCount(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    public static Timestamp getFormatTimeStamp(String time) {
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        try {
            return normalTime(time);
        } catch (Exception e) {
            logger.error("无法处理的日期格式：[" + time + "]");
            return null;
        }
    }

    private static Timestamp normalTime(String time) {
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        String[] dates = time.split(" ");
        String date1 = dates[0];
        String date2 = dates.length == 2 ? dates[1] : null;
        String year, month, day, hour, min, sec;
        if (StringUtils.isNotEmpty(date1) && date1.length() > 10) {
            date1 = date1.substring(0, 10);
        }
        String[] temp = date1.split("-");
        if (temp == null || temp.length != 3) {
            return null;
        }
        year = temp[0];
        month = temp[1];
        day = temp[2];
        if (StringUtils.isEmpty(date2)) {
            date2 = "00:00:00";
        }
        temp = date2.split(":");
        if (temp.length == 3) {
            hour = temp[0];
            min = temp[1];
            sec = temp[2];
        } else if (temp.length == 2) {
            hour = temp[0];
            min = temp[1];
            sec = "00";
        } else {
            hour = temp[0];
            min = "00";
            sec = "00";
        }
        return Timestamp.valueOf(year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec);
    }

//    public static void main(String[] args) {
//        System.out.println(countDifferHours(new Date(), stringToDate(YYYY_MM_DD_HH_MM_SS, "2017-06-15 16:27:12")));
//    }
}
