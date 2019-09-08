package com.gt.Enum;


public class BankTypeEnum {
    public static final int GH = 1;//工商银行
    public static final int HX = 14;//华夏银行
    public static final int PA = 13;//平安银行
    public static final int ZX = 12;//中信银行
    public static final int SHPD = 11;//上海浦东发展银行
    public static final int XY = 10;//兴业银行
    public static final int GD = 9;//中国光大银行
    public static final int MS = 8;//中国民生银行
    public static final int ZG = 7;//中国银行
    public static final int YZXS = 6;//邮政储蓄银行
    public static final int ZS = 5;//招商银行
    public static final int JT = 4;//交通银行
    public static final int NY = 3;//农业银行
    public static final int JX = 2;//建设银行
    public static final int QT = 15;//其它银行
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case GH:
			name = "工商银行";
			break;
		case HX:
			name = "华夏银行";
			break;
		case PA:
			name = "平安银行";
			break;
		case ZX:
			name = "中信银行";
			break;
		case SHPD:
			name = "上海浦东发展银行";
			break;
		case XY:
			name = "兴业银行";
			break;
		case GD:
			name = "中国光大银行";
			break;
		case MS:
			name = "中国民生银行";
			break;
		case ZG:
			name = "中国银行";
			break;
		case YZXS:
			name = "邮政储蓄银行";
			break;
		case ZS:
			name = "招商银行";
			break;
		case JT:
			name = "交通银行";
			break;
		case NY:
			name = "农业银行";
			break;
		case JX:
			name = "建设银行";
			break;
		case QT:
			name = "其他银行";
			break;
		}
		return name;
	}
    
}
