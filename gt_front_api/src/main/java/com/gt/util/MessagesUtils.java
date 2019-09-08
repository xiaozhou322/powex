package com.gt.util;

import java.io.IOException;

public class MessagesUtils {
	
	/*
	 * 100			发送成功
		101			验证失败
		102			手机号码格式不正确
		103			会员级别不够
		104			内容未审核
		105			内容过多
		106			账户余额不足
		107			Ip受限
		108			手机号码发送太频繁
		120			系统升级
	 * 
	 * */
    public static int send(String account,String pass,String phone,String content){
    	try {
			sendSMS.send(account,pass,content,phone) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 1 ;
    }
    
    public static void main(String args[]) throws Exception{
//    	System.out.println(send("2a951d8c450215e55ef887b111b662fc", "13725558294", "【58BTC网】您的验证码是：#code#，5分钟内有效。请不要把验证码泄露给其他人。"));
    }
  
}
