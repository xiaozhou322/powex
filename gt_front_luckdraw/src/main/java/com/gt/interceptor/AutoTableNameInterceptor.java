package com.gt.interceptor;

import org.hibernate.EmptyInterceptor;

import com.gt.util.Utils;

public class AutoTableNameInterceptor extends EmptyInterceptor {

	/*private static String srcName = ""; //源表名
	private static String destName = ""; // 目标表名
*/	
	private static ThreadLocal<String> srcName = new ThreadLocal<>();
	private static ThreadLocal<String> destName = new ThreadLocal<>();

	public AutoTableNameInterceptor() {}

	/*public AutoTableNameInterceptor(String srcName,String destName){
		this.srcName = srcName;
		this.destName = destName;
	}
	
	@Override
	public String onPrepareStatement(String sql) {
		String sqlQuery = super.onPrepareStatement(sql);
		if(!Utils.isNull(srcName) || !Utils.isNull(destName)){
			System.out.println("sqlQuery:"+sqlQuery);
			return sqlQuery.replace(srcName, destName);
		}else{
			return sqlQuery;
		}
	}*/
	
	public static void setReplaceTableName(String srcName,String destName){
		AutoTableNameInterceptor.srcName.set(srcName);
		AutoTableNameInterceptor.destName.set(destName);
	}
	
	public static String getSrcName(){
		if(null != AutoTableNameInterceptor.srcName.get()){
			return AutoTableNameInterceptor.srcName.get();
		}else{
			return "";
		}
	}
	
	public static String getDestName(){
		if(null != AutoTableNameInterceptor.destName.get()){
			return AutoTableNameInterceptor.destName.get();
		}else{
			return "";
		}
	}
	  
	@Override
	public String onPrepareStatement(String sql) {
		String sqlQuery = super.onPrepareStatement(sql);
		if(!Utils.isNull(AutoTableNameInterceptor.srcName.get()) || !Utils.isNull(AutoTableNameInterceptor.destName.get())){
			System.out.println("sqlQuery:"+sqlQuery);
			return sqlQuery.replace(AutoTableNameInterceptor.srcName.get(), AutoTableNameInterceptor.destName.get());
		}else{
			return sqlQuery;
		}
	}
}
