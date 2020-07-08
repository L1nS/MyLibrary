package com.lins.modulesystem.utils;

import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class BaseDateUtils {
    /**
     * yyyy-MM-dd HH:mm:ss字符串
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * yyyy-MM-dd字符串
     */
    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";


    /**
     * 计算小说更新时间
     *
     * @param updateTime
     * @return
     */
    public static String getNovelUpdateTime(long updateTime) {
        String time;
        long temp = getCurTimeLong() - updateTime;
        if (temp / 60 < 1) {
            time = "刚刚";
        } else {
            temp = temp / 60;
            if (temp / 60 < 1) {
                time = temp % 60 + "分钟前";
            } else {
                temp = temp / 60;
                if (temp / 24 < 1) {
                    time = temp % 24 + "小时前";
                } else {
                    time = temp / 24 + "天前";
                }
            }
        }
        return time;
    }

    /**
     * 时间差
     * @param endTime
     * @return
     */
    public static String getDifferenceTime(long endTime) {
        long nowTime = System.currentTimeMillis();
        if (nowTime > endTime) {
            return "00天00小时";
        } else {
            long diffTime = (endTime - nowTime) / 1000;
            if (diffTime / 3600 < 1) {
                return "00天00小时";
            } else {
                String h;
                String d = "00天";
                diffTime = diffTime / 3600;
                if (diffTime / 24 < 1) {
                    h = diffTime % 24 + "小时";
                    return d + h;
                } else {
                    h = diffTime % 24 + "小时";
                    d = diffTime / 24 + "天";
                    return d + h;
                }
            }
        }
    }


    /**
     * 获取当前系统时间戳,精确到秒
     *
     * @return
     */
    public static long getCurTimeLong() {
        long time = System.currentTimeMillis() / 1000;
        return time;
    }

    /**
     * 获取系统日期
     *
     * @return
     */
    public static int getCurDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 根据时间戳获取Day
     *
     * @param time
     * @return
     */
    public static int getDay(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        Date date = new Date(System.currentTimeMillis());
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    /**
     * 根据时间戳获取Hour
     *
     * @param time
     * @return
     */
    public static int getHour(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        Date date = new Date(System.currentTimeMillis());
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    /**
     * 获取当前时间Hour
     *
     * @return
     */
    public static int getCurHour() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        Date date = new Date(System.currentTimeMillis());
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    /**
     * 将时间戳转化为yyyy-MM-dd HH:mm
     *
     * @param time
     * @return
     */
    public static String getDateMinuteFormate(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间戳转化为yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static String getDateFormate(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        return simpleDateFormat.format(date);

    }

    public static String getDateFormate2(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前时间
     *
     * @return HH:mm:ss
     */
    public static String getCurHmsTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获得几天之前或者几天之后的日期
     *
     * @param diff 差值：正的往后推，负的往前推
     * @return
     */
    public static String getOtherDay(int diff) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, diff);
        return getDateFormat(mCalendar.getTime());
    }

    /**
     * 将date转成yyyy-MM-dd字符串<br>
     *
     * @param date Date对象
     * @return yyyy-MM-dd
     */
    public static String getDateFormat(Date date) {
        return dateSimpleFormat(date, defaultDateFormat.get());
    }

    /**
     * 将date转成字符串
     *
     * @param date   Date
     * @param format SimpleDateFormat
     *               <br>
     *               注： SimpleDateFormat为空时，采用默认的yyyy-MM-dd HH:mm:ss格式
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String dateSimpleFormat(Date date, SimpleDateFormat format) {
        if (format == null)
            format = defaultDateTimeFormat.get();
        return (date == null ? "" : format.format(date));
    }


    /**
     * yyyy-MM-dd格式
     */
    public static final ThreadLocal<SimpleDateFormat> defaultDateFormat = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DEFAULT_FORMAT_DATE);
        }
    };


    /**
     * yyyy-MM-dd HH:mm:ss格式
     */
    public static final ThreadLocal<SimpleDateFormat> defaultDateTimeFormat = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        }

    };

    /**
     * 判断星期几，1=周日，7=周六
     *
     * @param day
     * @return
     */
    public static boolean isDayOfWeek(String day) {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        Logger.d("mWay:" + mWay);
        if (day.equals(mWay)) {
            return true;
        } else
            return false;
    }

}
