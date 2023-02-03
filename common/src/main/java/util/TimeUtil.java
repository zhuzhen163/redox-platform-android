package util;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    private static Object currentMonthFirst;

    public static String getNowStr(String time) {
        SimpleDateFormat oldtime = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newtime = new SimpleDateFormat("yyyy-MM");
        String date = null;
        try {
            date = newtime.format(oldtime.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String getNowStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }

    public static int getNowStrInt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return Integer.parseInt(sdf.format(date));
    }

    public static String getHHmm() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");// HH:mm:ss//获取当前时间

        Date date = new Date(System.currentTimeMillis());
        //
        //
        return simpleDateFormat.format(date);
    }

    public static String getYearMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM");
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getTimeStr(String times) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(times);
        return sdf.format(date);
    }

    public static String getTimeStrByLong(Long times) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(times);
        return sdf.format(date);
    }

    public static String getDateID() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }


    public static Long getNowLong() {
        Date date = new Date();
        return date.getTime();
    }

    public static Long getLongTimeByStr(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = sdf.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return Long.parseLong("0");
        }
    }

    public static Long getLongTimeByStr(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return Long.parseLong("0");
        }
    }

    /**
     * 获取指定日期的间隔天数的日期字符串
     */
    public static Integer getNextDateID(Integer date, Integer i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date befoerdate;
        try {
            befoerdate = sdf.parse(date + "");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(befoerdate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + i);
        return Integer.parseInt(sdf.format(calendar.getTime()));
    }

    /**
     * 获取指定日期的间隔天数的日期字符串
     */
    public static Integer getNextDateID(String date, Integer i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date befoerdate;
        try {
            befoerdate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(befoerdate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + i);
        return Integer.parseInt(sdf.format(calendar.getTime()));
    }

    public static String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        return sdf.format(date);
    }

    /*
* 将时间转换为时间戳
*/
    public static long dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return ts;
    }

    /*
* 将时间转换为时间戳
*/
    public static long dateToStamp11(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return ts;
    }

    public static String getSignNowDate() {//签到要用：获取当前的年月日
        Date dNow = new Date();   //当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //设置时间格式
        String defaultEndDate = sdf.format(dNow); //格式化当前时间
        Log.i("获取今天的时间：", defaultEndDate);
        return defaultEndDate;
    }

    public static String getYesterdayDate() {//签到要用：获取昨天的年月日
        Date dNow = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //设置时间格式
        String defaultEndDate = sdf.format(dNow); //格式化当前时间
        Log.i("获取昨天的时间：", defaultEndDate);
        return defaultEndDate;
    }

    //todo:把时间转化为date类的方法
    public static Date stringToData(String dateString) {
        if (TextUtils.isEmpty(dateString) || "null".equalsIgnoreCase(dateString)) {
            dateString = "1995-10-10 00:00:00";
        }
        ParsePosition parsePosition = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(dateString, parsePosition);
        return date;
    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static long getCurTimeLong() {
        long time = System.currentTimeMillis();
        return time;
    }

    //获取当月第一天
    public static String getCurrentMonthFirst() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        int day = cale.get(Calendar.DATE);
        int hour = cale.get(Calendar.HOUR_OF_DAY);
        int minute = cale.get(Calendar.MINUTE);

        int second = cale.get(Calendar.SECOND);
        int dow = cale.get(Calendar.DAY_OF_WEEK);
        int dom = cale.get(Calendar.DAY_OF_MONTH);
        int doy = cale.get(Calendar.DAY_OF_YEAR);
        String firstday, lastday;          // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime());
        System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);            // 获取当前日期字符串          Date d = new Date();          System.out.println("当前日期字符串1：" + format.format(d));          System.out.println("当前日期字符串2：" + year + "/" + month + "/" + day + " "                  + hour + ":" + minute + ":" + second);
        return firstday;
    }

    //获取当月最后一天
    public static String getDefineCurrentMonthLast() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        int day = cale.get(Calendar.DATE);
        int hour = cale.get(Calendar.HOUR_OF_DAY);
        int minute = cale.get(Calendar.MINUTE);

        int second = cale.get(Calendar.SECOND);
        int dow = cale.get(Calendar.DAY_OF_WEEK);
        int dom = cale.get(Calendar.DAY_OF_MONTH);
        int doy = cale.get(Calendar.DAY_OF_YEAR);
        String firstday, lastday;          // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime());
        return lastday;
    }

    /**
     * 根据提供的年月获取该月份的第一天
     *
     * @param year
     * @param monthOfYear
     * @return
     */
    public static String getFirstDayOfMonth(String year, String monthOfYear) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        int years = Integer.parseInt(year);
        int monthOfYears = Integer.parseInt(monthOfYear);
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, years);
        cal.set(Calendar.MONTH, monthOfYears);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return format.format(cal.getTime());
    }

    /**
     * 根据提供的年月获取该月份的最后一天
     *
     * @param years
     * @param monthOfYears
     * @return
     * @Description: (这里用一句话描述这个方法的作用)
     * @Author: gyz
     * @Since: 2017-1-9下午2:29:38
     */
    public static String getLastDayOfMonth(String years, String monthOfYears) {
        Calendar calendar = Calendar.getInstance();
        // 设置时间,当前时间不用设置
        calendar.set(Calendar.YEAR, Integer.parseInt(years));
        calendar.set(Calendar.MONTH, Integer.parseInt(monthOfYears) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        return format.format(calendar.getTime());
    }

    /**
     * 根据时间戳获取描述性时间，如3分钟前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */
    public static long getDescriptionTimeFromTimestamp(String timestamp) {
        long currentTime = System.currentTimeMillis();
        long tims = getTimeMillis(timestamp);
        long timeGap = (currentTime - tims) / 1000;// 与现在时间相差秒数
        long timeStr = timeGap / 60;
        return timeStr;
    }


    public static long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {
        }
        return returnMillis;
    }


    public static String secondFormat(Long times) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        Date date = new Date(times);
        return sdf.format(date);
    }


    /**
     * 秒转hh:mm:ss格式
     * @param secString 秒字符串
     * @return hh:mm:ss
     * String
     */
    public static String secondsToFormat(String secString){
        Integer seconds = Integer.parseInt(secString);
        Integer hour =0;
        Integer min =0;
        Integer second =0;
        String result ="";

        if (seconds>60) {   //是否大于零
            min = seconds/60;  //分钟
            second = seconds%60;  //秒
            if (min>60) {   //存在时
                hour=min/60;
                min=min%60;
            }
        }
        if (hour>0) {
            result=hour+":";
        }
        if (min>0) {
            result=result+min+":";
        }else if (min==0&&hour>0) {  //当分为0时,但是时有值,所以要显示,避免出现2时0秒现象
            result=result+min+":";
        }
        result=result+second;   //秒必须出现无论是否大于零
        return result;
    }

}
