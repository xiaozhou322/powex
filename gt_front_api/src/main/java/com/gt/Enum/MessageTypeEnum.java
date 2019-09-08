package com.gt.Enum;

//短信内容类型
public class MessageTypeEnum {
	public static final int BEGIN = 0;
    public static final int SHENQING_API = 1;//申请api
    public static final int BANGDING_MOBILE = 2;//绑定手机
    public static final int JIEBANG_MOBILE = 3;//解绑手机
    public static final int CNY_TIXIAN = 4;//人民币提现
    public static final int VIRTUAL_TIXIAN = 5;//虚拟币提现
    public static final int CHANGE_LOGINPWD = 6;//修改登陆密码
    public static final int CHANGE_TRADEPWD = 7;//修改交易密码
    public static final int VIRTUAL_WITHDRAW_ACCOUNT = 8;//虚拟币提现地址设置
    public static final int FIND_PASSWORD = 9;//找回登陆密码
    public static final int CNY_ACCOUNT_WITHDRAW = 10;//设置人民币提现账号
    public static final int REG_CODE =12;//注册
    public static final int BALANCE_CODE =13;//币生币
    public static final int TRANSFER_CODE =14;//会员转账
    public static final int API_CODE =15;//会员添加API
    public static final int PUSH_CODE =16;//PUSH_CODE
    public static final int USDT_TIXIAN =17;//USDT提现
    public static final int loginTwo = 18;//二次登录
    public static final int END = 20;
    
    
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case SHENQING_API:
			name = "SHENQING_API";
			break;
		case BANGDING_MOBILE:
			name = "BANGDING_MOBILE";
			break;
		case JIEBANG_MOBILE:
			name = "JIEBANG_MOBILE";
			break;
		case CNY_TIXIAN:
			name = "CNY_TIXIAN";	
			break;
		case VIRTUAL_TIXIAN:
			name = "VIRTUAL_TIXIAN";		
			break;
		case CHANGE_LOGINPWD:
			name = "CHANGE_LOGINPWD";
			break;
		case CHANGE_TRADEPWD:
			name = "CHANGE_TRADEPWD";
			break ;
		case FIND_PASSWORD:
			name = "FIND_PASSWORD" ;
			break ;
		case CNY_ACCOUNT_WITHDRAW:
			name = "CNY_ACCOUNT_WITHDRAW" ;
			break ;
		case REG_CODE:
			name = "REG_CODE" ;
			break ;		
		case BALANCE_CODE:
			name = "BALANCE_CODE" ;
			break ;	
		case TRANSFER_CODE:
			name = "TRANSFER_CODE" ;
			break ;
		case API_CODE:
			name = "API_CODE" ;
			break ;
		case PUSH_CODE:
			name = "PUSH_CODE" ;
			break ;	
		}
		return name;
	}
    
}
