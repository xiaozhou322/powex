package com.gt.Enum;

public class IdentityTypeEnum {//身份证件类型
    public static final int SHENFENZHENG = 0;//身份证
    public static final int JUNGUANGZHEN = 1;//军官证
    public static final int HUZHAO = 2 ;//护照
    public static final int TAIWAN = 3 ;//台湾居民通行证
    public static final int GANGAO = 4 ;//港澳居民通行证
    
    public static String getEnumString(int value) {
		String name = "";
		switch(value){
		case SHENFENZHENG:
			name = "身份证";
			break ;
		case JUNGUANGZHEN:
			name = "军官证";
			break ;
		case HUZHAO:
			name = "护照";
			break ;
		case TAIWAN:
			name = "台湾居民通行证";
			break ;
		case GANGAO:
			name = "港澳居民通行证";
			break ;
		}
		return name;
	}
    
}
