package com.gt.util;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.util.StringUtils;


public class DateUtil extends DateSupport {
	
	private static final long ONE_DAY_TIME = 24*60*60*1000;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 

	public static String nowDateForStr(String format) {
		Date date = new Date();
			
		return dateForString(date, format);
	}

	/**
	 * 根据格式yyyy-MM-dd,获取当前日期
	 * 
	 * @author tangshengyu
	 * @version falvm
	 * @date Dec 9, 2009
	 * @return String
	 */
	public static String nowDateForStrYMD() {

		return nowDateForStr("yyyy-MM-dd");

	}

	public static String nowDateForStrYMDHMS() {

		return nowDateForStr("yyyy-MM-dd HH:mm:ss");

	}
	
	public static String getYMDHMSSS() {
		return nowDateForStr("yyyyMMddHHmmssSSS");
	}
	
	public static String getYMDHM() {
		return nowDateForStr("yyyy-MM-dd HH:mm");
	}
	
	public static String getYMDHMS() {
		return nowDateForStr("yyyyMMddHHmmss");
	}
	
	public static String dateToFullStr(Date dateDate) {
		if (dateDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(dateDate);
			return dateString;
		}
		return "";
	}
	
	public static String getTommorrow() {
		String strCurrentDate = "";
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, 1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		strCurrentDate = formatter.format(c.getTime());
		return strCurrentDate;
	}
	
