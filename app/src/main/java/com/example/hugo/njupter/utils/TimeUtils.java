package com.example.hugo.njupter.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final long DayInMillis = 24 * 3600 * 1000;
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    public static String getTime(SimpleDateFormat dateFormat){
        long time=System.currentTimeMillis();
        return dateFormat.format(new Date(time));
    }

    public static String dtFormat(Date date, String dateFormat) {
        return getFormat(dateFormat).format(date);
    }

    private static final DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    public static String getTime(int hour, int minute) {
        Date date = new Date();
        date.setHours(hour);
        date.setMinutes(minute);
        return TIME_FORMAT.format(date);
    }

    public static String getDate(long timeInMillis) {
        return getTime(timeInMillis, DATE_FORMAT_DATE);
    }

    public static String getDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, monthOfYear, dayOfMonth);
        return getTime(cal.getTimeInMillis(), DATE_FORMAT_DATE);
    }

    public static String getDate() {
        return getTime(getCurrentTimeInLong(), DATE_FORMAT_DATE);
    }

    public static String getTime() {
        return getTime(getCurrentTimeInLong(), DEFAULT_DATE_FORMAT);
    }

    public static String getTokenDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Log.d("--------","time is :"+dateFormat.format(new Date()));
        return dateFormat.format(new Date());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    public static byte[] getByteTime() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month = cal.get(Calendar.MONTH) + 1;//获取月份
        int day = cal.get(Calendar.DATE);//获取日
        int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
        int minute = cal.get(Calendar.MINUTE);//分
        int second = cal.get(Calendar.SECOND);//秒
        byte times[] = new byte[7];
        byte yearBytes[] = intToByte(year);
        times[0] = yearBytes[3];
        times[1] = yearBytes[2];
        byte monthBytes[] = intToByte(month);
        times[2] = monthBytes[3];
        byte dayBytes[] = intToByte(day);
        times[3] = dayBytes[3];
        byte hoursBytes[] = intToByte(hour);
        times[4] = hoursBytes[3];
        byte minutes[] = intToByte(minute);
        times[5] = minutes[3];
        byte seconds[] = intToByte(second);
        times[6] = seconds[3];
        return times;
    }

    public static Date strToDate(String str) {
        Date date = null;
        try {
            date = DATE_FORMAT_DATE.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * int 转 byte（）
     *
     * @param i 2016
     * @return E0 07 00 00
     */
    public static byte[] intToByte(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }





    public static int getDayNum(Date preDate, Date nextDate) {
        int dayNum = (int) ((nextDate.getTime() - preDate.getTime()) / (24 * 3600 * 1000));
        return dayNum;
    }

    public static String getRecentTime(long l) {
        long current =getCurrentTimeInLong()-l;
        int second =(int) (current%1000);
        if(second<60){
            return "1分钟前";
        }
        else if(second<3600){
            return String.valueOf(second%60)+"分钟前";
        }else{
            return String.valueOf(second%3600)+"小时前";
        }
    }
}
