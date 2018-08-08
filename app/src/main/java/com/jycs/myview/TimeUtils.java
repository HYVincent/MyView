package com.jycs.myview;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Reinhard Tristan Eugen Heydrich on 2016/12/12 15:20
 */
public class TimeUtils {


    public static final String DATE_FORMAT_1 = "yyyy年MM月dd号";
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_3 = "yyyy-MM-dd";


    public static String getTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String getDate(String format) {
        return getDate(format, System.currentTimeMillis());
    }

    public static String getDate(String format, long millisecond) {
        return new SimpleDateFormat(format).format(new Date(millisecond));
    }


    /**
     * 字符串转时间戳
     * @param date 日期
     * @return 时间戳
     */
    public static long getTime(String format, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = new Date();
        try {
            d = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d.getTime();
    }


    /**
     * 日期转时间戳
     * @param time
     * @return
     */
    public static long getTimeMillis(String format, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();
    }



    /**
     * 判断是非闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }


    /**
     * 获得指定日期的后一天
     *
     * @return
     */
    public static String getSpecifiedDayAfter(Date date, int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + i);
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        String dayAfter = y + "-" + m + "-" + d;
        return dayAfter;
    }


    /**
     * 获取当前年
     * @return
     */
    public static int getYears(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     * @return
     */
    public static int getMonth(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }


    /**
     * 获取当前日
     * @return
     */
    public static int getDay(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前小时
     * @return
     */
    public static int getHour(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前小时
     * @return
     */
    public static int getHour(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * 获取当前分钟
     * @return
     */
    public static int getMin(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取当前分钟
     * @return
     */
    public static int getMin(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取当前月的总天数
     * @return
     */
    public static int getDayCount(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取指定年月的总天数
     * @param years
     * @param month
     * @return
     */
    public static int getDay(int years, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, years);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DATE);

    }

    public static int getWeek(int year, int month, int day){
        int[] date = {7,1,2,3,4,5,6};
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return date[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }


    /**
     * 返回今天的日期
     *
     * @return
     */
    public static String getTodayDate() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        return sf.format(c.getTime());
    }

    /**
     * 返回指定时间的星期
     *
     * @param millis
     * @return
     */
    public static String getWeekOfDate(long millis) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 是否过去的时间
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static boolean getPastTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR, getYears());
        calendar2.set(Calendar.MONTH, getMonth() - 1);
        calendar2.set(Calendar.DAY_OF_MONTH, getDay());
        return calendar2.getTimeInMillis() >= calendar.getTimeInMillis();
    }
}