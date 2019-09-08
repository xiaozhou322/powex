package com.gt.Enum;

/**
 * 项目方审核状态
 * @author zhouyong
 *
 */
public class AuditStatusEnum {
	public static final int waitAudit = 101 ;//待审核
	public static final int auditPass = 102 ;//审核通过
	public static final int auditNopass = 103 ;//审核不通过
	
	public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case waitAudit:
			name = "待审核" ;
			break;
		case auditPass:
			name = "审核通过" ;	
			break;
		case auditNopass:
			name = "审核不通过" ;
			break;
		}
		return name;
	}
}
