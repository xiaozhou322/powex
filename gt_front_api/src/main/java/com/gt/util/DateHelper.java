package com.gt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/1.
 */
public final class DateHelper {
    private DateHelper() {
    }

    /**
     * ****************************************
     *
     * @author : scofieldcai@126.com
     * @ClassName : DateFormatType
     * @date : 2013-6-22 下午10:58:34
     * @Description: 日期格式化的类型
     * ****************************************
     **/
    public enum DateFormatType {
        YearMonthDay_HourMinuteSecond_MESC("yyyyMMddHHmmssSSS"),
        YearMonthDay_HourMinuteSecond_Custom("yyyy/MM/dd HH:mm:ss"),
        YearMonthDay_HourMinuteSecond_Log("yyyy-MM-dd HH_mm_ss"),
        YearMonthDay_HourMinuteSecond("yyyy-MM-dd HH:mm:ss"),
        YearMonthDayHourMinuteSecond("yyyyMMddHHmmss"),
        YearMonthDay_HourMinute("yyyy-MM-dd HH:mm"),
        YearMonthDay_Hour("yyyy-MM-dd HH"),
        YearMonthDay("yyyy-MM-dd"),
        YearMonthDay_Log("yyyyMMdd"),
        YearMonth("yyyy-MM"),
        Year("yyyy"),
        HourMinuteSecond("HH:mm:ss"),
        HourMinute("HH:mm"),
        MonthDay_HourMinute("MM-dd HH:mm");


        private final String dateFormat;

        private SimpleDateFormat sdf;

        DateFormatType(String dateFormat) {
            this.dateFormat = dateFormat;
        }

        public SimpleDateFormat getDateFormat() {
            if (sdf == null) {
                sdf = new SimpleDateFormat(dateFormat);
            }
            return sdf;
        }

        public String getFormat() {
            return dateFormat;
        }
    }


    /*****************************************
     * @param strDate
     * @param dateFormatType
     * @return
     * @author : scofieldcai@126.com
     * @Title : string2Date
     * @returnType : Date
     * @Description: 根据字符串日期格式化时间。
     * 主要功能：用于比较，时间的排序。
     *****************************************/
    public static final Date string2Date(String strDate, DateFormatType dateFormatType) {
        SimpleDateFormat sdf = dateFormatType.getDateFormat();
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
//            LOG.e(DateHelper.class,"",e);
        }
        return date;
    }


    /**
     * 获取当前时间，时分设置成传过来的时分
     *
     * @param hourMinute
     * @return
     */
    public static final Date getCurrentDateByHHSS(String hourMinute) {
        return retryAssignDateByHHSS(null, hourMinute);
    }

    /**
     * 把指定时间的时分设置成传过来的时分
     *
     * @param date       指定时间
     * @param hourMinute 重新设置的时分
     * @return
     */
    public static final Date retryAssignDateByHHSS(Date date, String hourMinute) {

        Date tempDate = date;
        if (tempDate == null) {
            tempDate = new Date(System.currentTimeMillis());
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(tempDate);

        Date temp = string2Date(hourMinute, DateFormatType.HourMinute);
        if (temp == null) {
            return null;
        }
        Calendar calTemp = Calendar.getInstance();
        calTemp.setTime(temp);

        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), calTemp.get(Calendar.HOUR_OF_DAY), calTemp.get(Calendar.MINUTE), 0);

        return cal.getTime();
    }

    public static final int getDayOfWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekDay =  calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(weekDay == 0){
            weekDay = 7;
        }
        return weekDay;
    }


    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }


    /**
     * 比较开始日期和结束日期相处的秒钟
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Long getDifferSecond(Date beginDate, Date endDate){
        try {
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            long endMillis = end.getTimeInMillis();

            Calendar begin = Calendar.getInstance();
            begin.setTime(beginDate);
            long beginMillis = begin.getTimeInMillis();

            return beginMillis - endMillis;

        } catch (Exception e) {
            return null;
        }
    }
    
    public static String secondsConvertMinute(Long seconds) {
    	Long minutes = seconds / 60; 
        Long remainingSeconds = seconds % 60; 
        if(minutes == 0) {
        	return "暂无";
        }
        return (minutes+1)+"分钟";
    }
    
    
    /** 
     * 计算两个日期之间相差的天数 
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
	 * @throws ParseException 
     */  
    public static int daysBetween(Date smdate,Date bdate) throws ParseException  
    {  
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	smdate=sdf.parse(sdf.format(smdate));
    	bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();  
        cal.setTime(smdate);  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(bdate);  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);
          
       return Integer.parseInt(String.valueOf(between_days));         
    }  
    
    
	/**
	*字符串的日期格式的计算
	*/
    public static int daysBetween(String smdate,String bdate) throws ParseException{
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();  
        cal.setTime(sdf.parse(smdate));  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(sdf.parse(bdate));  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);
          
       return Integer.parseInt(String.valueOf(between_days));   
    }
    
    

    public static void main(String[] args) throws ParseException {


        Date date1 = new Date();
        Date date2 = string2Date("2018-07-27 12:00:00", DateFormatType.YearMonthDay_HourMinuteSecond);

        Long time = getDifferSecond(date1,date2);
        Long min = time / 1000;


      //  int day = getDayOfWeek(new Date());

        System.out.println(min);

        
		double d = 88.88;
		long l = Math.round(d);
		System.out.println(l);
		
		long ll = 100L;
		double dd = (double) ll;
		System.out.println(dd);
		
		System.out.println(daysBetween("2006-01-06","2012-01-22"));
    }
}
