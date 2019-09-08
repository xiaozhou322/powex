package com.gt.comm;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//七天涨跌幅数据
public class Day7UpsDowns {

	//virtualId_day
	
	private static Map<String, Double> map = new HashMap<String, Double>() ;
	public static Double get(int id,String day){
		if(day.endsWith("today")){
			return null ;
		}
		
		synchronized (map) {
			return map.get(id+"_"+day) ;
		}
	}
	public static void set(int id,String day,Double value){
		synchronized (map) {
			map.put(id+"_"+day, value) ;
		}
	}
	
	public static List<String> getDays(int length){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
		
		List<String> list = new ArrayList<String>() ;
		Date pre = new Date() ;
		for (int i = 0; i < length; i++) {
			if(i==0){
				list.add(sdf.format(pre)) ;
			}else{
				list.add(0,sdf.format(pre)) ;
			}
			pre = getPre(pre) ;
		}
		return list ;
	}
	
	public static Date getPre(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
	
	public static void main(String args[]) throws Exception {
		List<String> list = getDays(7) ;
		for (String string : list) {
			System.out.println(string);
		}
	}
}
