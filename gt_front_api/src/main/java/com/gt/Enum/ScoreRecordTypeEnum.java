package com.gt.Enum;

public class ScoreRecordTypeEnum {
    public static final int LOGIN = 1;//
    public static final int CHONGZHI = 2;//
    public static final int JIAOYIE = 3;//
    public static final int JINGZICHANGE = 4;//
    public static final int SHIMINGRENZHENG = 5;//
    public static final int SHOUJIRENZHENG = 6;//
    public static final int YOUXIANGRENZHENG = 7;//
    public static final int GUGERENGZHENG = 8;//
    public static final int SHOUCICHONGZHI = 9;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == LOGIN){
			name = "登录";
		}else if(value == CHONGZHI){
			name = "充值";
		}else if(value == JIAOYIE){
			name = "交易额";
		}else if(value == JINGZICHANGE){
			name = "净资产额";
		}else if(value == SHIMINGRENZHENG){
			name = "实名认证";
		}else if(value == SHOUJIRENZHENG){
			name = "手机认证";
		}else if(value == YOUXIANGRENZHENG){
			name = "邮箱认证";
		}else if(value == GUGERENGZHENG){
			name = "谷歌认证";
		}else if(value == SHOUCICHONGZHI){
			name = "首次充值";
		}
		return name;
	}
    
}
