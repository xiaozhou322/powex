package com.gt.Enum;

public class SendMailTypeEnum {
	public static final int BEGIN = 0;//验证邮件
    public static final int ValidateMail = 1;//验证邮件
    public static final int FindPassword = 2;//找回密码
    public static final int RegMail = 3;//注册
    public static final int END = 4;//注册
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == ValidateMail){
			name = "ValidateMail";
		}else if(value == FindPassword){
			name = "ValidateMail";
		}else if(value == RegMail){
			name = "RegMail";
		}
		return name;
	}
    
}
