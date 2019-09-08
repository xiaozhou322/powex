package com.gt.auto.order;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lx on 17-1-19.
 */
public enum OrderType {
	error(0, ""), sell(1, "卖单"), buy(2, "买单");

	private int code;
	private String desc;

	public static Map<Integer, OrderType> typeMap;
	static {
		typeMap = new HashMap<Integer, OrderType>();
		for (OrderType period : OrderType.values()) {
			typeMap.put(period.getCode(), period);
		}
	}

	OrderType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
