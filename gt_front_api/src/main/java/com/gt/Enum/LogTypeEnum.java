package com.gt.Enum;

public class LogTypeEnum {
	public static final int User_LOGIN = 1;//用户登录
    public static final int User_BIND_PHONE = 2;//绑定手机
    public static final int User_BIND_EMAIL = 3;//绑定邮箱
    public static final int User_SET_TRADE_PWD = 4;//设置交易密码
    public static final int User_UPDATE_TRADE_PWD = 5;//更新交易密码
    public static final int User_UPDATE_LOGIN_PWD = 6;//更新登陆密码
    public static final int User_UPDATE_GOOGLE = 7;//更新谷歌验证器
    public static final int User_BIND_GOOGLE = 8;//绑定谷歌验证器
    public static final int User_CERT = 9;//实名认证
    public static final int User_RESET_PWD = 10;//重置登陆密码
    public static final int User_BTC = 101;//虚拟币操作
    public static final int User_USDT = 102;//USDT操作
    public static final int User_CNY = 103;//CNY操作
    
    public static final int Admin_LOGIN = 11;//管理员登陆
    public static final int Admin_ADD = 12;//新增管理员
    public static final int Admin_UPDATE = 13;//修改管理员
    public static final int Admin_ROLE = 14;//修改管理员角色
    
    public static final int Admin_USER = 15;//会员管理操作
    public static final int Admin_BTC = 16;//虚拟币管理操作
    public static final int Admin_USDT = 17;//USDT管理操作
    public static final int Admin_CNY = 18;//CNY管理操作
    public static final int Admin_SYSTEM = 19;//系统管理操作
    public static final int Admin_ARTICLE = 20;//资讯管理操作
    public static final int Admin_REWARD = 21;//奖励管理操作
    public static final int Admin_QUESTION = 22;//问答管理操作
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == User_LOGIN){
			name = "用户登录";
		}else if(value == User_BIND_PHONE){
			name = "绑定手机";
		}else if(value == User_BIND_EMAIL){
			name = "绑定邮箱";
		}else if(value == User_SET_TRADE_PWD){
			name = "设置交易密码";
		}else if(value == User_UPDATE_TRADE_PWD){
			name = "更新交易密码";
		}else if(value == User_UPDATE_LOGIN_PWD){
			name = "更新登陆密码";
		}else if(value == User_UPDATE_GOOGLE){
			name = "更新谷歌验证器";
		}else if(value == User_BIND_GOOGLE){
			name = "绑定谷歌验证器";
		}else if(value == User_CERT){
			name = "实名认证";
		}else if(value == User_RESET_PWD){
			name = "重置登陆密码";
		}else if(value == User_BTC){
			name = "虚拟币操作";
		}else if(value == User_USDT){
			name = "USDT操作";
		}else if(value == User_CNY){
			name = "CNY操作";
		}else if(value == Admin_LOGIN){
			name = "管理员登陆";
		}else if(value == Admin_ADD){
			name = "新增管理员";
		}else if(value == Admin_UPDATE){
			name = "修改管理员";
		}else if(value == Admin_ROLE){
			name = "修改管理员角色";
		}else if(value == Admin_USER){
			name = "会员管理操作";
		}else if(value == Admin_BTC){
			name = "虚拟币管理操作";
		}else if(value == Admin_USDT){
			name = "USDT管理操作";
		}else if(value == Admin_CNY){
			name = "CNY管理操作";
		}else if(value == Admin_SYSTEM){
			name = "系统管理操作";
		}else if(value == Admin_ARTICLE){
			name = "文章管理操作";
		}else if(value == Admin_REWARD){
			name = "奖励管理操作";
		}else if(value == Admin_QUESTION){
			name = "问答管理操作";
		}
		
		return name;
	}
}
