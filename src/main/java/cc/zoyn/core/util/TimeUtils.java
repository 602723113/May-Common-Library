package cc.zoyn.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class TimeUtils {

    // Prevent accidental construction
    private TimeUtils() {
    }

    private static final int YEAR = 365 * 24 * 60 * 60; // 年
    private static final int MONTH = 30 * 24 * 60 * 60; // 月
    private static final int DAY = 24 * 60 * 60; // 天
    private static final int HOUR = 60 * 60; // 小时
    private static final int MINUTE = 60; // 分钟
    private static Calendar calendar = Calendar.getInstance();

    /**
     * 根据时间戳获取描述性时间，如3分钟前，1天前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */
    public static String getDescriptionTimeFromTimestamp(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
        String timeStr;
        if (timeGap > YEAR) {
            timeStr = timeGap / YEAR + "年前";
        } else if (timeGap > MONTH) {
            timeStr = timeGap / MONTH + "个月前";
        } else if (timeGap > DAY) {// 1天以上
            timeStr = timeGap / DAY + "天前";
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = timeGap / HOUR + "小时前";
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    /**
     * Long型时间格式化
     *
     * @param time       时间
     * @param dateFormat 日期格式,如yyyy年MM月dd日 HH:mm:ss
     * @return 格式化后的日期字符串
     */
    public static String timeToFormatDate(Long time, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(time);
    }

    /**
     * long型时间转中式日期
     *
     * @param time 时间
     * @return
     */
    public static String timeToDate(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return format.format(time);
    }

    /**
     * 得到当前的时间，时间格式yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**
     * 得到当前的时间,自定义时间格式
     * y 年 M 月 d 日 H 时 m 分 s 秒
     *
     * @param dateFormat 输出显示的时间格式
     * @return
     */
    public static String getCurrentDate(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(new Date());
    }

    /**
     * 日期格式化，默认日期格式yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getFormatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 日期格式化，自定义输出日期格式
     *
     * @param date
     * @return
     */
    public static String getFormatDate(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    /**
     * 返回当前日期的前一个时间日期，amount为正数 当前时间后的时间 为负数 当前时间前的时间
     * 默认日期格式yyyy-MM-dd
     *
     * @param field  日历字段
     *               y 年 M 月 d 日 H 时 m 分 s 秒
     * @param amount 数量
     * @return 一个日期
     */
    public static String getPreDate(String field, int amount) {
        calendar.setTime(new Date());
        if (field != null && !field.equals("")) {
            if (field.equals("y")) {
                calendar.add(Calendar.YEAR, amount);
            } else if (field.equals("M")) {
                calendar.add(Calendar.MONTH, amount);
            } else if (field.equals("d")) {
                calendar.add(Calendar.DAY_OF_MONTH, amount);
            } else if (field.equals("H")) {
                calendar.add(Calendar.HOUR, amount);
            }
        } else {
            return null;
        }
        return getFormatDate(calendar.getTime());
    }

    /**
     * 某一个日期的前一个日期
     *
     * @param date,某一个日期
     * @param field      日历字段
     *                   y 年 M 月 d 日 H 时 m 分 s 秒
     * @param amount     数量
     * @return 一个日期
     */
    public static String getPreDate(Date date, String field, int amount) {
        calendar.setTime(date);
        if (field != null && !field.equals("")) {
            if (field.equals("y")) {
                calendar.add(Calendar.YEAR, amount);
            } else if (field.equals("M")) {
                calendar.add(Calendar.MONTH, amount);
            } else if (field.equals("d")) {
                calendar.add(Calendar.DAY_OF_MONTH, amount);
            } else if (field.equals("H")) {
                calendar.add(Calendar.HOUR, amount);
            }
        } else {
            return null;
        }
        return getFormatDate(calendar.getTime());
    }

    /**
     * 某一个时间的前一个时间
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getPreDate(String date) throws ParseException {
        Date d = new SimpleDateFormat().parse(date);
        String preD = getPreDate(d, "d", 1);
        Date preDate = new SimpleDateFormat().parse(preD);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(preDate);
    }
}

