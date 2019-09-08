package com.gt.util;

public class Constant {
	public final static boolean isRelease = true ;//must change when release
//	public final static String WEBROOT = Configuration.getInstance().getValue("WEBROOT") ;
	public final static String Domain = Configuration.getInstance().getValue("Domain") ;
	public final static String GoogleAuthName = Configuration.getInstance().getValue("GoogleAuthName") ;
	public final static String MailName = Configuration.getInstance().getValue("MailName") ;
	public final static String APPKEY = Configuration.getInstance().getValue("APPKEY") ;
	public final static String Endpoint = Configuration.getInstance().getValue("Endpoint") ;
	public final static String AccessKeyId = Configuration.getInstance().getValue("AccessKeyId") ;
	public final static String AccessKeySecret = Configuration.getInstance().getValue("AccessKeySecret") ;
	public final static String BucketName = Configuration.getInstance().getValue("BucketName") ;
	public final static String OSSURL = Configuration.getInstance().getValue("OSSURL") ;
	public final static String IS_OPEN_OSS = Configuration.getInstance().getValue("IS_OPEN_OSS") ;
	public final static String IS_APPKEY = Configuration.getInstance().getValue("IS_APPKEY") ;
	public final static boolean closeLimit = false ;
	
	public final static int VIP = 6;//VIP等级
	public final static int TYPES = 10;//虚拟币提现手续费
	
	public static Long messageTime = 3*60*1000L ;//短信有效时间
	public static Long mailTime = 30*60*1000L ;//邮件有效时间
	public static Long twiceTime = 1*60*1000L ;//两次请求的间隔时间
	
	/*
	 * 分页数量
	 * */
	public final static int RecordPerPage = 20 ;//充值记录分页
	public final static int AppRecordPerPage = 10 ;//app分页数量
	
	public final static int VirtualCoinWithdrawTimes = 10 ;//虚拟币每天提现次数
	public final static int CnyWithdrawTimes = 10 ;//人民币每天体现次数
	public static final boolean TradeSelf = true ;//
	

	public final static String uploadPicDirectory =  "upload"+"/"+"images" ;
	public final static String DataBaseDirectory = "upload"+"/"+"dataBase" ;
	public static final String EmailReg = "^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";//邮箱正则
	public static final String PhoneReg = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";
	public final static int ErrorCountLimit = 10 ;//错误N次之后需要等待2小时才能重试
	public final static int ErrorCountAdminLimit = 30 ;//后台登陆错误
	
}