	/**
	 * getLastWeekDateTime:获取当前时间的上一个周时间. <br/>
	 * @author Administrator
	 * @return
	 * @since JDK 1.6
	 */
	public static String getLastWeekDateTime(){
		Date date = new Date();//当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化对象
		Calendar calendar = Calendar.getInstance();//日历对象
		calendar.setTime(date);//设置当前日期
		calendar.add(Calendar.WEEK_OF_MONTH, -1);//周数减一
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 获取 日期 是第几周 yyyy-ww
	 * 
	 * @return
	 */
	public static String getWeekNumber(String date) {
		if (date.length() < 9) {
			return date;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(7);
		String fmtStr = "yyyy-MM-dd";
		if (date.length() > 10) {
			fmtStr = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat df = new SimpleDateFormat(fmtStr);
		try {
			calendar.setTime(df.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		String wStr = week + "";
		if (week < 10) {
			wStr = "0" + wStr;
		}
		return calendar.get(Calendar.YEAR) + "-" + wStr;
	}
	
	/**
	 * 获取 日期 是第几周 yyyy-ww
	 * 
	 * @return
	 */
	public static String[] getDateByWeeks(String date) {
		// 2014-03-27 23:59:59 == > yyyy-ww
		String week = getWeekNumber(date);
		String[] s = getDateByWeek(week);
		// return new String[] { s[0] + " 00:00:00", s[1] + " 23:59:59" };
		return new String[] { s[0], s[1] };
	}
	
	/**
	 * 根据自然周获取开始日期和结束日期
	 * 
	 * @param weekStr
	 * @return
	 */
	public static String[] getDateByWeek(String weekdate) {
		String[] arr = weekdate.split("-");
		int year = Integer.parseInt(arr[0]);
		int week = Integer.parseInt(arr[1]);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		// String date1 = dateFormat.format(c.getTime());
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		String start = dateFormat1.format(c.getTime());
		c.add(Calendar.DATE, 6);
		String end = dateFormat1.format(c.getTime());
		return new String[] { start, end };
	}
	
	/**
	 * getMonthLastDay:获取当前月最后一天. <br/>
	 * @author Administrator
	 * @return
	 * @since JDK 1.6
	 */
	public static String getMonthLastDay(){
		return getFirstLastDayInMonth(nowDateForStrYMDHMS())[1];
	}
	
	/**
	 * 获取当天是第几月
	 * 
	 * @return yyyy-MM
	 */
	public static String getMonthNumber() {
		SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
		return ym.format(new Date());
	}
	
	/**
	 * 获取当天是第几月
	 * 
	 * @return yyyy-MM
	 */
	public static String getMonthNumber(String date) {
		SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
		if (StringUtils.isEmpty(date)) {
			return ym.format(new Date());
		}
		return date.substring(0, 7);
	}
	
	/**
	 * getLastMonthDateTime:获取当前时间的上一个月时间. <br/>
	 * @author Administrator
	 * @return
	 * @since JDK 1.6
	 */
	public static String getLastMonthDateTime(){
		Date date = new Date();//当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化对象
		Calendar calendar = Calendar.getInstance();//日历对象
		calendar.setTime(date);//设置当前日期
		calendar.add(Calendar.MONTH, -1);//月份减一
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 某一个月第一天和最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String[] getFirstLastDayInMonth(String dateStr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// calendar.add(Calendar.MONTH, -1);
		// calendar.set(Calendar.MONTH, value)
		Date theDate = calendar.getTime();

		// 上个月第一天
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first).append(
				" 00:00:00");
		day_first = str.toString();

		// 上个月最后一天
		calendar.add(Calendar.MONTH, 1); // 加一个月
		calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		String day_last = df.format(calendar.getTime());
		StringBuffer endStr = new StringBuffer().append(day_last).append(
				" 23:59:59");
		day_last = endStr.toString();

		return new String[] { day_first, day_last };
	}
	
	/**
	 * 
	 * getMonthByNum:获取n天后的月份日期
	 * @author Administrator
	 * @param day
	 * @return
	 * @since JDK 1.6
	 */
	public static String getMonthByNum(int day){
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance(); 
		c.setTimeInMillis(new Date().getTime());
		c.add(Calendar.MONTH, day);//天后的日期
		//System.out.println("date="+formatDate.format(c.getTime()));
		return formatDate.format(c.getTime());
	}
	
	/**
	 * getMonthByNum:获取n天后的月份日期
	 * @author Administrator
	 * @param day
	 * @return
	 * @since JDK 1.6
	 */
	public static String getMonthByTime(String strDate, int day){
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance(); 
		c.setTime(strToDate(strDate));
		c.add(Calendar.MONTH, day);//天后的日期
		//System.out.println("date="+formatDate.format(c.getTime()));
		return formatDate.format(c.getTime());
	}
	
	public static Date strToDate(String strDate) {
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		   ParsePosition pos = new ParsePosition(0);
		   Date strtodate = formatter.parse(strDate, pos);
		   return strtodate;
		}
	
	/**
	 * 获取最近七天
	 * 
	 * @return
	 */
	public static String getDay7() {
		long day7 = 1000 * 60 * 60 * 24 * 7;
		long off = System.currentTimeMillis() - day7;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(off));
	}
	
	/**
	 * 获取最近七天
	 * 
	 * @return
	 */
	public static String getDay7(String date) {
		Calendar c = Calendar.getInstance();
		Date et = null;
		try {
			et = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(et);
		c.add(Calendar.DAY_OF_YEAR, -7);
		return format.format(c.getTime());
	}
	
	/**
	 * 获取指定天数前后N天的时间
	 * @return
	 */
	public static String getDayBytime(String date,int n) {
		Calendar c = Calendar.getInstance();
		Date et = null;
		try {
			et = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(et);
		c.add(Calendar.DAY_OF_YEAR, n);
		return format.format(c.getTime());
	}
	
	/**
	 * 根据当前日期获取当前月中的最后一天
	 * 
	 * @return
	 * @author tangshengyu
	 * @version panyu
	 * @date Jul 16, 2010
	 * @return String
	 */
	public static String dateForMonthLastDayYMD(String strDate) {
		int lastDay = getDateMonthLastDay(strDate);
		Date d = strForDate(strDate, "yyyy-MM-dd");
		d.setDate(lastDay);
		return dateForString(d, "yyyy-MM-dd");
	}

	public static void main(String args[]) {
		Date d = new Date();
		Date da = DateUtil.strForDate("2009-09-09 11:11:11",
				"yyyy-MM-dd hh:mm:ss");
		DateUtil.dateForString(da, "yyyyMMddHHmmss");
		System.out.println(DateUtil.nowDateForStr("HH:mm:ss"));
		System.out.println(DateUtil.dateForString(strForDate("17:38:23",
				"HH:mm:ss"), "HH:mm:ss"));
		System.out.println(DateUtil.getDate(-2, "yyyy-MM-dd"));
	}

	/**
	 * 取当前年月日
	 * 
	 * @return
	 */
	public static String getYMDHMSS() {
		// TODO Auto-generated method stub
		return nowDateForStr("yyyyMMddHHmmss");
	}

	/**
	 * 取指定时间
	 * 
	 * @param from
	 *            从今天开始 前几天为负值，后几天为正直
	 * @param format
	 *            格式化类型
	 *            
	 *            例如取前一天getDate(-1, "YYYY_MM_DD")
	 *            取后一天 getDate(1, "YYYY_MM_DD");
	 * @return
	 */
	public static String getDate(int from, String format) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, from);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String day = dateFormat.format(c.getTime());
		return day;
	}
	
	/**
	 * 获取N天前后的时间
	 * @return
	 */
	public static String getDayTime(int n) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_YEAR, n);
		return formatter.format(c.getTime());
	}
	
	public static String getNowMonthFirst(){
		 //获取当前月第一天：
	    Calendar c = Calendar.getInstance();    
	    c.add(Calendar.MONTH, 0);
	    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	    return format.format(c.getTime());
	}

	/**
	 * 获取当前凌晨0点的时间
	 * @return
	 */
	public static Date getCurrentZeroPoint(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取同比时间段
	 * yyyy-MM-dd hh:mm:ss
	 * @param beginDay
	 * @param endDay
	 * @return
	 */
	public static String[] getYearBasis(String beginDay,String endDay){
		String[] section = new String[2];
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date beginTime = dateFormat.parse(beginDay);
			Date endTime = dateFormat.parse(endDay);
			
			Calendar c= Calendar.getInstance();
			c.setTime(beginTime);
			c.add(Calendar.YEAR, -1);
			beginTime = c.getTime();
			section[0] = dateFormat.format(beginTime);
			
			c.setTime(endTime);
			c.add(Calendar.YEAR, -1);
			endTime = c.getTime();
			section[1] = dateFormat.format(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return section;
	}
	
	/**
	 * 获取环比时间段
	 * @param beginDay
	 * @param endDay
	 * @return
	 */
	public static String[] getLinkRelativeRatio(String beginDay,String endDay){
		String[] section = new String[2];
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date beginTime = dateFormat.parse(beginDay);
			Date endTime = dateFormat.parse(endDay);
			
			long sectionTime = endTime.getTime() - beginTime.getTime();
			
			endTime = new Date(beginTime.getTime()-ONE_DAY_TIME);
			beginTime = new Date(endTime.getTime()-sectionTime);
			
			section[0] = dateFormat.format(beginTime);
			section[1] = dateFormat.format(endTime);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return section;
	}
	
	/**
	 * getRelaMonthTime:前后N月的月份日期
	 * @author liubing
	 * @param str
	 * @param n
	 * @return
	 */
	public static String getRelaMonthTime(String str,int n){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		try {
			Calendar c= Calendar.getInstance();
			Date date = dateFormat.parse(str);
			c.setTime(date);
			c.add(Calendar.MONTH, n);
			date = c.getTime();
			return dateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void transMap2Bean(Map<String, Object> map, Object obj) {  
	    //ConvertUtils.register(new DateLocaleConverter(), Date.class);
	    ConvertUtils.register(new Converter()  
	    {  
	         
	   
	      @SuppressWarnings("rawtypes")  
	      @Override  
	      public Object convert(Class arg0, Object arg1)  
	      {  
//	        System.out.println("注册字符串转换为date类型转换器");  
	        if(arg1 == null)  
	        {  
	          return null;  
	        }  
	        if(!(arg1 instanceof String))  
	        {  
	          throw new ConversionException("只支持字符串转换 !");  
	        }  
	        String str = (String)arg1;  
	        if(str.trim().equals(""))  
	        {  
	          return null;  
	        }  
	           
	        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	           
	        try{  
	          return sd.parse(str);  
	        }  
	        catch(ParseException e)  
	        {  
	          throw new RuntimeException(e);  
	        }  
	           
	      }  
	         
	    }, java.util.Date.class);  
	    if (map == null || obj == null) {  
	      return;  
	    }  
	    try {  
	    	String key = null;
	    	Object value = null;
	    	Map newMap = new HashMap();
	      for(Map.Entry<String, Object> entry:map.entrySet()){
	    	 key = entry.getKey();
	    	 value = entry.getValue();
	    	 newMap.put(key.toLowerCase().replace("_", ""), value);
	      }
	      BeanUtils.populate(obj, newMap);  
	    } catch (Exception e) {  
	      System.out.println("Map<String,Object>转化Bean异常：" + e);  
	    }  
	  }
	
	
	public static void  populate2(Object obj,Map<String,Object> map){
		Class clazz = obj.getClass();
		
		Map<String,Method> mapValue = new HashMap<String,Method>();
		
		Method[] methods = clazz.getDeclaredMethods();

		String methodName = null;
		for(Method method:methods){
			methodName = method.getName();
			if(methodName.startsWith("set")){
				mapValue.put(methodName.substring(3).toLowerCase(), method);
			}
		}
		
		try {
			String key = null;
			for(Map.Entry<String, Object> entry:map.entrySet()){
					key = entry.getKey();
					if(mapValue.containsKey(key)){
						mapValue.get(key).invoke(obj, entry.getValue()+"");
					}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date begin,Date end) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		begin=sdf.parse(sdf.format(begin));
		end=sdf.parse(sdf.format(end));
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(begin);
		
		long time1 = cal.getTimeInMillis();
		cal.setTime(end);
		
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);
		
		return Integer.parseInt(String.valueOf(between_days));           
    }
    
    /**
     * 获取指定日期的n天后的日期
     * @param dateBegin
     * @param day 正数是n天后，负数是n天前
     * @return
     */
    public static String getForwardDate(String dateBegin, int day) {
    	try {
			SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdft.parse(dateBegin);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, day);
			return sdft.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return dateBegin;
    }
    /**
     * 获取制定时间转字符串
     */
    public static String getStringDate(Date time, String format) {
		SimpleDateFormat sdft = new SimpleDateFormat(format);
		try{
			if(time !=null){
				return sdft.format(time);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
    }
}
