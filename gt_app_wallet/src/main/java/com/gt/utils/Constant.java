package com.gt.utils;

public class Constant {
	public final static boolean isRelease = true ;//must change when release
	
	public final static String APP_MD5_KEY = Configuration.getInstance().getValue("APP_MD5_KEY") ;
	
	
	
	/*
	 * 分页数量
	 * */
	public final static int RecordPerPage = 20 ;//充值记录分页
	public final static int AppRecordPerPage = 10 ;//app分页数量
	
}
